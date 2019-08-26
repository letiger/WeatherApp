package letier.brandon.weatherapp.service.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Main {
    private Double temp;
    private String temp_min;
    private String humidity;
    private String pressure;
    private String temp_max;
}