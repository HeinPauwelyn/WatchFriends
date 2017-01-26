package nmct.jaspernielsmichielhein.watchfriends.database;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;

public class FollowedSeriesLoader extends AsyncTaskLoader<Cursor> {

    private Cursor mData;
    private Context mContext;

    public FollowedSeriesLoader(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onStartLoading() {
        if (mData != null) {
            deliverResult(mData);
        }


        if (takeContentChanged() || mData == null) {
            forceLoad();
        }
    }

    @Override
    public Cursor loadInBackground() {
        String[] columns = new String[]{
                Contract.FollowedSeriesColumns._ID,
                Contract.FollowedSeriesColumns.COLUMN_FOLLOWEDSERIES_NR
        };

        mData = mContext.getContentResolver().query(nmct.jaspernielsmichielhein.watchfriends.provider.Contract.FOLLOWEDSERIES_URI, columns, null, null, null);

        mData.getCount();

        return mData;
    }

    @Override
    public void deliverResult(Cursor cursor) {
        if (isReset()) {
            if (cursor != null) {
                cursor.close();
            }
            return;
        }

        Cursor oldData = mData;
        mData = cursor;

        if (isStarted()) {
            super.deliverResult(cursor);
        }

        if (oldData != null && oldData != cursor && !oldData.isClosed()) {
            oldData.close();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void onCanceled(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }
}
