package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;

import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentProfileBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
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
    }

    public void loadUser() {
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
        final ProfileFragmentViewModel that = this;
        fragmentProfileBinding.setViewmodel(that);
        int[] ids = {16148, 64095, 1402, 1399, 25778, 14506, 33088, 13687};
        setWatchlist(new ObservableArrayList<Series>());
        loadSeries(ids, watchlist);
    }

    private void loadSeries(int[] ids, final ObservableArrayList<Series> seriesToLoad) {

        for (final int id : ids) {

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
                            if (returnedSeries != null) {

                                seriesToLoad.add(returnedSeries);
                                notifyPropertyChanged(BR.viewmodel);
                            }
                        }
                    });
        }
    }
}