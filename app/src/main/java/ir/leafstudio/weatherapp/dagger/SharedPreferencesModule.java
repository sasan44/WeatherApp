package ir.leafstudio.weatherapp.dagger;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import ir.leafstudio.weatherapp.MySharedPreferences;
import ir.leafstudio.weatherapp.dagger.ContextModule;
import ir.leafstudio.weatherapp.dagger.WeatherApplicationScope;


@Module(includes = {ContextModule.class , WeatherModule.class})
public class SharedPreferencesModule {

    @Provides
    @WeatherApplicationScope
    MySharedPreferences provideMySharedPreferences(SharedPreferences sharedPreferences , Gson gson ) {
        return new MySharedPreferences(sharedPreferences , gson);
    }
    @Provides
    @WeatherApplicationScope
    SharedPreferences provideSharedPreferences(Context context ) {
        SharedPreferences sharedPreferences =  context.getSharedPreferences("PrefName", Context.MODE_PRIVATE) ;
        return sharedPreferences;
    }
}
