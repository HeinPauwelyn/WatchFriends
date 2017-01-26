package nmct.jaspernielsmichielhein.watchfriends.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper INSTANCE;
    private static Object object = new Object();

    public static DatabaseHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (object) {
                INSTANCE = new DatabaseHelper(context.getApplicationContext());
            }
        }
        return INSTANCE;
    }

    private DatabaseHelper(Context context) {
        super(context, Contract.DATABASE_NAME, null, Contract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        onUpgrade(db, 1, 2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        while (oldVersion < newVersion) {
            switch (oldVersion) {
                case 0:
                    upgradeTo1(db);
                    oldVersion++;
                    break;
                case 1:
                    upgradeTo2(db);
                    oldVersion++;
                    break;
                default:
                    throw new IllegalStateException(
                            "onUpgrade() with unknown oldVersion " + oldVersion);
            }
        }
    }

    private void upgradeTo1(SQLiteDatabase db) {
        db.execSQL(Contract.FollowedSeriesDB.CREATE_TABLE);
        db.execSQL(Contract.WatchedEpisodeDB.CREATE_TABLE);
    }

    private void upgradeTo2(SQLiteDatabase db) {
        db.execSQL("ALTER TABLE " + Contract.FollowedSeriesDB.TABLE_NAME + " ADD COLUMN " + Contract.FollowedSeriesDB.COLUMN_FOLLOWEDSERIES_FOLLOWING + " INTEGER DEFAULT 1");
        db.execSQL("ALTER TABLE " + Contract.WatchedEpisodeDB.TABLE_NAME + " ADD COLUMN " + Contract.WatchedEpisodeDB.COLUMN_WATCHED_EPISODE_WATCHED + " INTEGER DEFAULT 1");
    }
}
