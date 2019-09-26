package letier.brandon.weatherapp.repository.forecast;

import io.reactivex.Single;
import letier.brandon.weatherapp.service.model.Forecast;

public interface ForecastRepository {
    Single<Forecast> getDailyForecastByCity(String city);
    Single<Forecast> getDailyForecastByLocation(Double latitude, Double longitude);
}
