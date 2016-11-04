package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;

import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentHomeBinding;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentSeriesBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiHelper;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class HomeFragmentViewModel extends BaseObservable {

    private Context context;
    private FragmentHomeBinding fragmentHomeBinding;

    @Bindable
    private ArrayList<Series> recommendedByFriends = new ArrayList<>();
    private ArrayList<Series> newSeries = new ArrayList<>();
    private ArrayList<Series> watchList = new ArrayList<>();
    private ArrayList<Series> featured = new ArrayList<>();

    public HomeFragmentViewModel(Context context, FragmentHomeBinding fragmentHomeBinding) {
        this.context = context;
        this.fragmentHomeBinding = fragmentHomeBinding;
    }

    public HomeFragmentViewModel() {

        ApiHelper.getMoviedbServiceInstance().getSeries(16148).subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new Action1<Series>() {
                     @Override
                     public void call(Series returnedSeries) {
                         recommendedByFriends.add(returnedSeries);
                         //fragmentHomeBinding.se
                     }
                 });
    }

    public ArrayList<Series> getRecommendedByFriends() {
        return recommendedByFriends;
    }

    public void setRecommendedByFriends(ArrayList<Series> recommendedByFriends) {
        this.recommendedByFriends = recommendedByFriends;
    }

    public ArrayList<Series> getNewSeries() {
        return newSeries;
    }

    public void setNewSeries(ArrayList<Series> newSeries) {
        this.newSeries = newSeries;
    }

    public ArrayList<Series> getWatchList() {
        return watchList;
    }

    public void setWatchList(ArrayList<Series> watchList) {
        this.watchList = watchList;
    }

    public ArrayList<Series> getFeatured() {
        return featured;
    }

    public void setFeatured(ArrayList<Series> featured) {
        this.featured = featured;
    }
}
