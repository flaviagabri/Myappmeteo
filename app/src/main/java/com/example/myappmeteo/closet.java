package com.example.myappmeteo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
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
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class closet extends AppCompatActivity {
    public String city;
    int temperature;
    ImageView imageView,imageView1,imageView2;
    TextView textView2;



    final static String APIkey= "5354c964cdff845f60041f186ea7c710";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);

        Intent intent1 = getIntent();
        city = intent1.getStringExtra("city");

        findClothes();
        //ImageView imageView,imageView1,imageView2;

        //int_temp= Integer.parseInt(temperature);

        imageView=(ImageView) findViewById(R.id.imageView);
        imageView1=(ImageView) findViewById(R.id.imageView2);
        imageView2=(ImageView) findViewById(R.id.imageView3);
        textView2=(TextView) findViewById(R.id.textView2);

         //prova con if

        /*if(temperature>25 && temperature<45) {// molto caldo
            imageView.setImageResource(R.drawable.pantaloncini);
            imageView1.setImageResource(R.drawable.tshirt);
            imageView2.setImageResource(R.drawable.sandali);
        }

        else if(temperature<5) { //molto freddo

            imageView.setImageResource(R.drawable.pantaloni);
            imageView1.setImageResource(R.drawable.maglione);
            imageView2.setImageResource(R.drawable.anfibi);

        }
         else if( temperature>5 &&temperature<15){ //abbastanza freddo
            imageView.setImageResource(R.drawable.pantaloni);
            imageView1.setImageResource(R.drawable.maglione);
            imageView2.setImageResource(R.drawable.anfibi);


        }
         else if( temperature>15 && temperature<25 )// { // temperatura mite
            imageView.setImageResource(R.drawable.pantaleggeri);
            imageView1.setImageResource(R.drawable.camicia);
            imageView2.setImageResource(R.drawable.converse);


        }*/

        //prova con switch-case

        /*switch((-10 <= temperature && temperature<=5)? 0 :
                (5<temperature && temperature<=15)? 1 :
                        (15<temperature && temperature<=25)? 2:
                                (25<temperature && temperature<=45)? 3:4) {

            case 0:
                imageView.setImageResource(R.drawable.pantaloni);
                imageView1.setImageResource(R.drawable.maglione);
                imageView2.setImageResource(R.drawable.anfibi);
                break;
            case 1:
                imageView.setImageResource(R.drawable.pantaloni);
                imageView1.setImageResource(R.drawable.maglione);
                imageView2.setImageResource(R.drawable.anfibi);
                break;
            case 2:
                imageView.setImageResource(R.drawable.pantaleggeri);
                imageView1.setImageResource(R.drawable.camicia);
                imageView2.setImageResource(R.drawable.converse);
                break;
            case 3:
                imageView.setImageResource(R.drawable.pantaloncini);
                imageView1.setImageResource(R.drawable.tshirt);
                imageView2.setImageResource(R.drawable.sandali);
                break;

        }*/
        switch(temperature){

            case -10: case -9: case -8: case -7: case -6: case -5: case -4: case -3: case -2: case -1: case 0: case 1: case 2: case 3: case 4: case 5:
                imageView.setImageResource(R.drawable.pantaloni);
                imageView1.setImageResource(R.drawable.maglione);
                imageView2.setImageResource(R.drawable.anfibi);
                break;
            case 6: case 7: case 8: case 9: case 10: case 11: case 12: case 13: case 14: case 15:
                imageView.setImageResource(R.drawable.pantaloni);
                imageView1.setImageResource(R.drawable.maglione);
                imageView2.setImageResource(R.drawable.anfibi);
                break;
            case 16: case 17: case 18: case 19: case 20: case 21: case 22: case 23: case 24: case 25:
                imageView.setImageResource(R.drawable.pantaleggeri);
                imageView1.setImageResource(R.drawable.camicia);
                imageView2.setImageResource(R.drawable.converse);
                break;
            case 26:  case 27: case 28: case 29: case 30: case 31: case 32: case 33:  case 34: case 35: case 36: case 37: case 38: case 39: case 40:
                imageView.setImageResource(R.drawable.pantaloncini);
                imageView1.setImageResource(R.drawable.tshirt);
                imageView2.setImageResource(R.drawable.sandali);
                break;


        }




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
        //private BreakIterator forecast_description;  //lo ha creato lui ???
        //private int int_temp;   //creato lui

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
                //forecast_description.setText(description);

                JSONObject main = jsonObject.getJSONObject("main");
                temperature=main.getInt("temp");
                String temp=String.valueOf(temperature);
                textView2.setText(temp);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }









}

