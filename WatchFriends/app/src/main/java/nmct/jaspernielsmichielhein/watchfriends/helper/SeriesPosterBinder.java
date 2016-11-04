package nmct.jaspernielsmichielhein.watchfriends.helper;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.widget.ListView;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;

public class SeriesPosterBinder {

    @BindingAdapter("items")
    public static void setItems (ListView listView, ObservableList<Series> series){
        SeriesAdapter adapter = new SeriesAdapter(listView.getContext());
        adapter.addAll(series);
        listView.setAdapter(adapter);
    }
}
