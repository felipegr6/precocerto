package br.com.fggs1.gs1project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import br.com.fggs1.gs1project.R;

public class SplashActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        }, 1500);
    }
}