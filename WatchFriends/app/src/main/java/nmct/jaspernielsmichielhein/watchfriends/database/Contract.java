package nmct.jaspernielsmichielhein.watchfriends.database;

import android.provider.BaseColumns;

public class Contract {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "database.db";
    private static final String TEXT_TYPE = " TEXT";

    //interface uitbreiden
    public interface FavoritesColumns extends BaseColumns {
        public static final String TABLE_NAME = "favorites";
        public static final String COLUMN_FAVORITE_NR = "favoritenr";
        public static final String COLUMN_FAVORITE_NAME = "favoritename";
    }

    public static abstract class FavoritesDB implements FavoritesColumns {
        public static final String CREATE_TABLE = "create table "
                + TABLE_NAME + "(" + _ID
                + " integer primary key autoincrement, "
                + COLUMN_FAVORITE_NR + " integer, "
                + COLUMN_FAVORITE_NAME + " text not null"
                + ")";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public interface FollowedSeriesColumns extends BaseColumns {
        public static final String TABLE_NAME = "followingseries";
        public static final String COLUMN_FOLLOWEDSERIES_NR = "followingseriesnr";
        public static final String COLUMN_FOLLOWEDSERIES_NAME = "followingseriesname";
    }

    public static abstract class FollowedSeriesDB implements FollowedSeriesColumns {
        public static final String CREATE_TABLE = "create table "
                + TABLE_NAME + "(" + _ID
                + " integer primary key autoincrement, "
                + COLUMN_FOLLOWEDSERIES_NR + " integer, "
                + COLUMN_FOLLOWEDSERIES_NAME + " text not null"
                + ")";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public interface WatchedColumns extends BaseColumns {
        public static final String TABLE_NAME = "watched";
        public static final String COLUMN_WATCHED_NR = "watchednr";
        public static final String COLUMN_WATCHED_NAME = "watchedname";
    }

    public static abstract class WatchedDB implements WatchedColumns {
        public static final String CREATE_TABLE = "create table "
                + TABLE_NAME + "(" + _ID
                + " integer primary key autoincrement, "
                + COLUMN_WATCHED_NR + " integer, "
                + COLUMN_WATCHED_NAME + " text not null"
                + ")";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}