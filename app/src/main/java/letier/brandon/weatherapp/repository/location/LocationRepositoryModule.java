package letier.brandon.weatherapp.repository.location;

import dagger.Module;
import dagger.Provides;

@Module
public class LocationRepositoryModule {
    @Provides
    LocationRepository providesForecastRepository(LocationRepositoryImpl forecastRepository) {
        return forecastRepository;
    }
}
