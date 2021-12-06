package com.example.appdonacion.ui.maps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationProvider {


    private static final int LOCATION_REQUEST_INTERVAL = 3000;
    private static final int LOCATION_REQUEST_FASTEST_INTERVAL = 3000;
    private static final int MAX_REQUEST_LOCATION_UPDATES = 3;

    private static LocationRequest createLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(LOCATION_REQUEST_INTERVAL);
        locationRequest.setFastestInterval(LOCATION_REQUEST_FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        return locationRequest;
    }

    public static void requestCurrentLocation(final Context context, final ResultListener<Location> resultListener) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && isProviderEnabled(context)) {

            final FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
            LocationRequest locationRequest = createLocationRequest().setNumUpdates(MAX_REQUEST_LOCATION_UPDATES);

            fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    Location location = locationResult.getLastLocation();

                    if (location != null) {
                        resultListener.finish(location);
                    } else {
                        resultListener.finish(null);
                    }
                }

            }, null).addOnFailureListener(e -> resultListener.finish(null));

        }
        else {
            resultListener.finish(null);
        }
    }

    public static boolean isProviderEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager != null && (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
    }

    public interface ResultListener<T> {
        void finish(T result);
    }
}
