package letier.brandon.weatherapp.repository.location;

import java.util.List;

import io.reactivex.Observable;
import letier.brandon.weatherapp.data.LocationEntity;

public interface LocationRepository {

    // Fetch cached list of words.
    Observable<List<LocationEntity>> getAllWords();

    // Add a new word
    void insert(LocationEntity wordEntity);
}
