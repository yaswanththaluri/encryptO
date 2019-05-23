package com.yaswanththaluri.encrypto;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class About extends AppCompatActivity {

    private ImageView close;
    private TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        t1 = findViewById(R.id.textLay1);
        t2 = findViewById(R.id.textLay2);
        t3 = findViewById(R.id.textLay3);
        t4 = findViewById(R.id.textLay4);
        t5 = findViewById(R.id.textLay5);
        t6 = findViewById(R.id.textLay6);
        t7 = findViewById(R.id.textLay7);
        t8 = findViewById(R.id.textLay8);
        t9 = findViewById(R.id.textLay9);
        t10 = findViewById(R.id.textLay10);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/stylish.ttf");

        t1.setTypeface(typeface);
        t2.setTypeface(typeface);
        t3.setTypeface(typeface);
        t4.setTypeface(typeface);
        t5.setTypeface(typeface);
        t6.setTypeface(typeface);
        t7.setTypeface(typeface);
        t8.setTypeface(typeface);
        t9.setTypeface(typeface);
        t10.setTypeface(typeface);


        close = findViewById(R.id.closeabout);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(About.this, MainActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
    }
}
