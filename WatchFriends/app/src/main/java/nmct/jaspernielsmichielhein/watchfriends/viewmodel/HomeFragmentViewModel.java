package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;

import com.android.databinding.library.baseAdapters.BR;

import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentHomeBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiMovieDbHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiWatchFriendsHelper;
import nmct.jaspernielsmichielhein.watchfriends.model.MediaItem;
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

        ApiHelper.subscribe(ApiWatchFriendsHelper.getWatchFriendsServiceInstance().getLists(), new Action1<SeriesListData>() {
            @Override
            public void call(SeriesListData seriesListData) {
                if (seriesListData != null) {
                    for (int id : seriesListData.getSeriesLists()) {
                        loadSeriesList(id);
                    }
                }
            }
        });
    }

    private void loadSeriesList(int id) {

        ApiHelper.subscribe(ApiMovieDbHelper.getMoviedbServiceInstance().getSeriesList(id), new Action1<SeriesList>() {
            @Override
            public void call(SeriesList seriesList) {
                seriesLists.add(seriesList);
                notifyPropertyChanged(BR.viewmodel);

                seriesAddedListener.updateLists(seriesLists);
            }
        });
    }

    public interface ISeriesAddedListener {

        void updateLists(ObservableArrayList<SeriesList> seriesLists);
        void updateCarousel(ObservableArrayList<MediaItem> series);
    }
}