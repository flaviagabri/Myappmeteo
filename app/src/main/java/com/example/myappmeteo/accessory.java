package com.example.myappmeteo;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.TextView;

import android.content.Intent;
import android.os.Bundle;


public class accessory extends AppCompatActivity {


        String description;
        TextView temperature;
        ImageView imageView,imageView1;
        double temp;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_accessory);


            Intent intent2 = getIntent();
            temp = intent2.getDoubleExtra("temperature",-999);
            description=intent2.getStringExtra("description");
            temperature = (TextView) findViewById(R.id.temp_textView);
            temperature.setText(String.valueOf(temp));

            imageView=(ImageView) findViewById(R.id.imageView);
            imageView1=(ImageView) findViewById(R.id.imageView1);

            findAccessory();




        }

    private void findAccessory() {


            if(temp<=10){
               switch(description){

                   case "Clear":{
                       imageView.setImageResource(R.drawable.occhiali);
                       imageView1.setImageResource(R.drawable.scg);
                   }
                   break;
                   case "Drizzle":
                   case "Rain":
                   case "Thunderstorm":
                   case "Snow":{
                       imageView.setImageResource(R.drawable.scg);
                       imageView1.setImageResource(R.drawable.ombrello);
                   }
                   break;
                   default:{
                       imageView.setImageResource(R.drawable.sc);
                       imageView1.setImageResource(R.drawable.guanti);
                   }
                   break;

               }
           }



            if(temp>10 && temp<20){
                   switch(description){

                       case "Clear":{
                           imageView.setImageResource(R.drawable.occhiali);
                       }
                       break;
                       case "Drizzle":
                       case "Rain":
                       case "Thunderstorm":
                       case "Snow":{

                           imageView.setImageResource(R.drawable.ombrello);
                       }
                       break;
                       default:{
                           imageView.setImageResource(R.drawable.sc);
                           imageView1.setImageResource(R.drawable.guanti);
                       }
                       break;

                   }

            }


            if(temp>=20){

                   switch(description){

                       case "Clear":{
                           imageView.setImageResource(R.drawable.occhialic);
                           imageView1.setImageResource(R.drawable.acqua);
                       }
                       break;
                       case "Drizzle":
                       case "Rain":
                       case "Thunderstorm":
                       case "Snow":{
                           imageView.setImageResource(R.drawable.acqua);
                           imageView1.setImageResource(R.drawable.ombrello);
                       }
                       break;
                       default:{
                           imageView.setImageResource(R.drawable.acqua);
                       }
                       break;

                   }

           }


    }
}







