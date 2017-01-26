package nmct.jaspernielsmichielhein.watchfriends.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import nmct.jaspernielsmichielhein.watchfriends.helper.ApiHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiWatchFriendsHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.AuthHelper;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import nmct.jaspernielsmichielhein.watchfriends.model.UserData;
import nmct.jaspernielsmichielhein.watchfriends.provider.Contract;
import rx.functions.Action1;

public class SyncAdapter extends AbstractThreadedSyncAdapter {

    private ContentResolver contentResolver;
    private SyncResult syncResult;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        this.contentResolver = context.getContentResolver();
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        this.contentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient provider, SyncResult syncResult) {
        try {
            this.syncResult = syncResult;
            syncWatched(account);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void syncWatched(Account account) {
        Log.d("SyncAdapter", "Syncing");
        try {
            final List<Series> followingSeries = new ArrayList<>();

            ApiHelper.subscribe(ApiWatchFriendsHelper.getWatchFriendsServiceInstance().getUser(null, AuthHelper.getAuthToken(this.getContext())), new Action1<UserData>() {
                @Override
                public void call(UserData userData) {
                    if (userData != null) {
                        followingSeries.addAll(userData.getWatchlist());
                        updateFollowingSeries(followingSeries);
                    }
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
            syncResult.stats.numIoExceptions++;
            throw ex;
        }
    }

    private void updateFollowingSeries(List<Series> followingSeries) {
        ArrayList<ContentProviderOperation> operationList = new ArrayList<>();

        //lokale followings verwijderen
        ContentProviderOperation delete = ContentProviderOperation.newDelete(Contract.FOLLOWEDSERIES_URI).build();
        operationList.add(delete);
        syncResult.stats.numDeletes++;

        //alle followings lokaal toevoegen
        for (Series s : followingSeries) {
            ContentValues values = seriesToContentValuesList(s);
            ContentProviderOperation insert = ContentProviderOperation.newInsert(Contract.FOLLOWEDSERIES_URI).withValues(values).build();
            operationList.add(insert);
            syncResult.stats.numInserts++;
        }

        if (operationList.size() > 0) {
            try {
                contentResolver.applyBatch(Contract.AUTHORITY, operationList);
                contentResolver.notifyChange(Contract.FOLLOWEDSERIES_URI, null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private ContentValues seriesToContentValuesList(Series s) {
        String[] columns = new String[]{
                nmct.jaspernielsmichielhein.watchfriends.database.Contract.FollowedSeriesColumns.COLUMN_FOLLOWEDSERIES_NR,
                nmct.jaspernielsmichielhein.watchfriends.database.Contract.FollowedSeriesColumns.COLUMN_FOLLOWEDSERIES_FOLLOWING
        };
        Object[] columnValues = new Object[]{s.getId(), s.getFollowing()};

        ContentValues values = new ContentValues();
        int counter = 0;
        for (String column : columns) {
            Type type = columnValues[counter].getClass();

            if (type == String.class) {
                values.put(column, (String) columnValues[counter]);
            } else if (type == Integer.class) {
                values.put(column, (Integer) columnValues[counter]);
            } else if (type == Boolean.class) {
                values.put(column, (Boolean) columnValues[counter]);
            } else if (type == Double.class) {
                values.put(column, (Double) columnValues[counter]);
            }

            counter++;
        }
        return values;
    }
}
