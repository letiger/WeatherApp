package letier.brandon.weatherapp.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class LocationEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String word;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getWord() {
        return word;
    }
    public void setWord(@NonNull String word) {
        this.word = word;
    }
}
