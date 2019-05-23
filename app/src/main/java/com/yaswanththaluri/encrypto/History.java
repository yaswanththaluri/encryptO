package com.yaswanththaluri.encrypto;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class History extends AppCompatActivity {

    private ImageView goBack, noresults;
    private TextView head;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private RealmResults<Message> msgList;
    private List<Message> msg;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        realm = Realm.getDefaultInstance();

        noresults = findViewById(R.id.noresults);

        head = findViewById(R.id.head);
        Typeface typeface2 = Typeface.createFromAsset(getAssets(), "fonts/macondo.ttf");
        head.setTypeface(typeface2);

        goBack = findViewById(R.id.goHome);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(History.this, MainActivity.class);
                Pair[] p = new Pair[1];
                p[0] = new Pair(head, "trans3");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(History.this, p);
                finish();
                startActivity(i, options.toBundle());

                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        recyclerView = findViewById(R.id.listview);
        msg = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(History.this));

        messageAdapter = new MessageAdapter(msg, History.this);
        recyclerView.setAdapter(messageAdapter);




    }

    @Override
    public void onBackPressed() {

        Toast.makeText(History.this, "Back Button Disabled! Use arrow to go back", Toast.LENGTH_SHORT).show();

    }


    private void accessData()
    {
        msgList = realm.where(Message.class).findAll();
        for (Message i : msgList)
        {
            msg.add(i);
            messageAdapter.notifyDataSetChanged();
        }

        int count = messageAdapter.getItemCount();

        if(count == 0)
        {
            noresults.setVisibility(View.VISIBLE);
        }
        else
        {
            noresults.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        accessData();
    }

    @Override
    public void onPause() {
        super.onPause();
        msg.clear();
    }




}
