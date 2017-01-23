package nmct.jaspernielsmichielhein.watchfriends.model;

import android.databinding.ObservableArrayList;

/**
 * Created by jasper on 23/01/17.
 */

public class WFEventsPage {

    private ObservableArrayList<WFEvent> docs;
    private int total;
    private int limit;
    private String page;
    private int pages;

    public ObservableArrayList<WFEvent> getDocs() {
        return docs;
    }

    public void setDocs(ObservableArrayList<WFEvent> docs) {
        this.docs = docs;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
