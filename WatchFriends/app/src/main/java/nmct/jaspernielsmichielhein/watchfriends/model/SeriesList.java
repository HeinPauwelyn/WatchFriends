package nmct.jaspernielsmichielhein.watchfriends.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

public class SeriesList extends BaseObservable {

    private String created_by;
    @Bindable private String description;
    private int favorite_count;
    private String id;
    @Bindable ObservableArrayList<Series> items;
    private int item_count;
    private String iso_639_1;
    @Bindable String name;
    private String poster_path;

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ObservableArrayList<Series> getItems() {
        return items;
    }

    public void setItems(ObservableArrayList<Series> items) {
        this.items = items;
    }

    public int getItem_count() {
        return item_count;
    }

    public void setItem_count(int item_count) {
        this.item_count = item_count;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
}
