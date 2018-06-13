package ir.leafstudio.weatherapp;

import java.util.List;

import ir.leafstudio.weatherapp.Screens.UIListener;
import ir.leafstudio.weatherapp.openweathermodel.Forecast;
import ir.leafstudio.weatherapp.openweathermodel.OpenWeather;
import ir.leafstudio.weatherapp.retrofit.ApiService;
import ir.leafstudio.weatherapp.retrofit.CallServer;
import ir.leafstudio.weatherapp.retrofit.ServerLoadListener;
import timber.log.Timber;

public class Presenter implements ServerLoadListener {
    UIListener uiListener;

    ApiService apiService;
    private SavedSettings savedSettings;

    public Presenter(ApiService apiService, MySharedPreferences sharedPreferences) {
        this.apiService = apiService;
        setSavedSettings(new SavedSettings(sharedPreferences));
        Timber.d("Presenter " + sharedPreferences.getDataList());

    }

    @Override
    public void onCurrentWeatherLoaded(OpenWeather openWeather, SavedCity city) {
        Timber.d("onCurrentWeatherLoaded : " + openWeather + " , City :" + city);
        uiListener.updateUIcurrentWeather(openWeather, city);
    }

    @Override
    public void onFiveDayForecastLoaded(Forecast forecast, SavedCity city) {
        Timber.d("onFiveDayForecastLoaded " + forecast + city);
        uiListener.updateUIForcastWeather(forecast, city);
    }

    @Override
    public void onCurrentWeatherLoadFailed() {

    }

    @Override
    public void onFiveDayForecastLoadFailed() {

    }

    @Override
    public void loadingFromServer() {
        uiListener.isLoading();
    }

    public void setListener(UIListener listener) {
        this.uiListener = listener;
    }

    public List<SavedCity> getSavedCityList() {
        return getSavedSettings().getListOfcities();
    }

    public void load() {
        for (SavedCity city : getSavedCityList()) {
            CallServer callServer = new CallServer(this, apiService, city);
            callServer.setListener(this);
        }
        uiListener.savedCityUpdated();

    }

    public SavedSettings getSavedSettings() {
        return savedSettings;
    }

    public void setSavedSettings(SavedSettings savedSettings) {
        this.savedSettings = savedSettings;
    }

    public void listOfcitiesUpdated() {
        uiListener.listOfcitiesUpdated();
        load();
    }

    public void deleteFromListofCities(SavedCity savedCity) {
        savedSettings.removeCity(savedCity);
    }

    public void addToListofCities(SavedCity savedCity) {
        savedSettings.addCity(savedCity);

    }

    public void deleteFromListofCities(int savedCity) {
        savedSettings.removeCity(savedCity);
        uiListener.listOfcitiesUpdated();
    }
}
