package ir.leafstudio.weatherapp.dagger;

import com.squareup.picasso.Picasso;

import dagger.Component;
import ir.leafstudio.weatherapp.retrofit.ApiService;

@WeatherApplicationScope
@Component(modules = {WeatherModule.class, PicassoModule.class})
public interface WeatherAppComponent {

    Picasso getPicasso();

    ApiService getApiService();

}
