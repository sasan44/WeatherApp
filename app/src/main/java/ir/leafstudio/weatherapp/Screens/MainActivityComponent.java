package ir.leafstudio.weatherapp.Screens;


import dagger.Component;
import ir.leafstudio.weatherapp.Presenter;
import ir.leafstudio.weatherapp.dagger.WeatherAppComponent;
import ir.leafstudio.weatherapp.retrofit.ApiService;

@Component(modules = {MainActivityModule.class  },dependencies = WeatherAppComponent.class)
@MainActivityScope
public interface MainActivityComponent {

//    void injectFragmentManager(MainActivity mainActivity);
    Presenter getPresenter();

    ApiService getApiService();

    ScreenSlidePagerAdapter getScreenSlidePagerAdapter();

}
