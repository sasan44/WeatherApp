package ir.leafstudio.weatherapp.Screens;

import ir.leafstudio.weatherapp.SavedCity;
import ir.leafstudio.weatherapp.openweathermodel.Forecast;
import ir.leafstudio.weatherapp.openweathermodel.OpenWeather;

public interface UIListener {
    void isLoading();

    void updateUIcurrentWeather(OpenWeather body, SavedCity city);//TODO data

    void updateUIForcastWeather(Forecast body, SavedCity city);//TODO data

    void listOfcitiesUpdated();
}
