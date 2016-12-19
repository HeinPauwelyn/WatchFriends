package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;

import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentHomeBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiMovieDbHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiWatchFriendsHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.Contract;
import nmct.jaspernielsmichielhein.watchfriends.model.MediaItem;
import nmct.jaspernielsmichielhein.watchfriends.model.Page;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import nmct.jaspernielsmichielhein.watchfriends.model.SeriesList;
import nmct.jaspernielsmichielhein.watchfriends.model.SeriesListData;
import rx.functions.Action1;

public class HomeFragmentViewModel extends BaseObservable {

    private ISeriesAddedListener seriesAddedListener;
    private Context context;
    private FragmentHomeBinding fragmentHomeBinding;
    @Bindable private ObservableArrayList<SeriesList> seriesLists = null;
    @Bindable private ObservableArrayList<MediaItem> carousel = null;

    public ObservableArrayList<SeriesList> getSeriesLists() {
        return seriesLists;
    }
    public void setSeriesLists(ObservableArrayList<SeriesList> seriesLists) {
        this.seriesLists = seriesLists;
    }

    public ObservableArrayList<MediaItem> getCarousel() {
        return carousel;
    }
    public void setCarousel(ObservableArrayList<MediaItem> carousel) {
        this.carousel = carousel;
    }

    public HomeFragmentViewModel(Context context, FragmentHomeBinding fragmentHomeBinding, ISeriesAddedListener listener) {
        this.context = context;
        this.fragmentHomeBinding = fragmentHomeBinding;
        seriesAddedListener = listener;

        setSeriesLists(new ObservableArrayList<SeriesList>());
    }

    public void getData() {

        // ApiHelper.subscribe(ApiWatchFriendsHelper.getWatchFriendsServiceInstance().getLists(), new Action1<SeriesListData>() {
        //     @Override
        //     public void call(SeriesListData seriesListData) {
        //         if (seriesListData != null) {
        //             for (int id : seriesListData.getSeriesLists()) {
        //                 loadSeriesList(id);
        //             }
        //         }
        //     }
        // });

        // ApiHelper.subscribe(ApiMovieDbHelper.getMoviedbServiceInstance().getPopular(), new Action1<Page<Series>>() {
        //     @Override
        //     public void call(Page<Series> seriesPage) {
        //         if (seriesPage != null) {

        //             ObservableArrayList<Series> series = new ObservableArrayList<Series>();
        //             Random rnd = new Random();
        //             int size = seriesPage.getResults().size();
        //             ArrayList<Integer> takenSeries = new ArrayList<Integer>();

        //             for (int i = 0; i < 5; i++) {
        //                 Integer number = rnd.nextInt(size);

        //                 if (takenSeries.contains(number)) {
        //                     i--;
        //                 }
        //                 else {
        //                     takenSeries.add(number);
        //                     series.add(seriesPage.getResults().get(number));
        //                 }
        //             }
        //             seriesAddedListener.updateCarousel(series);
        //         }
        //     }
        // });
    }

    private void loadSeriesList(int id) {

        // ApiHelper.subscribe(ApiMovieDbHelper.getMoviedbServiceInstance().getSeriesList(id), new Action1<SeriesList>() {
        //     @Override
        //     public void call(SeriesList seriesList) {
        //         seriesLists.add(seriesList);
        //         notifyPropertyChanged(BR.viewmodel);

        //         seriesAddedListener.updateLists(seriesLists);
        //     }
        // });
    }

    public interface ISeriesAddedListener {
        void updateLists(ObservableArrayList<SeriesList> seriesLists);
        void updateCarousel(ObservableArrayList<Series> backdrop_path);
    }
}