package nmct.jaspernielsmichielhein.watchfriends.provider;

import android.net.Uri;

public class Contract {
    public static final String AUTHORITY = "nmct.jaspernielsmichielhein.watchfriends.favorites";
    public static final String AUTHORITY_FOLLOWED = "nmct.jaspernielsmichielhein.watchfriends.followedseries";

    //CONTENT-URIS
    public static final Uri FAVORITES_URI = Uri.parse("content://" + AUTHORITY + "/favorites");
    public static final Uri FAVORITES_ITEM_URI = Uri.parse("content://" + AUTHORITY + "/favorites/");

    public static final Uri FOLLOWEDSERIES_URI = Uri.parse("content://" + AUTHORITY_FOLLOWED + "/followedseries");
    public static final Uri FOLLOWEDSERIES_ITEM_URI = Uri.parse("content://" + AUTHORITY_FOLLOWED + "/followedseries/");

    public static final Uri WATCHED_URI = Uri.parse("content://" + AUTHORITY + "/watched");
    public static final Uri WATCHED_ITEM_URI = Uri.parse("content://" + AUTHORITY + "/watched/");

    //MIME-TYPES
    public static final String FAVORITES_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.nmct.watchfriends.favorite";
    public static final String FAVORITES_ITEM_CONTENT_TYPE = "vnd.android.cursor.item/vnd.nmct.watchfriends.favorite";

    public static final String FOLLOWEDSERIES_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.nmct.watchfriends.followedseries";
    public static final String FOLLOWEDSERIES_ITEM_CONTENT_TYPE = "vnd.android.cursor.item/vnd.nmct.watchfriends.followedseries";

    public static final String WATCHED_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.nmct.watchfriends.watched";
    public static final String WATCHED_ITEM_CONTENT_TYPE = "vnd.android.cursor.item/vnd.nmct.watchfriends.watched";

    public static final int FAVORITES_ID_PATH_POSITION = 1;
    public static final int FOLLOWEDSERIES_ID_PATH_POSITION = 1;
    public static final int WATCHED_ID_PATH_POSITION = 1;
}
