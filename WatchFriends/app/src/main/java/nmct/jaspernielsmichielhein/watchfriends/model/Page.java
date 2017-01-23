package nmct.jaspernielsmichielhein.watchfriends.model;

import android.databinding.ObservableArrayList;

/**
 * Created by hein_ on 19-Nov-16.
 */

public class Page<TObject> {

    private int page;
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
