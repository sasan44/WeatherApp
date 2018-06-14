package ir.leafstudio.weatherapp.retrofit;

import android.util.Log;

import com.google.gson.Gson;

import ir.leafstudio.weatherapp.SavedCity;
import ir.leafstudio.weatherapp.openweathermodel.Forecast;
import ir.leafstudio.weatherapp.openweathermodel.OpenWeather;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class CallServer implements ApiService {
    //TODO add Timber & think if appid
    ServerLoadListener listener;
    String appid = "856af595113e0bffc7a19161e7b6ba11";
    SavedCity city;
    ApiService apiService;

    public CallServer(ServerLoadListener serverLoadListener, ApiService apiService, SavedCity savedCity) {
        this.listener = serverLoadListener;
        this.city = savedCity;
        this.apiService = apiService;
        getCurrentWeather(appid, city.getLat(), city.getLon());
        getFiveDayForecast(appid, city.getLat(), city.getLon());
        //   getFiveDayForecast(appid, lat, lon);
    }

    @Override
    public Call<OpenWeather> getCurrentWeather(String appid, double lat, double lon) {

        listener.loadingFromServer();
        Call<OpenWeather> call = apiService.getCurrentWeather(appid
                , lat
                , lon);
        call.enqueue(new Callback<OpenWeather>() {
            @Override
            public void onResponse(Call<OpenWeather> call, Response<OpenWeather> response) {
                //  Timber.d("onResponse" + new Gson().toJson(response.body()));
                if (response.body() != null)
                    listener.onCurrentWeatherLoaded(response.body(), city);
                else
                    Timber.d("NUL " + call.toString());
            }

            @Override
            public void onFailure(Call<OpenWeather> call, Throwable t) {
                Timber.d("onFailure" + call.toString());
                listener.onCurrentWeatherLoadFailed();
                t.printStackTrace();
            }
        });
        return null;
    }


    @Override
    public Call<Forecast> getFiveDayForecast(String appid, double lat, double lon) {
        listener.loadingFromServer();
        Call<Forecast> call = apiService.getFiveDayForecast(appid
                , lat
                , lon);
        call.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                //     Timber.d("onResponse" + new Gson().toJson(response.body()));
                if (response.body() != null)
                    listener.onFiveDayForecastLoaded(response.body(), city);
                else
                    Timber.d("NUL " + call.toString());
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Timber.d("onFailure" + call.toString());
                listener.onFiveDayForecastLoadFailed();
                t.printStackTrace();
            }
        });
        return null;
    }


    public void setListener(ServerLoadListener listener) {
        this.listener = listener;
    }
}
