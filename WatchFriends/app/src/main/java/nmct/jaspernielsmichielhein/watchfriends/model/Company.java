package nmct.jaspernielsmichielhein.watchfriends.model;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

public class Company extends BaseObservable implements Parcelable {
    private String description = "";
    private String headquarters = "";
    private String homepage = "";
    private int id = 0;
    private String logo_path = "";
    private String name = "";

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public void setLogo_path(String logo_path) {
        this.logo_path = logo_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // PARCELABLE
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getDescription());
        dest.writeString(this.getHeadquarters());
        dest.writeString(this.getHomepage());
        dest.writeInt(this.getId());
        dest.writeString(this.getLogo_path());
        dest.writeString(this.getName());
    }

    public static final Creator<Company> CREATOR = new Creator<Company>() {
        @Override
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        @Override
        public Company[] newArray(int size) {
            return new Company[size];
        }
    };

    protected Company(Parcel in) {
        setDescription(in.readString());
        setHeadquarters(in.readString());
        setHomepage(in.readString());
        setId(in.readInt());
        setLogo_path(in.readString());
        setName(in.readString());
    }
}