package nmct.jaspernielsmichielhein.watchfriends.model;

import android.databinding.BaseObservable;

public class User extends BaseObservable {

    private String email;
    private String id;
    private Name name;
    private String picture;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
