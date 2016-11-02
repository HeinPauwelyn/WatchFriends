package nmct.jaspernielsmichielhein.watchfriends.model;

import java.util.ArrayList;

/**
 * Created by hein_ on 02-Nov-16.
 */

public class Home {

    private ArrayList<Series> recommerdByFriends = new ArrayList<>();
    private ArrayList<Series> newSeries = new ArrayList<>();
    private ArrayList<Series> watchList = new ArrayList<>();
    private ArrayList<Series> featured = new ArrayList<>();

    public ArrayList<Series> getRecommerdByFriends() {
        return recommerdByFriends;
    }

    public void setRecommerdByFriends(ArrayList<Series> recommerdByFriends) {
        this.recommerdByFriends = recommerdByFriends;
    }

    public ArrayList<Series> getNewSeries() {
        return newSeries;
    }

    public void setNewSeries(ArrayList<Series> newSeries) {
        this.newSeries = newSeries;
    }

    public ArrayList<Series> getWatchList() {
        return watchList;
    }

    public void setWatchList(ArrayList<Series> watchList) {
        this.watchList = watchList;
    }

    public ArrayList<Series> getFeatured() {
        return featured;
    }

    public void setFeatured(ArrayList<Series> featured) {
        this.featured = featured;
    }
}
