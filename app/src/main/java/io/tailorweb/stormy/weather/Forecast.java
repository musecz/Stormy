package io.tailorweb.stormy.weather;

import io.tailorweb.stormy.R;

public class Forecast {
    private Current mCurrentForecast;
    private Day[] mDailyForecast;
    private Hour[] mHourlyForecast;

    public Current getCurrentForecast() {
        return mCurrentForecast;
    }

    public void setCurrentForecast(Current currentForecast) {
        mCurrentForecast = currentForecast;
    }

    public Day[] getDailyForecast() {
        return mDailyForecast;
    }

    public void setDailyForecast(Day[] daylyForecast) {
        mDailyForecast = daylyForecast;
    }

    public Hour[] getHourlyForecast() {
        return mHourlyForecast;
    }

    public void setHourlyForecast(Hour[] hourlyForecast) {
        mHourlyForecast = hourlyForecast;
    }

    public static int getIconId(String iconString){

        int iconId = R.drawable.clear_day;
        if(iconString.equals("clear-Day")){
            iconId = R.drawable.clear_day;
        }
        else if (iconString.equals("clear-night")){
            iconId = R.drawable.clear_night;
        }
        else if (iconString.equals("rain")) {
            iconId = R.drawable.rain;
        }
        else if (iconString.equals("snow")) {
            iconId = R.drawable.snow;
        }
        else if (iconString.equals("sleet")) {
            iconId = R.drawable.sleet;
        }
        else if (iconString.equals("wind")) {
            iconId = R.drawable.wind;
        }
        else if (iconString.equals("fog")) {
            iconId = R.drawable.fog;
        }
        else if (iconString.equals("cloudy")) {
            iconId = R.drawable.cloudy;
        }
        else if (iconString.equals("partly-cloudy-Day")) {
            iconId = R.drawable.partly_cloudy;
        }
        else if (iconString.equals("partly-cloudy-night")) {
            iconId = R.drawable.cloudy_night;
        }

        return iconId;
    }

}
