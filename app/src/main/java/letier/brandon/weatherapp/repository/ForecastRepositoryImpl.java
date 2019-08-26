package letier.brandon.weatherapp.repository;

import android.content.Context;

import javax.inject.Inject;

import io.reactivex.Single;
import letier.brandon.weatherapp.service.model.Forecast;
import letier.brandon.weatherapp.service.ForecastWebService;
import retrofit2.Response;

public class ForecastRepositoryImpl implements ForecastRepository {
    private final Context context;
    private final ForecastWebService forecastWebService;

    @Inject
    ForecastRepositoryImpl(Context context, ForecastWebService forecastWebService) {
        this.forecastWebService = forecastWebService;
        this.context = context;
    }

    @Override
    public Single<Forecast> getDailyForecastByCity(String city) {
        return forecastWebService.getDailyForecast(context, city)
                .firstOrError()
                .map(Response::body);
    }

    @Override
    public Single<Forecast> getDailyForecastByLocation(Double latitude, Double longitude) {
        return forecastWebService.getDailyForecast(context, latitude, longitude)
                .firstOrError()
                .map(Response::body);
    }
}
