package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;

import java.util.ArrayList;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentHomeBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiWatchFriendsHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.AuthHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.MediaItem;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import nmct.jaspernielsmichielhein.watchfriends.model.SeriesList;
import rx.functions.Action1;

public class HomeFragmentViewModel extends BaseObservable {

    private Interfaces.headerChangedListener headerListener;
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

        if (context instanceof Interfaces.headerChangedListener) {
            headerListener = (Interfaces.headerChangedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement headerChangedListener");
        }
    }

    public void loadSeries(){
        getData();
        setHeader();
    }

    private void getData() {
        ApiHelper.subscribe(ApiWatchFriendsHelper.getWatchFriendsServiceInstance().getLists(AuthHelper.getAuthToken(this.context)), new Action1<ArrayList<SeriesList>>() {
            @Override
            public void call(ArrayList<SeriesList> seriesList) {
                if (seriesList != null) {

                    for (SeriesList list : seriesList) {
                        if (list.getName().toLowerCase().equals("popular")) {
                            seriesAddedListener.updateCarousel(list.getResults());
                        }
                    }

                    ObservableArrayList<SeriesList> obList = new ObservableArrayList<SeriesList>();
                    obList.addAll(seriesList);
                    seriesAddedListener.updateLists(obList);
                }
            }
        });
    }

    private void setHeader(){
        headerListener.setTitle(context.getResources().getString(R.string.title_activity_main));
        headerListener.enableAppBarScroll(false);
        headerListener.getHeaderImage().setImageResource(0);
    }

    public interface ISeriesAddedListener {
        void updateLists(ObservableArrayList<SeriesList> seriesLists);
        void updateCarousel(ObservableArrayList<Series> backdrop_path);
    }
}