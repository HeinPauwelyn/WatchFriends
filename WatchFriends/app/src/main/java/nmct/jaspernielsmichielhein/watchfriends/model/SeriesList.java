package nmct.jaspernielsmichielhein.watchfriends.model;


import android.databinding.Bindable;
import android.databinding.ObservableArrayList;

public class SeriesList extends Page<Series> {
    @Bindable
    private String name;
    private String apiRequest;
    @Bindable
    private ObservableArrayList<Series> series;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApiRequest() {
        return apiRequest;
    }

    public void setApiRequest(String apiRequest) {
        this.apiRequest = apiRequest;
    }

    public ObservableArrayList<Series> getSeries() {
        return series;
    }

    public void setSeries(ObservableArrayList<Series> series) {
        this.series = series;
    }
}
