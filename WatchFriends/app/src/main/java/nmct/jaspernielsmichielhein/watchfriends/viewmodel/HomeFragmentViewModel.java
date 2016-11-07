package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;

import com.android.databinding.library.baseAdapters.BR;

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

    public ObservableArrayList<Series> getRecommendedByFriends() {
        return recommendedByFriends;
    }

    public void setRecommendedByFriends(ObservableArrayList<Series> recommendedByFriends) {
        this.recommendedByFriends = recommendedByFriends;
    }

    public HomeFragmentViewModel(Context context, FragmentHomeBinding fragmentHomeBinding) {
        this.context = context;
        this.fragmentHomeBinding = fragmentHomeBinding;
    }

    public void generateFakeData() {
        final HomeFragmentViewModel that = this;
        fragmentHomeBinding.setViewmodel(that);

        int[] ids = {16148, 64095, 248936, 1399, 25778, 14506, 33088, 13687};

        setRecommendedByFriends(new ObservableArrayList<Series>());
        for(int id : ids) {
            loadSeries(id);
        }
    }

    private void loadSeries(int id) {
        ApiHelper.subscribe(ApiHelper.getMoviedbServiceInstance().getSeries(id),
            new Action1<Series>() {
                @Override
                public void call(Series series) {
                    if (series != null) {
                        recommendedByFriends.add(series);
                        notifyPropertyChanged(BR.viewmodel);
                    }
                }
            });
    }
}