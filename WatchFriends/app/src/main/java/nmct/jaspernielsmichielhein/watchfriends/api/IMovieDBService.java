package nmct.jaspernielsmichielhein.watchfriends.api;

import nmct.jaspernielsmichielhein.watchfriends.helper.Contract;
import nmct.jaspernielsmichielhein.watchfriends.model.Episode;
import nmct.jaspernielsmichielhein.watchfriends.model.Season;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface IMovieDBService {
    //SERIES
    //https://api.themoviedb.org/3/tv/63174?api_key=1447c9e70c5784fbe8a492a4d5f37c8b&language=en-US
    @GET("tv/{series}?api_key=" + Contract.MOVIEDB_API_KEY + "&language=en-US")
    public Observable<Series> getSeries(@Path("series") int seriesId);

    //https://api.themoviedb.org/3/search/tv?api_key=1447c9e70c5784fbe8a492a4d5f37c8b&language=en-US&query=searchtext
    @GET("search/tv/?api_key=" + Contract.MOVIEDB_API_KEY + "&language=en-US")
    public Observable<Series> searchSeries(@Query("query") String query);

    //SEASON
    //https://api.themoviedb.org/3/tv/63174/season/2?api_key=1447c9e70c5784fbe8a492a4d5f37c8b&language=en-US
    @GET("tv/{series}/season/{season}?api_key=" + Contract.MOVIEDB_API_KEY + "&language=en-US")
    public Observable<Season> getSeason(@Path("series") int seriesId, @Path("season") int season);

    //EPISODE
    //https://api.themoviedb.org/3/tv/63174/season/2/episode/2?api_key=1447c9e70c5784fbe8a492a4d5f37c8b&language=en-US
    @GET("tv/{series}/season/{season}/episode/{episode}?api_key=" + Contract.MOVIEDB_API_KEY + "&language=en-US")
    public Observable<Episode> getEpisode(@Path("series") int seriesId, @Path("season") int season, @Path("episode") int episode);

}