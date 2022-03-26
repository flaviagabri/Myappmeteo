package com.example.myappmeteo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class transportation extends AppCompatActivity {

    public String city;

    ImageView transport, transport2;
    TextView forecast_description, message, message2;
    final static String APIkey= "5354c964cdff845f60041f186ea7c710";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation);

        Intent intent3 = getIntent();
        city = intent3.getStringExtra("city");


        forecast_description = (TextView) findViewById(R.id.forecast);
        transport = (ImageView) findViewById(R.id.transport);
        message = (TextView) findViewById(R.id.message);
        transport2 = (ImageView) findViewById(R.id.transport2);
        message2 = (TextView) findViewById(R.id.message2);


        findTransportation();
    }


    // this method execute the link and collect weather data using API from OpenWeatherMAp
    public void findTransportation(){

        try{
            // collect data in background through asyncTask
            transportation.WeatherTask2 task = new transportation.WeatherTask2();
            task.execute("https://api.openweathermap.org/data/2.5/weather?q="+city+"&units=metric&appid="+APIkey);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public class WeatherTask2 extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        // this method collect the entire JSON file into the string res
        @Override
        protected String doInBackground(String... strings) {

            String res = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(strings[0]);

                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream is = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(is);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    res = res + current;
                    data = reader.read();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res;
        }

        // when the method doInBackground is done, this method onPostExecute starts,
        // receiving in input the string res from doInBackground
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                JSONObject jsonObject = new JSONObject(s);

                JSONArray array = jsonObject.getJSONArray("weather");
                String main_description="";



                // array has only one element, even though we use a loop to get data
                for(int i=0; i<array.length(); i++){
                    JSONObject temp = array.getJSONObject(i);
                    main_description = temp.getString("main");

                }
                JSONObject main = jsonObject.getJSONObject("main");
                String temperature = main.getString("temp");

                forecast_description.setText("Current weather: "+main_description.toLowerCase());

                switch(main_description){
                    case "Thunderstorm":
                        transport.setImageResource(R.drawable.bus);
                        message.setText("you can take the bus to stay safe!");
                        break;
                    case "Drizzle":
                    case "Rain": {
                        transport.setImageResource(R.drawable.car);
                        message.setText("use the car rather than the scooter");
                        transport2.setImageResource(R.drawable.bus);
                        message2.setText("or you can take the bus!");
                    }
                    break;
                    case "Snow":
                        transport.setImageResource(R.drawable.apiedi);
                        message.setText("you may take a walk, but be careful!");
                        break;
                    case "Clear":
                    {
                        transport.setImageResource(R.drawable.apiedi);
                        message.setText("you may take a walk");
                        transport2.setImageResource(R.drawable.bicicletta);
                        message2.setText("or you can ride your bike!");
                    }
                    break;
                    case "Clouds":
                    {
                        transport.setImageResource(R.drawable.scooter);
                        message.setText("you may take your scooter");
                        transport2.setImageResource(R.drawable.car);
                        message2.setText("or you can take the car!");
                    }
                    break;

                    case "Mist":
                    case "Smoke":
                    case "Haze":
                    case "Fog":
                    {
                        transport.setImageResource(R.drawable.apiedi);
                        message.setText("you may take a walk");
                        transport2.setImageResource(R.drawable.car);
                        message2.setText("or you can take the car equipped with fog lights.Pay attention!");
                    }
                    break;
                    case "Tornado":
                    case "Squall":
                    {
                        message.setText("don't go anywhere unless it is important.Stay safe!");
                        message2.setText("Stay home!");

                    }
                    break;


                    default:
                    {
                        transport.setImageResource(R.drawable.sorry);
                        message.setText("Sorry, we couldn't find any transportation advice");
                    }
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}