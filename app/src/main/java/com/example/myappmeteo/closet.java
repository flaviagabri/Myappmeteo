package com.example.myappmeteo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class closet extends AppCompatActivity {
    public String city;
    double temperature;
    String main_description;
    ImageView imageView,imageView1,imageView2,imageView3;
    TextView textView2;
    final static String APIkey= "5354c964cdff845f60041f186ea7c710";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);

        Intent intent1 = getIntent();
        city = intent1.getStringExtra("city");

        findClothes();

        imageView=(ImageView) findViewById(R.id.imageView);
        imageView1=(ImageView) findViewById(R.id.imageView2);
        imageView2=(ImageView) findViewById(R.id.imageView3);
        textView2=(TextView) findViewById(R.id.textView2);
        imageView3=(ImageView) findViewById(R.id.imageView5);

    }

    private void findClothes() {

        try{
            // collect data in background through asyncTask
            closet.WeatherTask1 task = new closet.WeatherTask1();
            task.execute("https://api.openweathermap.org/data/2.5/weather?q="+city+"&units=metric&appid="+APIkey);

        }catch (Exception e){
            e.printStackTrace();
        }


    }



public class WeatherTask1 extends AsyncTask<String, Void, String> {


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
                String main_description = "";
                // array has only one element, even though we use a loop to get data
                for(int i=0; i<array.length(); i++){
                    JSONObject temp = array.getJSONObject(i);
                    description = temp.getString("description");
                    main_description = temp.getString("main");
                }
                //forecast_description.setText(description);

                JSONObject main = jsonObject.getJSONObject("main");
                temperature=main.getInt("temp");
                if (temperature <= 5) { //molto freddo
                    imageView.setImageResource(R.drawable.pantaloni);
                    imageView1.setImageResource(R.drawable.maglione);
                    imageView2.setImageResource(R.drawable.anfibi);
                    imageView3.setImageResource(R.drawable.woolrich);
                    Toast.makeText(getApplicationContext(), "Ti suggeriamo di mettere calze pesanti sotto" +
                            "i pantaloni per avere più caldo", Toast.LENGTH_LONG).show();

                }
                else if (temperature >5  && temperature < 10) { //abbastanza freddo
                    imageView.setImageResource(R.drawable.pantaloni);
                    imageView1.setImageResource(R.drawable.felpa);
                    imageView2.setImageResource(R.drawable.anfibi);
                    imageView3.setImageResource(R.drawable.woolrich);

                }
                else if (temperature >= 10 && temperature < 15) { // temperatura mite
                    imageView.setImageResource(R.drawable.jeans);
                    imageView1.setImageResource(R.drawable.felpa);
                    imageView2.setImageResource(R.drawable.sneakers);
                    imageView3.setImageResource(R.drawable.cappottino);

                }
                else if (temperature >= 15 && temperature < 20) {// molto caldo
                    imageView.setImageResource(R.drawable.pantaleggeri);
                    imageView1.setImageResource(R.drawable.camicia);
                    imageView2.setImageResource(R.drawable.converse);
                    imageView3.setImageResource(R.drawable.pelle);
                }

                else if (temperature >= 20 && temperature < 25) {

                    imageView.setImageResource(R.drawable.pantaleggeri);
                    imageView1.setImageResource(R.drawable.tshirt);
                    imageView2.setImageResource(R.drawable.converse);
                    imageView3.setImageResource(R.drawable.jeans2);

                }
                else if (temperature >= 25 ) {


                    imageView.setImageResource(R.drawable.pantaloncini);
                    imageView1.setImageResource(R.drawable.tshirt);
                    imageView2.setImageResource(R.drawable.sandali);
                    imageView3.setImageResource(R.drawable.bianco);


                }









            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }


    public void openAccessorize(View view){
        Log.i("temp", "Temp is"+temperature);
        Intent intent2= new Intent(this, accessory.class);
        intent2.putExtra("temperature", temperature);
        intent2.putExtra("description",main_description);
        startActivity(intent2);
    }









}

