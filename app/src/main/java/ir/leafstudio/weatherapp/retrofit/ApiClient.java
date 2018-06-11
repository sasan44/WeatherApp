package ir.leafstudio.weatherapp.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sasan on 11/28/2017.


public class ApiClient {


    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
//    http://openweathermap.org/img/w/10d.png
    private static Retrofit retrofit = null;

    private static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiService getAPIService() {
        return ApiClient.getClient().create(ApiService.class);
    }

}
 */