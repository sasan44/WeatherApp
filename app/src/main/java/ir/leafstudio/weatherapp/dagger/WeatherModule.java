package ir.leafstudio.weatherapp.dagger;

import dagger.Module;
import dagger.Provides;
import ir.leafstudio.weatherapp.retrofit.ApiService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    public static Retrofit getClient() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
