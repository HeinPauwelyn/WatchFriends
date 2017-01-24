package nmct.jaspernielsmichielhein.watchfriends.model;


public class List extends Page<Series> {
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
