package nmct.jaspernielsmichielhein.watchfriends.binder;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import nmct.jaspernielsmichielhein.watchfriends.adapter.SeriesListAdapter;
import nmct.jaspernielsmichielhein.watchfriends.helper.Utils;
import nmct.jaspernielsmichielhein.watchfriends.model.SeriesList;

public class SeriesListBinder {

    @BindingAdapter("items")
    public static void setSeriesList(RecyclerView view, ObservableArrayList<SeriesList> seriesLists) {
        SeriesListAdapter seriesListAdapter = new SeriesListAdapter(seriesLists, view.getContext(), null);

        view.setAdapter(seriesListAdapter);
        seriesListAdapter.notifyDataSetChanged();
    }
}
