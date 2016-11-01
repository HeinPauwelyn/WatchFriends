package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import nmct.jaspernielsmichielhein.watchfriends.BR;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentEpisodeBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiHelper;
import nmct.jaspernielsmichielhein.watchfriends.model.Episode;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class EpisodeFragmentViewModel extends BaseObservable {
    private FragmentEpisodeBinding fragmentEpisodeBinding;

    private int serieId;
    private int seasonNumber;
    private int episodeNumber;

    @Bindable
    private Episode episode = null;

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    public EpisodeFragmentViewModel(FragmentEpisodeBinding fragmentEpisodeBinding, int serieId, int seasonNumber, int episodeNumber) {
        this.fragmentEpisodeBinding = fragmentEpisodeBinding;
        this.serieId = serieId;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
    }

    public void loadEpisode() {
        ApiHelper.getMoviedbServiceInstance().getEpisode(serieId, seasonNumber, episodeNumber).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Episode>() {
                    @Override
                    public void call(Episode episode) {
                        fragmentEpisodeBinding.setEpisode(episode);
                        notifyPropertyChanged(BR.episode);
                    }
                });
    }
}