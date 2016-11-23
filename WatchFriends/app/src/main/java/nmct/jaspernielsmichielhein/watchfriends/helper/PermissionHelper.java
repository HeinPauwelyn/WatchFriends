package nmct.jaspernielsmichielhein.watchfriends.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.ViewGroup;

public class PermissionHelper {

    public static boolean check(final Context context, final String permission, String message, final int requestCode) {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }

        if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {
                Snackbar.make((ViewGroup) ((ViewGroup) ((Activity) context).findViewById(android.R.id.content)).getChildAt(0), message, Snackbar.LENGTH_INDEFINITE)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                askPermission(context, permission, requestCode);
                            }
                        })
                        .show();
                return false;
            } else {
                askPermission(context, permission, requestCode);
                return false;
            }
        } else {
            return true;
        }
    }

    private static void askPermission(Context context, String permission, int requestCode) {
        ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, requestCode);
    }

}