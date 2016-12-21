package nmct.jaspernielsmichielhein.watchfriends.database.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import nmct.jaspernielsmichielhein.watchfriends.provider.Contract;

public class DeleteFollowedSerieFromDBTask extends AsyncTask<ContentValues, Void, Void> {

    private Context mContext;

    public DeleteFollowedSerieFromDBTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(ContentValues... params) {
        String[] args = new String[]{String.valueOf(params[0].get(nmct.jaspernielsmichielhein.watchfriends.database.Contract.FollowedSeriesColumns.COLUMN_FOLLOWEDSERIES_NR))};
        mContext.getContentResolver().delete(
                Contract.FOLLOWEDSERIES_URI,
                nmct.jaspernielsmichielhein.watchfriends.database.Contract.FollowedSeriesColumns.COLUMN_FOLLOWEDSERIES_NR + "=?",
                args
        );
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
