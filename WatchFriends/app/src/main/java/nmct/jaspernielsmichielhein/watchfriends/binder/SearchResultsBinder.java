package nmct.jaspernielsmichielhein.watchfriends.binder;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.widget.ListView;

import nmct.jaspernielsmichielhein.watchfriends.adapter.SearchResultsAdapter;
import nmct.jaspernielsmichielhein.watchfriends.helper.Utils;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;

public class SearchResultsBinder {
    @BindingAdapter("items")
    public static void setSearchResults(ListView listView, ObservableArrayList<Series> series) {
        if (series != null) {
            SearchResultsAdapter searchResultsAdapter = new SearchResultsAdapter(listView.getContext(), listView);
            searchResultsAdapter.addAll(series);
            listView.setAdapter(searchResultsAdapter);
            Utils.setListViewHeightBasedOnChildren(listView);
        }
    }
}
