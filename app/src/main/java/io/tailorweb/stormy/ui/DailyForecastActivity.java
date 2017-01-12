package io.tailorweb.stormy.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.os.Bundle;
import java.util.Arrays;

import io.tailorweb.stormy.R;
import io.tailorweb.stormy.adapters.DayAdapter;
import io.tailorweb.stormy.weather.Day;

public class DailyForecastActivity extends ListActivity {
    public static final String Tag = DailyForecastActivity.class.getSimpleName();
    private Day[] mDays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);
//        Example of Adapter
//        String[] daysOfTheWeek = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,daysOfTheWeek);
//        setListAdapter(adapter);
        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
        mDays = Arrays.copyOf(parcelables, parcelables.length, Day[].class);
        DayAdapter adapter = new DayAdapter(this, mDays);
        setListAdapter(adapter);
    }

}

