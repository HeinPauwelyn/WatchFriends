package nmct.jaspernielsmichielhein.watchfriends.model;


import android.databinding.Bindable;

public class SeriesList extends Page<Series> {
    @Bindable
    private String name;
    private String apiRequest;

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
}
