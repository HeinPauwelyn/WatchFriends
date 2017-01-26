package nmct.jaspernielsmichielhein.watchfriends.database;

import android.provider.BaseColumns;

public class Contract {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "watchfriends.db";

    public interface FollowedSeriesColumns extends BaseColumns {
        public static final String TABLE_NAME = "followingseries";
        public static final String COLUMN_FOLLOWEDSERIES_NR = "followingseriesnr";
        public static final String COLUMN_FOLLOWEDSERIES_FOLLOWING = "following";
    }

    public static abstract class FollowedSeriesDB implements FollowedSeriesColumns {
        public static final String CREATE_TABLE = "create table "
                + TABLE_NAME + "(" + _ID
                + " integer primary key autoincrement, "
                + COLUMN_FOLLOWEDSERIES_NR + " integer, "
                + COLUMN_FOLLOWEDSERIES_FOLLOWING + " integer "
                + ")";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public interface WatchedEpisodeColumns extends BaseColumns {
        public static final String TABLE_NAME = "watched";
        public static final String COLUMN_WATCHED_SERIES_NR = "watchedseriesnr";
        public static final String COLUMN_WATCHED_SEASON_NR = "watchedseasonnr";
        public static final String COLUMN_WATCHED_EPISODE_NR = "watchedepisodenr";
        public static final String COLUMN_WATCHED_EPISODE_WATCHED = "watched";
    }

    public static abstract class WatchedEpisodeDB implements WatchedEpisodeColumns {
        public static final String CREATE_TABLE = "create table "
                + TABLE_NAME + "(" + _ID
                + " integer primary key autoincrement, "
                + COLUMN_WATCHED_SERIES_NR + " integer, "
                + COLUMN_WATCHED_SEASON_NR + " integer, "
                + COLUMN_WATCHED_EPISODE_NR + " integer, "
                + COLUMN_WATCHED_EPISODE_WATCHED + " integer "
                + ")";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}