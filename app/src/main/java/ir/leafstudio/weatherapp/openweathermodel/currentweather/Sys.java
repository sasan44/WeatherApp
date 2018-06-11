package ir.leafstudio.weatherapp.openweathermodel.currentweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sasan on 12/10/2017.
 */
public class Sys {

    @SerializedName("type")
    @Expose
    private Double type;
    @SerializedName("id")
    @Expose
    private Double id;
    @SerializedName("message")
    @Expose
    private Double message;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("sunrise")
    @Expose
    private Double sunrise;
    @SerializedName("sunset")
    @Expose
    private Double sunset;

    public Double getType() {
        return type;
    }

    public void setType(Double type) {
        this.type = type;
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getSunrise() {
        return sunrise;
    }

    public void setSunrise(Double sunrise) {
        this.sunrise = sunrise;
    }

    public Double getSunset() {
        return sunset;
    }

    public void setSunset(Double sunset) {
        this.sunset = sunset;
    }

}
