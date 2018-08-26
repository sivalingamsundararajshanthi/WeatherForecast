package edu.niu.z1829451.weatherforecast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

public class NetworkLoader extends AsyncTaskLoader<List<Weather>> {

    private Double latitude, longitude;

    public NetworkLoader(@NonNull Context context, Double latitude, Double longitude) {
        super(context);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Weather> loadInBackground() {
        return NetworkUtils.fetchWeatherData(latitude, longitude);
    }
}
