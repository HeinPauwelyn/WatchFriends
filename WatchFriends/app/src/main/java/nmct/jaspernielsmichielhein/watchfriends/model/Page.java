package nmct.jaspernielsmichielhein.watchfriends.model;

import android.databinding.ObservableArrayList;

/**
 * Created by hein_ on 19-Nov-16.
 */

public class Page<TObject> {

    private int page;
    private ObservableArrayList<TObject> results;
    private int total_results;
    private int total_pages;

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

    public int getTotal_results() {
        return total_results;
    }
    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }
    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }
}
