package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import android.content.Context;
import android.database.Cursor;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.api.WatchFriendsService;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentFollowedSeriesBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiWatchFriendsHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import nmct.jaspernielsmichielhein.watchfriends.model.SeriesList;
import nmct.jaspernielsmichielhein.watchfriends.provider.Contract;
import rx.functions.Action1;


public class FollowedSeriesFragmentViewModel extends BaseObservable {

    private ISeriesAddedListener seriesAddedListener;
    private Interfaces.headerChangedListener headerListener;
    private Context context;
    private FragmentFollowedSeriesBinding fragmentFollowedSeriesBinding;
    private WatchFriendsService service;

    @Bindable
    private ObservableArrayList<SeriesList> seriesList = null;

    public ObservableArrayList<SeriesList> getSeriesList() {
        return seriesList;
    }

    private void setSeriesList(ObservableArrayList<SeriesList> seriesList) {
        seriesList.add(0, new SeriesList());
        seriesList.get(0).setName("Following Series");
        seriesList.get(0).setSeries(new ObservableArrayList<Series>());
        this.seriesList = seriesList;
    }

    public FollowedSeriesFragmentViewModel(Context context, FragmentFollowedSeriesBinding fragmentFollowedSeriesBinding, ISeriesAddedListener listener) {
        this.context = context;
        this.fragmentFollowedSeriesBinding = fragmentFollowedSeriesBinding;
        seriesAddedListener = listener;

        service = ApiWatchFriendsHelper.getWatchFriendsServiceInstance();

        setSeriesList(new ObservableArrayList<SeriesList>());

        if (context instanceof Interfaces.headerChangedListener) {
            headerListener = (Interfaces.headerChangedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement headerChangedListener");
        }
    }

    private void addToSeriesList(Series serie) {
        seriesList.get(0).getSeries().add(serie);
    }

    public void getData() {
        String[] projection = new String[]{nmct.jaspernielsmichielhein.watchfriends.database.Contract.FollowedSeriesColumns.COLUMN_FOLLOWEDSERIES_NR};
        Cursor c = context.getContentResolver().query(
                Contract.FOLLOWEDSERIES_URI,
                projection,
                null,
                null,
                null
        );

        if (c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                loadSerie(c.getInt(c.getColumnIndex("followingseriesnr")));
            }
        }
        c.close();
        setHeader();
    }

    private void loadSerie(int id) {
        ApiHelper.subscribe(service.getSeries(id),
                new Action1<Series>() {
                    @Override
                    public void call(Series serie) {
                        addToSeriesList(serie);
                        seriesAddedListener.updateLists(seriesList);
                    }
                });
    }


    public interface ISeriesAddedListener {
        void updateLists(ObservableArrayList<SeriesList> seriesLists);
    }

    private void setHeader() {
        headerListener.setTitle(context.getResources().getString(R.string.following));
        headerListener.enableAppBarScroll(false);
        headerListener.getHeaderImage().setImageResource(0);
    }
}
