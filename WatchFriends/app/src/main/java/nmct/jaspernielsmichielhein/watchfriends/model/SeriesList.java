package nmct.jaspernielsmichielhein.watchfriends.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import java.util.ArrayList;

public class SeriesList extends BaseObservable {

    private String _name;
    private ArrayList<Series> _series;

    public SeriesList() {
    }

    public SeriesList(String name, ArrayList<Series> series) {
        _name = name;
        _series = series;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public ArrayList<Series> get_series() {
        return _series;
    }

    public void set_series(ArrayList<Series> _series) {
        this._series = _series;
    }
}
