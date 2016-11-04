package nmct.jaspernielsmichielhein.watchfriends.helper;

public class Interfaces {
    public interface onSeriesSelectedListener<Series> {
        void onSeriesSelected(Series series);
    }

    public interface onEpisodeSelectedListener<Episode> {
        void onEpisodeSelected(Episode episode);
    }
}