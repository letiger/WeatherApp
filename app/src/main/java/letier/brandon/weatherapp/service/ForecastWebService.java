package letier.brandon.weatherapp.service;

import android.content.Context;

import io.reactivex.Observable;
import letier.brandon.weatherapp.service.model.Forecast;
import retrofit2.Response;

public interface ForecastWebService {
    Observable<Response<Forecast>> getDailyForecast(Context context, String city);
    Observable<Response<Forecast>> getDailyForecast(Context context, Double latitude, Double longitude);
}