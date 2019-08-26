package letier.brandon.weatherapp.injection;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;
import letier.brandon.weatherapp.repository.ForecastRepositoryModule;
import letier.brandon.weatherapp.service.ForecastWebServiceModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ViewModelModule.class,
        ActivityModule.class,
        ForecastWebServiceModule.class,
        ForecastRepositoryModule.class
})
public interface WeatherAppComponent extends AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application app);

        WeatherAppComponent build();
    }

    void inject(Application app);
}
