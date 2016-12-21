package nmct.jaspernielsmichielhein.watchfriends.api;

import java.util.ArrayList;
import nmct.jaspernielsmichielhein.watchfriends.model.SeriesList;
import retrofit2.http.GET;
import rx.Observable;

public interface WatchFriendsService {

    @GET("list")
    Observable<ArrayList<SeriesList>> getLists();
}
