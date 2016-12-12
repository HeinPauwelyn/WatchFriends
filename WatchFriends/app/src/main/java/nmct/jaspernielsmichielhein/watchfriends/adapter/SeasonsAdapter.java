package nmct.jaspernielsmichielhein.watchfriends.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.database.tasks.DeleteWatchedEpisodeFromDBTask;
import nmct.jaspernielsmichielhein.watchfriends.database.tasks.SaveWatchedEpisodeToDBTask;
import nmct.jaspernielsmichielhein.watchfriends.databinding.RowSeasonBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.Episode;
import nmct.jaspernielsmichielhein.watchfriends.model.Season;
import nmct.jaspernielsmichielhein.watchfriends.provider.Contract;

public class SeasonsAdapter extends ArrayAdapter<Season> implements View.OnClickListener {
    private Interfaces.onSeasonSelectedListener mListener;

    private Context context;

    private int mSeriesID;

    public SeasonsAdapter(Context context, ListView listView, final String seriesName, final int seriesId) {
        super(context, R.layout.row_episode);
        this.context = context;
        mListener = (Interfaces.onSeasonSelectedListener) context;
        mSeriesID = seriesId;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Season selectedSeason = getItem(position);
                if (selectedSeason != null) {
                    mListener.onSeasonSelected(seriesName, seriesId, selectedSeason.getSeason_number());
                }
            }
        });
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final RowSeasonBinding rowSeasonBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_season, parent, false);
        Season season = getItem(position);
        rowSeasonBinding.setSeason(season);

        boolean watched = isSeasonWatched(season);

        ImageButton imgViewed = (ImageButton) rowSeasonBinding.getRoot().findViewById(R.id.imgViewed);
        imgViewed.setTag(position);

        if (watched) {
            imgViewed.setImageResource(R.drawable.ic_visibility_off_white_24dp);
        } else {
            imgViewed.setImageResource(R.drawable.ic_visibility_white_24dp);
        }
        imgViewed.setOnClickListener(this);
        return rowSeasonBinding.getRoot();
    }

    @Override
    public void onClick(final View v) {
        final ImageButton imgViewed = (ImageButton) v;
        int position = (int) imgViewed.getTag();
        final Season s = getItem(position);

        boolean watched = isSeasonWatched(s);

        if (watched) {
            new AlertDialog.Builder(this.getContext())
                    .setTitle("Mark complete season?")
                    .setMessage("Do you want to mark all episodes in this season as NOT watched?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Snackbar.make(v, "Marked season as not watched", Snackbar.LENGTH_LONG).show();
                            unWatchSeason(s);
                            imgViewed.setImageResource(R.drawable.ic_visibility_white_24dp);
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        } else {
            new AlertDialog.Builder(this.getContext())
                    .setTitle("Mark complete season?")
                    .setMessage("Do you want to mark all episodes in this season as watched?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Snackbar.make(v, "Marked season as watched", Snackbar.LENGTH_LONG).show();
                            watchSeason(s);
                            imgViewed.setImageResource(R.drawable.ic_visibility_off_white_24dp);
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        }
    }

    //todo way to get episodes from season
    //todo (un)watch complete season in backend
    private void watchSeason(Season s) {
        for(int i = 0; i < s.getEpisode_count(); i++){
            addToDb(mSeriesID, s.getSeason_number(), i);
        }
    }

    private void unWatchSeason(Season s){
        for(int i = 0; i < s.getEpisode_count(); i++){
            removeFromDb(mSeriesID, s.getSeason_number(), i);
        }
    }

    private void removeFromDb(int seriesId, int seasonNr, int episodeNr) {
        ContentValues values = new ContentValues();
        values.put(nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SERIES_NR, seriesId);
        values.put(nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SEASON_NR, seasonNr);
        values.put(nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_EPISODE_NR, episodeNr);

        executeAsyncTask(new DeleteWatchedEpisodeFromDBTask(context), values);
    }

    private void addToDb(int seriesId, int seasonNr, int episodeNr) {
        ContentValues values = new ContentValues();
        values.put(nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SERIES_NR, seriesId);
        values.put(nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SEASON_NR, seasonNr);
        values.put(nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_EPISODE_NR, episodeNr);
        values.put(nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_EPISODE_NAME, "");

        executeAsyncTask(new SaveWatchedEpisodeToDBTask(context), values);
    }

    static private <T> void executeAsyncTask(AsyncTask<T, ?, ?> task, T... params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            task.execute(params);
        }
    }

    private boolean isSeasonWatched(Season s) {
        Cursor c = context.getContentResolver().query(
                Contract.WATCHED_URI,
                null,
                nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SERIES_NR + " = " + mSeriesID + " AND " +
                        nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SEASON_NR + " = " + s.getSeason_number(),
                null,
                null
        );
        return s.getEpisode_count() <= c.getCount();
    }
}