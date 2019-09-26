package letier.brandon.weatherapp.ui.forecast;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import letier.brandon.weatherapp.repository.forecast.ForecastRepository;
import letier.brandon.weatherapp.service.model.Forecast;
import letier.brandon.weatherapp.util.Resource;
import letier.brandon.weatherapp.util.SchedulerProvider;

public class ForecastViewModel extends AndroidViewModel {
    private final MutableLiveData<Resource<Forecast>> forecastMutable = new MutableLiveData<>();
    final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final ForecastRepository repository;
    private final SchedulerProvider provider;

    @Inject
    ForecastViewModel(@NonNull Application application, ForecastRepository repository, SchedulerProvider provider) {
        super(application);
        this.repository = repository;
        this.provider = provider;
    }

    LiveData<Resource<Forecast>> getForecast() {
        return forecastMutable;
    }

    void initialize(Double latitude, Double longitude) {

        Disposable disposable = repository.getDailyForecastByLocation(latitude, longitude)
                .compose(provider.applySchedulersSingle())
                .doOnSubscribe(disposable1 -> forecastMutable.setValue(Resource.loading()))
                .subscribe(forecast -> forecastMutable.setValue(Resource.success(forecast)),
                        throwable -> forecastMutable.setValue(Resource.error(throwable.getMessage())));

        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
