package ir.leafstudio.weatherapp;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import ir.leafstudio.weatherapp.dagger.ContextModule;
import ir.leafstudio.weatherapp.Timber.TimberLogImplementation;
import ir.leafstudio.weatherapp.dagger.DaggerWeatherAppComponent;
import ir.leafstudio.weatherapp.dagger.WeatherAppComponent;
import ir.leafstudio.weatherapp.retrofit.ApiService;

public class WeatherApp extends Application {

    @Inject
    Picasso picasso;
    @Inject
    ApiService apiService;
    @Inject
    MySharedPreferences sharedPreferences;

    WeatherAppComponent component;

    Presenter presenter;

    @Override
    public void onCreate() {
        super.onCreate();
        TimberLogImplementation.init();

        component = DaggerWeatherAppComponent.builder()
                .contextModule(new ContextModule(getApplicationContext()))
                .build();

//        sharedPreferencesComponent = DaggerSharedPreferencesComponent.builder()
//                .contextModule(new ContextModule(getApplicationContext()))
//                .sharedPreferencesModule(new SharedPreferencesModule())
//                .build();
//        sharedPreferencesComponent.provideSharedPreferences();

        picasso = component.getPicasso();
        presenter = component.getPresenter();
        apiService = component.getApiService();
        sharedPreferences = component.provideMySharedPreferences();

    }


    public static WeatherApp get(Activity activity) {
        return (WeatherApp) activity.getApplication();
    }

    public Picasso getPicasso() {
        return picasso;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public ApiService getApiService() {
        return apiService;
    }

    public WeatherAppComponent getComponent() {
        return component;
    }

}

