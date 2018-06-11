package ir.leafstudio.weatherapp;

import java.util.ArrayList;
import java.util.List;

public class SavedSettings {

    List<SavedCity> listOfcities = new ArrayList<>();

    public SavedSettings() {

    }

    public List<SavedCity> getListOfcities() {

        return listOfcities;
    }

    public void addCity(SavedCity city) {
        listOfcities.add(city);
    }

    void addFake() {
        if (listOfcities != null) {
            listOfcities.clear();
            listOfcities.add(new SavedCity(35.6892, 51.3890, "tehran"));
            listOfcities.add(new SavedCity(32.6546, 51.6680, "Isfahan"));
            listOfcities.add(new SavedCity(31.3183, 48.6706, "ahvaz"));
            listOfcities.add(new SavedCity(40.7128, 74.0060, "New York City"));
        }
    }
}
