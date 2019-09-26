package letier.brandon.weatherapp.ui.wordlist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import letier.brandon.weatherapp.data.LocationEntity;
import letier.brandon.weatherapp.repository.location.LocationRepository;

public class LocationListViewModel extends AndroidViewModel {
    private final LocationRepository locationRepository;

    private final LiveData<PagedList<LocationEntity>> wordEntityList;

    @Inject
    LocationListViewModel(@NonNull Application application, LocationRepository locationRepository) {
        super(application);
        this.locationRepository = locationRepository;
        PagedList.Config CONFIG = (new PagedList.Config.Builder())
                .setPageSize(100)
                .setInitialLoadSizeHint(200)
                .setPrefetchDistance(10)
                .setEnablePlaceholders(false)
                .build();
        this.wordEntityList = new LivePagedListBuilder<>(locationRepository.getAllLocations(),
                CONFIG).build();
    }

    LiveData<PagedList<LocationEntity>> getWordEntityList() {
        return wordEntityList;
    }

    void deleteLocation(LocationEntity location) {
        locationRepository.deleteLocation(location);
    }
}
