package nmct.jaspernielsmichielhein.watchfriends.model;

import android.databinding.BaseObservable;

public class Genre extends BaseObservable {
    private int id = 0;
    private String name = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}