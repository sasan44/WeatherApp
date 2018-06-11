package ir.leafstudio.weatherapp.Screens.todayfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ir.leafstudio.weatherapp.R;
import ir.leafstudio.weatherapp.SavedCity;
import ir.leafstudio.weatherapp.Screens.fragmentUpdater;
import ir.leafstudio.weatherapp.openweathermodel.Forecast;
import ir.leafstudio.weatherapp.openweathermodel.OpenWeather;
import timber.log.Timber;

public class TodayFragment extends Fragment implements fragmentUpdater {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    Picasso picasso;

    TodayAdapter todayAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    private Unbinder unbinder;
    static String SavedCity_KEY = "SavedCity";
    private SavedCity savedCity;
    TextView today_textViewTemp;
    TextView today_textViewCity;
    TextView today_textViewWeatherDisc;
    OpenWeather openWeather;
    Forecast forecast;

    public TodayFragment() {
        // Required empty public constructor
    }


    public static TodayFragment newInstance(SavedCity savedCity,Picasso picasso) {
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Timber.d("onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        // TODO FragmentTodayBinding binding=DataBindingUtil.inflate(inflater, R.layout.fragment_today, container, false);

        today_textViewTemp = view.findViewById(R.id.today_textViewTemp);
        today_textViewCity = view.findViewById(R.id.today_textViewCity);


        unbinder = ButterKnife.bind(this, view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        todayAdapter = new TodayAdapter(picasso);
        mRecyclerView.setAdapter(todayAdapter);
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

        today_textViewCity.setText(openWeather.getName());
        openWeather.getRain();
        today_textViewWeather.setText(openWeather.getWeather().get(0).getMain()
                + " : " + openWeather.getWeather().get(0).getDescription());

        today_textViewClouds.setText("Clouds:" + openWeather.getClouds().getAll().toString());
        today_textViewHumidity.setText("Humidity:" + openWeather.getMain().getHumidity().toString());
        today_textViewPressure.setText("Pressure:" + openWeather.getMain().getPressure().toString());
        today_textViewMaxTemp.setText("MaxTemp:" + openWeather.getMain().getTempMax().toString());
        today_textViewTemp.setText("Temp:" + openWeather.getMain().getTemp().toString());
        today_textViewMinTemp.setText("MinTemp:" + openWeather.getMain().getTempMin().toString());

        today_textViewCountry.setText(openWeather.getSys().getCountry());
        today_textViewSunrise.setText(openWeather.getSys().getSunrise().toString());
        today_textViewSunset.setText(openWeather.getSys().getSunset().toString());
        today_textViewDt.setText("" + openWeather.getDt());

        today_textViewWindDeg.setText("WindDeg:" + openWeather.getWind().getDeg().toString());
        today_textViewWindSpeed.setText("WindSpeed:" + openWeather.getWind().getSpeed().toString());
        picasso.get()
                .load("http://openweathermap.org/img/w/" + openWeather.getWeather().get(0).getIcon() + ".png")//10d.png
                .resize(300, 300)
                .centerCrop()
                .into(today_imageView);

        Timber.d("updateUIcurrentWeather");
//        Log.d(TAG, "onResponse" + body.getWeather().get(0).getDescription());
    }


    @Override
    public void updateForecast(Forecast body, SavedCity savedCity) {
        Timber.d("updateUIForcastWeather " + body + savedCity);
        todayAdapter.setData(body);
        todayAdapter.notifyDataSetChanged();

    }

    @BindView(R.id.today_textViewCountry)
    public TextView today_textViewCountry;
    @BindView(R.id.today_textViewSunrise)
    public TextView today_textViewSunrise;
    @BindView(R.id.today_textViewSunset)
    public TextView today_textViewSunset;
    @BindView(R.id.today_textViewDt)
    public TextView today_textViewDt;

    @BindView(R.id.today_textViewClouds)
    public TextView today_textViewClouds;
    @BindView(R.id.today_textViewWindSpeed)
    public TextView today_textViewWindSpeed;
    @BindView(R.id.today_textViewWindDeg)
    public TextView today_textViewWindDeg;
    @BindView(R.id.today_textViewHumidity)
    public TextView today_textViewHumidity;
    @BindView(R.id.today_textViewMaxTemp)
    public TextView today_textViewMaxTemp;
    @BindView(R.id.today_textViewMinTemp)
    public TextView today_textViewMinTemp;
    @BindView(R.id.today_textViewPressure)
    public TextView today_textViewPressure;
    @BindView(R.id.today_textViewWeather)
    public TextView today_textViewWeather;
    @BindView(R.id.today_imageView)
    ImageView today_imageView;
}
