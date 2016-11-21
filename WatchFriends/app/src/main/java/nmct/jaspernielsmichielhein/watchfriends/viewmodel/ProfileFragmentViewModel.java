package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;
import java.util.Random;

import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentProfileBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiMovieDbHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.Page;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import nmct.jaspernielsmichielhein.watchfriends.model.SeriesList;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ProfileFragmentViewModel extends BaseObservable {

    private final Interfaces.headerChangedListener mListener;
    private Context context;
    private FragmentProfileBinding fragmentProfileBinding;
    @Bindable
    private ObservableArrayList<Series> watchlist = null;

    public ObservableArrayList<Series> getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(ObservableArrayList<Series> watchlist) {
        this.watchlist = watchlist;
    }

    public ProfileFragmentViewModel(Context context, FragmentProfileBinding fragmentProfileBinding) {
        this.context = context;
        this.fragmentProfileBinding = fragmentProfileBinding;

        if (context instanceof Interfaces.headerChangedListener) {
            mListener = (Interfaces.headerChangedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement onHeaderChanged");
        }

        setWatchlist(new ObservableArrayList<Series>());
    }

    public void getUser() {
        final ProfileFragmentViewModel that = this;
        fragmentProfileBinding.setViewmodel(that);
        setHeader();
    }

    private void setHeader() {
        mListener.setTitle("UserName");
        final FloatingActionButton fab = mListener.getActionButton();
        fab.setVisibility(View.INVISIBLE);
    }

    public void generateFakeDate() {
        getData(watchlist);
    }

    private void getData(final ObservableArrayList<Series> series) {
        ApiHelper.subscribe(ApiMovieDbHelper.getMoviedbServiceInstance().getPopular(), new Action1<Page<Series>>() {
            @Override
            public void call(Page<Series> seriesPage) {
                if (seriesPage != null) {
                    Random rnd = new Random();
                    int size = seriesPage.getResults().size();
                    ArrayList<Integer> takenSeries = new ArrayList<Integer>();

                    for (int i = 0; i < 5; i++) {
                        Integer number = rnd.nextInt(size);

                        if (takenSeries.contains(number)) {
                            i--;
                        } else {
                            takenSeries.add(number);
                            series.add(seriesPage.getResults().get(number));
                        }
                    }
                }
            }
        });
    }
}