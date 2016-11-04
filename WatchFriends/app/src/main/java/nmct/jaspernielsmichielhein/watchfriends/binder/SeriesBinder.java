package nmct.jaspernielsmichielhein.watchfriends.binder;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.widget.ListView;

import nmct.jaspernielsmichielhein.watchfriends.adapter.SeriesAdapter;
import nmct.jaspernielsmichielhein.watchfriends.helper.Utils;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;

public class SeriesBinder {
    @BindingAdapter("items")
    public static void setSeries(ListView listView, ObservableList<Series> series) {
        if(series != null) {
            SeriesAdapter seriesAdapter = new SeriesAdapter(listView.getContext(), listView);
            series.addAll(series);
            listView.setAdapter(seriesAdapter);
            //Utils.setListViewHeightBasedOnChildren(listView);
        }
    }
}