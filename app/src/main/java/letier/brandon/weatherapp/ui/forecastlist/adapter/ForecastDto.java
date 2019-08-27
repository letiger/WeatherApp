package letier.brandon.weatherapp.ui.forecastlist.adapter;

import letier.brandon.weatherapp.service.model.Forecast;
import lombok.Getter;

@Getter
public class ForecastDto {
    private int id;
    private String name;
    private Double temp_max;

    public ForecastDto(int id, Forecast forecast) {
        this.id = id + forecast.getId();
        this.name = forecast.getName();
        this.temp_max = forecast.getMain().getTemp();
    }
}