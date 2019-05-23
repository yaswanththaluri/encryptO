package com.yaswanththaluri.encrypto;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView encrypt;
    private TextView decrypt;
    private TextView history;
    private TextView head;
    private ImageView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        encrypt = findViewById(R.id.encrypt);
        decrypt = findViewById(R.id.decrypt);
        history = findViewById(R.id.history);
        head = findViewById(R.id.head);
        info = findViewById(R.id.info);


        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/stylish.ttf");
        encrypt.setTypeface(typeface);
        decrypt.setTypeface(typeface);
        history.setTypeface(typeface);

        Typeface typeface2 = Typeface.createFromAsset(getAssets(), "fonts/macondo.ttf");
        head.setTypeface(typeface2);


        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Encrypt.class);
                Pair[] p = new Pair[1];
                p[0] = new Pair(encrypt, "trans1");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, p);
                startActivity(i, options.toBundle());
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Decrypt.class);
                Pair[] p = new Pair[1];
                p[0] = new Pair(decrypt, "trans2");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, p);
                startActivity(i, options.toBundle());
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, History.class);
                Pair[] p = new Pair[1];
                p[0] = new Pair(history, "trans3");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, p);
                startActivity(i, options.toBundle());
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, About.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });



    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {


        // Complete all the activities

        if (doubleBackToExitPressedOnce) {
            finishAffinity();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }

}
