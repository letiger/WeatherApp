package letier.brandon.weatherapp.service.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Forecast {
    private String visibility;
    private String timezone;
    private Main main;
    private String dt;
    private String name;
    private String cod;
    private String id;
    private String base;
}