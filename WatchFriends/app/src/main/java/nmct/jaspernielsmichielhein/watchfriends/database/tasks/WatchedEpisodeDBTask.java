package nmct.jaspernielsmichielhein.watchfriends.database.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import nmct.jaspernielsmichielhein.watchfriends.database.DatabaseHelper;
import nmct.jaspernielsmichielhein.watchfriends.provider.Contract;

public class WatchedEpisodeDBTask extends AsyncTask<ContentValues, Void, Void> {

    private Context mContext;

    public WatchedEpisodeDBTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(ContentValues... params) {
        updateIfExistsElseInsert(params[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    private void updateIfExistsElseInsert(ContentValues values) {
        SQLiteDatabase db = DatabaseHelper.getInstance(mContext).getWritableDatabase();
        int rows = db.update(
                nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeDB.TABLE_NAME,
                values,
                nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SERIES_NR + " = " + values.getAsString(nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SERIES_NR) + " AND "
                        + nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SEASON_NR + " = " + values.getAsString(nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SEASON_NR) + " AND "
                        + nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_EPISODE_NR + " = " + values.getAsString(nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_EPISODE_NR),
                null);
        if (rows == 0) {
            mContext.getContentResolver().insert(Contract.WATCHED_URI, values);
        }
    }
}
