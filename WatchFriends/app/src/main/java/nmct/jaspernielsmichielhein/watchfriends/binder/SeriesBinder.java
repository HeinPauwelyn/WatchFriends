package nmct.jaspernielsmichielhein.watchfriends.binder;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.RecyclerView;

import nmct.jaspernielsmichielhein.watchfriends.adapter.SeriesAdapter;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;

public class SeriesBinder {
    /*@BindingAdapter("items")
    public static void setSeries(ListView listView, ObservableList<Series> series) {
        if(series != null) {
            SeriesAdapter seriesAdapter = new SeriesAdapter(listView.getContext(), listView);
            seriesAdapter.addAll(series);
            listView.setAdapter(seriesAdapter);
            //Utils.setListViewHeightBasedOnChildren(listView);
        }
    }*/

    @BindingAdapter("items")
    public static void setSeries(RecyclerView recyclerView, ObservableArrayList<Series> series) {
        if(series != null) {
            SeriesAdapter seriesAdapter = new SeriesAdapter(recyclerView.getContext(), series);
            recyclerView.setAdapter(seriesAdapter);
            seriesAdapter.notifyDataSetChanged();
            //Utils.setListViewHeightBasedOnChildren(listView);
        }
    }
}