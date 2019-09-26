package letier.brandon.weatherapp.injection;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import letier.brandon.weatherapp.service.GpsService;
import letier.brandon.weatherapp.ui.forecast.ForecastActivity;
import letier.brandon.weatherapp.ui.forecastlist.ForecastListActivity;

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract ForecastActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract ForecastListActivity bindForecastListActivity();
}
