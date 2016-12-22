package nmct.jaspernielsmichielhein.watchfriends.api;

import java.util.ArrayList;

import nmct.jaspernielsmichielhein.watchfriends.model.Episode;
import nmct.jaspernielsmichielhein.watchfriends.model.Season;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import nmct.jaspernielsmichielhein.watchfriends.model.SeriesList;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface WatchFriendsService {

    // HOME LISTS
    @GET("list")
    Observable<ArrayList<SeriesList>> getLists();

    //SERIES (+ similar series)
    @GET("series/{series}")
    Observable<Series> getSeries(@Path("series") int seriesId);

    //SEASON
    @GET("series/{series}/season/{season}")
    Observable<Season> getSeason(@Path("series") int seriesId, @Path("season") int season);

    //EPISODE
    @GET("tv/{series}/season/{season}/episode/{episode}")
    Observable<Episode> getEpisode(@Path("series") int seriesId, @Path("season") int season, @Path("episode") int episode);
}
