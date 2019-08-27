package letier.brandon.weatherapp.ui.forecastlist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import letier.brandon.weatherapp.repository.ForecastRepository;
import letier.brandon.weatherapp.service.City;
import letier.brandon.weatherapp.ui.forecastlist.adapter.ForecastDto;
import letier.brandon.weatherapp.util.Resource;
import letier.brandon.weatherapp.util.SchedulerProvider;

public class ForecastListViewModel extends AndroidViewModel {

    private static final int MAX = 10;
    private final ForecastRepository repository;
    private final SchedulerProvider provider;
    private final MutableLiveData<Resource<List<ForecastDto>>> forecastListMutable =
            new MutableLiveData<>();
    final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    ForecastListViewModel(@NonNull Application application, ForecastRepository repository,
                                 SchedulerProvider provider) {
        super(application);
        this.repository = repository;
        this.provider = provider;
    }

    LiveData<Resource<List<ForecastDto>>> getForecastList() {
        return forecastListMutable;
    }

    void initialize() {
        Disposable disposable = Single.zip(
                repository.getDailyForecastByCity(City.BLOEMFONTEIN),
                repository.getDailyForecastByCity(City.CAPE_TOWN),
                repository.getDailyForecastByCity(City.DURBAN),
                repository.getDailyForecastByCity(City.JOHANNESBURG),
                repository.getDailyForecastByCity(City.POLOKWANE),
                (bloem, capeTown, durban, Joburg, polokwane) -> {
                    List<ForecastDto> forecastList = new ArrayList<>();
                    for (int i = 0; i < MAX; i++) {
                        forecastList.add(new ForecastDto(i,bloem));
                        forecastList.add(new ForecastDto(i,capeTown));
                        forecastList.add(new ForecastDto(i,durban));
                        forecastList.add(new ForecastDto(i,Joburg));
                        forecastList.add(new ForecastDto(i,polokwane));
                    }

                    return forecastList;
                })
                .compose(provider.applySchedulersSingle())
                .doOnSubscribe(disposable1 -> forecastListMutable.setValue(Resource.loading()))
                .subscribe(forecasts -> forecastListMutable.setValue(Resource.success(forecasts)),
                        throwable -> forecastListMutable.setValue(Resource.error()));

        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
