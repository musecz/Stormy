package io.tailorweb.stormy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import io.tailorweb.stormy.R;
import io.tailorweb.stormy.weather.Day;

public class DayAdapter extends BaseAdapter {

    private Context mContext;
    private Day[] mDays;

    public DayAdapter(Context context, Day[] days){
        mContext = context;
        mDays = days;
    }

    @Override
    public int getCount() {
        return mDays.length;
    }

    @Override
    public Object getItem(int i) {
        return mDays[i];
    }

    @Override
    public long getItemId(int i) {
//        Tag Items for easy reference
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder holder;

        if (view == null) {
            // brand new
            view = LayoutInflater.from(mContext).inflate(R.layout.daily_list_item,null);
            holder = new viewHolder();
            holder.iconImageView = (ImageView) view.findViewById(R.id.iconImageView);
            holder.temperatureLabel = (TextView) view.findViewById(R.id.temparatureLabel);
            holder.dayLabel = (TextView) view.findViewById(R.id.dayNameLabel);

            view.setTag(holder);
        } else {
            // We reuse the view otherwise
            holder = (viewHolder) view.getTag();
        }
        // We load the data
        Day day = mDays[i];
        holder.iconImageView.setImageResource(day.getIconId());
        holder.temperatureLabel.setText(day.getTemperatureMax()+"");
        holder.dayLabel.setText(day.getDayOfTheWeek());

        return null;
    }

    private static class viewHolder {
        ImageView iconImageView; // Public by default
        TextView temperatureLabel;
        TextView dayLabel;
    }
}
