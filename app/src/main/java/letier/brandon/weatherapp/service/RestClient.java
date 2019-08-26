package letier.brandon.weatherapp.service;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

class RestClient {
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private static final int HTTP_TIMEOUT_DEFAULT_SECONDS = 60;
    private static final String NETWORK = "NETWORK";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(getClient())
            .build();

    private static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .readTimeout(HTTP_TIMEOUT_DEFAULT_SECONDS, TimeUnit.SECONDS)
                .connectTimeout(HTTP_TIMEOUT_DEFAULT_SECONDS, TimeUnit.SECONDS)
                .addInterceptor(getHttpLoggingInterceptor())
                .build();
    }

    private static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor(message ->
                Timber.tag(NETWORK).v(message)).setLevel(HttpLoggingInterceptor.Level.BASIC);
    }

    static <T> T createService(final Class<T> service) {
        return retrofit.create(service);
    }
}