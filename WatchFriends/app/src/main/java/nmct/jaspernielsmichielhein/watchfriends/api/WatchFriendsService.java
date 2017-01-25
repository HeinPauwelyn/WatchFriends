package nmct.jaspernielsmichielhein.watchfriends.api;

import com.android.annotations.Nullable;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Date;

import nmct.jaspernielsmichielhein.watchfriends.model.Achievement;
import nmct.jaspernielsmichielhein.watchfriends.model.Episode;
import nmct.jaspernielsmichielhein.watchfriends.model.Follower;
import nmct.jaspernielsmichielhein.watchfriends.model.Page;
import nmct.jaspernielsmichielhein.watchfriends.model.SeriesList;
import nmct.jaspernielsmichielhein.watchfriends.model.Season;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import nmct.jaspernielsmichielhein.watchfriends.model.User;
import nmct.jaspernielsmichielhein.watchfriends.model.UserData;
import nmct.jaspernielsmichielhein.watchfriends.model.WFEvent;
import nmct.jaspernielsmichielhein.watchfriends.model.WFEventsPage;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface WatchFriendsService {

    //AUTH
    @FormUrlEncoded
    @POST("auth/register")
    Call<JsonObject> register(@Field("email") String email, @Field("lastname") String lastname, @Field("firstname") String firstname, @Field("password") String password);

    @FormUrlEncoded
    @POST("auth/login")
    Call<JsonObject> login(@Field("email") String email, @Field("password") String password);

    @GET("auth/logout")
    java.util.Observable logout(@Query("access_token") String token);

    @GET("auth/logoffonall")
    java.util.Observable logoutAll(@Query("access_token") String token);

    @GET("auth/login")
    java.util.Observable loginToken(@Query("access_token") String token);

    //LISTS
    @GET("list")
    Observable<ArrayList<SeriesList>> getLists(@Query("access_token") String token);

    @GET("series/popular/{page}")
    Observable<Page<Series>> getSeriesPopular(@Path("page") int page, @Query("access_token") String token);

    @GET("series/today/{page}")
    Observable<Page<Series>> getSeriesToday(@Path("page") int page, @Query("access_token") String token);

    @GET("series/popular")
    Observable<Page<Series>> getSeriesRecommended(@Query("access_token") String token);

    //USERS
    @GET("users/search/{query}")
    Observable<ArrayList<User>> searchUsers(@Path("query") String query, @Query("access_token") String authToken);

    @GET("users/{id}")
    Observable<UserData> getUser(@Path("id") String userId, @Query("access_token") String authToken);

    @PUT("users/{id}")
    Observable<UserData> updateUser(@Body User user, @Query("access_token") String authToken);

    @GET("users/{id}/achievements")
    Observable<ArrayList<Achievement>> getUserAchievements(@Path("id") String userId, @Query("access_token") String authToken);

    //FOLLOWERS
    @GET("users/{id}/followers")
    Observable<ArrayList<Follower>> getUserFollowers(@Path("id") String userId, @Query("access_token") String authToken);

    @GET("users/{id}/follows")
    Observable<ArrayList<Follower>> getUserFollows(@Path("id") String userId, @Query("access_token") String authToken);

    @GET("users/{follower}/follows/{followed}")
    Observable<Follower> getUserFollows(@Path("follower") String followerId, @Path("followed") String followedId, @Query("access_token") String authToken);

    @PUT("users/{follower}/follows/{followed}")
    Observable updateUserFollows(@Path("follower") String followerId, @Path("followed") String followedId, @Field("following") Boolean following, @Query("access_token") String authToken);

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
    Observable<Boolean> getFollowedSeries(@Query("user") String userId, @Path("series") int seriesId, @Query("access_token") String token);

    @PUT("followed/{series}")
    Observable UpdateFollowedSeries(@Query("user") String userId, @Path("series") int seriesId, @Field("following") Boolean following, @Query("access_token") String token);

    //EVENTS
    @PUT("event")
    Observable AddEvent(@Body WFEvent event, @Query("access_token") String token);

    @GET("feed/{page}")
    Observable<WFEventsPage> getFeedEvents(@Path("page") int page, @Query("access_token") String token)
}