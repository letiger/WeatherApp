package letier.brandon.weatherapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import letier.brandon.weatherapp.R;
import letier.brandon.weatherapp.ui.forecast.ForecastActivity;
import letier.brandon.weatherapp.ui.forecastlist.ForecastListActivity;
import letier.brandon.weatherapp.ui.wordlist.LocationListActivity;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button forecast = findViewById(R.id.forecast);
        forecast.setOnClickListener(view ->
                startActivity(new Intent(this, ForecastActivity.class)));

        Button forecastCity = findViewById(R.id.forecast_list);
        forecastCity.setOnClickListener(view ->
                startActivity(new Intent(this, ForecastListActivity.class)));

        Button pagedList = findViewById(R.id.paged_list);
        pagedList.setOnClickListener(view ->
                startActivity(new Intent(this, LocationListActivity.class)));
    }
}
