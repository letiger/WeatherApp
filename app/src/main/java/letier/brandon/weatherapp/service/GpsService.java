package letier.brandon.weatherapp.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

import javax.inject.Inject;

public class GpsService extends Service {

    public static final String BROADCAST_FILTER = "GpsService.BROADCAST_FILTER";
    public static final String LOCATION = "GpsService.LOCATION";

    private LocationManager manager;
    private LocationListener listener;

    @Inject
    public GpsService(){}

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onCreate() {
        super.onCreate();
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Add filter to intent for broadcasting.
                Intent intent = new Intent(BROADCAST_FILTER);
                intent.putExtra(LOCATION, location);
                sendBroadcast(intent);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                // Left blank intentionally
            }

            @Override
            public void onProviderEnabled(String s) {
                // Left blank intentionally
            }

            // Send user to settings for permission.
            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };

        // Initialize the manager.
        manager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);


        // noinspection missingPermission
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, listener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Prevent memory leaks.
        manager.removeUpdates(listener);
    }
}