package letier.brandon.weatherapp.data;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLocation(LocationEntity wordEntity);

    @Update
    void updateLocation(LocationEntity wordEntity);

    @Query("DELETE FROM LocationEntity")
    void deleteAll();

    @Delete
    void deleteLocation(LocationEntity wordEntity);

    @Query("SELECT * from LocationEntity ORDER BY id ASC")
    DataSource.Factory<Integer, LocationEntity> getAllWords();
}
