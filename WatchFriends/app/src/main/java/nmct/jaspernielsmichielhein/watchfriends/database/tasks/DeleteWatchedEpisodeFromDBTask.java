package nmct.jaspernielsmichielhein.watchfriends.database.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import nmct.jaspernielsmichielhein.watchfriends.provider.Contract;

public class DeleteWatchedEpisodeFromDBTask extends AsyncTask<ContentValues, Void, Void> {

    private Context mContext;

    public DeleteWatchedEpisodeFromDBTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(ContentValues... params) {
        String[] args = new String[]{
                String.valueOf(params[0].get(nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SERIES_NR)),
                String.valueOf(params[0].get(nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SEASON_NR)),
                String.valueOf(params[0].get(nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_EPISODE_NR))
        };
        mContext.getContentResolver().delete(
                Contract.WATCHED_URI,
                nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SERIES_NR + "=? AND "
                + nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SEASON_NR + "=? AND "
                + nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_EPISODE_NR + "=?",
                args
        );
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}