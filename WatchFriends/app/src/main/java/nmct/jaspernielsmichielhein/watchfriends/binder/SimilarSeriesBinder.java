package nmct.jaspernielsmichielhein.watchfriends.binder;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.widget.ListView;

import nmct.jaspernielsmichielhein.watchfriends.adapter.SimilarSeriesAdapter;
import nmct.jaspernielsmichielhein.watchfriends.helper.Utils;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;

public class SimilarSeriesBinder {
    @BindingAdapter("items")
    public static void setSeries(ListView listView, ObservableList<Series> similarSeries) {
        if(similarSeries != null) {
            SimilarSeriesAdapter similarSeriesAdapter = new SimilarSeriesAdapter(listView.getContext(), listView);
            similarSeriesAdapter.addAll(similarSeries);
            listView.setAdapter(similarSeriesAdapter);
            Utils.setListViewHeightBasedOnChildren(listView);
        }
    }
}