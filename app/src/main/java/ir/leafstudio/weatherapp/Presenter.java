package ir.leafstudio.weatherapp;

import java.util.List;

import ir.leafstudio.weatherapp.Screens.UIListener;
import ir.leafstudio.weatherapp.openweathermodel.Forecast;
import ir.leafstudio.weatherapp.openweathermodel.OpenWeather;
import ir.leafstudio.weatherapp.retrofit.ApiService;
import ir.leafstudio.weatherapp.retrofit.CallServer;
import ir.leafstudio.weatherapp.retrofit.ServerLoadListener;
import timber.log.Timber;

public class Presenter implements ServerLoadListener, Settings { //DataLoadListener
    UIListener uiListener;

    ApiService apiService;
    private SavedSettings savedSettings;

    public Presenter(ApiService apiService, SavedSettings savedSettings) {
        this.apiService = apiService;
        this.savedSettings = savedSettings;
        Timber.d("Presenter " + savedSettings.getListOfcities());

    }

    //ServerLoadListener
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

    public void loadFreshData() {
        for (SavedCity city : getSavedCityList()) {
            CallServer callServer = new CallServer(this, apiService, city);
            callServer.setListener(this);
        }
        uiListener.listOfcitiesUpdated();

    }

    public SavedSettings getSavedSettings() {
        return savedSettings;
    }

    public void setSavedSettings(SavedSettings savedSettings) {
        this.savedSettings = savedSettings;
    }

    //Settings
    @Override
    public List<SavedCity> getSavedCityList() {
        return getSavedSettings().getListOfcities();
    }

    @Override
    public void listOfcitiesUpdated() {
        uiListener.listOfcitiesUpdated();
        loadFreshData();
    }

    @Override
    public void deleteFromListofCities(SavedCity savedCity) {
        savedSettings.removeCity(savedCity);
        listOfcitiesUpdated();
    }

    @Override
    public void addToListofCities(SavedCity savedCity) {
        savedSettings.addCity(savedCity);
        listOfcitiesUpdated();

    }

    @Override
    public void deleteFromListofCities(int savedCity) {
        savedSettings.removeCity(savedCity);
        listOfcitiesUpdated();
    }
}
