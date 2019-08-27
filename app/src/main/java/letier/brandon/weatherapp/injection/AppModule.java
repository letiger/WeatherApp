package letier.brandon.weatherapp.injection;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import letier.brandon.weatherapp.util.SchedulerProvider;

@Module
class AppModule {

    @Provides
    Context provideContext(Application app) {
        return app;
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.DEFAULT;
    }
}
