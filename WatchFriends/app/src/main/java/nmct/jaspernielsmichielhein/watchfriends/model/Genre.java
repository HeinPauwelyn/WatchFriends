package nmct.jaspernielsmichielhein.watchfriends.model;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

public class Genre extends BaseObservable implements Parcelable {
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

    // PARCELABLE
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.getId());
        dest.writeString(this.getName());
    }

    public static final Creator<Genre> CREATOR = new Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel in) {
            return new Genre(in);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };

    protected Genre(Parcel in) {
        setId(in.readInt());
        setName(in.readString());
    }
}