package nmct.jaspernielsmichielhein.watchfriends.database;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import nmct.jaspernielsmichielhein.watchfriends.provider.*;
import nmct.jaspernielsmichielhein.watchfriends.provider.Contract;

public class SaveFavoriteToDBTask extends AsyncTask<ContentValues, Void, Void> {

    private Context mContext;
    public SaveFavoriteToDBTask(Context context) {mContext = context; }

    @Override
    protected Void doInBackground(ContentValues... params) {
        Uri newUri = mContext.getContentResolver().insert(Contract.FAVORITES_URI, params[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
