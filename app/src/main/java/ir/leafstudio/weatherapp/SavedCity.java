package ir.leafstudio.weatherapp;

import android.os.Parcel;
import android.os.Parcelable;

public class SavedCity implements Parcelable {
    private double lat;
    private double lon;
    private String name;

    SavedCity() {

    }

    public SavedCity(double lat, double lon, String name) {
        this.lat = lat;
        this.lon = lon;
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(this.lat);
        parcel.writeDouble(this.lon);
        parcel.writeString(this.name);
    }

    public SavedCity(Parcel in) {
        this.lat = in.readDouble();
        this.lon = in.readDouble();
        this.name = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SavedCity createFromParcel(Parcel in) {
            return new SavedCity(in);
        }

        public SavedCity[] newArray(int size) {
            return new SavedCity[size];
        }
    };
}
