package ir.leafstudio.weatherapp.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    private final Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @WeatherApplicationScope
    public Context context() {
        return context;
    }
}
