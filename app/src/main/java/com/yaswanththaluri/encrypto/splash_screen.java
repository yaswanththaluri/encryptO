package com.yaswanththaluri.encrypto;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        TextView name = findViewById(R.id.devName);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/stylish.ttf");
        name.setTypeface(typeface);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        },2000);
    }

}
