package ir.leafstudio.weatherapp.Screens.todayfragment;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import ir.leafstudio.weatherapp.R;
import ir.leafstudio.weatherapp.databinding.WeatherRecycleViewBinding;
import ir.leafstudio.weatherapp.openweathermodel.Forecast;
import timber.log.Timber;

public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.ViewHolder> {

    Forecast forecast;

    public TodayAdapter(Picasso picasso) {
        this.picasso = picasso;
    }

    public void setData(Forecast forecast) {
        Timber.d("onResponse" + forecast.getList().get(0).getWeather().get(0).getDescription());
        this.forecast = forecast;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WeatherRecycleViewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.weather_recycle_view, parent, false);
        ViewHolder vh = new ViewHolder(binding);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.setForecast(forecast.getList().get(position));
        //    holder.binding.textViewCountry.setText(forecast.getList().get(position).getSys().getCountry());
//         holder.binding.textViewSunrise.setText(forecast.getList().get(position).getSys().getSunrise().toString());
        //       holder.binding.textViewSunset.setText(forecast.getList().get(position).getSys().getSunset().toString());
//         holder.binding.textViewDt.setText(forecast.getList().get(position).getDt());
        picasso.get()
                .load("http://openweathermap.org/img/w/" + forecast.getList().get(position).getWeather().get(0).getIcon() + ".png")//10d.png
                .resize(300, 300)
                .centerCrop()
                .into(holder.binding.imageView);
    }

    Picasso picasso;

    @Override
    public int getItemCount() {
        Timber.d("Forecast " + forecast);
        if (forecast != null)
            return forecast.getList().size();
        else return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        WeatherRecycleViewBinding binding;

        public ViewHolder(WeatherRecycleViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}


