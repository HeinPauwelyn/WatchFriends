package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;

import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentSearchBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiWatchFriendsHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.AuthHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.Page;
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
        listener.setTitle("Search results");
        listener.enableAppBarScroll(false);
        listener.getHeaderImage().setImageResource(0);
    }

    private void loadSearchResults() {
        setSearchResults(new ObservableArrayList<Series>());
        ApiHelper.subscribe(ApiWatchFriendsHelper.getWatchFriendsServiceInstance().searchSeries(query, 1, AuthHelper.getAuthToken(this.context)), new Action1<Page<Series>>() {
            @Override
            public void call(Page<Series> searchResult) {
                ObservableArrayList<Series> series = new ObservableArrayList<Series>();
                if (searchResult != null) {
                    series = searchResult.getResults();
                }

                if (series.size() != 0) {
                    fragmentSearchBinding.txtNoResults.setVisibility(View.GONE);
                    fragmentSearchBinding.lvSearchResults.setVisibility(View.VISIBLE);
                    for (int i = 0; i < series.size(); i++) {
                        searchResults.add(series.get(i));
                        notifyPropertyChanged(BR.viewmodel);
                    }
                } else {
                    fragmentSearchBinding.txtNoResults.setVisibility(View.VISIBLE);
                    fragmentSearchBinding.lvSearchResults.setVisibility(View.GONE);
                }
            }
        });
    }
}