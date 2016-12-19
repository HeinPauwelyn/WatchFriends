package nmct.jaspernielsmichielhein.watchfriends.api;

import android.databinding.ObservableArrayList;

import java.util.ArrayList;

import nmct.jaspernielsmichielhein.watchfriends.helper.Contract;
import nmct.jaspernielsmichielhein.watchfriends.model.MediaPackage;
import nmct.jaspernielsmichielhein.watchfriends.model.SeriesList;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface WatchFriendsService {

    @GET("list")
    Observable<ArrayList<SeriesList>> getLists();
}
