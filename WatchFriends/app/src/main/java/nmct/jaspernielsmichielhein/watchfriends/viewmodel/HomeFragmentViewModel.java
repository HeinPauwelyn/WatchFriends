package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.adapters.Converters;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;
import com.squareup.picasso.Picasso;

import java.io.Console;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentHomeBinding;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentSeasonBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiHelper;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class HomeFragmentViewModel extends BaseObservable {
    private Context context;
    private FragmentHomeBinding fragmentHomeBinding;

    @Bindable
    private ObservableArrayList<Series> recommendedByFriends = null;

    @Bindable
    private ObservableArrayList<Series> popular = null;

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

    public HomeFragmentViewModel(Context context, FragmentHomeBinding fragmentHomeBinding) {
        this.context = context;
        this.fragmentHomeBinding = fragmentHomeBinding;
    }

    public void generateFakeData() {
        final HomeFragmentViewModel that = this;
        fragmentHomeBinding.setViewmodel(that);

        int[] ids1 = {16148, 64095, 1402, 1399, 25778, 14506, 33088, 13687};
        int[] ids2 = {1399, 1396, 4614, 12908, 45, 456, 2190, 16148};

        setRecommendedByFriends(new ObservableArrayList<Series>());
        setPopular(new ObservableArrayList<Series>());

        fillLists(ids1, recommendedByFriends);
        fillLists(ids2, popular);
    }

    private void fillLists(int[] ids, ObservableArrayList<Series> seriesToLoad) {

        for(int id : ids) {
            loadSeries(id, seriesToLoad);
        }
    }

    private void loadSeries(final int id, final ObservableArrayList<Series> seriesToLoad) {

        ApiHelper.getMoviedbServiceInstance().getSeries(id).subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .onErrorReturn(new Func1<Throwable, Series>() {
                     @Override
                     public Series call(Throwable throwable) {

                         Toast.makeText(context, "Series with ID " + id + ": " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                         return null;
                     }
                 })
                 .subscribe(new Action1<Series>() {
                     @Override
                     public void call(Series returnedSeries) {
                         if (returnedSeries != null) {

                             seriesToLoad.add(returnedSeries);
                             notifyPropertyChanged(BR.popular);
                         }
                     }
                 });
    }
}