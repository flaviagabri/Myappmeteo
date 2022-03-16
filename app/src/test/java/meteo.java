public class meteo {
}


package com.example.myclosetweather;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Log;
        import android.widget.TextView;

        import com.google.android.material.snackbar.Snackbar;

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

public class Meteo extends AppCompatActivity {
    public String city;
    final static String APIkey= "5354c964cdff845f60041f186ea7c710";
    TextView city_text_view, country_text_view, time, temp, feels_temp, forecast_description, temp_min, temp_max, humidity, wind_sp, visibility, sunrise, sunset;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo);
        Intent intent = getIntent();
        city = intent.getStringExtra("city");
        Log.i("Nuvola", "city " + city);
        //all answers we are getting from web
        forecast_description = (TextView) findViewById(R.id.forecast);
        temp = (TextView) findViewById(R.id.temp);
        feels_temp = (TextView) findViewById(R.id.feels_temp);
        time = (TextView) findViewById(R.id.time);
        temp_min = (TextView) findViewById(R.id.min_temp);
        temp_max = (TextView) findViewById(R.id.max_temp);
        wind_sp = (TextView) findViewById(R.id.wind_speed);
        humidity = (TextView) findViewById(R.id.humidity);
        visibility = (TextView) findViewById(R.id.visibility);
        sunrise = (TextView) findViewById(R.id.sunrise);
        sunset = (TextView) findViewById(R.id.sunset);
        city_text_view = (TextView) findViewById(R.id.selected_city);
        country_text_view = (TextView) findViewById(R.id.selected_country);


        //Snackbar.make(findViewById(R.id.meteo_layout), "Loading weather", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        findWeather();

    }


    // this method execute the link and collect weather data using API from OpenWeatherMAp
    public void findWeather(){

        try{
            // collect data in background through asyncTask
            WeatherTask task = new WeatherTask();
            task.execute("https://api.openweathermap.org/data/2.5/weather?q="+city+"&units=metric&appid="+APIkey);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public class WeatherTask extends AsyncTask<String, Void, String> {
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

                //get needed data from JSON

                JSONObject jsonObject = new JSONObject(s);

                JSONArray array = jsonObject.getJSONArray("weather");
                String description="";
                // array has only one element, even though we use a loop to get data
                for(int i=0; i<array.length(); i++){
                    JSONObject temp = array.getJSONObject(i);
                    description = temp.getString("description");
                }
                forecast_description.setText(description);

                JSONObject main = jsonObject.getJSONObject("main");
                String temperature = main.getString("temp");
                String temp_felt = main.getString("feels_like");
                String min = main.getString("temp_min");
                String max = main.getString("temp_max");
                String hum = main.getString("humidity");
                String vis = jsonObject.getString("visibility");
                JSONObject wind = jsonObject.getJSONObject("wind");
                String speed = wind.getString("speed");
                JSONObject sys = jsonObject.getJSONObject("sys");
                // remember to get country from here (sys) when you move this to the secondary activity
                String country = sys.getString("country");
                // time in the JSON are in Unix format
                Long rise = sys.getLong("sunrise");
                String sunr = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date(rise*1000));
                Long set = sys.getLong("sunset");
                String suns = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date(set*1000));
                // remember to get the name of the city from the generic JSON through the key "name"
                String city_name = jsonObject.getString("name");
                Long dt = jsonObject.getLong("dt");
                String updateTime = "Last updated at: "+new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date(dt*1000));

                // put all data collected into the textviews previously created
                temp.setText(temperature+" °C");
                feels_temp.setText("Feels like: "+temp_felt+" °C");
                temp_min.setText(min+" °C");
                temp_max.setText(max+" °C");
                humidity.setText(hum+" %");
                wind_sp.setText(speed+" m/s");
                visibility.setText(vis + " m");
                time.setText(updateTime);
                sunrise.setText(sunr);
                sunset.setText(suns);
                country_text_view.setText(country);
                city_text_view.setText(city_name);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}