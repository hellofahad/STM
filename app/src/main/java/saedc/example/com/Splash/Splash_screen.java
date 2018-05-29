package saedc.example.com.Splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import saedc.example.com.LoginPage.Login;
import saedc.example.com.R;

public class Splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread  thread = new Thread(){

            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent Intent = new Intent(getApplicationContext(),Login.class);
                    startActivity(Intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

    }
}
