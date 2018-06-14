package ir.leafstudio.weatherapp.Screens.todayfragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;

import butterknife.Unbinder;
import ir.leafstudio.weatherapp.R;
import ir.leafstudio.weatherapp.SavedCity;
import ir.leafstudio.weatherapp.Screens.fragmentUpdater;
import ir.leafstudio.weatherapp.databinding.FragmentTodayBinding;
import ir.leafstudio.weatherapp.openweathermodel.Forecast;
import ir.leafstudio.weatherapp.openweathermodel.OpenWeather;
import timber.log.Timber;

public class TodayFragment extends Fragment implements fragmentUpdater {
    Picasso picasso;

    TodayAdapter todayAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    static String SavedCity_KEY = "SavedCity";
    private SavedCity savedCity;


    OpenWeather openWeather;
    Forecast forecast;

    public TodayFragment() {
        // Required empty public constructor
    }


    public static TodayFragment newInstance(SavedCity savedCity, Picasso picasso) {
        TodayFragment fragment = new TodayFragment();
        Bundle args = new Bundle();
        args.putParcelable(SavedCity_KEY, savedCity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   this.picasso = MainActivity.get(this).getPicasso();
        Timber.d("onCreate");
        //    Presenter presenter = new Presenter();
        //    presenter.setListener(this);
        if (getArguments() != null) {
            setSavedCity((SavedCity) getArguments().getParcelable(SavedCity_KEY));
        }
    }

    FragmentTodayBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Timber.d("onCreateView");
//        FragmentTodayBinding binding = FragmentTodayBinding.inflate(inflater, container, false);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_today, container, false);
        View view = binding.getRoot();
        //here data must be an instance of the class MarsDataProvider
//        binding.setMarsdata(data);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        binding.recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        todayAdapter = new TodayAdapter(picasso);
        binding.recyclerView.setAdapter(todayAdapter);
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public SavedCity getSavedCity() {
        return savedCity;
    }

    public void setSavedCity(SavedCity savedCity) {
        this.savedCity = savedCity;
    }

    @Override
    public void update(OpenWeather body, SavedCity savedCity) {
        Timber.d("updateUIcurrentWeather " + body + savedCity);
        openWeather = body;


        binding.todayViewId.todayTextViewCity.setText(openWeather.getName());
        openWeather.getRain();
        binding.todayViewId.todayTextViewWeather.setText(openWeather.getWeather().get(0).getMain()
                + " : " + openWeather.getWeather().get(0).getDescription());

        binding.todayViewId.todayTextViewClouds.setText("Clouds:" + openWeather.getClouds().getAll().toString());
        binding.todayViewId.todayTextViewHumidity.setText("Humidity:" + openWeather.getMain().getHumidity().toString());
        binding.todayViewId.todayTextViewPressure.setText("Pressure:" + openWeather.getMain().getPressure().toString());
        binding.todayViewId.todayTextViewMaxTemp.setText("MaxTemp:" + kelvinToCelsius(openWeather.getMain().getTempMax()));
        binding.todayViewId.todayTextViewTemp.setText("Temp:" + kelvinToCelsius(openWeather.getMain().getTemp()));
        binding.todayViewId.todayTextViewMinTemp.setText("MinTemp:" + kelvinToCelsius(openWeather.getMain().getTempMin()));

        binding.todayViewId.todayTextViewCountry.setText(openWeather.getSys().getCountry());
        Date sunrise = new Date(openWeather.getSys().getSunrise() * 1000);
        Date sunset = new Date(openWeather.getSys().getSunset() * 1000);
        binding.todayViewId.todayTextViewSunrise.setText("sunrise:" + DateFormat.getTimeInstance().format(sunrise));
        binding.todayViewId.todayTextViewSunset.setText("sunset:" + DateFormat.getTimeInstance().format(sunset));

        Date dt = new Date(openWeather.getDt() * 1000);

        binding.todayViewId.todayTextViewDt.setText(DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM, DateFormat.SHORT).format(dt));

        binding.todayViewId.todayTextViewWindDeg.setText("Wind direction:" + openWeather.getWind().getDeg().toString());
        binding.todayViewId.todayTextViewWindSpeed.setText("WindSpeed:" + openWeather.getWind().getSpeed().toString());
        picasso.get()
                .load("http://openweathermap.org/img/w/" + openWeather.getWeather().get(0).getIcon() + ".png")//10d.png
                .resize(300, 300)
                .centerCrop()
                .into(binding.todayViewId.todayImageView);

        Timber.d("updateUIcurrentWeather");
//        Log.d(TAG, "onResponse" + body.getWeather().get(0).getDescription());
    }


    @Override
    public void updateForecast(Forecast body, SavedCity savedCity) {
        Timber.d("updateUIForcastWeather " + body + savedCity);
        todayAdapter.setData(body);
        todayAdapter.notifyDataSetChanged();

    }

    public String kelvinToCelsius (double degreesKelvin) {
        double c = degreesKelvin - 273.16;
        String result = String.format("%.2f", c);

        return result;
    }



    public static double fahrenheitToCelsius(double fahrenheit) {
        return ((5 * (fahrenheit - 32.0)) / 9.0);
    }


}
