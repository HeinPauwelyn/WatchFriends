package nmct.jaspernielsmichielhein.watchfriends.model;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.concurrent.Callable;

public class Series implements Parcelable {
    private String backdrop_path = "";
    private ObservableArrayList<nmct.jaspernielsmichielhein.watchfriends.model.Creator> created_by = new ObservableArrayList<nmct.jaspernielsmichielhein.watchfriends.model.Creator>();
    private int[] episode_run_time = new int[0];
    private String first_air_date = "";
    private ObservableArrayList<Genre> genres = new ObservableArrayList<Genre>();
    private String homepage = "";
    private int id = 0;
    private boolean in_production = false;
    private String[] languages = new String[0];
    private String last_air_date = "";
    private String name = "";
    private ObservableArrayList<Network> networks = new ObservableArrayList<Network>();
    private int number_of_episodes = 0;
    private int number_of_seasons = 0;
    private String[] origin_country = new String[0];
    private String original_language = "";
    private String original_name = "";
    private String overview = "";
    private double popularity = 0;
    private String poster_path = "";
    private ObservableArrayList<Company> production_companies = new ObservableArrayList<Company>();
    private ObservableArrayList<Season> seasons = new ObservableArrayList<Season>();
    private String status = "";
    private String type = "";
    private double vote_average = 0;
    private int vote_count = 0;

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public ObservableArrayList<nmct.jaspernielsmichielhein.watchfriends.model.Creator> getCreated_by() {
        return created_by;
    }

    public void setCreated_by(ObservableArrayList<nmct.jaspernielsmichielhein.watchfriends.model.Creator> created_by) {
        this.created_by = created_by;
    }

    public int[] getEpisode_run_time() {
        return episode_run_time;
    }

    public void setEpisode_run_time(int[] episode_run_time) {
        this.episode_run_time = episode_run_time;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public ObservableArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ObservableArrayList<Genre> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIn_production() {
        return in_production;
    }

    public void setIn_production(boolean in_production) {
        this.in_production = in_production;
    }

    public String[] getLanguages() {
        return languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public void setLast_air_date(String last_air_date) {
        this.last_air_date = last_air_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObservableArrayList<Network> getNetworks() {
        return networks;
    }

    public void setNetworks(ObservableArrayList<Network> networks) {
        this.networks = networks;
    }

    public int getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(int number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(int number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
    }

    public String[] getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(String[] origin_country) {
        this.origin_country = origin_country;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public ObservableArrayList<Company> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(ObservableArrayList<Company> production_companies) {
        this.production_companies = production_companies;
    }

    public ObservableArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ObservableArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    //PARCELABLE
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getBackdrop_path());
        dest.writeTypedList(this.getCreated_by());
        dest.writeIntArray(this.getEpisode_run_time());
        dest.writeString(this.getFirst_air_date());
        dest.writeTypedList(this.getGenres());
        dest.writeString(this.getHomepage());
        dest.writeInt(this.getId());
        dest.writeValue(this.isIn_production());
        dest.writeStringArray(this.getLanguages());
        dest.writeString(this.getLast_air_date());
        dest.writeString(this.getName());
        dest.writeTypedList(this.getNetworks());
        dest.writeInt(this.getNumber_of_episodes());
        dest.writeInt(this.getNumber_of_seasons());
        dest.writeStringArray(this.getOrigin_country());
        dest.writeString(this.getOriginal_language());
        dest.writeString(this.getOriginal_name());
        dest.writeString(this.getName());
        dest.writeString(this.getOverview());
        dest.writeDouble(this.getPopularity());
        dest.writeString(this.getPoster_path());
        dest.writeTypedList(this.getProduction_companies());
        dest.writeTypedList(this.getSeasons());
        dest.writeString(this.getStatus());
        dest.writeString(this.getType());
        dest.writeDouble(this.getVote_average());
        dest.writeInt(this.getVote_count());
    }

    public static final Creator<Series> CREATOR = new Creator<Series>() {
        @Override
        public Series createFromParcel(Parcel in) {
            return new Series(in);
        }

        @Override
        public Series[] newArray(int size) {
            return new Series[size];
        }
    };

    protected Series(Parcel in) {
        created_by = new ObservableArrayList<nmct.jaspernielsmichielhein.watchfriends.model.Creator>();
        genres = new ObservableArrayList<Genre>();
        networks = new ObservableArrayList<Network>();
        production_companies = new ObservableArrayList<Company>();
        seasons = new ObservableArrayList<Season>();

        setBackdrop_path(in.readString());
        in.readTypedList(created_by, nmct.jaspernielsmichielhein.watchfriends.model.Creator.CREATOR);
        setEpisode_run_time(in.createIntArray());
        setFirst_air_date(in.readString());
        in.readTypedList(genres, Genre.CREATOR);
        setHomepage(in.readString());
        setId(in.readInt());
        setIn_production(in.readByte() != 0);
        setLanguages(in.createStringArray());
        setLast_air_date(in.readString());
        setName(in.readString());
        in.readTypedList(networks, Network.CREATOR);
        setNumber_of_episodes(in.readInt());
        setNumber_of_seasons(in.readInt());
        setOrigin_country(in.createStringArray());
        setOriginal_language(in.readString());
        setOriginal_name(in.readString());
        setName(in.readString());
        setOverview(in.readString());
        setPopularity(in.readDouble());
        setPoster_path(in.readString());
        in.readTypedList(production_companies, Company.CREATOR);
        in.readTypedList(seasons, Season.CREATOR);
        setStatus(in.readString());
        setType(in.readString());
        setVote_average(in.readDouble());
        setVote_count(in.readInt());
    }
}