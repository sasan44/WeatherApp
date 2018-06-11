package ir.leafstudio.weatherapp;

import android.app.Activity;
import android.app.Application;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import ir.leafstudio.weatherapp.Timber.TimberLogImplementation;
import ir.leafstudio.weatherapp.dagger.WeatherAppComponent;
import ir.leafstudio.weatherapp.retrofit.ApiService;

public class WeatherApp extends Application {

    @Inject
    Picasso picasso;
    @Inject
    ApiService apiService;
    WeatherAppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        TimberLogImplementation.init();

//        component = DaggerWeatherAppComponent.builder()
//                .contextModule(new ContextModule(getApplicationContext()))
//                .build();
//
//        picasso = component.getPicasso();
//        apiService = component.getApiService();

    }

    public static WeatherApp get(Activity activity) {
        return (WeatherApp) activity.getApplication();
    }

    public Picasso getPicasso() {
        return picasso;
    }

    public ApiService getApiService() {
        return apiService;
    }

    public WeatherAppComponent getComponent() {
        return component;
    }
}

