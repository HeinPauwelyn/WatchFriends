package nmct.jaspernielsmichielhein.watchfriends.api;

import nmct.jaspernielsmichielhein.watchfriends.helper.Contract;
import nmct.jaspernielsmichielhein.watchfriends.model.Episode;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface IMovieDBService {
    //EPISODE
    //https://api.themoviedb.org/3/tv/63174/season/2/episode/2?api_key=1447c9e70c5784fbe8a492a4d5f37c8b&language=en-US
    @GET("tv/{series}/season/{season}/episode/{episode}?api_key=" + Contract.MOVIEDB_API_KEY + "&language=en-US")
    public Observable<Episode> getEpisode(@Path("series") int seriesId, @Path("season") int season, @Path("episode") int episode);
}