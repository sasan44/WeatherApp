package ir.leafstudio.weatherapp.Screens;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import ir.leafstudio.weatherapp.Presenter;
import ir.leafstudio.weatherapp.retrofit.ApiService;

@Module
public class MainActivityModule {

    private MainActivity mainActivity;

    MainActivityModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Provides
    @MainActivityScope
    Presenter getPresenter(ApiService apiService) {
        return new Presenter(apiService);
    }

    @Provides
    @MainActivityScope
    ScreenSlidePagerAdapter getScreenSlidePagerAdapter(Presenter presenter, Picasso picasso) {
        return new ScreenSlidePagerAdapter(mainActivity.getSupportFragmentManager(), presenter.getSavedCityList(), picasso);
    }

    @Provides
    @MainActivityScope
    FragmentManager injectFragmentManager(MainActivity mainActivity) {
        return mainActivity.getSupportFragmentManager();
    }
}
