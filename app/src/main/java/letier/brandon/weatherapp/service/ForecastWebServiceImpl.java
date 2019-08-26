package letier.brandon.weatherapp.service;

import android.content.Context;

import io.reactivex.Observable;
import letier.brandon.weatherapp.service.model.Forecast;
import letier.brandon.weatherapp.util.NetworkHelper;
import retrofit2.Response;

public class ForecastWebServiceImpl implements ForecastWebService {

    private static final String APP_ID = "put your app id here";
    private final ForecastApi api = RestClient.createService(ForecastApi.class);

    @Override
    public Observable<Response<Forecast>> getDailyForecast(Context context, String city) {
        return NetworkHelper.errorIfNoConnection(context)
                .flatMap(hasConnection -> api.getDailyForecastByCity(APP_ID, UnitType.METRIC,city));
    }

    @Override
    public Observable<Response<Forecast>> getDailyForecast(Context context, Double latitude, Double longitude) {
        return NetworkHelper.errorIfNoConnection(context)
                .flatMap(hasConnection ->api.getDailyForecastByLocation(APP_ID, UnitType.METRIC, latitude,longitude));
    }
}