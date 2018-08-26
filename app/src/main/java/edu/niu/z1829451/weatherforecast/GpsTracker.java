package edu.niu.z1829451.weatherforecast;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityManagerCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class GpsTracker extends Service implements LocationListener {

    private final Context context;
    protected LocationManager locationManager;
    private Location location;

    public GpsTracker(Context context) {
        Log.d("GPSTRACKERCONSTRUCTOR", "GPS");
        this.context = context;
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

            if (ActivityCompat.checkSelfPermission((Activity)context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                ActivityCompat.requestPermissions((Activity)context, new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                }, 10);
//                return TODO;
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 10, this);
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                Log.d("LatitudeLongitude", location.getLatitude() + " " + location.getLongitude());
            }
        } catch(Exception e){
            Log.d("EXCEPTIONOCCURED",e.getMessage());
            e.printStackTrace();
        }

        return location;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
