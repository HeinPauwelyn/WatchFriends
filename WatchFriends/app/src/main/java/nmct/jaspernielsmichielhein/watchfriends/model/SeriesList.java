package nmct.jaspernielsmichielhein.watchfriends.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;

import java.util.ArrayList;

public class SeriesList extends BaseObservable {

    @Bindable
    private String name;
    @Bindable
    private ObservableArrayList<Series> series;

    public SeriesList() {
    }

    public SeriesList(String name, ObservableArrayList<Series> series) {
        this.name = name;
        this.series = series;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObservableArrayList<Series> getSeries() {
        return series;
    }

    public void setSeries(ObservableArrayList<Series> series) {
        this.series = series;
    }
}
