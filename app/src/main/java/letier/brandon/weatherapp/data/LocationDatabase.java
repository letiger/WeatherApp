package letier.brandon.weatherapp.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {LocationEntity.class}, version=3, exportSchema = false)
public abstract class LocationDatabase extends RoomDatabase {
    private static final String DB_NAME = "location_database";
    private static final int MAX_LOCATIONS = 1000;
    private static volatile LocationDatabase INSTANCE;

    public static LocationDatabase init(final Context context) {
        if (INSTANCE == null) {
            synchronized (LocationDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        LocationDatabase.class, DB_NAME)
//                        .addCallback(roomDbCallback)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return INSTANCE;
    }

    public abstract LocationDao wordDao();

    private static RoomDatabase.Callback roomDbCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final LocationDao mDao;

        PopulateDbAsync(LocationDatabase db) {
            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            LocationEntity word = new LocationEntity();

            for (int i=0; i< MAX_LOCATIONS; i++) {
                int  wordCount = i+1;
                word.setLocation("Location : " + wordCount );
                mDao.insertLocation(word);
            }
            return null;
        }
    }
}
