package nmct.jaspernielsmichielhein.watchfriends.binder;

import android.databinding.BindingAdapter;
import android.widget.ListView;

import nmct.jaspernielsmichielhein.watchfriends.adapter.EpisodesAdapter;
import nmct.jaspernielsmichielhein.watchfriends.helper.Utils;
import nmct.jaspernielsmichielhein.watchfriends.viewmodel.SeasonFragmentViewModel;

public class EpisodesBinder {
    @BindingAdapter("items")
    public static void setEpisodes(ListView listView, SeasonFragmentViewModel seasonFragmentViewModel) {
        if (seasonFragmentViewModel != null && seasonFragmentViewModel.getSeason().getEpisodes() != null) {
            EpisodesAdapter episodesAdapter = new EpisodesAdapter(listView.getContext(), listView, seasonFragmentViewModel.getSeriesId());
            episodesAdapter.addAll(seasonFragmentViewModel.getSeason().getEpisodes());
            listView.setAdapter(episodesAdapter);
            Utils.setListViewHeightBasedOnChildren(listView);
        }
    }
}