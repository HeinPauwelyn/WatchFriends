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
import nmct.jaspernielsmichielhein.watchfriends.database.tasks.WatchedEpisodeDBTask;
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

        watched = isWatched(episode);
    }

    public void loadEpisode() {
        fragmentEpisodeBinding.setViewmodel(this);
        notifyPropertyChanged(BR.viewmodel);
        setHeader();
    }

    private void setHeader() {
        listener.setTitle(episode.getName());
        listener.enableAppBarScroll(true);
        Picasso.with(context).load(episode.getImage_uri()).into(listener.getHeaderImage());
        initFloatingActionButton(listener.getActionButton());
    }

    private void initFloatingActionButton(final FloatingActionButton fab) {
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
                    editWatched(false);
                } else {
                    fab.setImageResource(R.drawable.ic_visibility_off_white_24dp);
                    fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.themeBlack)));
                    Snackbar.make(v, "Marked episode as watched", Snackbar.LENGTH_LONG).show();

                    editWatched(true);
                }
            }
        });
    }

    private void editWatched(boolean watched) {
        ContentValues values = new ContentValues();
        values.put(Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SERIES_NR, episode.getId());
        values.put(Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SEASON_NR, episode.getSeason_number());
        values.put(Contract.WatchedEpisodeColumns.COLUMN_WATCHED_EPISODE_NR, episode.getEpisode_number());
        values.put(Contract.WatchedEpisodeColumns.COLUMN_WATCHED_EPISODE_WATCHED, watched);

        executeAsyncTask(new WatchedEpisodeDBTask(context), values);
    }

    private boolean isWatched(Episode e) {
        Cursor c = context.getContentResolver().query(
                nmct.jaspernielsmichielhein.watchfriends.provider.Contract.WATCHED_URI,
                new String[]{Contract.WatchedEpisodeColumns.COLUMN_WATCHED_EPISODE_WATCHED},
                Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SERIES_NR + " = " + e.getId() + " AND " +
                        Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SEASON_NR + " = " + e.getSeason_number() + " AND " +
                        Contract.WatchedEpisodeColumns.COLUMN_WATCHED_EPISODE_NR + " = " + e.getEpisode_number(),
                null,
                null);
        if (c.getCount() > 0) {
            int watched = 0;
            if (c.moveToLast())
                watched = c.getInt(c.getColumnIndex(Contract.WatchedEpisodeColumns.COLUMN_WATCHED_EPISODE_WATCHED));
            c.close();
            return watched > 0;
        } else {
            c.close();
            return false;
        }
    }

    static private <T> void executeAsyncTask(AsyncTask<T, ?, ?> task, T... params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            task.execute(params);
        }
    }
}