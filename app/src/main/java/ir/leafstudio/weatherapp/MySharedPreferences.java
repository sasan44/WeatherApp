package ir.leafstudio.weatherapp;

import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

import ir.leafstudio.weatherapp.openweathermodel.currentweather.List;

public class MySharedPreferences {
    @Inject
    SharedPreferences mSharedPreferences;
    private java.util.List<SavedCity> cityList2 = new ArrayList<>();
    private SavedCity cityList[];
    @Inject
    Gson gson;

    @Inject
    public MySharedPreferences(SharedPreferences mSharedPreferences, Gson gson) {
        this.mSharedPreferences = mSharedPreferences;
        this.gson = gson;
    }

    public void putData(SavedCity savedCity) {
        SharedPreferences.Editor prefsEditor = mSharedPreferences.edit();
        String json = gson.toJson(savedCity);
        prefsEditor.putString("SavedCity", json);
        prefsEditor.commit();
    }

    public void putDataList(java.util.List<SavedCity> cityList) {
        SharedPreferences.Editor prefsEditor = mSharedPreferences.edit();
        SavedCity[] bar = cityList.toArray(new SavedCity[cityList.size()]);
        String json = gson.toJson(bar);
        prefsEditor.putString("SavedCityList", json);
        prefsEditor.commit();
    }

    public java.util.List<SavedCity> getDataList() {
        String json = mSharedPreferences.getString("SavedCityList", "");
        Type collectionType = new TypeToken<Collection<SavedCity>>() {
        }.getType();
        return gson.fromJson(json, collectionType); //Collection<SavedCity> enums =
    }

//    public SavedCity getData() {
//        String json = mSharedPreferences.getString("SavedCity", "");
//        SavedCity obj = gson.fromJson(json, SavedCity.class);
//        return obj;
//    }

}
