package ir.leafstudio.weatherapp;


import dagger.Module;
import dagger.Provides;
import ir.leafstudio.weatherapp.dagger.WeatherApplicationScope;

@Module
public class SettingModule {
    @Provides
    @WeatherApplicationScope
    public SettingModule getSetting() {
        return  null;
    }
}
