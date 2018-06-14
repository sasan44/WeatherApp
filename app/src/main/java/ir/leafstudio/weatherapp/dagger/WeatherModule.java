package ir.leafstudio.weatherapp.dagger;

import com.fatboyindustrial.gsonjodatime.DateTimeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import ir.leafstudio.weatherapp.MySharedPreferences;
import ir.leafstudio.weatherapp.Presenter;
import ir.leafstudio.weatherapp.SavedSettings;
import ir.leafstudio.weatherapp.Screens.MainActivityScope;
import ir.leafstudio.weatherapp.retrofit.ApiService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import org.joda.time.DateTime;

@Module(includes = {NetworkModule.class})
public class WeatherModule {
    @Provides
    @WeatherApplicationScope
    public ApiService getApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    @Provides
    @WeatherApplicationScope
    public Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeConverter());
        return gsonBuilder.create();
    }

    @Provides
    @WeatherApplicationScope
    public static Retrofit getClient(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }



    @Provides
    @WeatherApplicationScope
    Presenter getPresenter(ApiService apiService , SavedSettings savedSettings) {
        return new Presenter(apiService , savedSettings);
    }

}
