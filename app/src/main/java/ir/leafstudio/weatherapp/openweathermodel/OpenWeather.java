package ir.leafstudio.weatherapp.openweathermodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.leafstudio.weatherapp.openweathermodel.currentweather.Clouds;
import ir.leafstudio.weatherapp.openweathermodel.currentweather.Coord;
import ir.leafstudio.weatherapp.openweathermodel.currentweather.Main;
import ir.leafstudio.weatherapp.openweathermodel.currentweather.Rain;
import ir.leafstudio.weatherapp.openweathermodel.currentweather.Sys;
import ir.leafstudio.weatherapp.openweathermodel.currentweather.Weather;
import ir.leafstudio.weatherapp.openweathermodel.currentweather.Wind;

/**
 * Created by Sasan on 12/10/2017.
 */
public class OpenWeather {

    @SerializedName("coord")
    @Expose
    private Coord coord;
    @SerializedName("weather")
    @Expose
    private List<Weather> weather = null;
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;
    @SerializedName("rain")
    @Expose
    private Rain rain;
    @SerializedName("dt")
    @Expose
    private double dt;
    @SerializedName("sys")
    @Expose
    private Sys sys;
    @SerializedName("id")
    @Expose
    private double id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cod")
    @Expose
    private double cod;


    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public double getDt() {
        return dt;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCod() {
        return cod;
    }

    public void setCod(double cod) {
        this.cod = cod;
    }

}
