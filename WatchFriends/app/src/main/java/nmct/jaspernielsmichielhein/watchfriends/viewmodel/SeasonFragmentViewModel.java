package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import nmct.jaspernielsmichielhein.watchfriends.BR;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentSeasonBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiHelper;
import nmct.jaspernielsmichielhein.watchfriends.model.Season;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SeasonFragmentViewModel extends BaseObservable {
    private Context context;
    private FragmentSeasonBinding fragmentSeasonBinding;

    private int seriesId;
    private int seasonNumber;

    @Bindable
    private Season season = null;

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public SeasonFragmentViewModel(Context context, FragmentSeasonBinding fragmentSeasonBinding, int seriesId, int seasonNumber) {
        this.context = context;
        this.fragmentSeasonBinding = fragmentSeasonBinding;
        this.seriesId = seriesId;
        this.seasonNumber = seasonNumber;
    }

    public void loadSeason(final SeasonFragmentViewModel seasonFragmentViewModel) {
        ApiHelper.getMoviedbServiceInstance().getSeason(seriesId, seasonNumber).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Season>() {
                    @Override
                    public void call(Season returnedSeason) {
                        setSeason(returnedSeason);
                        fragmentSeasonBinding.setViewmodel(seasonFragmentViewModel);
                        notifyPropertyChanged(BR.viewmodel);
                        /*fragmentSeasonBinding.setSeason(season);
                        fragmentSeasonBinding.setSeasonEpisodes(season.getEpisodes());
                        notifyPropertyChanged(BR.season);
                        notifyPropertyChanged(BR.seasonEpisodes);*/
                    }
                });
    }
}