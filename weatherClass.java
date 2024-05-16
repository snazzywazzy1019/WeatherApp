package com.example.weatherapp;

import android.util.Log;

import java.nio.channels.ScatteringByteChannel;

public class WeatherDate
{
    private final String[] date;
    private final int temp;
    private final int feelslikeTemp;
    private final int humidity;
    private final String weather;
    public WeatherDate(String d, String t, String ft, String w, String h)
    {
        date = convertDate(d);
        temp = convertTemp(Float.parseFloat(t));
        feelslikeTemp = convertTemp(Float.parseFloat(ft));
        weather = w;
        humidity = Integer.parseInt(h);
    }
    public String[] getDate()
    {
        return date;
    }
    public int getHumid()
    {
        return humidity;
    }
    public float getFeelslikeTemp(){ return feelslikeTemp; }

    public String getWeather() {
        return weather;
    }

    public float getTemp() {
        return temp;
    }
    public String toString()
    {
        return "D: " + date[0] + " TIME: " + date[1] + " T: " + temp + " W: " + weather;
    }
    public String[] convertDate(String date)
    {
        Log.d("WORKING", date);
        String[] hi = date.split(" ");
        Log.d("WORKING",hi[0].substring(hi[0].indexOf("-") + 1, hi[0].indexOf("-") + 3) );
        Log.d("WORKING", hi[0].substring(hi[0].lastIndexOf("-")));
        try
        {
            int month = Integer.parseInt(hi[0].substring(hi[0].indexOf("-") + 1, hi[0].indexOf("-") + 3));
            switch(month)
            {
                case 1:
                    hi[0] = "JAN " + hi[0].substring(hi[0].lastIndexOf("-") + 1);
                    break;
                case 2:
                    hi[0] = "FEB " + hi[0].substring(hi[0].lastIndexOf("-") + 1);
                    break;
                case 3:
                    hi[0] = "MARCH " + hi[0].substring(hi[0].lastIndexOf("-") + 1);
                    break;
                case 4:
                    hi[0] = "APRIL " + hi[0].substring(hi[0].lastIndexOf("-") + 1);
                    break;
                case 5:
                    hi[0] = "MAY " + hi[0].substring(hi[0].lastIndexOf("-") + 1);
                    break;
                case 6:
                    hi[0] = "JUNE " + hi[0].substring(hi[0].lastIndexOf("-") + 1);
                    break;
                case 7:
                    hi[0] = "JULY " + hi[0].substring(hi[0].lastIndexOf("-") + 1);
                    break;
                case 8:
                    hi[0] = "AUG " + hi[0].substring(hi[0].lastIndexOf("-") + 1);
                    break;
                case 9:
                    hi[0] = "SEP " + hi[0].substring(hi[0].lastIndexOf("-") + 1);
                    break;
                case 10:
                    hi[0] = "OCT " + hi[0].substring(hi[0].lastIndexOf("-") + 1);
                    break;
                case 11:
                    hi[0] = "NOV " + hi[0].substring(hi[0].lastIndexOf("-") + 1);
                    break;
                case 12:
                    hi[0] = "DEC " + hi[0].substring(hi[0].lastIndexOf("-") + 1);
                    break;
            }
        }
        catch(NumberFormatException e)
        {
            Log.d("WORKING", "SOMETHING WRONG WITH DATE");
            hi[0] = "BROKEN";
        }

        hi[1] = convertTime(hi);

        return hi;

    }

    public String convertTime(String[] hi)
    {
        String timeGW = hi[1];
        String timeZone = "am";
        int time = Integer.parseInt(timeGW.substring(0,2));
        if(time - 5 < 0)
        {
            time = 24 + (time-5);
            try {
                hi[0] = hi[0].substring(0, hi[0].indexOf(" ") + 1) + (Integer.parseInt(hi[0].substring(hi[0].indexOf(" ")+1)) - 1);
            }
            catch(Exception e)
            {
                Log.d("WORKING", e.toString());
            }
        }
        else
            time = time - 5;

        if(time < 10)
        {
            return "0" + time + ":00" + timeZone;
        }
        if(time > 12)
        {
            time = time - 12;
            timeZone = "pm";
        }

        return time + ":00 " + timeZone;
    }
    public int convertTemp(float kelvin)
    {
        return (int)((kelvin - 275.15f) * 1.8f + 32f);
    }

}
