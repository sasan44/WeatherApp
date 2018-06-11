package ir.leafstudio.weatherapp.retrofit;


import ir.leafstudio.weatherapp.openweathermodel.Forecast;
import ir.leafstudio.weatherapp.openweathermodel.OpenWeather;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Sasan on 11/28/2017.
 */

public interface ApiService {

    String appid="856af595113e0bffc7a19161e7b6ba11" ;


    @GET("weather")
    Call<OpenWeather> getCurrentWeather(
             @Query("appid") String appid ,
             @Query("lat") double lat,
             @Query("lon") double    lon
     );
    @GET("forecast")
    Call<Forecast>  getFiveDayForecast(
             @Query("appid") String appid,
             @Query("lat") double lat,
             @Query("lon") double lon
     );



}
