package letier.brandon.weatherapp.ui.forecast;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import letier.brandon.weatherapp.R;
import letier.brandon.weatherapp.injection.ViewModelFactory;
import letier.brandon.weatherapp.service.GpsService;
import letier.brandon.weatherapp.service.model.Forecast;
import letier.brandon.weatherapp.service.model.Main;
import letier.brandon.weatherapp.service.model.Weather;
import letier.brandon.weatherapp.ui.forecastlist.ForecastListActivity;
import letier.brandon.weatherapp.util.Resource;
import letier.brandon.weatherapp.util.Strings;

public class ForecastActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 10;

    private LinearLayout progress;
    private LinearLayout main;
    private TextView today;
    private TextView summary;
    private TextView temperature;
    private TextView location;
    private TextView min;
    private TextView max;
    private TextView pressure;
    private TextView humidity;
    private LinearLayout retryContainer;
    private BroadcastReceiver receiver;
    private ForecastViewModel viewModel;
    private FloatingActionButton fab;

    @Inject
    ViewModelFactory factory;

    @Inject
    GpsService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeLayout();

        viewModel = ViewModelProviders.of(this, factory)
                .get(ForecastViewModel.class);

        // If no permissions is needed, carry on with the flow.
        if (!checkPermissions()) {
            startGpsService();
        }

        viewModel.getForecast().observe(this, this::populateView);
    }

    /**
     * Determine if the necessary permissions are granted. Request permission when needed.
     *
     * @return : Returns a boolean to indicate if the device needs permission.
     */
    private boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, REQUEST_CODE);
                return true;
            }
        }
        return false;
    }

    private void startGpsService() {
        toggleProgress(true);
        Intent intent = new Intent(getApplicationContext(), GpsService.class);
        startService(intent);
    }

    private void stopGpsService(Location location) {
        Intent intent = new Intent(getApplicationContext(), GpsService.class);
        stopService(intent);
        viewModel.initialize(location.getLatitude(), location.getLongitude());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (receiver == null) {

            // Initialize receiver.
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Location location = (Location) intent.getExtras().get(GpsService.LOCATION);
                    if (location != null) {
                        stopGpsService(location);
                    }
                }
            };

            registerReceiver(receiver, new IntentFilter(GpsService.BROADCAST_FILTER));
        }
    }

    @Override protected void onDestroy() {
        service = null;
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Handle permission request results
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                startGpsService();
            } else {
                // Fail safe to continue flow of the app.
                displayRetryGUI();
            }
        }
    }

    private void initializeLayout() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        progress = findViewById(R.id.progress_container);
        main = findViewById(R.id.main_container);
        today = findViewById(R.id.today);
        summary = findViewById(R.id.summary);
        temperature = findViewById(R.id.temperature);
        location = findViewById(R.id.location);
        min = findViewById(R.id.min);
        max = findViewById(R.id.max);
        pressure = findViewById(R.id.pressure);
        humidity = findViewById(R.id.humidity);
        Button retry = findViewById(R.id.retry);
        retryContainer = findViewById(R.id.retry_container);

        retry.setOnClickListener(v -> {
            toggleScreen(false);
            toggleProgress(true);
            if (!checkPermissions()) {
                startGpsService();
            }
        });

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, ForecastListActivity.class);
            startActivity(intent);
        });
    }

    private void populateView(Resource<Forecast> resource) {
        if (resource != null) {
            switch (resource.getResourceState()) {
                case LOADING:
                    toggleProgress(true);
                    break;

                case ERROR:
                    toggleProgress(false);
                    displayRetryGUI();
                    break;

                case SUCCESS:
                    toggleProgress(false);
                    if (resource.getData() != null) {
                        bindData(resource.getData());
                    } else {
                        displayRetryGUI();
                    }
                    break;
            }
        }
    }

    private void bindData(Forecast forecast) {
        Main main = forecast.getMain();
        Weather weather = forecast.getWeather().get(0);
        temperature.setText(Strings.temperature(main.getTemp()));
        today.setText(Strings.formatDate(new Date()));
        location.setText(Strings.capitalize(forecast.getName()));
        summary.setText(weather.getDescription());
        min.setText(main.getTemp_min());
        max.setText(main.getTemp_max());
        humidity.setText(main.getHumidity());
        pressure.setText(main.getPressure());
        toggleScreen(false);
        toggleProgress(false);
    }

    private void displayRetryGUI() {
        toggleProgress(false);
        toggleScreen(true);
    }

    private void toggleScreen(boolean isRetryShowing) {
        if (isRetryShowing) {
            retryContainer.setVisibility(View.VISIBLE);
            main.setVisibility(View.GONE);
        } else {
            retryContainer.setVisibility(View.GONE);
            main.setVisibility(View.VISIBLE);
        }
    }

    private void toggleProgress(boolean isProgressShowing) {
        if (isProgressShowing) {
            progress.setVisibility(View.VISIBLE);
            main.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
        } else {
            progress.setVisibility(View.GONE);
            main.setVisibility(View.VISIBLE);
            fab.setVisibility(View.VISIBLE);
        }
    }
}
