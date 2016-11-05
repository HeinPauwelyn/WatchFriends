package nmct.jaspernielsmichielhein.watchfriends.helper;

import nmct.jaspernielsmichielhein.watchfriends.model.Episode;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;

public class Interfaces {
    public interface onSeriesSelectedListener {
        void onSeriesSelected(Series series);
    }

    public interface onEpisodeSelectedListener {
        void onEpisodeSelected(Episode episode);
    }
}