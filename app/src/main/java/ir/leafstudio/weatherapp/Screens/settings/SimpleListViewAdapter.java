package ir.leafstudio.weatherapp.Screens.settings;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.leafstudio.weatherapp.Presenter;
import ir.leafstudio.weatherapp.R;
import ir.leafstudio.weatherapp.SavedCity;

public class SimpleListViewAdapter extends ArrayAdapter {
    //to reference the Activity
    private final Context context;

    List<SavedCity> savedCities = new ArrayList<>();
    Presenter presenter;

    public SimpleListViewAdapter(Context context, List<SavedCity> savedCities, Presenter presenter) {
        super(context, R.layout.list_view, savedCities);
        this.context = context;
        this.presenter = presenter;
        this.savedCities = savedCities;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);

        TextView textViewCity = view.findViewById(R.id.textViewCity);
        TextView textViewCountry = view.findViewById(R.id.textViewCountry);
        Button buttonDelete = view.findViewById(R.id.buttonDelete);

        textViewCity.setText(savedCities.get(position).getName());
//            textViewCountry.setText(values[position]);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.deleteFromListofCities(savedCities.get(position));
                presenter.deleteFromListofCities(position);
                notifyDataSetChanged(savedCities);
            }
        });
        return view;
    }

    private void notifyDataSetChanged(List<SavedCity> savedCities) {
        savedCities.clear();
        savedCities.addAll(presenter.getSavedCityList());
        this.notifyDataSetChanged();
    }


}