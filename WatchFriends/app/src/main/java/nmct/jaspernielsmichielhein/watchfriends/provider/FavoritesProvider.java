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

public class FavoritesProvider extends ContentProvider {

    private DatabaseHelper databaseHelper;

    private static final int FAVORITES = 1;
    private static final int FAVORITES_ID = 2;

    private static HashMap<String, String> FAVORITES_PROJECTION_MAP;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Contract.AUTHORITY, "favorites", FAVORITES);
        uriMatcher.addURI(Contract.AUTHORITY, "favorites/#", FAVORITES_ID);
    }

    @Override
    public boolean onCreate() {
        databaseHelper = DatabaseHelper.getInstance(getContext());
        FAVORITES_PROJECTION_MAP = new HashMap<>();
        FAVORITES_PROJECTION_MAP.put(nmct.jaspernielsmichielhein.watchfriends.database.Contract.FavoritesColumns._ID, nmct.jaspernielsmichielhein.watchfriends.database.Contract.FavoritesColumns._ID);
        FAVORITES_PROJECTION_MAP.put(nmct.jaspernielsmichielhein.watchfriends.database.Contract.FavoritesColumns.COLUMN_FAVORITE_NR, nmct.jaspernielsmichielhein.watchfriends.database.Contract.FavoritesColumns.COLUMN_FAVORITE_NR);
        FAVORITES_PROJECTION_MAP.put(nmct.jaspernielsmichielhein.watchfriends.database.Contract.FavoritesColumns.COLUMN_FAVORITE_NAME, nmct.jaspernielsmichielhein.watchfriends.database.Contract.FavoritesColumns.COLUMN_FAVORITE_NAME);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (uriMatcher.match(uri)) {
            case FAVORITES:
                queryBuilder.setTables(nmct.jaspernielsmichielhein.watchfriends.database.Contract.FavoritesDB.TABLE_NAME);
                queryBuilder.setProjectionMap(FAVORITES_PROJECTION_MAP);
                break;
            case FAVORITES_ID:
                queryBuilder.setTables(nmct.jaspernielsmichielhein.watchfriends.database.Contract.FavoritesDB.TABLE_NAME);
                queryBuilder.setProjectionMap(FAVORITES_PROJECTION_MAP);

                String favoriteid = uri.getPathSegments().get(Contract.FAVORITES_ID_PATH_POSITION);
                DatabaseUtils.concatenateWhere(selection, "( " + nmct.jaspernielsmichielhein.watchfriends.database.Contract.FavoritesColumns._ID + " = ?" + ")");
                selectionArgs = DatabaseUtils.appendSelectionArgs(selectionArgs, new String[]{"" + favoriteid});
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
            case FAVORITES:
                return Contract.FAVORITES_CONTENT_TYPE;
            case FAVORITES_ID:
                return Contract.FAVORITES_ITEM_CONTENT_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case FAVORITES:
                long newRowId = db.insert(
                        nmct.jaspernielsmichielhein.watchfriends.database.Contract.FavoritesDB.TABLE_NAME, null, values);
                if (newRowId > 0) {
                    Uri productItemUri = ContentUris.withAppendedId(Contract.FAVORITES_ITEM_URI, newRowId);
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
            case FAVORITES:
                count = db.delete(
                        nmct.jaspernielsmichielhein.watchfriends.database.Contract.FavoritesDB.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            case FAVORITES_ID:
                String favoritesItemId = uri.getPathSegments().get(1);
                finalWhere = "Id = " + favoritesItemId;

                if (selection != null) {
                    finalWhere = DatabaseUtils.concatenateWhere(finalWhere, selection);
                }

                count = db.delete(
                        nmct.jaspernielsmichielhein.watchfriends.database.Contract.FavoritesDB.TABLE_NAME,
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
            case FAVORITES:
                count = db.update(
                        nmct.jaspernielsmichielhein.watchfriends.database.Contract.FavoritesDB.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs
                );
                break;

            case FAVORITES_ID:
                String productId = uri.getPathSegments().get(1);
                finalWhere = "Id = " + productId;

                if (selection != null) {
                    finalWhere = DatabaseUtils.concatenateWhere(finalWhere, selection);
                }

                count = db.update(
                       nmct.jaspernielsmichielhein.watchfriends.database.Contract.FavoritesDB.TABLE_NAME,
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
