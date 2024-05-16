package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    TextView title;
    EditText zipcode;
    Button search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = findViewById(R.id.name);
        zipcode = findViewById(R.id.enterZip);
        search = findViewById(R.id.searchButton);
        //activity_main =
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(zipcode.getText().length() == 5 && checkInt(zipcode.getText()))
                {
                    Intent intent = new Intent(MainActivity.this, weatherPage.class);
                    intent.putExtra("zipcode", zipcode.getText().toString());
                    startActivity(intent);
                }
            }
        });


        //code here #1
        //update views (textview, imageview, other views...)

    }


    public boolean checkInt(Editable edit)
    {
        String check = edit.toString();
        for(int x = 0; x < check.length(); x++)
        {
            if(!Character.isDigit(check.charAt(x)))
                return false;
        }
        return true;
    }
}