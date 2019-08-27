package letier.brandon.weatherapp.injection;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import letier.brandon.weatherapp.ui.forecastlist.ForecastListViewModel;
import letier.brandon.weatherapp.ui.home.MainViewModel;

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ForecastListViewModel.class)
    abstract ViewModel bindForecastListViewModel(ForecastListViewModel viewModel);

                                         @Binds
    abstract ViewModelProvider.Factory bindViewModelFractory(ViewModelFactory factory);
}
