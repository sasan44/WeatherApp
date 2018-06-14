package ir.leafstudio.weatherapp.Screens.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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

    void setList(List<SavedCity> savedCityList) {
        savedCities.clear();
        savedCities.addAll(presenter.getSavedCityList());
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);

        TextView textViewCity = view.findViewById(R.id.listView_textViewCity);
        TextView textViewCountry = view.findViewById(R.id.textViewCountry);
        ImageButton buttonDelete = view.findViewById(R.id.listView_buttonDelete);

        textViewCity.setText(savedCities.get(position).getName());
//            textViewCountry.setText(values[position]);


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                deleteCity(position);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Item will be deleted , Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });
        return view;
    }




    private void notifyDataSetChanged(List<SavedCity> savedCities) {
        savedCities.clear();
        savedCities.addAll(presenter.getSavedCityList());
        this.notifyDataSetChanged();
    }

    private void deleteCity(int position) {
        presenter.deleteFromListofCities(savedCities.get(position));
        presenter.deleteFromListofCities(position);
        presenter.listOfcitiesUpdated();
        notifyDataSetChanged(savedCities);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }
}