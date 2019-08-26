package letier.brandon.weatherapp.service;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({
        City.CAPE_TOWN,
        City.JOHANNESBURG,
        City.DURBAN,
        City.POLOKWANE,
        City.BLOEMFONTEIN,
})
@Retention(RetentionPolicy.RUNTIME)
public @interface City {
    String CAPE_TOWN = "Cape Town,ZA";
    String JOHANNESBURG = "Johannesburg,ZA";
    String DURBAN = "Durban,ZA";
    String POLOKWANE = "Polokwane,ZA";
    String BLOEMFONTEIN = "Bloemfontein,ZA";

}
