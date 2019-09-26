package letier.brandon.weatherapp.repository.location;

import android.arch.paging.DataSource;

import letier.brandon.weatherapp.data.LocationEntity;


public interface LocationRepository {

    // Fetch cached list of words.
    DataSource.Factory<Integer, LocationEntity> getAllLocations();

    // Add a new location
    void insert(LocationEntity location);

    // Update a location
    void update(LocationEntity location);

    // Delete a location
    void deleteLocation(LocationEntity location);
}
