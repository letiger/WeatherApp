package letier.brandon.weatherapp.injection;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import letier.brandon.weatherapp.ui.forecastlist.ForecastListViewModel;
import letier.brandon.weatherapp.ui.forecast.ForecastViewModel;
import letier.brandon.weatherapp.ui.wordlist.LocationListViewModel;
import letier.brandon.weatherapp.ui.wordlist.add.AddLocationViewModel;

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ForecastViewModel.class)
    abstract ViewModel bindMainViewModel(ForecastViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ForecastListViewModel.class)
    abstract ViewModel bindForecastListViewModel(ForecastListViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LocationListViewModel.class)
    abstract ViewModel bindLocationListViewModel(LocationListViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AddLocationViewModel.class)
    abstract ViewModel bindAddLocationViewModel(AddLocationViewModel viewModel);


    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
