package letier.brandon.weatherapp.ui.forecastlist;

import android.app.Application;
import android.arch.core.executor.testing.InstantTaskExecutorRule;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.Single;
import letier.brandon.weatherapp.repository.forecast.ForecastRepository;
import letier.brandon.weatherapp.service.City;
import letier.brandon.weatherapp.service.model.Forecast;
import letier.brandon.weatherapp.service.model.Main;
import letier.brandon.weatherapp.ui.forecastlist.adapter.ForecastDto;
import letier.brandon.weatherapp.util.Resource;
import letier.brandon.weatherapp.util.ResourceState;
import letier.brandon.weatherapp.util.SchedulerProvider;

public class ForecastListViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final SchedulerProvider provider = SchedulerProvider.IMMEDIATE;
    private final Double TEMPERATURE = new Double("50.55");

    private ForecastListViewModel viewModel;

    @Mock
    private Application application;

    @Mock
    private ForecastRepository repository;

    @Before
    public void setupViewModel() {
        MockitoAnnotations.initMocks(this);
        viewModel = new ForecastListViewModel(application, repository, provider);

        Mockito.when(repository.getDailyForecastByCity(City.BLOEMFONTEIN))
                .thenReturn(Single.just(getForecast(City.BLOEMFONTEIN)));
        Mockito.when(repository.getDailyForecastByCity(City.CAPE_TOWN))
                .thenReturn(Single.just(getForecast(City.CAPE_TOWN)));
        Mockito.when(repository.getDailyForecastByCity(City.DURBAN))
                .thenReturn(Single.just(getForecast(City.DURBAN)));
        Mockito.when(repository.getDailyForecastByCity(City.JOHANNESBURG))
                .thenReturn(Single.just(getForecast(City.JOHANNESBURG)));
        Mockito.when(repository.getDailyForecastByCity(City.POLOKWANE))
                .thenReturn(Single.just(getForecast(City.POLOKWANE)));
    }

    @Test
    public void initialize_success() {
        viewModel.initialize();

        Resource<List<ForecastDto>> resource = viewModel.getForecastList().getValue();
        Assert.assertNotNull(resource);
        Assert.assertEquals(resource.getResourceState(), ResourceState.SUCCESS);

        List<ForecastDto> forecastDtoList = resource.getData();
        Assert.assertNotNull(forecastDtoList);
        Assert.assertEquals(forecastDtoList.size(), 50);

    }

    @Test
    public void initialize_failure() {
        Mockito.when(repository.getDailyForecastByCity(City.BLOEMFONTEIN))
                .thenReturn(Single.error(new Throwable()));

        viewModel.initialize();

        Resource<List<ForecastDto>> resource = viewModel.getForecastList().getValue();
        Assert.assertNotNull(resource);
        Assert.assertEquals(resource.getResourceState(), ResourceState.ERROR);

        List<ForecastDto> forecastDtoList = resource.getData();
        Assert.assertNull(forecastDtoList);
    }

    @Test
    public void clearViewModel() {

        viewModel.initialize();
        org.junit.Assert.assertTrue("Check if data is available for disposal",
                viewModel.compositeDisposable.size() > 0);

        viewModel.onCleared();
        org.junit.Assert.assertTrue("Check if mutables have been disposed of",
                viewModel.compositeDisposable.isDisposed());

    }

    private Forecast getForecast(@City String city) {
        return Forecast.builder()
                .name(city)
                .main(Main.builder().temp(TEMPERATURE).build())
                .build();
    }
}
