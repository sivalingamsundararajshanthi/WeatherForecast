package edu.niu.z1829451.weatherforecast;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Weather>> {

    LocationManager locationManager;
    Location lastKnownLocation;
    LocationListener locationListener;

    private static final int WEATHER_ID = 1;

    private RecyclerView mRecyclerView;
    private WeatherAdapter mWeatherAdapter;
    private TextView todayTemp, todaySummary, todayPlace;
    private GpsTracker gpsTracker;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Log.d("LOCATION", "lat: " + lastKnownLocation.getLatitude() + " long: " + lastKnownLocation.getLongitude());
            getSupportLoaderManager().initLoader(WEATHER_ID, null, this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todayTemp = findViewById(R.id.temp_today);
        todaySummary = findViewById(R.id.sum_today_tv);
        todayPlace = findViewById(R.id.place_id);

        mRecyclerView = findViewById(R.id.recyclerView_id);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mWeatherAdapter = new WeatherAdapter();
        mRecyclerView.setAdapter(mWeatherAdapter);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        if(Build.VERSION.SDK_INT<23){

        } else {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                Log.d("Granted", "Granted");
                gpsTracker = new GpsTracker(this);
//                lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                lastKnownLocation = gpsTracker.getLocation();
//                Log.d("LOCATION", "lat: " + lastKnownLocation.getLatitude() + " long: " + lastKnownLocation.getLongitude());

                getSupportLoaderManager().initLoader(WEATHER_ID, null, this);
            }
        }
    }

    @NonNull
    @Override
    public Loader<List<Weather>> onCreateLoader(int i, @Nullable Bundle bundle) {
//        return new NetworkLoader(this, 38.9240923, -77.4376809);
        return new NetworkLoader(this, lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Weather>> loader, List<Weather> weathers) {

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {
            List<Address> address = geocoder.getFromLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude(), 1);

            if(address != null && address.size() > 0){
                todayPlace.setText(address.get(0).getLocality());
                Log.d("Address", address.get(0).toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        todayTemp.setText(String.valueOf(weathers.get(0).getTemp()));
        todaySummary.setText(weathers.get(0).getSummary());
        if(weathers != null){
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            mWeatherAdapter.setWeatherData(weathers);
        }
        else
            Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Weather>> loader) {

    }
}
