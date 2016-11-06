package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import android.content.Context;
import android.content.res.ColorStateList;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentSeriesBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SeriesFragmentViewModel extends BaseObservable {
    private Interfaces.onHeaderChanged mListener;

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

    @Bindable
    private ObservableArrayList<Series> similarSeries = null;

    public ObservableArrayList<Series> getSimilarSeries() {
        return similarSeries;
    }

    public void setSimilarSeries(ObservableArrayList<Series> similarSeries) {
        this.similarSeries = similarSeries;
    }

    private boolean followed = false;

    public SeriesFragmentViewModel(Context context, FragmentSeriesBinding fragmentSeriesBinding, Series series) {
        this.context = context;
        this.fragmentSeriesBinding = fragmentSeriesBinding;
        this.series = series;
        if (context instanceof Interfaces.onHeaderChanged) {
            mListener = (Interfaces.onHeaderChanged) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement onHeaderChanged");
        }
    }

    public void loadSeries() {
        fragmentSeriesBinding.setViewmodel(this);
        notifyPropertyChanged(BR.viewmodel);
        setHeader();
        loadSimilarSeries();
    }

    private void setHeader() {
        mListener.onSetTitle(series.getName());
        mListener.onSetImage(series.getImage_uri());

        final FloatingActionButton fab = mListener.onGetActionButton();
        initFloatingActionButton(fab);
    }

    private void initFloatingActionButton(final FloatingActionButton fab) {
        if (followed) {
            fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.themeBlack)));
            fab.setImageResource(R.drawable.ic_clear_white_24dp);
        } else {
            fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.themeAccent)));
            fab.setImageResource(R.drawable.ic_add_white_24dp);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (followed) {
                    fab.setImageResource(R.drawable.ic_add_white_24dp);
                    fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.themeAccent)));
                    Snackbar.make(v, "No longer following " + series.getName(), Snackbar.LENGTH_LONG).show();
                } else {
                    fab.setImageResource(R.drawable.ic_clear_white_24dp);
                    fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.themeBlack)));
                    Snackbar.make(v, "Following " + series.getName(), Snackbar.LENGTH_LONG).show();
                }
                followed = !followed;
            }
        });
    }

    private void loadSimilarSeries() {
        int[] ids = {63174, 4604, 1412, 60735, 60708};
        setSimilarSeries(new ObservableArrayList<Series>());

        for (int id : ids) {
            ApiHelper.getMoviedbServiceInstance().getSeries(id).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorReturn(new Func1<Throwable, Series>() {
                        @Override
                        public Series call(Throwable throwable) {
                            return null;
                        }
                    })
                    .subscribe(new Action1<Series>() {
                        @Override
                        public void call(Series returnedSeries) {
                            similarSeries.add(returnedSeries);
                            notifyPropertyChanged(BR.viewmodel);
                        }
                    });
        }
    }
}