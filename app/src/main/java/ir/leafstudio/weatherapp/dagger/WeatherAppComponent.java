package ir.leafstudio.weatherapp.dagger;

import android.content.SharedPreferences;

import com.squareup.picasso.Picasso;

import dagger.Component;
import ir.leafstudio.weatherapp.MySharedPreferences;
import ir.leafstudio.weatherapp.Presenter;
import ir.leafstudio.weatherapp.retrofit.ApiService;

@WeatherApplicationScope
@Component(modules = {WeatherModule.class, PicassoModule.class, SharedPreferencesModule.class})
public interface WeatherAppComponent {

    Presenter getPresenter();

    Picasso getPicasso();

    ApiService getApiService();

    MySharedPreferences provideMySharedPreferences();


}
