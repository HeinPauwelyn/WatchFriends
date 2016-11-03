package nmct.jaspernielsmichielhein.watchfriends.helper;

import nmct.jaspernielsmichielhein.watchfriends.model.Episode;

public class SeriesHelper {
    public static String getShortcode(int seasonNumber, int episodeNumber) {
        return "S" + getNumber(seasonNumber) + "E" + getNumber(episodeNumber);
    }

    private static String getNumber(int number) {
        String result = String.valueOf(number);
        if (result.length() == 1) {
            return "0" + result;
        } else {
            return result;
        }
    }
}