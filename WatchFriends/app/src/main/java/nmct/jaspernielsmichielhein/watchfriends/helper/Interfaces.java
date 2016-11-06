package nmct.jaspernielsmichielhein.watchfriends.helper;

import android.net.Uri;
import android.support.design.widget.FloatingActionButton;

import nmct.jaspernielsmichielhein.watchfriends.model.Episode;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;

public class Interfaces {
    public interface onHeaderChanged {
        void onSetTitle(String title);
        void onSetImage(Uri uri);
        FloatingActionButton onGetActionButton();
    }

    public interface onSeriesSelectedListener {
        void onSeriesSelected(Series series);
    }

    public interface onEpisodeSelectedListener {
        void onEpisodeSelected(Episode episode);
    }
}