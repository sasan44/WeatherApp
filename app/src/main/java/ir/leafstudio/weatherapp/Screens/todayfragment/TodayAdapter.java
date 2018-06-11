package ir.leafstudio.weatherapp.Screens.todayfragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.leafstudio.weatherapp.R;
import ir.leafstudio.weatherapp.openweathermodel.Forecast;
import timber.log.Timber;

public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.ViewHolder> {

    Forecast forecast;

    public TodayAdapter(Picasso picasso) {
        this.picasso = picasso;
    }

    public TodayAdapter(Forecast forecast) {
        this.forecast = forecast;
//        this.moviesList = moviesList;
    }

    public void setData(Forecast forecast) {
        Log.d("adaptor set data ", "onResponse" + forecast.getList().get(0).getWeather().get(0).getDescription());
        this.forecast = forecast;
        this.notifyDataSetChanged();


//        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weatherrecycleview, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewWeather.setText(forecast.getList().get(position).getWeather().get(0).getMain()
        +" : " + forecast.getList().get(position).getWeather().get(0).getDescription());
        holder.textViewDtTxt.setText(forecast.getList().get(position).getDtTxt());

        holder.textViewClouds.setText("Clouds:" +forecast.getList().get(position).getClouds().getAll().toString());
        holder.textViewHumidity.setText("Humidity:" +forecast.getList().get(position).getMain().getHumidity().toString());
        holder.textViewPressure.setText("Pressure:" +forecast.getList().get(position).getMain().getPressure().toString());
        holder.textViewMaxTemp.setText("MaxTemp:" +forecast.getList().get(position).getMain().getTempMax().toString());
        holder.textViewTemp.setText("Temp:" +forecast.getList().get(position).getMain().getTemp().toString());
        holder.textViewMinTemp.setText("MinTemp:" +forecast.getList().get(position).getMain().getTempMin().toString());

        //   holder.textViewCountry.setText(forecast.getList().get(position).getSys().getCountry());
//        holder.textViewSunrise.setText(forecast.getList().get(position).getSys().getSunrise().toString());
        //      holder.textViewSunset.setText(forecast.getList().get(position).getSys().getSunset().toString());
//        holder.textViewDt.setText(forecast.getList().get(position).getDt());

        holder.textViewWindDeg.setText("WindDeg:" + forecast.getList().get(position).getWind().getDeg().toString());
        holder.textViewWindSpeed.setText("WindSpeed:" +forecast.getList().get(position).getWind().getSpeed().toString());
        picasso.get()
                .load("http://openweathermap.org/img/w/" + forecast.getList().get(position).getWeather().get(0).getIcon() + ".png")//10d.png
                .resize(300, 300)
                .centerCrop()
                .into(holder.imageView);
    }
    Picasso picasso ;
    @Override
    public int getItemCount() {
        Timber.d("Forecast " + forecast);
        if (forecast != null)
            return forecast.getList().size();
        else return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewClouds)
        public TextView textViewClouds;
        @BindView(R.id.textViewTemp)
        public TextView textViewTemp;
        @BindView(R.id.textViewWindSpeed)
        public TextView textViewWindSpeed;
        @BindView(R.id.textViewWindDeg)
        public TextView textViewWindDeg;
        @BindView(R.id.textViewDtTxt)
        public TextView textViewDtTxt;
        @BindView(R.id.textViewHumidity)
        public TextView textViewHumidity;
        @BindView(R.id.textViewMaxTemp)
        public TextView textViewMaxTemp;
        @BindView(R.id.textViewMinTemp)
        public TextView textViewMinTemp;
        @BindView(R.id.textViewPressure)
        public TextView textViewPressure;
        @BindView(R.id.textViewWeather)
        public TextView textViewWeather;
        @BindView(R.id.imageView)
        ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }
}


