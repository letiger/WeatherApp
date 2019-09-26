package letier.brandon.weatherapp.ui.forecast;

import android.app.Application;
import android.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;
import letier.brandon.weatherapp.repository.forecast.ForecastRepository;
import letier.brandon.weatherapp.service.model.Forecast;
import letier.brandon.weatherapp.util.Resource;
import letier.brandon.weatherapp.util.ResourceState;
import letier.brandon.weatherapp.util.SchedulerProvider;

public class HomeViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private static final String NAME = "NAME";
    private final SchedulerProvider provider = SchedulerProvider.IMMEDIATE;
    private static final double latitude = 37.421998333333335;
    private static final double longitude = -122.08400000000002;

    private ForecastViewModel viewModel;
    private final Forecast forecast = Forecast.builder()
            .name(NAME)
            .build();

    @Mock
    private Application application;

    @Mock
    private ForecastRepository repository;

    @Before
    public void setupViewModel() {
        MockitoAnnotations.initMocks(this);
        viewModel = new ForecastViewModel(application, repository, provider);
    }

    @Test
    public void initialize_success() {
        Mockito.when(repository.getDailyForecastByLocation(latitude, longitude))
                .thenReturn(Single.just(forecast));

        viewModel.initialize(latitude, longitude);

        // Test the API request
        Resource<Forecast> resource = viewModel.getForecast().getValue();
        Assert.assertNotNull(resource);
        Assert.assertEquals(resource.getResourceState(), ResourceState.SUCCESS);

        // Test the mutable.
        Forecast forecast = resource.getData();
        Assert.assertNotNull(forecast);
        Assert.assertEquals(forecast, this.forecast);
    }

    @Test
    public void initialize_failure() {
        Mockito.when(repository.getDailyForecastByLocation(latitude, longitude))
                .thenReturn(Single.error(new Throwable()));

        viewModel.initialize(latitude, longitude);

        Resource<Forecast> resource = viewModel.getForecast().getValue();
        Assert.assertNotNull(resource);
        Assert.assertEquals(resource.getResourceState(), ResourceState.ERROR);

        // Negative test the mutable.
        Forecast forecast = resource.getData();
        Assert.assertNull(forecast);
    }

    @Test
    public void clearViewModel() {
        Mockito.when(repository.getDailyForecastByLocation(latitude, longitude))
                .thenReturn(Single.just(forecast));

        viewModel.initialize(latitude, longitude);
        Assert.assertTrue("Check if data is available for disposal",
                viewModel.compositeDisposable.size() > 0);

        viewModel.onCleared();
        Assert.assertTrue("Check if mutables have been disposed of",
                viewModel.compositeDisposable.isDisposed());

    }
}
