package io.tailorweb.stormy.ui;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.List;

import io.tailorweb.stormy.R;
import io.tailorweb.stormy.adapters.DayAdapter;
import io.tailorweb.stormy.weather.Day;

public class DailyForecastActivity extends ListActivity {

    private Day[] mDays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);
//        Example of Adapter
//        String[] daysOfTheWeek = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,daysOfTheWeek);
//        setListAdapter(adapter);
        DayAdapter adapter = new DayAdapter(this, mDays);
    }

}

