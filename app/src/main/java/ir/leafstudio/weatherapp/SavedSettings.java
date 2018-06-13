package ir.leafstudio.weatherapp;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class SavedSettings {

    List<SavedCity> listOfcities = new ArrayList<>();
    MySharedPreferences sharedPreferences;

    public SavedSettings(MySharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public List<SavedCity> getListOfcities() {
        Timber.d("getListOfcities " + sharedPreferences.getDataList().size());
        return sharedPreferences.getDataList();
    }

    public void addCity(SavedCity city) {
        listOfcities = getListOfcities();
        listOfcities.add(city);
        sharedPreferences.putDataList(listOfcities);
    }

    public void removeCity(SavedCity city) {
        listOfcities = getListOfcities();
        Timber.d("listOfcities.size() : " + listOfcities.size());
        listOfcities.remove(city);
        Timber.d("listOfcities.size() : " + listOfcities.size());
        sharedPreferences.putDataList(listOfcities);

    }

    public void removeCity(int city) {
        listOfcities = getListOfcities();
        Timber.d("listOfcities.size() : " + listOfcities.size());
        listOfcities.remove(city);
        Timber.d("listOfcities.size() : " + listOfcities.size());
        sharedPreferences.putDataList(listOfcities);

    }

    void addFake() {
        if (listOfcities != null) {
            listOfcities.clear();
            listOfcities.add(new SavedCity(35.6892, 51.3890, "tehran"));
            listOfcities.add(new SavedCity(32.6546, 51.6680, "Isfahan"));
            listOfcities.add(new SavedCity(31.3183, 48.6706, "ahvaz"));
            listOfcities.add(new SavedCity(40.7128, 74.0060, "New York City"));
        }
        sharedPreferences.putDataList(listOfcities);
    }
}
