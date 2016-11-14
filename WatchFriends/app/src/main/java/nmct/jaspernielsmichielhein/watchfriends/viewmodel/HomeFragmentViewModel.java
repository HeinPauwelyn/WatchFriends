package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;

import com.android.databinding.library.baseAdapters.BR;

import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentHomeBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiHelper;
import nmct.jaspernielsmichielhein.watchfriends.model.MediaItem;
import nmct.jaspernielsmichielhein.watchfriends.model.MediaPackage;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import rx.functions.Action1;

public class HomeFragmentViewModel extends BaseObservable {

    private ISeriesAddedToCarouselListener seriesAddedToCarouselListener;
    private Context context;
    private FragmentHomeBinding fragmentHomeBinding;
    @Bindable private ObservableArrayList<Series> recommendedByFriends = null;
    @Bindable private ObservableArrayList<Series> popular = null;
    @Bindable private ObservableArrayList<MediaItem> carousel = null;

    public ObservableArrayList<Series> getRecommendedByFriends() {
        return recommendedByFriends;
    }
    public void setRecommendedByFriends(ObservableArrayList<Series> recommendedByFriends) {
        this.recommendedByFriends = recommendedByFriends;
    }
    public ObservableArrayList<Series> getPopular() {
        return popular;
    }
    public void setPopular(ObservableArrayList<Series> popular) {
        this.popular = popular;
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

    public void generateFakeData() {
        final HomeFragmentViewModel that = this;
        fragmentHomeBinding.setViewmodel(that);

        int[] ids1 = {16148, 64095, 1402, 1399, 25778, 14506, 33088, 13687};
        int[] ids2 = {1399, 1396, 4614, 12908, 45, 456, 2190, 16148};
        int[] ids3 = {8318, 57243, 1418, 2734, 63174, 25384};

        setRecommendedByFriends(new ObservableArrayList<Series>());
        setPopular(new ObservableArrayList<Series>());
        setCarousel(new ObservableArrayList<MediaItem>());

        loadSeries(ids1, recommendedByFriends);
        loadSeries(ids2, popular);
        loadMedia(ids3, carousel);
    }

    private void loadMedia(int[] ids, final ObservableArrayList<MediaItem> seriesToLoad) {
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
    }
    private void loadSeries(int[] ids, final ObservableArrayList<Series> seriesToLoad) {
        for(final int id : ids) {
            ApiHelper.subscribe(ApiHelper.getMoviedbServiceInstance().getSeries(id),
                new Action1<Series>() {
                    @Override
                    public void call(Series returnedSeries) {
                        if (returnedSeries != null) {
                            seriesToLoad.add(returnedSeries);
                            notifyPropertyChanged(BR.viewmodel);
                        }
                    }
                });
        }
    }

    public interface ISeriesAddedToCarouselListener {

        void updateCarousel(ObservableArrayList<MediaItem> series);
    }
}