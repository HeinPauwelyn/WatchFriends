package nmct.jaspernielsmichielhein.watchfriends.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;

public class Page<TObject> extends BaseObservable {

    private int page;
    @Bindable
    private ObservableArrayList<TObject> results;
    private int totalResults;
    private int totalPages;

    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }

    public ObservableArrayList<TObject> getResults() {
        return results;
    }
    public void setResults(ObservableArrayList<TObject> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
