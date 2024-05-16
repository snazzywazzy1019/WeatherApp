package com.example.weatherapp;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.weatherapp.databinding.ActivityWeatherPageBinding;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class weatherPage extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityWeatherPageBinding binding;
    TextView date;
    TextView weather;
    TextView temp;
    TextView feelslike;
    TextView humid;
    Button home;
    Button next;
    Button previous;
    SeekBar seek;
    ImageView image;
    String zips;
    String apiKey = "INSERTKEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_page);
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            zips = extras.getString("zipcode");
        }
        seek = findViewById(R.id.timeSwitch);
        date = findViewById(R.id.timeDate);
        home = findViewById(R.id.back);
        next = findViewById(R.id.nextDay);
        previous = findViewById(R.id.previousDay);
        image = findViewById(R.id.weatherImage);
        weather = findViewById(R.id.weather);
        temp = findViewById(R.id.temperature);
        humid = findViewById(R.id.humidity);
        feelslike = findViewById(R.id.feelslike);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(weatherPage.this, MainActivity.class);
                startActivity(intent);
            }
        });


        AsyncThread myThread = new AsyncThread();
        myThread.execute();

    }
    public class AsyncThread extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... voids)
        {
            try
            {

                URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?zip=" + zips + "&appid=" + apiKey).toURI().toURL();
                URLConnection connection = url.openConnection();
                InputStream input = connection.getInputStream();
                BufferedReader readers = new BufferedReader(new InputStreamReader(input));
                String hi = readers.lines().collect(Collectors.joining());
                return hi;

            }
            catch(Exception e)
            {
                Log.d("BROKEN", "BROKEN");
                Log.d("BROKEN", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String everything)
        {
            ArrayList<WeatherDate> dates = sort(everything);
            setUp(dates, 0);
            seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
                {
                    setUp(dates, progress);

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });




        }
    }
    public ArrayList<WeatherDate> sort(String sorting)
    {
        ArrayList<WeatherDate> dates = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(sorting);
           // int s = json.getJSONArray("list").length();
            for (int x = 0; x < 5; x++)
            {

                String date = json.getJSONArray("list").getJSONObject(x).getString("dt_txt");
                String temp = json.getJSONArray("list").getJSONObject(x).getJSONObject("main").getString("temp");
                String feels_temp = json.getJSONArray("list").getJSONObject(x).getJSONObject("main").getString("feels_like");
                String weather = json.getJSONArray("list").getJSONObject(x).getJSONArray("weather").getJSONObject(0).getString("description");
                String humidity = json.getJSONArray("list").getJSONObject(x).getJSONObject("main").getString("humidity");
                WeatherDate wd = new WeatherDate(date, temp, feels_temp, weather, humidity);
                Log.d("WORKING", wd.toString());
                dates.add(wd);
            }
        }
        catch(Exception e)
        {

        }


        return dates;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_weather_page);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void setUp(ArrayList<WeatherDate> dates, int x)
    {
        WeatherDate start = dates.get(x);
        date.setText(start.getDate()[0] + " " + start.getDate()[1]);
        weather.setText("Weather: " + start.getWeather());
        Log.d("WORKING", ""+start.getHumid());
        humid.setText("Humiditiy: " + start.getHumid() + "");
        feelslike.setText("Feels like: " + start.getFeelslikeTemp() + "");
        temp.setText("Actual Temperature: " + start.getTemp()+"");
        if(start.getWeather().contains("snow"))
            image.setImageResource(R.drawable.snow);
        else if(start.getWeather().contains("clear"))
            image.setImageResource(R.drawable.clearsky);
        else if(start.getWeather().contains("cloud"))
            image.setImageResource(R.drawable.clouds);
        else if(start.getWeather().contains("rain") || start.getWeather().contains("drizzle") ||start.getWeather().contains("mist") )
            image.setImageResource(R.drawable.storm);
        else if(start.getWeather().contains("storm"))
            image.setImageResource(R.drawable.storms);
        else
            image.setImageResource(R.drawable.sunny);

    }
}