package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;

import nmct.jaspernielsmichielhein.watchfriends.api.SearchResult;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentSearchBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiMovieDbHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import rx.functions.Action1;

public class SearchFragmentViewModel extends BaseObservable {
    private Interfaces.headerChangedListener listener;

    private Context context;
    private FragmentSearchBinding fragmentSearchBinding;

    private ObservableArrayList<Series> searchResults = null;

    public ObservableArrayList<Series> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(ObservableArrayList<Series> searchResults) {
        this.searchResults = searchResults;
    }

    private String query = "";

    public SearchFragmentViewModel(Context context, FragmentSearchBinding fragmentSearchBinding, String query) {
        this.context = context;
        this.fragmentSearchBinding = fragmentSearchBinding;
        this.query = query;
        if (context instanceof Interfaces.headerChangedListener) {
            listener = (Interfaces.headerChangedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement headerChangedListener");
        }
    }

    public void search() {
        fragmentSearchBinding.setViewmodel(this);
        notifyPropertyChanged(BR.viewmodel);
        setHeader();
        loadSearchResults();
    }

    private void setHeader() {
        listener.collapseToolbar();
        listener.setTitle("Search results");
    }

    private void loadSearchResults() {
        setSearchResults(new ObservableArrayList<Series>());
        ApiHelper.subscribe(ApiMovieDbHelper.getMoviedbServiceInstance().getSearchResults(query), new Action1<SearchResult>() {
            @Override
            public void call(SearchResult searchResult) {
                ObservableArrayList<Series> series = new ObservableArrayList<Series>();
                if (searchResult != null) {
                    series = searchResult.getResults();
                }

                if (series.size() != 0) {
                    fragmentSearchBinding.txtNoResults.setVisibility(View.INVISIBLE);
                    fragmentSearchBinding.lvSearchResults.setVisibility(View.VISIBLE);
                    for (int i = 0; i < series.size(); i++) {
                        searchResults.add(series.get(i));
                        notifyPropertyChanged(BR.viewmodel);
                    }
                } else {
                    fragmentSearchBinding.txtNoResults.setVisibility(View.VISIBLE);
                    fragmentSearchBinding.lvSearchResults.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}