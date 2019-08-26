package letier.brandon.weatherapp.injection;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import letier.brandon.weatherapp.ui.MainActivity;

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract MainActivity binfMainActivity();
}
