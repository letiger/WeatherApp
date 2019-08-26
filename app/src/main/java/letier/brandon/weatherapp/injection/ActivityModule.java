package letier.brandon.weatherapp.injection;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import letier.brandon.weatherapp.service.GpsService;
import letier.brandon.weatherapp.ui.forecastlist.ForecastListActivity;
import letier.brandon.weatherapp.ui.home.MainActivity;

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract ForecastListActivity bindForecastListActivity();

    @ContributesAndroidInjector
    abstract GpsService bindGpsService();
}
