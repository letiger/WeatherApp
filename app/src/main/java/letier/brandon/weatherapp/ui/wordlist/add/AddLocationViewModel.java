package letier.brandon.weatherapp.ui.wordlist.add;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import letier.brandon.weatherapp.R;
import letier.brandon.weatherapp.data.LocationEntity;
import letier.brandon.weatherapp.repository.location.LocationRepository;

public class AddLocationViewModel extends AndroidViewModel {
    private final LocationRepository repository;
    private final MutableLiveData<ViewState> viewStateMutable = new MutableLiveData<>();
    private final MutableLiveData<Boolean> onCompleteMutable = new MutableLiveData<>();
    private LocationEntity newLocation;
    private LocationEntity currentLocation;

    class ViewState {
        String title;
        String buttonText;
        boolean isButtonEnabled;
    }

    @Inject
    AddLocationViewModel(@NonNull Application application, LocationRepository repository) {
        super(application);
        this.repository = repository;
    }

    LiveData<ViewState> getViewState() {
        return viewStateMutable;
    }

    public LiveData<Boolean> getOnComplete() {
        return onCompleteMutable;
    }

    void initialize(boolean isUpdatingLocation, LocationEntity currentLocation) {
        this.currentLocation = currentLocation;
        ViewState viewState = getCurrentViewState();
        viewState.isButtonEnabled = false;

        if (isUpdatingLocation) {
            viewState.title = getApplication().getResources().getString(R.string.edit_location);
            viewState.buttonText = getApplication().getResources().getString(R.string.update_location);
        } else {
            viewState.title = getApplication().getResources().getString(R.string.add_location);
            viewState.buttonText = getApplication().getResources().getString(R.string.save_location);
        }

        viewStateMutable.setValue(viewState);
    }

    void validateLocation(String locationText) {
        ViewState viewState = getCurrentViewState();

        if (locationText == null || locationText.isEmpty()) {
            viewState.isButtonEnabled = false;

            //clear the cache.
            newLocation = null;
        } else {
            newLocation = new LocationEntity();
            newLocation.setLocation(locationText);

            if (currentLocation == null) {
                viewState.isButtonEnabled = true;
            } else {
                viewState.isButtonEnabled = !currentLocation.getLocation().equals(locationText);
                newLocation.setId(currentLocation.getId());
            }
        }

        // Update view.
        viewStateMutable.setValue(viewState);
    }

    void onSaveClicked() {
        if (currentLocation == null) {
            repository.insert(newLocation);
        } else {
            repository.update(newLocation);
        }

        onCompleteMutable.setValue(true);
    }

    private ViewState getCurrentViewState() {
        ViewState viewState = this.viewStateMutable.getValue();
        if (viewState == null) {
            viewState = new ViewState();
        }
        return viewState;
    }
}
