package nmct.jaspernielsmichielhein.watchfriends.binder;

import android.databinding.BindingAdapter;
import android.widget.ListView;

import nmct.jaspernielsmichielhein.watchfriends.adapter.SeasonsAdapter;
import nmct.jaspernielsmichielhein.watchfriends.helper.Utils;
import nmct.jaspernielsmichielhein.watchfriends.viewmodel.SeriesFragmentViewModel;

public class SeasonsBinder {
    @BindingAdapter("items")
    public static void setSeasons(ListView listView, SeriesFragmentViewModel seriesFragmentViewModel) {
        if(seriesFragmentViewModel != null && seriesFragmentViewModel.getSeries().getSeasons() != null) {
            SeasonsAdapter seasonsAdapter = new SeasonsAdapter(listView.getContext(), listView, seriesFragmentViewModel.getSeries().getName(), seriesFragmentViewModel.getSeries().getId());
            seasonsAdapter.addAll(seriesFragmentViewModel.getSeries().getSeasons());
            listView.setAdapter(seasonsAdapter);
            Utils.setListViewHeightBasedOnChildren(listView);
        }
    }
}