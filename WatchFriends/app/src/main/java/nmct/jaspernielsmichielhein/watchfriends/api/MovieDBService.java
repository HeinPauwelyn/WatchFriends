package nmct.jaspernielsmichielhein.watchfriends.api;

import android.databinding.ObservableArrayList;

import nmct.jaspernielsmichielhein.watchfriends.helper.Contract;
import nmct.jaspernielsmichielhein.watchfriends.model.Episode;
import nmct.jaspernielsmichielhein.watchfriends.model.MediaPackage;
import nmct.jaspernielsmichielhein.watchfriends.model.Season;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import nmct.jaspernielsmichielhein.watchfriends.model.SeriesList;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface MovieDBService {
    //SERIES
    //https://api.themoviedb.org/3/tv/63174?api_key=1447c9e70c5784fbe8a492a4d5f37c8b&language=en-US
    @GET("tv/{series}?api_key=" + Contract.MOVIEDB_API_KEY + "&language=en-US")
    Observable<Series> getSeries(@Path("series") int seriesId);

    //SIMILAR SERIES
    //https://api.themoviedb.org/3/tv/63174/similar?api_key=1447c9e70c5784fbe8a492a4d5f37c8b&language=en-US
    @GET("tv/{series}/similar?api_key=" + Contract.MOVIEDB_API_KEY + "&language=en-US")
    Observable<SimilarSeriesResult> getSimilarSeries(@Path("series") int seriesId);

    //SEASON
    //https://api.themoviedb.org/3/tv/63174/season/2?api_key=1447c9e70c5784fbe8a492a4d5f37c8b&language=en-US
    @GET("tv/{series}/season/{season}?api_key=" + Contract.MOVIEDB_API_KEY + "&language=en-US")
    Observable<Season> getSeason(@Path("series") int seriesId, @Path("season") int season);

    //EPISODE
    //https://api.themoviedb.org/3/tv/63174/season/2/episode/2?api_key=1447c9e70c5784fbe8a492a4d5f37c8b&language=en-US
    @GET("tv/{series}/season/{season}/episode/{episode}?api_key=" + Contract.MOVIEDB_API_KEY + "&language=en-US")
    Observable<Episode> getEpisode(@Path("series") int seriesId, @Path("season") int season, @Path("episode") int episode);

    @GET("tv/{series}/images?api_key=" + Contract.MOVIEDB_API_KEY)
    Observable<MediaPackage> getMediaSeries(@Path("series") int seriesId);

    @GET("list/{id}?api_key=" + Contract.MOVIEDB_API_KEY)
    Observable<SeriesList> getSeriesList(@Path("id") int id);
}