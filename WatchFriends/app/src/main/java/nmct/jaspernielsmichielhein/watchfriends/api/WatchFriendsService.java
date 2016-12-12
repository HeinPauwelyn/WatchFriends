package nmct.jaspernielsmichielhein.watchfriends.api;

import nmct.jaspernielsmichielhein.watchfriends.model.SeriesListData;
import retrofit2.http.GET;
import rx.Observable;

public interface WatchFriendsService {

    @GET("serieslists.json")
    Observable<SeriesListData> getLists();
}
