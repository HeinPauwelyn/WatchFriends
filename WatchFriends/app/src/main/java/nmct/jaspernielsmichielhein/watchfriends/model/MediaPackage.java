package nmct.jaspernielsmichielhein.watchfriends.model;

import android.databinding.ObservableArrayList;

public class MediaPackage {

    private ObservableArrayList<MediaItem> backdrops = new ObservableArrayList<>();
    private ObservableArrayList<MediaItem> posters = new ObservableArrayList<>();
    private int id;

    public ObservableArrayList<MediaItem> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(ObservableArrayList<MediaItem> backdrops) {
        this.backdrops = backdrops;
    }

    public ObservableArrayList<MediaItem> getPosters() {
        return posters;
    }

    public void setPosters(ObservableArrayList<MediaItem> posters) {
        this.posters = posters;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
