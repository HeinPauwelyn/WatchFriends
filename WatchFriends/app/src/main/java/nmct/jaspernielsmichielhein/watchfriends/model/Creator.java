package nmct.jaspernielsmichielhein.watchfriends.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

public class Creator extends BaseObservable implements Parcelable {
    private int id = 0;
    private String name = "";
    private String profile_path = "";

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    // PARCELABLE
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.getId());
        dest.writeString(this.getName());
        dest.writeString(this.getProfile_path());
    }

    public static final Creator<nmct.jaspernielsmichielhein.watchfriends.model.Creator> CREATOR = new Creator<nmct.jaspernielsmichielhein.watchfriends.model.Creator>() {
        @Override
        public nmct.jaspernielsmichielhein.watchfriends.model.Creator createFromParcel(Parcel in) {
            return new nmct.jaspernielsmichielhein.watchfriends.model.Creator(in);
        }

        @Override
        public nmct.jaspernielsmichielhein.watchfriends.model.Creator[] newArray(int size) {
            return new nmct.jaspernielsmichielhein.watchfriends.model.Creator[size];
        }
    };

    protected Creator(Parcel in) {
        setId(in.readInt());
        setName(in.readString());
        setProfile_path(in.readString());
    }
}