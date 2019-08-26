package letier.brandon.weatherapp.service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface UnitType {
    // For temperature in Fahrenheit use
    String IMPERIAL = "imperial";

    // For temperature in Celsius use
    String METRIC = "metric";

    // Temperature in Kelvin is used by default, no need to use units parameter in API call
    String KELVIN =  null;
}