package ir.leafstudio.weatherapp.Screens;

import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.leafstudio.weatherapp.Presenter;
import ir.leafstudio.weatherapp.R;
import ir.leafstudio.weatherapp.SavedCity;
import ir.leafstudio.weatherapp.WeatherApp;
import ir.leafstudio.weatherapp.openweathermodel.Forecast;
import ir.leafstudio.weatherapp.openweathermodel.OpenWeather;
import ir.leafstudio.weatherapp.retrofit.ApiService;
import ir.leafstudio.weatherapp.Screens.todayfragment.TodayFragment;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements UIListener {
    @Inject
    ScreenSlidePagerAdapter mPagerAdapter;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @Inject
    ApiService apiService;
    @Inject
    Picasso picasso;
    @Inject
    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        this.picasso = WeatherApp.get(this).getPicasso();

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        this.setSupportActionBar(myToolbar);

        ConstraintLayout constraintLayout = findViewById(R.id.root_layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setAlpha(100);
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();


        MainActivityComponent mainActivityComponent = DaggerMainActivityComponent.builder()
                .mainActivityModule(new MainActivityModule(this))
                .weatherAppComponent(WeatherApp.get(this).getComponent())
                .build();

//        mainActivityComponent.injectMainActivity(this);
        this.presenter = mainActivityComponent.getPresenter();
        this.apiService = mainActivityComponent.getApiService();
        this.mPagerAdapter = mainActivityComponent.getScreenSlidePagerAdapter();
//       Presenter presenter  = new Presenter(apiService);
        presenter.setListener(this);
//        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), presenter.getSavedCityList(), picasso);
        viewpager.setOffscreenPageLimit(5);         /* limit is a fixed integer*/

        viewpager.setAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(viewpager);
        presenter.load();

    }

    @Override
    public void onBackPressed() {
        if (viewpager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewpager.setCurrentItem(viewpager.getCurrentItem() - 1);
        }
    }

    @Override
    public void isLoading() {
        //TODO progressbar
    }

    @Override
    public void updateUIcurrentWeather(OpenWeather body, SavedCity savedCity) {
        Timber.d("updateUIcurrentWeather " + savedCity);

        for (Fragment f : getSupportFragmentManager().getFragments()) {
            TodayFragment f1 = (TodayFragment) f;
            Timber.d("updateUIcurrentWeather f1 " + f1.getSavedCity());

            if (f1.getSavedCity() == savedCity) {
                f1.update(body, savedCity);
            }

        }

    }

    @Override
    public void updateUIForcastWeather(Forecast body, SavedCity savedCity) {
        Timber.d("updateUIForcastWeather");
        for (Fragment f : getSupportFragmentManager().getFragments()) {
            TodayFragment f1 = (TodayFragment) f;
            Timber.d("updateUIcurrentWeather f1 " + f1.getSavedCity());

            if (f1.getSavedCity() == savedCity) {
                f1.updateForecast(body, savedCity);
            }

        }
    }

    @Override
    public void savedCityUpdated() {
        Timber.d("savedCityUpdated");
        mPagerAdapter.notifyDataSetChanged();
    }

    public Picasso getPicasso() {
        return picasso;
    }

    public static MainActivity get(Fragment fragment) {
        return (MainActivity) fragment.getActivity();
    }

}
