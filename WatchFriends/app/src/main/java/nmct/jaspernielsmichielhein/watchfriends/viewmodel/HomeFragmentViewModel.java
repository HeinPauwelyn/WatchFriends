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
import nmct.jaspernielsmichielhein.watchfriends.model.MediaPackage;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import nmct.jaspernielsmichielhein.watchfriends.model.SeriesList;
import nmct.jaspernielsmichielhein.watchfriends.model.SeriesListData;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class HomeFragmentViewModel extends BaseObservable {

    private ISeriesAddedToCarouselListener seriesAddedToCarouselListener;
    private Context context;
    private FragmentHomeBinding fragmentHomeBinding;
    @Bindable private ObservableArrayList<SeriesList> items = null;
    @Bindable private ObservableArrayList<MediaItem> carousel = null;

    public ObservableArrayList<SeriesList> getItems() {
        return items;
    }
    public void setItems(ObservableArrayList<SeriesList> items) {
        this.items = items;
    }

    public ObservableArrayList<MediaItem> getCarousel() {
        return carousel;
    }
    public void setCarousel(ObservableArrayList<MediaItem> carousel) {
        this.carousel = carousel;
    }

    public HomeFragmentViewModel(Context context, FragmentHomeBinding fragmentHomeBinding, ISeriesAddedToCarouselListener listener) {
        this.context = context;
        this.fragmentHomeBinding = fragmentHomeBinding;
        seriesAddedToCarouselListener = listener;
    }

    public void getData() {

        ApiHelper.subscribe(ApiWatchFriendsHelper.getWatchFriendsServiceInstance().getLists(), new Action1<SeriesListData>() {
            @Override
            public void call(SeriesListData seriesListData) {
                for (int id : seriesListData.getFeatured()) {
                    loadSeriesList(id);
                }
            }
        });
    }
    private void loadSeriesList(int id) {

        ApiHelper.subscribe(ApiMovieDbHelper.getMoviedbServiceInstance().getSeriesList(id), new Action1<SeriesList>() {
            @Override
            public void call(SeriesList seriesList) {

            }
        });
    }


    /*private void loadMedia(int[] ids, final ObservableArrayList<MediaItem> seriesToLoad) {
        for(final int id : ids) {
            ApiHelper.subscribe(ApiHelper.getMoviedbServiceInstance().getMediaSeries(id),
                new Action1<MediaPackage>() {
                    @Override
                    public void call(MediaPackage returnedMedia) {
                        if (returnedMedia != null) {
                            if (returnedMedia.getBackdrops().size() != 0) {
                                seriesToLoad.add(returnedMedia.getBackdrops().get(0));
                                notifyPropertyChanged(BR.viewmodel);
                                seriesAddedToCarouselListener.updateCarousel(carousel);
                            }
                        }
                    }
                });
        }
    }*/

    public interface ISeriesAddedToCarouselListener {

        void updateCarousel(ObservableArrayList<MediaItem> series);
    }
}