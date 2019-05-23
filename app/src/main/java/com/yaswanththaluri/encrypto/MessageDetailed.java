package com.yaswanththaluri.encrypto;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;

public class MessageDetailed extends AppCompatActivity {


    private String msgType, msgDate, encMessage, decMessage;
    private ImageView copyEnc, copyDec, delete, close;
    private TextView encMessageDetailed, decMessageDetailed, msgTypeDetailed, date;
    private RealmAsyncTask realmAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detailed);


        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();


        msgType = bundle.getString("MsgType");
        msgDate = bundle.getString("MsgDate");
        encMessage = bundle.getString("encMsg");
        decMessage = bundle.getString("decMsg");


        encMessageDetailed = findViewById(R.id.encMessageDetailed);
        decMessageDetailed = findViewById(R.id.decMessageDetailed);
        msgTypeDetailed = findViewById(R.id.msgTypeDetailed);
        date = findViewById(R.id.dateMsg);


        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/stylish.ttf");
        msgTypeDetailed.setTypeface(typeface);


        close = findViewById(R.id.closeActivity);


        encMessageDetailed.setText(encMessage);
        decMessageDetailed.setText(decMessage);
        msgTypeDetailed.setText(msgType);
        date.setText(msgDate);


        copyEnc = findViewById(R.id.copyEncMsgDetailed);
        copyDec = findViewById(R.id.copyDecMsgDetailed);
        delete = findViewById(R.id.deleteMessage);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MessageDetailed.this, History.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });


        copyEnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyMessage(encMessage);
            }
        });


        copyDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyMessage(decMessage);
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData(msgDate);
            }
        });

    }




    private void copyMessage(String messageToCopy)
    {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Encrypted Text", messageToCopy);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(MessageDetailed.this, "Message Copied...!", Toast.LENGTH_SHORT).show();
    }


    private void deleteData(final String id)
    {

        Realm realm = Realm.getDefaultInstance();


        realmAsyncTask = realm.executeTransactionAsync(new Realm.Transaction() {
                                                      @Override
                                                      public void execute(Realm realm) {

                                                          RealmResults<Message> m = realm.where(Message.class).findAll();

                                                          for (Message i : m) {
                                                              if (i.getDate().equals(id)) {

                                                                  i.deleteFromRealm();
                                                              }
                                                          }

                                                      }
                                                  }, new Realm.Transaction.OnSuccess() {
                                                      @Override
                                                      public void onSuccess() {
                                                          Toast.makeText(MessageDetailed.this, "Message Deleted from Database...!", Toast.LENGTH_SHORT).show();
                                                          Intent i = new Intent(MessageDetailed.this, History.class);
                                                          startActivity(i);
                                                          overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                                          finish();

                                                      }
                                                  }, new Realm.Transaction.OnError() {
                                                      @Override
                                                      public void onError(Throwable error) {
                                                          Toast.makeText(MessageDetailed.this, "Error in deleting Message...!", Toast.LENGTH_SHORT).show();

                                                      }
                                                  }
        );



    }


    @Override
    protected void onStop() {
        super.onStop();

        if (realmAsyncTask != null && !realmAsyncTask.isCancelled()) {
            realmAsyncTask.cancel();
        }
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
