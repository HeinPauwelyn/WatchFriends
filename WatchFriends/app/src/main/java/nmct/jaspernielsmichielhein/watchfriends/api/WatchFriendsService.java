package nmct.jaspernielsmichielhein.watchfriends.api;

import nmct.jaspernielsmichielhein.watchfriends.helper.Contract;
import nmct.jaspernielsmichielhein.watchfriends.model.MediaPackage;
import nmct.jaspernielsmichielhein.watchfriends.model.SeriesList;
import nmct.jaspernielsmichielhein.watchfriends.model.SeriesListData;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface WatchFriendsService {

    @GET("serieslists.json")
    Observable<SeriesListData> getLists();
}
