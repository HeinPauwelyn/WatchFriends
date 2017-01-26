package nmct.jaspernielsmichielhein.watchfriends.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.HashMap;

import nmct.jaspernielsmichielhein.watchfriends.database.DatabaseHelper;

public class WatchedEpisodesProvider extends ContentProvider {

    private DatabaseHelper databaseHelper;

    private static final int WATCHED = 1;
    private static final int WATCHED_ID = 2;

    private static HashMap<String, String> WATCHED_PROJECTION_MAP;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Contract.AUTHORITY_WATCHED, "watched", WATCHED);
        uriMatcher.addURI(Contract.AUTHORITY_WATCHED, "watched/#", WATCHED_ID);
    }

    @Override
    public boolean onCreate() {
        databaseHelper = DatabaseHelper.getInstance(getContext());
        WATCHED_PROJECTION_MAP = new HashMap<>();
        WATCHED_PROJECTION_MAP.put(nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns._ID, nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns._ID);
        WATCHED_PROJECTION_MAP.put(nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SERIES_NR, nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SERIES_NR);
        WATCHED_PROJECTION_MAP.put(nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SEASON_NR, nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_SEASON_NR);
        WATCHED_PROJECTION_MAP.put(nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_EPISODE_NR, nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_EPISODE_NR);
        WATCHED_PROJECTION_MAP.put(nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_EPISODE_WATCHED, nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns.COLUMN_WATCHED_EPISODE_WATCHED);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (uriMatcher.match(uri)) {
            case WATCHED:
                queryBuilder.setTables(nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeDB.TABLE_NAME);
                queryBuilder.setProjectionMap(WATCHED_PROJECTION_MAP);
                break;
            case WATCHED_ID:
                queryBuilder.setTables(nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeDB.TABLE_NAME);
                queryBuilder.setProjectionMap(WATCHED_PROJECTION_MAP);

                String watchedid = uri.getPathSegments().get(Contract.WATCHED_ID_PATH_POSITION);
                DatabaseUtils.concatenateWhere(selection, "( " + nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeColumns._ID + " = ?" + ")");
                selectionArgs = DatabaseUtils.appendSelectionArgs(selectionArgs, new String[]{"" + watchedid});
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor data = queryBuilder.query(db, projection, selection, selectionArgs, null, null, null);
        data.getCount();
        data.setNotificationUri(getContext().getContentResolver(), uri);
        return data;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case WATCHED:
                return Contract.WATCHED_CONTENT_TYPE;
            case WATCHED_ID:
                return Contract.WATCHED_ITEM_CONTENT_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case WATCHED:
                long newRowId = db.insert(
                        nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeDB.TABLE_NAME, null, values);
                if (newRowId > 0) {
                    Uri productItemUri = ContentUris.withAppendedId(Contract.WATCHED_ITEM_URI, newRowId);
                    getContext().getContentResolver().notifyChange(productItemUri, null);
                    return productItemUri;
                }

                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String finalWhere;
        int count;
        switch (uriMatcher.match(uri)) {
            case WATCHED:
                count = db.delete(
                        nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeDB.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            case WATCHED_ID:
                String watchedItemId = uri.getPathSegments().get(1);
                finalWhere = "Id = " + watchedItemId;

                if (selection != null) {
                    finalWhere = DatabaseUtils.concatenateWhere(finalWhere, selection);
                }

                count = db.delete(
                        nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeDB.TABLE_NAME,
                        finalWhere,
                        selectionArgs
                );
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int count;
        String finalWhere;

        switch (uriMatcher.match(uri)) {
            case WATCHED:
                count = db.update(
                        nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeDB.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs
                );
                break;

            case WATCHED_ID:
                String productId = uri.getPathSegments().get(1);
                finalWhere = "Id = " + productId;

                if (selection != null) {
                    finalWhere = DatabaseUtils.concatenateWhere(finalWhere, selection);
                }

                count = db.update(
                        nmct.jaspernielsmichielhein.watchfriends.database.Contract.WatchedEpisodeDB.TABLE_NAME,
                        values,
                        finalWhere,
                        selectionArgs
                );
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }
}
