package ir.leafstudio.weatherapp;

import java.util.List;

public interface Settings {

    List<SavedCity> getSavedCityList();

    void listOfcitiesUpdated();

    void deleteFromListofCities(SavedCity savedCity);

    void deleteFromListofCities(int savedCityPosition);

    void addToListofCities(SavedCity savedCity);

}
