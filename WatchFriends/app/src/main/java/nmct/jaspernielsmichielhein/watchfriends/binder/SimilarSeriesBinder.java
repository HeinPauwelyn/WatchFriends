package nmct.jaspernielsmichielhein.watchfriends.binder;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.RecyclerView;

import nmct.jaspernielsmichielhein.watchfriends.adapter.SeriesAdapter;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;

public class SimilarSeriesBinder {
    @BindingAdapter("items")
    public static void setSimilarSeries(RecyclerView recyclerView, ObservableArrayList<Series> similarSeries) {
        if(similarSeries != null && similarSeries.size() != 0) {
            SeriesAdapter seriesAdapter = new SeriesAdapter(recyclerView.getContext(), similarSeries);
            recyclerView.setAdapter(seriesAdapter);
            seriesAdapter.notifyDataSetChanged();
        }
    }
}