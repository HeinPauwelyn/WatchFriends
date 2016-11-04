package nmct.jaspernielsmichielhein.watchfriends.binder;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.widget.ListView;

import nmct.jaspernielsmichielhein.watchfriends.adapter.EpisodesAdapter;
import nmct.jaspernielsmichielhein.watchfriends.helper.Utils;
import nmct.jaspernielsmichielhein.watchfriends.model.Episode;

public class EpisodesBinder {
    @BindingAdapter("items")
    public static void setSeasonEpisodes(ListView listView, ObservableList<Episode> episodes) {
        if(episodes != null) {
            EpisodesAdapter episodesAdapter = new EpisodesAdapter(listView.getContext());
            episodesAdapter.addAll(episodes);
            listView.setAdapter(episodesAdapter);
            Utils.setListViewHeightBasedOnChildren(listView);
        }
    }
}