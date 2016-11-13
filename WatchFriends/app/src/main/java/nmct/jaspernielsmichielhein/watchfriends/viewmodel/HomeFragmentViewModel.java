package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;

import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentHomeBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiWatchFriendsHelper;
import nmct.jaspernielsmichielhein.watchfriends.model.MediaItem;
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

    public void getData() {

        ApiWatchFriendsHelper.getWatchFriendsServiceInstance().getLists().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Func1<Throwable, SeriesListData>() {
                    @Override
                    public SeriesListData call(Throwable throwable) {
                        return null;
                    }
                })
                .subscribe(new Action1<SeriesListData>() {
                    @Override
                    public void call(SeriesListData returndList) {

                        for (int id : returndList.getFeatured()) {
                            loadSeriesList(id);
                        }
                    }
                });
    }

    private void loadSeriesList(int id) {

        ApiHelper.getMoviedbServiceInstance().getSeriesList(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Func1<Throwable, SeriesList>() {
                    @Override
                    public SeriesList call(Throwable throwable) {
                        return null;
                    }
                })
                .subscribe(new Action1<SeriesList>() {
                    @Override
                    public void call(SeriesList returndSeries) {


                    }
                });
    }

    public interface ISeriesAddedToCarouselListener {

        void updateCarousel(ObservableArrayList<MediaItem> series);
    }
}