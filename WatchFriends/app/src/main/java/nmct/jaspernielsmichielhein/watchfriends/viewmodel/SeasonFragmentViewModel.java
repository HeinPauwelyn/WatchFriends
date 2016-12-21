package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.squareup.picasso.Picasso;

import nmct.jaspernielsmichielhein.watchfriends.BR;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentSeasonBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiWatchFriendsHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.Season;
import rx.functions.Action1;

public class SeasonFragmentViewModel extends BaseObservable {
    private final Interfaces.headerChangedListener listener;
    private Context context;
    private FragmentSeasonBinding fragmentSeasonBinding;

    private Season season = null;

    @Bindable
    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    private String seriesName = "";
    private int seriesId = 0;
    private int seasonNumber = 0;

    public SeasonFragmentViewModel(Context context, FragmentSeasonBinding fragmentSeasonBinding, String seriesName, int seriesId, int seasonNumber) {
        this.context = context;
        this.fragmentSeasonBinding = fragmentSeasonBinding;
        this.seriesName = seriesName;
        this.seriesId = seriesId;
        this.seasonNumber = seasonNumber;
        if (context instanceof Interfaces.headerChangedListener) {
            listener = (Interfaces.headerChangedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement headerChangedListener");
        }
    }

    public void loadSeason() {
        final SeasonFragmentViewModel that = this;
        ApiHelper.subscribe(ApiWatchFriendsHelper.getWatchFriendsServiceInstance().getSeason(seriesId, seasonNumber),
            new Action1<Season>() {
                @Override
                public void call(Season returnedSeason) {
                    setSeason(returnedSeason);
                    season.initExtraFields();
                    if (season.getEpisodes().size() == 0) {
                        fragmentSeasonBinding.txtNoEpisodes.setVisibility(View.VISIBLE);
                    }
                    fragmentSeasonBinding.setViewmodel(that);
                    notifyPropertyChanged(BR.viewmodel);
                    setHeader();
                }
            });
    }

    private void setHeader() {
        listener.expandToolbar();
        listener.setTitle(seriesName);
        Picasso.with(context).load(season.getImage_uri()).into(listener.getHeaderImage());
        final FloatingActionButton fab = listener.getActionButton();
        fab.setVisibility(View.INVISIBLE);
    }
}