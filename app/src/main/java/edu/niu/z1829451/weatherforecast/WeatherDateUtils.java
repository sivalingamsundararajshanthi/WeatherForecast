package edu.niu.z1829451.weatherforecast;

import android.text.format.DateUtils;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.joda.time.DateTime;
import java.time.*;

public class WeatherDateUtils {

    /*This function converts an unix time stamp into a Date object*/
    public static Date convertToNormalDate(Long unixStamp){
        return new Date(unixStamp * 1000L);
    }

    /*This function gives the name of the weekday
     */
    public static String Day(Date date){
        String dateSample = DateFormat.getDateInstance().format(date);
        Log.d("DateFormat", dateSample);
        long d = date.getTime();
        Date dt = new Date(); //This will get today's date
        Calendar ct = Calendar.getInstance();
        ct.setTimeZone(TimeZone.getDefault());
        ct.setTime(dt);
        ct.add(Calendar.DATE, 1);
        Date tomo = ct.getTime();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setTimeZone(TimeZone.getDefault());
        Date d1 = calendar.getTime();
        long date1 = d1.getTime();

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return nameOfDay(c.get(Calendar.DAY_OF_WEEK));
    }

    private static String nameOfDay(int number){
        switch(number){
            case 1:
                return "Sunday";

            case 2:
                return "Monday";

            case 3:
                return "Tuesday";

            case 4:
                return "Wednesday";

            case 5:
                return "Thursday";

            case 6:
                return "Friday";

            case 7:
                return "Saturday";

            default:
                return "Invalid";

        }
    }
}
