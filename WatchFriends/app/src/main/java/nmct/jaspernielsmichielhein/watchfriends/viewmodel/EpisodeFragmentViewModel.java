package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import android.content.Context;
import android.content.res.ColorStateList;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.squareup.picasso.Picasso;

import nmct.jaspernielsmichielhein.watchfriends.BR;
import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentEpisodeBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.Episode;

public class EpisodeFragmentViewModel extends BaseObservable {
    private Interfaces.headerChangedListener listener;

    private Context context;
    private FragmentEpisodeBinding fragmentEpisodeBinding;

    private Episode episode = null;

    @Bindable
    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    private boolean watched = false;

    public EpisodeFragmentViewModel(Context context, FragmentEpisodeBinding fragmentEpisodeBinding, Episode episode) {
        this.context = context;
        this.fragmentEpisodeBinding = fragmentEpisodeBinding;
        this.episode = episode;
        if (context instanceof Interfaces.headerChangedListener) {
            listener = (Interfaces.headerChangedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement headerChangedListener");
        }
    }

    public void loadEpisode() {
        fragmentEpisodeBinding.setViewmodel(this);
        notifyPropertyChanged(BR.viewmodel);
        setHeader();
    }

    private void setHeader() {
        listener.expandToolbar();
        listener.setTitle(episode.getShortcode());
        Picasso.with(context).load(episode.getImage_uri()).into(listener.getHeaderImage());
        final FloatingActionButton fab = listener.getActionButton();
        initFloatingActionButton(fab);
    }

    private void initFloatingActionButton(final FloatingActionButton fab) {
        if (watched) {
            fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.themeBlack)));
            fab.setImageResource(R.drawable.ic_visibility_off_white_24dp);
        } else {
            fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.themeAccent)));
            fab.setImageResource(R.drawable.ic_visibility_white_24dp);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (watched) {
                    fab.setImageResource(R.drawable.ic_visibility_white_24dp);
                    fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.themeAccent)));
                    Snackbar.make(v, "Marked episode as not watched", Snackbar.LENGTH_LONG).show();
                } else {
                    fab.setImageResource(R.drawable.ic_visibility_off_white_24dp);
                    fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.themeBlack)));
                    Snackbar.make(v, "Marked episode as watched", Snackbar.LENGTH_LONG).show();
                }
                watched = !watched;
            }
        });
    }
}