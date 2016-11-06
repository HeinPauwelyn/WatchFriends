package nmct.jaspernielsmichielhein.watchfriends.model;

import android.databinding.ObservableArrayList;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

import nmct.jaspernielsmichielhein.watchfriends.helper.Contract;

public class Series implements Parcelable {
    private String all_creators;
    private String all_genres;
    private String backdrop_path = "";
    private ObservableArrayList<nmct.jaspernielsmichielhein.watchfriends.model.Creator> created_by = new ObservableArrayList<nmct.jaspernielsmichielhein.watchfriends.model.Creator>();
    private int[] episode_run_time = new int[0];
    private String first_air_date = "";
    private ObservableArrayList<Genre> genres = new ObservableArrayList<Genre>();
    private String homepage = "";
    private int id = 0;
    private Uri image_uri;
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
    private float rating = 0;
    private ObservableArrayList<Season> seasons = new ObservableArrayList<Season>();
    private String showed_on = "";
    private String status = "";
    private String time_period = "";
    private String type = "";
    private double vote_average = 0;
    private int vote_count = 0;

    public String getAll_creators() {
        return all_creators;
    }

    public void setAll_creators(String all_creators) {
        this.all_creators = all_creators;
    }

    public String getAll_genres() {
        return all_genres;
    }

    public void setAll_genres(String all_genres) {
        this.all_genres = all_genres;
    }

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

    public Uri getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(Uri image_uri) {
        this.image_uri = image_uri;
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public ObservableArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ObservableArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public String getShowed_on() {
        return showed_on;
    }

    public void setShowed_on(String showed_on) {
        this.showed_on = showed_on;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime_period() {
        return time_period;
    }

    public void setTime_period(String time_period) {
        this.time_period = time_period;
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

    public void initExtraFields() {
        makeAll_creators();
        makeAll_genres();
        makeImage_uri();
        makeShowed_on();
        makeTime_period();
        setRating((float) getVote_average() / 2);
    }

    public void makeAll_creators() {
        String all_creators = "";
        for (nmct.jaspernielsmichielhein.watchfriends.model.Creator creator : getCreated_by()) {
            all_creators += creator.getName() + ", ";
        }
        all_creators = all_creators.substring(0, all_creators.length() - 2);
        setAll_creators(all_creators);
    }

    public void makeAll_genres() {
        String all_genres = "";
        for (Genre genre : getGenres()) {
            all_genres += genre.getName() + ", ";
        }
        all_genres = all_genres.substring(0, all_genres.length() - 2);
        setAll_genres(all_genres);
    }

    public void makeImage_uri() {
        setImage_uri(Uri.parse(Contract.MOVIEDB_IMAGE_BASE_URL + getBackdrop_path()));
    }

    public void makeShowed_on() {
        String all_networks = "";
        for (Network network : getNetworks()) {
            all_networks += network.getName() + ", ";
        }
        all_networks = all_networks.substring(0, all_networks.length() - 2);

        int median_episode_time;
        int[] episode_times = getEpisode_run_time();
        if (episode_times.length == 1) {
            median_episode_time = episode_times[0];
        } else if(episode_times.length == 0) {
            median_episode_time = 0;
        } else {
            Arrays.sort(episode_times);
            int middle = ((episode_times.length) / 2);
            if (episode_times.length % 2 == 0) {
                int medianA = episode_times[middle];
                int medianB = episode_times[middle - 1];
                median_episode_time = (int) Math.ceil((medianA + medianB) / 2);
            } else {
                median_episode_time = episode_times[middle + 1];
            }
        }

        String showed_on = all_networks + " (" + median_episode_time + " min/ep)";

        setShowed_on(showed_on);
    }

    public void makeTime_period() {
        if(getStatus().equals("Ended")) {
            setTime_period(getFirst_air_date().substring(0, 4) + " - " + getLast_air_date().substring(0, 3));
        } else {
            setTime_period(getFirst_air_date().substring(0, 4) + " - Continuing");
        }
    }

    // PARCELABLE
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