package com.example.myappmeteo;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import android.content.Intent;
import android.os.Bundle;


public class accessory extends AppCompatActivity {


        TextView temperature;
        String temp="";
        String description="";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_accessory);


            Intent intent2 = getIntent();
            temp = intent2.getStringExtra("temperature");
            description=intent2.getStringExtra("description");
            temperature = (TextView) findViewById(R.id.temp_textView);
            temperature.setText(temp);


        }
    }






