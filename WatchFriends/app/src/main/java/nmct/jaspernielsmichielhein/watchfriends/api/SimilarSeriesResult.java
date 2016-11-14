package nmct.jaspernielsmichielhein.watchfriends.api;

import android.databinding.ObservableArrayList;

import nmct.jaspernielsmichielhein.watchfriends.model.Series;

public class SimilarSeriesResult {
    private int page = 0;
    private ObservableArrayList<Series> results;
    private int total_pages = 0;
    private int total_result = 0;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ObservableArrayList<Series> getResults() {
        return results;
    }

    public void setResults(ObservableArrayList<Series> results) {
        this.results = results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_result() {
        return total_result;
    }

    public void setTotal_result(int total_result) {
        this.total_result = total_result;
    }
}