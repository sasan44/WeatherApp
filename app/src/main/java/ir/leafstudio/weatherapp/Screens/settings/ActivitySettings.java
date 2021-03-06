package ir.leafstudio.weatherapp.Screens.settings;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import ir.leafstudio.weatherapp.Presenter;
import ir.leafstudio.weatherapp.R;
import ir.leafstudio.weatherapp.SavedCity;
import ir.leafstudio.weatherapp.SavedSettings;
import ir.leafstudio.weatherapp.Screens.BaseActivity;
import ir.leafstudio.weatherapp.WeatherApp;
import ir.leafstudio.weatherapp.databinding.ActivitySettingsBinding;
import ir.leafstudio.weatherapp.location.GetLocation;
import timber.log.Timber;

public class ActivitySettings extends BaseActivity implements GetLocation.OnLocaionFound, PlaceSelectionListener {

    Presenter presenter;
    GetLocation getLocation;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 10;
    GoogleApiClient googleApiClient;
    private static final int RESULT_OK = 22;
    private static final int RESULT_CANCELED = 23;
    SavedSettings savedSettings;
    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 1;
    private static final int PERMISSION_ACCESS_FINE_LOCATION = 2;
    MyGeocoder myGeocoder;
    ActivitySettingsBinding binding;
    SimpleListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_settings);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);

        // Inflate the layout for this fragment

        this.presenter = WeatherApp.get(this).getComponent().getPresenter();

        adapter = new SimpleListViewAdapter(this, presenter.getSavedCityList(), presenter);

        binding.listView.setAdapter(adapter);

        binding.buttonSearchCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autocom();
                // Method #1
            }
        });

        binding.buttonAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SavedCity savedCity = new SavedCity(
                        Double.parseDouble(binding.editTextLat.getText().toString())
                        , Double.parseDouble(binding.editTextLon.getText().toString())
                        , binding.editTextCity.getText().toString());
                presenter.getSavedSettings().addCity(savedCity);
                presenter.listOfcitiesUpdated();
                adapter.setList(presenter.getSavedCityList());
                adapter.notifyDataSetChanged();
            }
        });

        getLocation = new GetLocation(this);
        getLocation.setListener(this);

        myGeocoder = new MyGeocoder(this);
        binding.buttonFindMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askPermission();
                getLocation.start();
            }
        });
    }

    private void askPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_ACCESS_COARSE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onLocaionFound(Location location) {
        binding.editTextLat.setText(Double.toString(location.getLatitude()));
        binding.editTextLon.setText(Double.toString(location.getLongitude()));
        binding.editTextCity.setText(Double.toString(location.getAccuracy()));
        binding.editTextCity.setText(myGeocoder.getAddress(location.getLatitude(), location.getLongitude()));
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // All good!
                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    void autocom() {
        buildGoogleApiClient();
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                this.getFragmentManager().findFragmentById(R.id.place_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);
        autocompleteFragment.setFilter(typeFilter);
        autocompleteFragment.setHint("Search a Location");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Timber.d("Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Timber.d(status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    @Override
    public void onPlaceSelected(Place place) {
//        Log.i(LOG_TAG, "Place Selected: " + place.getName());
//        locationTextView.setText(getString(R.string.formatted_place_data, place
//                .getName(), place.getAddress(), place.getPhoneNumber(), place
//                .getWebsiteUri(), place.getRating(), place.getId()));
//        if (!TextUtils.isEmpty(place.getAttributions())){
//            attributionsTextView.setText(Html.fromHtml(place.getAttributions().toString()));
//        }
    }

    @Override
    public void onError(Status status) {
//        Log.e(LOG_TAG, "onError: Status = " + status.toString());
//        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
//                Toast.LENGTH_SHORT).show();
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .build();
        googleApiClient.connect();

    }


}
