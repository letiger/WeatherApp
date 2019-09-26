package letier.brandon.weatherapp.repository.location;

import android.app.Application;
import android.arch.paging.DataSource;
import android.os.AsyncTask;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;

import letier.brandon.weatherapp.data.LocationDao;
import letier.brandon.weatherapp.data.LocationDatabase;
import letier.brandon.weatherapp.data.LocationEntity;

public class LocationRepositoryImpl implements LocationRepository {
    private final LocationDao locationDao;
    private final DataSource.Factory<Integer, LocationEntity> allWords;

    @Inject
    LocationRepositoryImpl(Application application) {
        LocationDatabase db = LocationDatabase.init(application);
        this.locationDao = db.wordDao();
        this.allWords = locationDao.getAllWords();
    }

    @Override
    public DataSource.Factory<Integer, LocationEntity> getAllLocations() {
        return allWords;
    }

    @Override
    public void insert(LocationEntity location) {
        new LocationAsyncTask(locationDao, Task.ADD).execute(location);
    }

    @Override
    public void update(LocationEntity location) {
        new LocationAsyncTask(locationDao, Task.UPDATE).execute(location);
    }

    @Override
    public void deleteLocation(LocationEntity location) {
        new LocationAsyncTask(locationDao, Task.DELETE).execute(location);
    }

    private static class  LocationAsyncTask extends AsyncTask<LocationEntity, Void, Void> {

        private final @Task String task;
        private final LocationDao asyncTaskDao;

        LocationAsyncTask(LocationDao asyncTaskDao, @Task String task) {
            this.asyncTaskDao = asyncTaskDao;
            this.task = task;
        }

        @Override
        protected Void doInBackground(LocationEntity... wordEntities) {
            switch (task) {
                case Task.ADD:
                    asyncTaskDao.insertLocation(wordEntities[0]);
                    break;

                case Task.UPDATE:
                    asyncTaskDao.updateLocation(wordEntities[0]);
                    break;

                case Task.DELETE:
                    asyncTaskDao.deleteLocation(wordEntities[0]);
                    break;
            }
            return null;
        }
    }

    @StringDef({
            Task.ADD,
            Task.UPDATE,
            Task.DELETE
    })
    @Retention(RetentionPolicy.RUNTIME)
    @interface Task {
        String ADD = "ADD";
        String UPDATE = "UPDATE";
        String DELETE = "DELETE";
    }
}
