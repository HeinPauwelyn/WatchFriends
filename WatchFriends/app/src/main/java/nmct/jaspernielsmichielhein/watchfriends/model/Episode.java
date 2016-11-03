package nmct.jaspernielsmichielhein.watchfriends.model;

import android.databinding.BaseObservable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;

import nmct.jaspernielsmichielhein.watchfriends.helper.Contract;
import nmct.jaspernielsmichielhein.watchfriends.helper.SeriesHelper;

public class Episode extends BaseObservable {
    private String air_date = "";
    private int episode_number = 0;
    private String name = "";
    private String overview = "";
    private int id = 0;
    private Uri image_uri;
    private String production_code = "";
    private int season_number = 0;
    private String shortcode = "";
    private String still_path = "";
    private double vote_average = 0;
    private int vote_count = 0;

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public int getEpisode_number() {
        return episode_number;
    }

    public void setEpisode_number(int episode_number) {
        this.episode_number = episode_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Uri getImage_uri() {
        return Uri.parse(Contract.MOVIEDB_IMAGE_BASE_URL + getStill_path());
    }

    public void setImage_uri() {
        this.image_uri = getImage_uri();
    }

    public String getProduction_code() {
        return production_code;
    }

    public void setProduction_code(String production_code) {
        this.production_code = production_code;
    }

    public int getSeason_number() {
        return season_number;
    }

    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }

    public String getShortcode() {
        return SeriesHelper.getShortcode(this.getSeason_number(), this.getEpisode_number());
    }

    public void setShortcode() {
        this.shortcode = getShortcode();
    }

    public String getStill_path() {
        return still_path;
    }

    public void setStill_path(String still_path) {
        this.still_path = still_path;
    }

    public float getVote_average() {
        return (float) (vote_average / 2);
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
        this.setImage_uri();
        this.setShortcode();
    }
}