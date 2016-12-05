package nmct.jaspernielsmichielhein.watchfriends.database;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import nmct.jaspernielsmichielhein.watchfriends.provider.Contract;

public class DeleteFollowedSerieFromDBTask extends AsyncTask<ContentValues, Void, Void> {

    private Context mContext;
    public DeleteFollowedSerieFromDBTask(Context context) {mContext = context; }

    @Override
    protected Void doInBackground(ContentValues... params) {
        mContext.getContentResolver().delete(
                Contract.FOLLOWEDSERIES_URI,
                nmct.jaspernielsmichielhein.watchfriends.database.Contract.FollowedSeriesColumns.COLUMN_FOLLOWEDSERIES_NR,
                null
        );
        //Uri newUri = mContext.getContentResolver().delete(Contract.FOLLOWEDSERIES_URI, nmct.jaspernielsmichielhein.watchfriends.database.Contract.FollowedSeriesColumns.COLUMN_FOLLOWEDSERIES_NR, params);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
