package letier.brandon.weatherapp.service;

import io.reactivex.Observable;
import letier.brandon.weatherapp.service.model.Forecast;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ForecastApi {
    @GET("weather")
    Observable<Response<Forecast>> getDailyForecastByCity(@Query("APPID") String appId,
                                                          @Query("units") String unitType,
                                                          @Query("q") String city);

    @GET("weather")
    Observable<Response<Forecast>> getDailyForecastByLocation(@Query("APPID") String appId,
                                                              @Query("units") String unitType,
                                                              @Query("lat") Double latitude,
                                                              @Query("lon") Double longitude);
}