package nmct.jaspernielsmichielhein.watchfriends.api;

import com.android.annotations.Nullable;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Date;

import nmct.jaspernielsmichielhein.watchfriends.model.Episode;
import nmct.jaspernielsmichielhein.watchfriends.model.Follower;
import nmct.jaspernielsmichielhein.watchfriends.model.Page;
import nmct.jaspernielsmichielhein.watchfriends.model.SeriesList;
import nmct.jaspernielsmichielhein.watchfriends.model.Season;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import nmct.jaspernielsmichielhein.watchfriends.model.User;
import nmct.jaspernielsmichielhein.watchfriends.model.UserData;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
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

    //LISTS
    @GET("list")
    Observable<ArrayList<SeriesList>> getLists(@Header("Authorization") String token);

    //USERS
    @GET("users/search/{query}")
    Observable<ArrayList<User>> searchUsers(@Path("query") String query, @Header("Authorization") String token);

    @GET("users/{id}")
    Observable<UserData> getUser(@Path("id") String userId, @Header("Authorization") String authToken);

    //FOLLOWERS
    @GET("users/{id}/followers")
    Observable<ArrayList<Follower>> getUserFollowers(@Path("id") String userId, @Header("Authorization") String token);

    @GET("users/{id}/follows")
    Observable<ArrayList<Follower>> getUserFollows(@Path("id") String userId, @Header("Authorization") String token);

    @GET("users/{follower}/follows/{followed}")
    Observable<Follower> getUserFollows(@Path("follower") String followerId, @Path("followed") String followedId, @Header("Authorization") String token);

    @PUT("users/{follower}/follows/{followed}")
    Observable<Follower> updateUserFollows(@Path("follower") String followerId, @Path("followed") String followedId, @Field("following") Boolean following, @Header("Authorization") String token);

    //SERIES
    @GET("series/{series}")
    Observable<Series> getSeries(@Path("series") int seriesId, @Header("Authorization") String token);

    @GET("series/search/{query}/{page}")
    Observable<Page<Series>> searchSeries(@Path("query") String query, @Path("page") int page, @Header("Authorization") String token);

    //SEASON
    @GET("series/{series}/season/{season}")
    Observable<Season> getSeason(@Path("series") int seriesId, @Path("season") int season);

    //EPISODE
    @GET("series/{series}/season/{season}/episode/{episode}")
    Observable<Episode> getEpisode(@Path("series") int seriesId, @Path("season") int season, @Path("episode") int episode);

    //FOLLOWED SERIES
    @GET("followed")
    Observable<ArrayList<Series>> getFollowedSeries(@Query("user") String userId, @Header("Authorization") String token);

    @GET("followed/{series}")
    Observable<Boolean> getFollowedSeries(@Query("user") String userId, @Path("series") int seriesId, @Header("Authorization") String token);

    @PUT("followed/{series}")
    Observable<Boolean> UpdateFollowedSeries(@Query("user") String userId, @Path("series") int seriesId, @Field("following") Boolean following, @Header("Authorization") String token);

}