package nmct.jaspernielsmichielhein.watchfriends.model;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.os.Parcel;
import android.os.Parcelable;

public class Season extends BaseObservable implements Parcelable {
    private String _id = "";
    private String air_date = "";
    private ObservableArrayList<Episode> episodes = new ObservableArrayList<Episode>();
    private int episode_count = 0;
    private String overview = "";
    private String name = "";
    private int id = 0;
    private String poster_path = "";
    private int season_number = 0;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public ObservableArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(ObservableArrayList<Episode> episodes) {
        this.episodes = episodes;
    }

    public int getEpisode_count() {
        return episode_count;
    }

    public void setEpisode_count(int episode_count) {
        this.episode_count = episode_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public int getSeason_number() {
        return season_number;
    }

    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }

    // PARCELABLE
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.get_id());
        dest.writeString(this.getAir_date());
        dest.writeInt(this.getEpisode_count());
        dest.writeInt(this.getId());
        dest.writeString(this.getOverview());
        dest.writeString(this.getName());
        dest.writeString(this.getPoster_path());
        dest.writeInt(this.getSeason_number());
    }

    public static final Creator<Season> CREATOR = new Creator<Season>() {
        @Override
        public Season createFromParcel(Parcel in) {
            return new Season(in);
        }

        @Override
        public Season[] newArray(int size) {
            return new Season[size];
        }
    };

    protected Season(Parcel in) {
        set_id(in.readString());
        setAir_date(in.readString());
        setEpisode_count(in.readInt());
        setId(in.readInt());
        setOverview(in.readString());
        setName(in.readString());
        setPoster_path(in.readString());
        setSeason_number(in.readInt());
    }
}