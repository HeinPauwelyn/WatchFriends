package nmct.jaspernielsmichielhein.watchfriends.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.database.Contract;
import nmct.jaspernielsmichielhein.watchfriends.database.tasks.DeleteWatchedEpisodeFromDBTask;
import nmct.jaspernielsmichielhein.watchfriends.database.tasks.SaveWatchedEpisodeToDBTask;
import nmct.jaspernielsmichielhein.watchfriends.databinding.RowEpisodeBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.Episode;

public class EpisodesAdapter extends ArrayAdapter<Episode> implements View.OnClickListener {
    private Interfaces.onEpisodeSelectedListener mListener;

    private Context context;

    private boolean watched = false;

    public EpisodesAdapter(Context context, ListView listView) {
        super(context, R.layout.row_episode);
        this.context = context;
        mListener = (Interfaces.onEpisodeSelectedListener) context;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Episode selectedEpisode = getItem(position);
                if (selectedEpisode != null) {
                    mListener.onEpisodeSelected(selectedEpisode);
                }
            }
        });
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final RowEpisodeBinding rowEpisodeBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_episode, parent, false);
        Episode episode = getItem(position);
        rowEpisodeBinding.setEpisode(episode);

        watched = isWatched(episode);

        ImageButton imgViewed = (ImageButton) rowEpisodeBinding.getRoot().findViewById(R.id.imgViewed);
        imgViewed.setTag(position);
        if (watched) {
            imgViewed.setImageResource(R.drawable.ic_visibility_off_white_24dp);
        } else {
            imgViewed.setImageResource(R.drawable.ic_visibility_white_24dp);
        }
        imgViewed.setOnClickListener(this);
        return rowEpisodeBinding.getRoot();
    }

    @Override
    public void onClick(View v) {
        ImageButton imgViewed = (ImageButton) v;
        int position = (int) imgViewed.getTag();
        Episode e = getItem(position);

        if (watched) {
            imgViewed.setImageResource(R.drawable.ic_visibility_white_24dp);
            Snackbar.make(v, "Marked episode as not watched", Snackbar.LENGTH_LONG).show();

            unWatch(e);

        } else {
            imgViewed.setImageResource(R.drawable.ic_visibility_off_white_24dp);
            Snackbar.make(v, "Marked episode as watched", Snackbar.LENGTH_LONG).show();

            watch(e);
        }
        watched = !watched;
    }

    private void watch(Episode e) {
        ContentValues values = new ContentValues();
        values.put(Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SERIES_NR, e.getId());
        values.put(Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SEASON_NR, e.getSeason_number());
        values.put(Contract.WatchedEpisodeColumns.COLUMN_WATCHED_EPISODE_NR, e.getEpisode_number());
        values.put(Contract.WatchedEpisodeColumns.COLUMN_WATCHED_EPISODE_NAME, e.getName());

        executeAsyncTask(new SaveWatchedEpisodeToDBTask(context), values);
    }

    private void unWatch(Episode e) {
        ContentValues values = new ContentValues();
        values.put(Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SERIES_NR, e.getId());
        values.put(Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SEASON_NR, e.getSeason_number());
        values.put(Contract.WatchedEpisodeColumns.COLUMN_WATCHED_EPISODE_NR, e.getEpisode_number());

        executeAsyncTask(new DeleteWatchedEpisodeFromDBTask(context), values);
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

    static private <T> void executeAsyncTask(AsyncTask<T, ?, ?> task, T... params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            task.execute(params);
        }
    }
}