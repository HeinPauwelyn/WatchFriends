package nmct.jaspernielsmichielhein.watchfriends.model;

import android.databinding.ObservableArrayList;

public class SeriesListData {

    private ObservableArrayList<Integer> seriesLists;
    private ObservableArrayList<Integer> featured;

    public ObservableArrayList<Integer> getSeriesLists() {
        return seriesLists;
    }

    public void setSeriesLists(ObservableArrayList<Integer> seriesLists) {
        this.seriesLists = seriesLists;
    }

    public ObservableArrayList<Integer> getFeatured() {
        return featured;
    }

    public void setFeatured(ObservableArrayList<Integer> featured) {
        this.featured = featured;
    }
}
