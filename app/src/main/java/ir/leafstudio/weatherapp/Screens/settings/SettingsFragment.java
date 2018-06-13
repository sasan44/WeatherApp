package ir.leafstudio.weatherapp.Screens.settings;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.leafstudio.weatherapp.Presenter;
import ir.leafstudio.weatherapp.location.GetLocation;
import ir.leafstudio.weatherapp.R;
import ir.leafstudio.weatherapp.SavedCity;
import ir.leafstudio.weatherapp.SavedSettings;
import timber.log.Timber;

public class SettingsFragment extends Fragment implements GetLocation.OnLocaionFound, PlaceSelectionListener {

    private static final int RESULT_OK = 22;
    private static final int RESULT_CANCELED = 23;
    SavedSettings savedSettings;
     private static final int PERMISSION_ACCESS_COARSE_LOCATION = 1;
    private static final int PERMISSION_ACCESS_FINE_LOCATION = 2;

    @BindView(R.id.editTextCity)
    EditText editTextCity;
    @BindView(R.id.editTextLat)
    EditText editTextLat;
    @BindView(R.id.editTextLon)
    EditText editTextLon;
    @BindView(R.id.buttonAddCity)
    Button buttonAddCity;
    @BindView(R.id.buttonFindMyLocation)
    Button buttonFindMyLocation;
    @BindView(R.id.buttonSearchCity)
    Button buttonSearchCity;

    public SettingsFragment() {
        // Required empty public constructor
    }

    Presenter presenter;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);


        buttonSearchCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autocom();
                // Method #1


            }
        });

        buttonAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SavedCity savedCity = new SavedCity(
                        Double.parseDouble(editTextLat.getText().toString())
                        , Double.parseDouble(editTextLon.getText().toString())
                        , editTextCity.getText().toString());
                presenter.getSavedSettings().addCity(savedCity);
                presenter.listOfcitiesUpdated();


            }
        });
        getLocation = new GetLocation(getActivity());
        getLocation.setListener(this);
        buttonFindMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_ACCESS_COARSE_LOCATION);
                }
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ACCESS_FINE_LOCATION);
                }
                getLocation.start();

            }
        });

        return view;
    }

    GetLocation getLocation;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // All good!
                } else {
                    Toast.makeText(getContext(), "Need your location!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }


    @Override
    public void onLocaionFound(Location location) {
        editTextLat.setText(Double.toString(location.getLatitude()));
        editTextLon.setText(Double.toString(location.getLongitude()));
        editTextCity.setText(Double.toString(location.getAccuracy()));
    }

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 10;
    GoogleApiClient googleApiClient;

    void autocom() {

//        buildGoogleApiClient();
//
//        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
//                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
//                .build();
//        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
//                getActivity().getFragmentManager().findFragmentById(R.id.place_fragment2);
//        autocompleteFragment.setOnPlaceSelectedListener(this);
//        autocompleteFragment.setFilter(typeFilter);
//        autocompleteFragment.setHint("Search a Location");

    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .build();
        googleApiClient.connect();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                Timber.d("Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
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


}
