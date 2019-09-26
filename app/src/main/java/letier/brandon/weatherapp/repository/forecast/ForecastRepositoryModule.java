package letier.brandon.weatherapp.repository.forecast;

import dagger.Module;
import dagger.Provides;

@Module
public class ForecastRepositoryModule {

    @Provides
    ForecastRepository providesForecastRepository(ForecastRepositoryImpl forecastRepository) {
        return forecastRepository;
    }
}