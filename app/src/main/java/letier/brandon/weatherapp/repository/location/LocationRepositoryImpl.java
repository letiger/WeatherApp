package letier.brandon.weatherapp.repository.location;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import letier.brandon.weatherapp.data.LocationDao;
import letier.brandon.weatherapp.data.LocationDatabase;
import letier.brandon.weatherapp.data.LocationEntity;

public class LocationRepositoryImpl implements LocationRepository {
    private final LocationDao wordDao;
    private final Maybe<List<LocationEntity>> allWords;

    @Inject
    public LocationRepositoryImpl(Application application) {
        LocationDatabase db = LocationDatabase.init(application);
        this.wordDao = db.wordDao();
        this.allWords = wordDao.getAllWords();
    }

    @Override
    public Observable<List<LocationEntity>> getAllWords() {
        return allWords.toObservable();
    }

    @Override
    public void insert(LocationEntity wordEntity) {
        this.wordDao.insertWord(wordEntity);
    }

    private static class  AddWordAsyncTask extends AsyncTask<LocationEntity, Void, Void> {

        private final LocationDao asyncTaskDao;

        public AddWordAsyncTask(LocationDao asyncTaskDao) {
            this.asyncTaskDao = asyncTaskDao;
        }

        @Override
        protected Void doInBackground(LocationEntity... wordEntities) {
            asyncTaskDao.insertWord(wordEntities[0]);
            return null;
        }
    }
}
