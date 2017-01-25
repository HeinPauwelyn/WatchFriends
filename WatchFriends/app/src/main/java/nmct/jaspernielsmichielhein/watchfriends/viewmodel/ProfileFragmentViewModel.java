package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;

import java.util.ArrayList;
import java.util.Random;

import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentProfileBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiWatchFriendsHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.AuthHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import nmct.jaspernielsmichielhein.watchfriends.model.SeriesList;
import rx.functions.Action1;

public class ProfileFragmentViewModel extends BaseObservable {

    private final Interfaces.headerChangedListener headerListener;
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
            headerListener = (Interfaces.headerChangedListener) context;
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
        headerListener.enableAppBarScroll(false);
        headerListener.setTitle("UserName");
        headerListener.getHeaderImage().setImageResource(0);
    }

    public void generateFakeDate() {
        getData(watchlist);
    }

    private void getData(final ObservableArrayList<Series> series) {
        ApiHelper.subscribe(ApiWatchFriendsHelper.getWatchFriendsServiceInstance().getLists(AuthHelper.getAuthToken(this.context)), new Action1<ObservableArrayList<SeriesList>>() {
            @Override
            public void call(ObservableArrayList<SeriesList> seriesPage) {
                if (seriesPage != null) {
                    Random rnd = new Random();
                    int size = seriesPage.get(0).getResults().size();
                    ArrayList<Integer> takenSeries = new ArrayList<Integer>();

                    for (int i = 0; i < 5; i++) {
                        Integer number = rnd.nextInt(size);

                        if (takenSeries.contains(number)) {
                            i--;
                        } else {
                            takenSeries.add(number);
                            series.add(seriesPage.get(0).getResults().get(number));
                        }
                    }
                }
            }
        });
    }
}