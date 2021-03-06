package ir.leafstudio.weatherapp.Screens;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.leafstudio.weatherapp.MySharedPreferences;
import ir.leafstudio.weatherapp.Presenter;
import ir.leafstudio.weatherapp.R;
import ir.leafstudio.weatherapp.SavedCity;
import ir.leafstudio.weatherapp.Screens.settings.ActivitySettings;
import ir.leafstudio.weatherapp.WeatherApp;
import ir.leafstudio.weatherapp.databinding.ActivityMainBinding;
import ir.leafstudio.weatherapp.openweathermodel.Forecast;
import ir.leafstudio.weatherapp.openweathermodel.OpenWeather;
import ir.leafstudio.weatherapp.retrofit.ApiService;
import ir.leafstudio.weatherapp.Screens.todayfragment.TodayFragment;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements UIListener {


    @Inject
    ApiService apiService;
    @Inject
    Picasso picasso;
    @Inject
    Presenter presenter;
    @Inject
    MySharedPreferences mySharedPreferences;
    @Inject
    ScreenSlidePagerAdapter mPagerAdapter;
    FragmentManager manager;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        this.picasso = WeatherApp.get(this).getPicasso();

        MainActivityComponent mainActivityComponent = DaggerMainActivityComponent.builder()
                .mainActivityModule(new MainActivityModule(this))
                .weatherAppComponent(WeatherApp.get(this).getComponent())
                .build();

//        mainActivityComponent.injectMainActivity(this);
        this.presenter = WeatherApp.get(this).getPresenter();
        this.apiService = mainActivityComponent.getApiService();
        this.mPagerAdapter = mainActivityComponent.getScreenSlidePagerAdapter();

        manager = getSupportFragmentManager();
        binding.include2.settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(MainActivity.this, ActivitySettings.class);

                MainActivity.this.startActivity(myIntent);
                overridePendingTransition(R.anim.enter, R.anim.exit);

            }
        });

        mPagerAdapter.setList(presenter.getSavedCityList());
        presenter.setListener(this);
        binding.viewpager.setOffscreenPageLimit(5);       /* limit is a fixed integer*/
        binding.viewpager.setAdapter(mPagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewpager);
        presenter.loadFreshData();

    }

    @Override
    public void onBackPressed() {
        if (binding.viewpager.getCurrentItem() == 0) { // If the user is currently looking at the first step, allow the system to handle the  Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {  // Otherwise, select the previous step.
            binding.viewpager.setCurrentItem(binding.viewpager.getCurrentItem() - 1);
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
            if (f instanceof TodayFragment) {
                TodayFragment f1 = (TodayFragment) f;
                Timber.d("updateUIcurrentWeather f1 " + f1.getSavedCity());

//            if (f1.getSavedCity() == savedCity) {
//                f1.update(body, savedCity);
//                Timber.d("updateUIcurrentWeather f1 updated" + f1.getSavedCity());
//            }
                if (f1.getSavedCity().getLat() == savedCity.getLat() && f1.getSavedCity().getLon() == savedCity.getLon()) {
                    f1.update(body, savedCity);
                    Timber.d("updateUIcurrentWeather f1 updated" + f1.getSavedCity());

                }
            }
        }

    }

    @Override
    public void updateUIForcastWeather(Forecast body, SavedCity savedCity) {
        Timber.d("updateUIForcastWeather");
        for (Fragment f : getSupportFragmentManager().getFragments()) {
            if (f instanceof TodayFragment) {
                TodayFragment f1 = (TodayFragment) f;
                Timber.d("updateUIcurrentWeather f1 " + f1.getSavedCity());

//            if (f1.getSavedCity() == savedCity) {
//                f1.updateForecast(body, savedCity);
//            }
                if (f1.getSavedCity().getLat() == savedCity.getLat() && f1.getSavedCity().getLon() == savedCity.getLon()) {
                    f1.updateForecast(body, savedCity);
                }
            }
        }
    }

    @Override
    public void listOfcitiesUpdated() {
        Timber.d("listOfcitiesUpdated");
        mPagerAdapter.setList(presenter.getSavedCityList());
        mPagerAdapter.notifyDataSetChanged();
    }

    public Picasso getPicasso() {
        return picasso;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public static MainActivity get(Fragment fragment) {
        return (MainActivity) fragment.getActivity();
    }

}
