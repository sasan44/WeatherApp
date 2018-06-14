package ir.leafstudio.weatherapp;

import ir.leafstudio.weatherapp.openweathermodel.Forecast;
import ir.leafstudio.weatherapp.openweathermodel.OpenWeather;

public interface DataLoadListener {

    void onCurrentWeatherLoaded(OpenWeather openWeather , SavedCity city);
    void onFiveDayForecastLoaded(Forecast forecast , SavedCity city);
    void onCurrentWeatherLoadFailed();
    void onFiveDayForecastLoadFailed();
    void loadingFromServer();
}
