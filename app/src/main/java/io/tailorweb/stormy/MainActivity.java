package io.tailorweb.stormy;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    public CurrentWeather mCurrentWeather;
    private TextView mTemperatureLabel;
    private TextView mTimeLabel;
    private TextView mSummaryLabel;
    private ImageView mIconImageView;
    private TextView mPrecipValue;
    private TextView mHumidityValue;
    private ImageView mRefresh;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTemperatureLabel = (TextView) findViewById(R.id.temparatureLabel);
        mTimeLabel = (TextView) findViewById(R.id.timeTextView);
        mSummaryLabel = (TextView) findViewById(R.id.sumaryLabel);
        mPrecipValue = (TextView) findViewById(R.id.precipValue);
        mHumidityValue = (TextView) findViewById(R.id.humidityValue);
        mIconImageView = (ImageView) findViewById(R.id.iconImageView);
        mRefresh = (ImageView) findViewById(R.id.refreshImageView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);

        final double latitude = 45.764795;
        final double longitude = 3.053601;

        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getForecast(latitude, longitude);
            }
        });
        getForecast(latitude, longitude);
    }

    private void getForecast(double latitude, double longitude) {
        String apiKey = "65088fd9cf99797c5241333917cff891";
        String foreCastUrl = "https://api.darksky.net/forecast/" + apiKey + "/" +
                latitude + "," + longitude;
        if (isNetworkAvailable()) {
            toggleRefresh();
            OkHttpClient client = new OkHttpClient();
            // Build the request
            Request request = new Request.Builder()
                    .url(foreCastUrl)
                    .build();
            Call call = client.newCall(request);
            // enqueue make the call async -> create a worker thread
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    try {
                        String jsonData = response.body().string();
//                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            mCurrentWeather = getCurrentDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });
                         } else {
                            // Handle error with Dialog
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                    catch (JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }

                }
            });
        } else {
            Toast.makeText(this, R.string.network_unavailable_message, Toast.LENGTH_LONG).show();
        }
    }

    private void toggleRefresh() {
        if (mProgressBar.getVisibility() == View.INVISIBLE){
            mProgressBar.setVisibility(View.VISIBLE);
            mRefresh.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefresh.setVisibility(View.VISIBLE);
        }
    }

    private void updateDisplay() {
        mTemperatureLabel.setText(mCurrentWeather.getTemperature()+"");
        mTimeLabel.setText("At "+ mCurrentWeather.getFormattedTime()+" it will be");
        mHumidityValue.setText(mCurrentWeather.getHumidity()+"");
        mPrecipValue.setText(mCurrentWeather.getPrecipChance()+"%");
        mSummaryLabel.setText(mCurrentWeather.getSummary());
        mIconImageView.setImageResource(mCurrentWeather.getIconId());

    }

    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        JSONObject currently = forecast.getJSONObject("currently");
        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setHumidity(currently.getDouble("humidity"));
        currentWeather.setTime(currently.getLong("time"));
        currentWeather.setIcon(currently.getString("icon"));
        currentWeather.setPrecipChance(currently.getDouble("precipProbability"));
        currentWeather.setSummary(currently.getString("summary"));
        currentWeather.setTemperature(currently.getDouble("temperature"));
        currentWeather.setTimeZone(forecast.getString("timezone"));
        return currentWeather;
    }

    private boolean isNetworkAvailable() {
        //Need to update permission : ACCESS_NETWORK_STATE
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }
}
