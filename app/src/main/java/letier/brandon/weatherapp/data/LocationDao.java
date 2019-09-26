package letier.brandon.weatherapp.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWord(LocationEntity wordEntity);

    @Query("DELETE FROM LocationEntity")
    void deleteAll();

    @Query("SELECT * from LocationEntity ORDER BY id ASC")
    Maybe<List<LocationEntity>> getAllWords();
}
