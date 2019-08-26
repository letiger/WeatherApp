package letier.brandon.weatherapp.service.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Weather {
    private String icon;
    private String description;
    private String main;
    private String id;
}
