package nmct.jaspernielsmichielhein.watchfriends.binder;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.widget.ListView;

import nmct.jaspernielsmichielhein.watchfriends.adapter.SeasonsAdapter;
import nmct.jaspernielsmichielhein.watchfriends.helper.Utils;
import nmct.jaspernielsmichielhein.watchfriends.model.Season;
import nmct.jaspernielsmichielhein.watchfriends.model.Season;

public class SeasonsBinder {
    @BindingAdapter("items")
    public static void setSeasons(ListView listView, ObservableList<Season> seasons) {
        if(seasons != null) {
            SeasonsAdapter seasonsAdapter = new SeasonsAdapter(listView.getContext(), listView);
            seasonsAdapter.addAll(seasons);
            listView.setAdapter(seasonsAdapter);
            Utils.setListViewHeightBasedOnChildren(listView);
        }
    }
}