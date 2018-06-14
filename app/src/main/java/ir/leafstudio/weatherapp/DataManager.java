package ir.leafstudio.weatherapp;

import ir.leafstudio.weatherapp.openweathermodel.Forecast;
import ir.leafstudio.weatherapp.openweathermodel.OpenWeather;
import ir.leafstudio.weatherapp.retrofit.ApiService;
import ir.leafstudio.weatherapp.retrofit.CallServer;
import ir.leafstudio.weatherapp.retrofit.ServerLoadListener;

public class DataManager implements ServerLoadListener {
    ApiService apiService;

    public DataManager(ApiService apiService) {
        this.apiService = apiService;
    }

    OpenWeather getManagedCurrentData(SavedCity savedCity) {
        OpenWeather openWeather = getCurrentFromDB(savedCity);
        if (isOld(openWeather)) {
            loadingFromServer();
            CallServer callServer = new CallServer(this, apiService, savedCity);
            return null;
        } else {
            return openWeather;
        }
    }

    @Override
    public void onCurrentWeatherLoaded(OpenWeather openWeather, SavedCity city) {
        setToDB(openWeather, city);
        dataLoadListener.onCurrentWeatherLoaded(openWeather, city);
    }

    @Override
    public void onFiveDayForecastLoaded(Forecast forecast, SavedCity city) {
        setToDB(forecast, city);
        dataLoadListener.onFiveDayForecastLoaded(forecast, city);
    }

    @Override
    public void onCurrentWeatherLoadFailed() {

    }

    @Override
    public void onFiveDayForecastLoadFailed() {

    }

    @Override
    public void loadingFromServer() {

    }

    private boolean isOld(OpenWeather openWeather) {
        if (openWeather.getDt() > 10000)
            return true;
        else
            return false;
    }

    private boolean isOld(Forecast forecast) {
        if (forecast.getList().get(0).getDt() > 10000)
            return true;
        else
            return false;
    }

    OpenWeather getCurrentFromDB(SavedCity city) {
        return null;
    }

    Forecast getForecastFromDB(SavedCity city) {
        return null;
    }

    void setToDB(OpenWeather openWeather, SavedCity city) {

    }

    void setToDB(Forecast forecast, SavedCity city) {

    }

    public void loadFreshData(java.util.List<SavedCity> savedCityList) {
        for (SavedCity city : savedCityList) {
            CallServer callServer = new CallServer(this, apiService, city);
            callServer.setListener(this);
        }
        dataLoadListener.loadingFromServer();
    }

    DataLoadListener dataLoadListener;

    public void setListener(DataLoadListener listener) {
        this.dataLoadListener = listener;
    }


}
