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
    double temperature;
    String description;
    ImageView transport;
    TextView forecast_description;
    final static String APIkey= "5354c964cdff845f60041f186ea7c710";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation);

        Intent intent3 = getIntent();
        city = intent3.getStringExtra("city");

        findTransportation();

        forecast_description = (TextView) findViewById(R.id.forecast);



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
                String description="";



                // array has only one element, even though we use a loop to get data
                for(int i=0; i<array.length(); i++){
                    JSONObject temp = array.getJSONObject(i);
                    description = temp.getString("main");

                }
                forecast_description.setText(description);
                /*if(description.equals("Thunderstorm")){

                    transport.setImageResource(R.drawable.bus);

                }
                if(description.equals("Drizzle")){

                    transport.setImageResource(R.drawable.car);

                }
                if(description.equals("Rain")){

                    transport.setImageResource(R.drawable.car);

                }
                if(description.equals("Snow")){

                    transport.setImageResource(R.drawable.apiedi);

                }
                if(description.equals("Clear")){

                    transport.setImageResource(R.drawable.apiedi);

                }
                if(description.equals("Clouds")){

                    transport.setImageResource(R.drawable.scooter);

                }*/

                switch(description){

                    case "Thunderstorm":
                        transport.setImageResource(R.drawable.bus);
                        break;
                    case "Drizzle":
                        transport.setImageResource(R.drawable.car);
                        break;
                    case "Rain":
                        transport.setImageResource(R.drawable.car);
                        break;
                    case "Snow":
                        transport.setImageResource(R.drawable.apiedi);
                        break;
                    case "Clear":
                        transport.setImageResource(R.drawable.apiedi);
                        break;
                    case "Clouds":
                        transport.setImageResource(R.drawable.scooter);
                        break;
                    default:
                        transport.setImageResource(R.drawable.bus);

                }













            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}