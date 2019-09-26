package letier.brandon.weatherapp.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity
public class LocationEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String location;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getLocation() {
        return location;
    }
    public void setLocation(@NonNull String location) {
        this.location = location;
    }
}
