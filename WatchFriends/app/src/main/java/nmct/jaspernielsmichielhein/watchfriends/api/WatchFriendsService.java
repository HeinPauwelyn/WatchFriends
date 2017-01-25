package nmct.jaspernielsmichielhein.watchfriends.api;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import nmct.jaspernielsmichielhein.watchfriends.model.Episode;
import nmct.jaspernielsmichielhein.watchfriends.model.Page;
import nmct.jaspernielsmichielhein.watchfriends.model.SeriesList;
import nmct.jaspernielsmichielhein.watchfriends.model.Season;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface WatchFriendsService {

    //REGISTER
    @FormUrlEncoded
    @POST("auth/register")
    Call<JsonObject> register(@Field("email") String email, @Field("lastname") String lastname, @Field("firstname") String firstname, @Field("password") String password);

    //LOGIN
    @FormUrlEncoded
    @POST("auth/login")
    Call<JsonObject> login(@Field("email") String email, @Field("password") String password);

    //HOME LISTS
    @GET("list")
    Observable<ArrayList<SeriesList>> getLists(@Query("access_token") String token);

    //SERIES
    @GET("series/{series}")
    Observable<Series> getSeries(@Path("series") int seriesId, @Query("access_token") String token);

    @GET("series/search/{query}/{page}")
    Observable<Page<Series>> searchSeries(@Path("query") String query, @Path("page") int page, @Query("access_token") String authToken);

    //SEASON
    @GET("series/{series}/season/{season}")
    Observable<Season> getSeason(@Path("series") int seriesId, @Path("season") int season);

    //EPISODE
    @GET("series/{series}/season/{season}/episode/{episode}")
    Observable<Episode> getEpisode(@Path("series") int seriesId, @Path("season") int season, @Path("episode") int episode);

    //FOLLOWED SERIES
    @GET("followed")
    Observable<ArrayList<Series>> getFollowedSeries(@Query("user") String userId, @Query("access_token") String token);

    @GET("followed/{series}")
    Observable<ArrayList<Series>> getFollowedSeries(@Query("user") String userId, @Path("series") int seriesId, @Query("access_token") String token);
}