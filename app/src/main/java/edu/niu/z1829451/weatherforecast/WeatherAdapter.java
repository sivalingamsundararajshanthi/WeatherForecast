package edu.niu.z1829451.weatherforecast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {
    private List<Weather> weathers;

    public WeatherAdapter(){
//        this.weathers = weathers;
    }

    public void setWeatherData(List<Weather> weathers){
        this.weathers = weathers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layout = R.layout.weather_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layout, parent, false);
        WeatherViewHolder viewHolder = new WeatherViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
//        holder.mWeatherTextView.setText(weathers.get(position).toString());
//        holder.dayTv.setText(weathers);
        if(position != 0){
            holder.dayTv.setText(weathers.get(position).day());
            holder.highTemp.setText(String.valueOf(weathers.get(position).getHighTemp()));
            holder.lowTemp.setText(String.valueOf(weathers.get(position).getLowTemp()));
            holder.sum.setText(String.valueOf(weathers.get(position).getSummary()));
        }
    }

    @Override
    public int getItemCount() {

        if(weathers == null){
            return 0;
        } else {
            return weathers.size();
        }
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder{

        public final TextView dayTv, highTemp, lowTemp, sum;

        public WeatherViewHolder(View itemView) {
            super(itemView);
            dayTv = itemView.findViewById(R.id.day_id);
            highTemp = itemView.findViewById(R.id.high_temp);
            lowTemp = itemView.findViewById(R.id.low_temp);
            sum = itemView.findViewById(R.id.summary);
        }
    }
}
