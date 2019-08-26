package letier.brandon.weatherapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import io.reactivex.Observable;

public class NetworkHelper {
    public NetworkHelper() {
    }

    private static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static Observable<Boolean> errorIfNoConnection(Context context) {
        return Observable.just(isConnected(context)).map((hasConnection) -> {
            if (!hasConnection) {
                throw new RuntimeException("Something went wrong :-,");
            } else {
                return true;
            }
        });
    }
}