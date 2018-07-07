package com.example.manpreetkaur.pet_treasure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_);

        Thread myThread = new Thread(){
            @Override
            public void run() {

                try{
                    sleep(3000);

                    Intent login_intent = new Intent(SplashScreen_Activity.this, Login_Activity.class);
                    startActivity(login_intent);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        myThread.start();
    }
}
