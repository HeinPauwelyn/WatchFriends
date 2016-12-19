package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.squareup.picasso.Picasso;

import nmct.jaspernielsmichielhein.watchfriends.BR;
import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.database.Contract;
import nmct.jaspernielsmichielhein.watchfriends.database.tasks.DeleteWatchedEpisodeFromDBTask;
import nmct.jaspernielsmichielhein.watchfriends.database.tasks.SaveWatchedEpisodeToDBTask;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentEpisodeBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.Episode;

public class EpisodeFragmentViewModel extends BaseObservable {
    private Interfaces.headerChangedListener listener;

    private Context context;
    private FragmentEpisodeBinding fragmentEpisodeBinding;
    private Episode episode = null;

    @Bindable
    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    private boolean watched = false;

    public EpisodeFragmentViewModel(Context context, FragmentEpisodeBinding fragmentEpisodeBinding, Episode episode) {
        this.context = context;
        this.fragmentEpisodeBinding = fragmentEpisodeBinding;
        this.episode = episode;
        if (context instanceof Interfaces.headerChangedListener) {
            listener = (Interfaces.headerChangedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement headerChangedListener");
        }
    }

    public void loadEpisode() {
        fragmentEpisodeBinding.setViewmodel(this);
        notifyPropertyChanged(BR.viewmodel);
        setHeader();
    }

    private void setHeader() {
        listener.expandToolbar();
        listener.setTitle(episode.getShortcode());
        Picasso.with(context).load(episode.getImage_uri()).into(listener.getHeaderImage());
        final FloatingActionButton fab = listener.getActionButton();
        initFloatingActionButton(fab);
    }

    private void initFloatingActionButton(final FloatingActionButton fab) {
        watched = isWatched(episode);
        if (watched) {
            fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.themeBlack)));
            fab.setImageResource(R.drawable.ic_visibility_off_white_24dp);
        } else {
            fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.themeAccent)));
            fab.setImageResource(R.drawable.ic_visibility_white_24dp);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watched = isWatched(episode);
                if (watched) {
                    fab.setImageResource(R.drawable.ic_visibility_white_24dp);
                    fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.themeAccent)));
                    Snackbar.make(v, "Marked episode as not watched", Snackbar.LENGTH_LONG).show();

                    unWatch();
                } else {
                    fab.setImageResource(R.drawable.ic_visibility_off_white_24dp);
                    fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.themeBlack)));
                    Snackbar.make(v, "Marked episode as watched", Snackbar.LENGTH_LONG).show();

                    watch();
                }
            }
        });
    }

    //todo backend should provide if user watched episode or not, now it just happens synchronously
    private boolean isWatched(Episode e) {
        Cursor c = context.getContentResolver().query(
                nmct.jaspernielsmichielhein.watchfriends.provider.Contract.WATCHED_URI, null,
                Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SERIES_NR + " = " + e.getId() + " AND " +
                        Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SEASON_NR + " = " + e.getSeason_number() + " AND " +
                        Contract.WatchedEpisodeColumns.COLUMN_WATCHED_EPISODE_NR + " = " + e.getEpisode_number(),
                null,
                null);
        c.close();
        return c.getCount() > 0;
    }

    //todo duplicate code across files, centralise code
    private void watch() {
        ContentValues values = new ContentValues();
        values.put(Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SERIES_NR, episode.getId());
        values.put(Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SEASON_NR, episode.getSeason_number());
        values.put(Contract.WatchedEpisodeColumns.COLUMN_WATCHED_EPISODE_NR, episode.getEpisode_number());
        values.put(Contract.WatchedEpisodeColumns.COLUMN_WATCHED_EPISODE_NAME, episode.getName());

        executeAsyncTask(new SaveWatchedEpisodeToDBTask(context), values);
    }

    private void unWatch() {
        ContentValues values = new ContentValues();
        values.put(Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SERIES_NR, episode.getId());
        values.put(Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SEASON_NR, episode.getSeason_number());
        values.put(Contract.WatchedEpisodeColumns.COLUMN_WATCHED_EPISODE_NR, episode.getEpisode_number());

        executeAsyncTask(new DeleteWatchedEpisodeFromDBTask(context), values);
    }

    static private <T> void executeAsyncTask(AsyncTask<T, ?, ?> task, T... params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            task.execute(params);
        }
    }
}