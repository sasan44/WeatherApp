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
    SavedSettings savedSettings = new SavedSettings();
    public Presenter(ApiService apiService) {
        Timber.d("Presenter");
        this.apiService = apiService;

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
        return savedSettings.getListOfcities();
    }

    public void load() {
        for (SavedCity city : getSavedCityList()) {
            CallServer callServer = new CallServer(this,apiService, city);
            callServer.setListener(this);
        }
        uiListener.savedCityUpdated();

    }
}
