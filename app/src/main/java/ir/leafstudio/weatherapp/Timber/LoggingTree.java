package ir.leafstudio.weatherapp.Timber;

import timber.log.Timber;

public class LoggingTree extends Timber.Tree{
    @Override
    protected void log(int priority, String tag, String message, Throwable t) {

    }
}
