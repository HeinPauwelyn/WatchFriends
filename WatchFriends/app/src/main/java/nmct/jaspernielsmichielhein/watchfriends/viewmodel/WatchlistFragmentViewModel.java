package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import android.content.Context;
import android.database.Cursor;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;

import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.Episode;
import nmct.jaspernielsmichielhein.watchfriends.provider.Contract;

public class WatchlistFragmentViewModel extends BaseObservable {
    private Interfaces.headerChangedListener listener;

    private Context context;

    private ObservableArrayList<Episode> episodes = null;

    public ObservableArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(ObservableArrayList<Episode> episodes) {
        this.episodes = episodes;
    }

    public WatchlistFragmentViewModel(Context context, ObservableArrayList<Episode> episodes) {
        this.context = context;
        this.episodes = episodes;

        if (episodes != null) {
            if (episodes.size() == 0) {
                //No next or missed episodes to watch
            }
        }

        if (context instanceof Interfaces.headerChangedListener) {
            listener = (Interfaces.headerChangedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement headerChangedListener");
        }
    }

    public void loadEpisodes() {
        setHeader();
        getFollowedSeries();
    }

    public void getFollowedSeries(){
        Cursor c = context.getContentResolver().query(
                Contract.FOLLOWEDSERIES_URI,
                null,
                null,
                null,
                null
        );
        Log.d("series count: ", Integer.toString(c.getCount()));
        c.close();
    }

    private void setHeader() {
        listener.collapseToolbar();
        listener.setTitle("To Watch");
        final FloatingActionButton fab = listener.getActionButton();
        fab.hide();
    }
}
