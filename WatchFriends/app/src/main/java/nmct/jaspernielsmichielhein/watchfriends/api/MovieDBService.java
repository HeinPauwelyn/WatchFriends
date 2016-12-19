package nmct.jaspernielsmichielhein.watchfriends.api;

import nmct.jaspernielsmichielhein.watchfriends.helper.Contract;
import nmct.jaspernielsmichielhein.watchfriends.model.Episode;
import nmct.jaspernielsmichielhein.watchfriends.model.Page;
import nmct.jaspernielsmichielhein.watchfriends.model.Season;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import nmct.jaspernielsmichielhein.watchfriends.model.SeriesList;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface MovieDBService {
    //SERIES
    //https://api.themoviedb.org/3/tv/63174?api_key=1447c9e70c5784fbe8a492a4d5f37c8b&append_to_response=images,similar
    @GET("tv/{series}?append_to_response=images,similar&api_key=" + Contract.MOVIEDB_API_KEY)
    Observable<Series> getSeries(@Path("series") int seriesId);

    //SIMILAR SERIES
    //https://api.themoviedb.org/3/tv/63174/similar?api_key=1447c9e70c5784fbe8a492a4d5f37c8b&language=en-US
    @GET("tv/{series}/similar?language=en-US&api_key=" + Contract.MOVIEDB_API_KEY)
    Observable<SearchResult> getSimilarSeries(@Path("series") int seriesId);

    //SEASON
    //https://api.themoviedb.org/3/tv/63174/season/2?api_key=1447c9e70c5784fbe8a492a4d5f37c8b&language=en-US
    @GET("tv/{series}/season/{season}?language=en-US&api_key=" + Contract.MOVIEDB_API_KEY)
    Observable<Season> getSeason(@Path("series") int seriesId, @Path("season") int season);

    //EPISODE
    //https://api.themoviedb.org/3/tv/63174/season/2/episode/2?api_key=1447c9e70c5784fbe8a492a4d5f37c8b&language=en-US
    @GET("tv/{series}/season/{season}/episode/{episode}?language=en-US&api_key=" + Contract.MOVIEDB_API_KEY)
    Observable<Episode> getEpisode(@Path("series") int seriesId, @Path("season") int season, @Path("episode") int episode);

    //SEARCH
    //https://api.themoviedb.org/3/search/tv?api_key=1447c9e70c5784fbe8a492a4d5f37c8b&language=en-US&query=suits&page=1
    @GET("search/tv?append_to_response=images,similar&language=en-US&api_key=" + Contract.MOVIEDB_API_KEY)
    Observable<SearchResult> getSearchResults(@Query("query") String query);
}