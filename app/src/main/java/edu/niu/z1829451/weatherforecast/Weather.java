package edu.niu.z1829451.weatherforecast;

import java.time.LocalDate;
import java.util.Date;

public class Weather {

    private double temp;
    private String summary;
    private double highTemp;
    private double lowTemp;
    private Date date;


    public Weather(double temp, String summary, Date date) {
        this(temp, summary, 0,0, date);
    }

    public Weather(double temp, String summary, double highTemp, double lowTemp, Date date) {
        this.temp = temp;
        this.summary = summary;
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
        this.date = date;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String day(){
        return WeatherDateUtils.Day(this.date);
    }

    public double getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(double highTemp) {
        this.highTemp = highTemp;
    }

    public double getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(double lowTemp) {
        this.lowTemp = lowTemp;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(getSummary());

        if(!(this.temp == 0)){
            stringBuilder.append("current temperature: ");
            stringBuilder.append(getTemp());
        }

        if(!(this.highTemp == 0)){
            stringBuilder.append("Highest temperature: ");
            stringBuilder.append(this.highTemp);
        }

        if(!(this.lowTemp == 0)){
            stringBuilder.append("Lowest temperature: ");
            stringBuilder.append(this.lowTemp);
        }


        return stringBuilder.toString();
    }
}
