package letier.brandon.weatherapp.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Strings {
    private static final String UI_TIME = "E dd MMM yyyy";

    public static String temperature(Double amount) {
        return String.format(Locale.getDefault(),  "%d \u2103", Math.round(amount));
    }

    public static String temperature(String amount) {
        return String.format(Locale.getDefault(),"%s \u2103", amount);
    }

    public static String formatDate(Date date) {
        SimpleDateFormat uiFormat = new SimpleDateFormat(UI_TIME, Locale.getDefault());
        return uiFormat.format(date);
    }

    public static String capitalize(String string) {
        return string.length() >= 2 ? string.substring(0, 1).toUpperCase() + string.substring(1)
                : string.length() >= 1 ? string.toUpperCase() : string;
    }
}
