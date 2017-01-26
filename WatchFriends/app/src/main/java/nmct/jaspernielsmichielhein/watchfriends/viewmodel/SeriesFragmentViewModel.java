package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;
import com.squareup.picasso.Picasso;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.database.Contract;
import nmct.jaspernielsmichielhein.watchfriends.database.tasks.FollowedSeriesDBTask;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentSeriesBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;

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
        this.similarSeries = series.getSimilar().getResults();
        if (series.getSeasons().size() == 0) {
            fragmentSeriesBinding.txtNoSeasons.setVisibility(View.VISIBLE);
        }
        if (series.getSimilar().getResults().size() == 0) {
            fragmentSeriesBinding.txtNoSimilar.setVisibility(View.VISIBLE);
            fragmentSeriesBinding.rvSimilar.setVisibility(View.GONE);
        }
        if (context instanceof Interfaces.headerChangedListener) {
            listener = (Interfaces.headerChangedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement headerChangedListener");
        }

        followed = isFollowed(series);
    }

    public void loadSeries() {
        fragmentSeriesBinding.setViewmodel(this);
        notifyPropertyChanged(BR.viewmodel);
        setHeader();
    }

    private void setHeader() {
        listener.setTitle(series.getName());
        listener.enableAppBarScroll(true);
        initFloatingActionButton(listener.getActionButton());
        Picasso.with(context).load(series.getImage_uri()).into(listener.getHeaderImage());
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

                    editFollow(false);

                } else {
                    fab.setImageResource(R.drawable.ic_clear_white_24dp);
                    fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.themeBlack)));
                    Snackbar.make(v, "Following " + series.getName(), Snackbar.LENGTH_LONG).show();

                    editFollow(true);
                }
                followed = !followed;
            }
        });
    }

    private void editFollow(boolean following) {
        ContentValues values = new ContentValues();
        values.put(Contract.FollowedSeriesColumns.COLUMN_FOLLOWEDSERIES_NR, series.getId());
        values.put(Contract.FollowedSeriesColumns.COLUMN_FOLLOWEDSERIES_NAME, series.getName());
        values.put(Contract.FollowedSeriesColumns.COLUMN_FOLLOWEDSERIES_FOLLOWING, following);

        executeAsyncTask(new FollowedSeriesDBTask(context), values);
    }

    private boolean isFollowed(Series s) {
        Cursor c = context.getContentResolver().query(
                nmct.jaspernielsmichielhein.watchfriends.provider.Contract.FOLLOWEDSERIES_URI,
                new String[]{Contract.FollowedSeriesColumns.COLUMN_FOLLOWEDSERIES_FOLLOWING},
                Contract.FollowedSeriesColumns.COLUMN_FOLLOWEDSERIES_NR + " = " + s.getId(),
                null,
                null);
        if (c.getCount() > 0) {
            int following = 0;
            if (c.moveToLast())
                following = c.getInt(c.getColumnIndex(Contract.FollowedSeriesColumns.COLUMN_FOLLOWEDSERIES_FOLLOWING));
            c.close();
            return following > 0;
        } else {
            c.close();
            return false;
        }
    }

    static private <T> void executeAsyncTask(AsyncTask<T, ?, ?> task, T... params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            task.execute(params);
        }
    }
}