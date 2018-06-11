package ir.leafstudio.weatherapp.retrofit;

import ir.leafstudio.weatherapp.SavedCity;
import ir.leafstudio.weatherapp.openweathermodel.Forecast;
import ir.leafstudio.weatherapp.openweathermodel.OpenWeather;

public interface ServerLoadListener {

    void onCurrentWeatherLoaded(OpenWeather openWeather , SavedCity city);
    void onFiveDayForecastLoaded(Forecast forecast , SavedCity city);
    void onCurrentWeatherLoadFailed();
    void onFiveDayForecastLoadFailed();
    void loadingFromServer();
}
