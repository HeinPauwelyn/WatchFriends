package nmct.jaspernielsmichielhein.watchfriends;

import android.app.Application;

public class WatchfriendsApplication extends Application {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}