package ir.leafstudio.weatherapp.Screens;

import ir.leafstudio.weatherapp.SavedCity;
import ir.leafstudio.weatherapp.openweathermodel.Forecast;
import ir.leafstudio.weatherapp.openweathermodel.OpenWeather;

public interface fragmentUpdater {
    void update(OpenWeather body, SavedCity savedCity);

    void updateForecast(Forecast body, SavedCity savedCity);
}
