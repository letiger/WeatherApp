package letier.brandon.weatherapp.service;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ForecastWebServiceModule {

    @Provides
    @Singleton
    static ForecastWebService providesMandateWebService() {
        return new ForecastWebServiceImpl();
    }
}