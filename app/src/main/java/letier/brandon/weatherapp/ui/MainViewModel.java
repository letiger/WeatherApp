package letier.brandon.weatherapp.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import javax.inject.Inject;

public class MainViewModel extends AndroidViewModel {
    private MutableLiveData<String> message = new MutableLiveData<>();

    @Inject
    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMessage() {
        return message;
    }

    void initialize() {
        message.setValue("View Model has been initialized");
    }
}
