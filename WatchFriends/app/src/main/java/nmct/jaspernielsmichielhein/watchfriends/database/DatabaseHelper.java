package nmct.jaspernielsmichielhein.watchfriends.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper INSTANCE;
    private static Object object = new Object();

    public static DatabaseHelper getInstance(Context context) {
        if (INSTANCE == null) {
            //via keyword synchronized vermijden we dat meerdere threads Databasehelper-object proberen aan te maken
            synchronized (object) {
                INSTANCE = new DatabaseHelper(context.getApplicationContext());     //opm: hier pas object aanmaken!
            }
        }
        return INSTANCE;
    }

    private DatabaseHelper(Context context) {
        //opm: hier wordt database-versienummer doorgegeven
        super(context, Contract.DATABASE_NAME, null, Contract.DATABASE_VERSION);
    }


    //zie ook onderaan voor versie bestemd tijdens de ontwikkeling van app
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
                case 2:
                    //upgrade logic from version 2 to 3
                case 3:
                    //upgrade logic from version 3 to 4
                    break;
                default:
                    throw new IllegalStateException(
                            "onUpgrade() with unknown oldVersion " + oldVersion);
            }
        }
    }

    private void upgradeTo2(SQLiteDatabase db) {
        db.execSQL("ALTER TABLE " + Contract.FollowedSeriesDB.TABLE_NAME + " ADD COLUMN " + Contract.FollowedSeriesDB.COLUMN_FOLLOWEDSERIES_FOLLOWING + " INTEGER DEFAULT 1");
        db.execSQL("ALTER TABLE " + Contract.WatchedEpisodeDB.TABLE_NAME + " ADD COLUMN " + Contract.WatchedEpisodeDB.COLUMN_WATCHED_EPISODE_WATCHED + " INTEGER DEFAULT 1");
    }

    private void upgradeTo1(SQLiteDatabase db) {
        db.execSQL(Contract.FollowedSeriesDB.CREATE_TABLE);
        db.execSQL(Contract.WatchedEpisodeDB.CREATE_TABLE);
    }


/*    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Contract.ProductsDB.CREATE_TABLE);
    }

    //For development time schema upgrades where data loss is not an issue
    //remove your existing tables and call onCreate() to recreate the database.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL(Contract.ProductsDB.DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }*/

}
