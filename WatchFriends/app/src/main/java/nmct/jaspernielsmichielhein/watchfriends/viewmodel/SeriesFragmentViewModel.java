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
import com.squareup.picasso.Picasso;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.api.SimilarSeriesResult;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentSeriesBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import rx.functions.Action1;

public class SeriesFragmentViewModel extends BaseObservable {
    private Interfaces.headerChangedListener listener;

    private Context context;
    private FragmentSeriesBinding fragmentSeriesBinding;

    private Series series = null;

    @Bindable
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
        if (context instanceof Interfaces.headerChangedListener) {
            listener = (Interfaces.headerChangedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement headerChangedListener");
        }
    }

    public void loadSeries() {
        fragmentSeriesBinding.setViewmodel(this);
        notifyPropertyChanged(BR.viewmodel);
        setHeader();
        loadSimilarSeries();
    }

    private void setHeader() {
        listener.expandToolbar();
        listener.setTitle(series.getName());
        Picasso.with(context).load(series.getImage_uri()).into(listener.getHeaderImage());
        final FloatingActionButton fab = listener.getActionButton();
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
        setSimilarSeries(new ObservableArrayList<Series>());
        ApiHelper.subscribe(ApiHelper.getMoviedbServiceInstance().getSimilarSeries(series.getId()),
            new Action1<SimilarSeriesResult>() {
                @Override
                public void call(SimilarSeriesResult similarSeriesResult) {
                    ObservableArrayList<Series> series = similarSeriesResult.getResults();
                    if (series.size() != 0) {
                        for (int i = 0; i < 5; i++) {
                            ApiHelper.subscribe(
                            ApiHelper.getMoviedbServiceInstance().getSeries(series.get(i).getId()),
                                new Action1<Series>() {
                                    @Override
                                    public void call(Series series) {
                                        similarSeries.add(series);
                                        notifyPropertyChanged(BR.viewmodel);
                                    }
                                });
                        }
                    }
                }
            });
    }
}