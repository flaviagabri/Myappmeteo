package com.example.myappmeteo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    EditText cityField;
    String cityName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityField = (EditText) findViewById(R.id.city_name_editText);

    }
    public void openWeather(View view){
        Intent intent= new Intent(this, com.example.myappmeteo.Meteo.class);
        cityName = cityField.getText().toString();
        //Log.i("Nuvola", "city " + cityName);
        intent.putExtra("city", cityName);//passing the city name to Meteo activity

        startActivity(intent);
    }

    public void openCloset (View view){
        Intent intent1= new Intent(this, com.example.myappmeteo.closet.class);
        cityName = cityField.getText().toString();
        intent1.putExtra("city", cityName); //passing the city name to closet activity

        startActivity(intent1);
    }

    public void openTransportation(View view) {
        Intent intent3= new Intent(this, com.example.myappmeteo.transportation.class);
        cityName = cityField.getText().toString();
        intent3.putExtra("city", cityName);

        startActivity(intent3);
    }
}