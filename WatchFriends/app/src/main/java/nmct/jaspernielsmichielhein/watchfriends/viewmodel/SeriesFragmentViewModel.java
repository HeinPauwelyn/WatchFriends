package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;

import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentSeriesBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiHelper;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SeriesFragmentViewModel extends BaseObservable {
    private Context context;
    private FragmentSeriesBinding fragmentSeriesBinding;


    @Bindable
    private Series series = null;

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public SeriesFragmentViewModel(Context context, FragmentSeriesBinding fragmentSeriesBinding, Series series) {
        this.context = context;
        this.fragmentSeriesBinding = fragmentSeriesBinding;
        this.series = series;
    }

    public void loadSeries() {
        /*
        ApiHelper.getMoviedbServiceInstance().getSeries(seriesId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Series>() {
                    @Override
                    public void call(Series returnedSeries) {
                        setSeries(returnedSeries);
                        fragmentSeriesBinding.setSeries(series);
                        notifyPropertyChanged(BR.series);
                    }
                });
        */
    }
}