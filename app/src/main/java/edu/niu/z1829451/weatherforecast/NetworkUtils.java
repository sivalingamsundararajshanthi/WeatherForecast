package edu.niu.z1829451.weatherforecast;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class NetworkUtils {

    private static final String API_URL =
            "https://api.darksky.net/forecast/please insert your own API key here/37.8267,-122.4233";

    private static final String API_URL_FINAL =
            "https://api.darksky.net/forecast/please insert your own API key here/";

    public static List<Weather> fetchWeatherData(Double latitude, Double longitude){
        String location = latitude.toString() + "," + longitude.toString();
        StringBuilder stringBuilder = new StringBuilder(API_URL_FINAL);
        stringBuilder.append(location);
        Uri weatherUri = Uri.parse(stringBuilder.toString()).buildUpon()
                .build();

        URL url = buildUrl(weatherUri);

        Log.d("THEURL", url.toString());

        String json= "";

        try {
            json = getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return extractFromJson(json);
    }

    private static URL buildUrl(Uri uri){
        try{
            URL weatherUrl = new URL(uri.toString());
            return weatherUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }

    private static List<Weather> extractFromJson(String json){
        if(TextUtils.isEmpty(json))
            return null;

        List<Weather> weathers = new ArrayList<>();

        try{
            JSONObject baseJsonResponse = new JSONObject(json);

            JSONObject currentWeather = baseJsonResponse.getJSONObject("currently");

            Long date = currentWeather.getLong("time");

            Date dateObject = WeatherDateUtils.convertToNormalDate(date);

            double temperature = currentWeather.getDouble("temperature");

            String condition = currentWeather.getString("summary");

            weathers.add(new Weather(temperature, condition, dateObject));

            JSONObject dailyObject = baseJsonResponse.getJSONObject("daily");

            JSONArray data = dailyObject.getJSONArray("data");

            for(int i=0;i<data.length();i++){
                JSONObject objectInArray = data.getJSONObject(i);
                String summary = objectInArray.getString("summary");
                double tempHigh = objectInArray.getDouble("temperatureHigh");
                double tempLow = objectInArray.getDouble("temperatureLow");
                Long dateInArray = objectInArray.getLong("time");
                Date dte = WeatherDateUtils.convertToNormalDate(dateInArray);

                weathers.add(new Weather(0, summary, tempHigh, tempLow, dte));
                Log.d("Size", weathers.get(i).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return weathers;
    }
}
