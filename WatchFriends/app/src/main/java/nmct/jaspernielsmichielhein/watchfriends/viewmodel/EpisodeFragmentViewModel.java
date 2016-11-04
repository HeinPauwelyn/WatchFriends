package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import nmct.jaspernielsmichielhein.watchfriends.BR;
import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentEpisodeBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiHelper;
import nmct.jaspernielsmichielhein.watchfriends.model.Episode;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class EpisodeFragmentViewModel extends BaseObservable {
    private Context context;
    private FragmentEpisodeBinding fragmentEpisodeBinding;

    @Bindable
    private Episode episode = null;

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    public EpisodeFragmentViewModel(Context context, FragmentEpisodeBinding fragmentEpisodeBinding, Episode episode) {
        this.context = context;
        this.fragmentEpisodeBinding = fragmentEpisodeBinding;
        this.episode = episode;
    }

    public void loadEpisode() {
        /*ApiHelper.getMoviedbServiceInstance().getEpisode(seriesId, seasonNumber, episodeNumber).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Episode>() {
                    @Override
                    public void call(Episode returnedEpisode) {
                        setEpisode(returnedEpisode);
                        fragmentEpisodeBinding.setEpisode(episode);
                        notifyPropertyChanged(BR.episode);
                        loadImages();
                    }
                });*/
        fragmentEpisodeBinding.setEpisode(episode);
        notifyPropertyChanged(BR.episode);
        loadImages();
    }

    private void loadImages() {
        ImageView imgHeaderEpisode = (ImageView) ((Activity) context).findViewById(R.id.imgHeaderEpisode);
        Picasso.with(context).load(episode.getImage_uri()).into(imgHeaderEpisode);
    }
}