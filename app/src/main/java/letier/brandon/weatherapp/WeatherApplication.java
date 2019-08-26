package letier.brandon.weatherapp;

import android.app.Activity;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.DispatchingAndroidInjector;
import letier.brandon.weatherapp.injection.DaggerWeatherAppComponent;
import letier.brandon.weatherapp.injection.WeatherAppComponent;
import timber.log.Timber;

public class WeatherApplication extends DaggerApplication {

    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
        DaggerWeatherAppComponent.builder().application(this).build().inject(this);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        WeatherAppComponent component = DaggerWeatherAppComponent.builder().application(this).build();
        component.inject(this);
        return component;
    }

}
