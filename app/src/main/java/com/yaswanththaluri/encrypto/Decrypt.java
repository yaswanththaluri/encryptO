package com.yaswanththaluri.encrypto;

import android.app.ActivityOptions;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmAsyncTask;

public class Decrypt extends AppCompatActivity {


    private ImageView goBack;
    private TextView head;
    private TextView hint, pinHint;
    private ImageView decrypt, copy, clear, save, decryptPin;
    private LinearLayout pinLay;
    private EditText pin1, pin2, pin3, msg;
    private String messageToDecrypt, decryptedMsg;
    private Realm realm;
    private RealmAsyncTask realmAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt);

        realm = Realm.getDefaultInstance();

        head = findViewById(R.id.head);
        Typeface typeface2 = Typeface.createFromAsset(getAssets(), "fonts/macondo.ttf");
        head.setTypeface(typeface2);

        save = findViewById(R.id.saveDecryptMessage);

        hint = findViewById(R.id.hintDecrypt);
        pinHint = findViewById(R.id.pinHint);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/stylish.ttf");
        hint.setTypeface(typeface);
        pinHint.setTypeface(typeface);

        goBack = findViewById(R.id.goHome);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Decrypt.this, MainActivity.class);
                Pair[] p = new Pair[1];
                p[0] = new Pair(head, "trans2");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Decrypt.this, p);
                startActivity(i, options.toBundle());
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        pin1 = findViewById(R.id.pin1);
        pin2 = findViewById(R.id.pin2);
        pin3 = findViewById(R.id.pin3);
        copy = findViewById(R.id.copy);
        msg = findViewById(R.id.msg);




        pin1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (pin1.getText().length() == 1)
                {
                    pin1.clearFocus();
                    pin2.requestFocus();
                }

            }
        });



        pin2.addTextChangedListener(new TextWatcher() {

            private int previousLength;
            private boolean backSpace;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                previousLength = charSequence.length();

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (pin2.getText().length() == 1)
                {
                    pin2.clearFocus();
                    pin3.requestFocus();
                }

                backSpace = previousLength > editable.length();

                if (backSpace)
                {
                    pin2.clearFocus();
                    pin1.requestFocus();
                }

            }
        });



        pin3.addTextChangedListener(new TextWatcher() {

            private int previousLength;
            private boolean backSpace;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                previousLength = charSequence.length();

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                pin3.clearFocus();

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(pin3.getWindowToken(), 0);

                backSpace = previousLength > editable.length();

                if (backSpace)
                {
                    pin3.clearFocus();
                    pin2.requestFocus();
                }

            }
        });


        decryptPin = findViewById(R.id.decryptMessage);

        decryptPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String p1 = pin1.getText().toString();
                String p2 = pin2.getText().toString();
                String p3 = pin3.getText().toString();


                if (!p1.equals("") && !p2.equals("") && !p3.equals(""))
                {
                    int a = Integer.parseInt(p1);
                    int b = Integer.parseInt(p2);
                    int c = Integer.parseInt(p3);


                    decryptMessage(messageToDecrypt, a, b, c);


                    pin1.setText("");
                    pin2.setText("");
                    pin3.setText("");

                }
                else
                {
                    Toast.makeText(Decrypt.this, "PIN Should be of lenght '3'", Toast.LENGTH_SHORT).show();
                }

            }
        });


        pinLay = findViewById(R.id.pinLayout);
        clear = findViewById(R.id.clear);




        decrypt = findViewById(R.id.decryptEnter);

        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ms = msg.getText().toString();

                if (!ms.equals(""))
                {
                    messageToDecrypt = msg.getText().toString();
                    pinLay.setVisibility(View.VISIBLE);
                    decrypt.setVisibility(View.INVISIBLE);
                }


            }
        });



        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg.setText("");
                hint.setText("Enter Message to Decrypt:");
                copy.setVisibility(View.INVISIBLE);
                clear.setVisibility(View.INVISIBLE);
                decrypt.setVisibility(View.VISIBLE);
                msg.setFocusableInTouchMode(true);

                save.setVisibility(View.INVISIBLE);
            }
        });


        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Decrypted Text", decryptedMsg);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(Decrypt.this, "Message Copied...!", Toast.LENGTH_SHORT).show();
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDecryptedMessage(decryptedMsg);
            }
        });


    }

    @Override
    public void onBackPressed() {

        Toast.makeText(Decrypt.this, "Back Button Disabled! Use arrow to go back", Toast.LENGTH_SHORT).show();

    }



    private void decryptMessage(String message, int x, int y, int z)
    {
        String dec = "";


        int n = 127-33;
        ArrayList<Character> c = new ArrayList<Character>();
        int j = 33;
        for(int i=0; i<n;i++)
        {
            c.add((char)j);
            j++;
        }


        int iter2 = (int) ((x*(Math.sqrt(n))-y)*z);

        for (int i=0; i<message.length(); i++)
        {

            int ind = c.indexOf(message.charAt(i));

            for (int k = 0; k<iter2; k++)
            {
                if(ind == 0)
                {
                    ind = n-1;
                }
                else
                {
                    ind = ind - 1;
                }
            }

            dec = dec + c.get(ind);
        }

        decryptedMsg = dec;

        hint.setText("Your Decrypted Message");

        msg.setText(dec);

        msg.setFocusableInTouchMode(false);

        copy.setVisibility(View.VISIBLE);

        clear.setVisibility(View.VISIBLE);

        pinLay.setVisibility(View.INVISIBLE);

        save.setVisibility(View.VISIBLE);

    }



    private void saveDecryptedMessage(final String message)
    {
        realmAsyncTask = realm.executeTransactionAsync(new Realm.Transaction() {
                                          @Override
                                          public void execute(Realm realm) {

                                              String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

                                              Message msg = realm.createObject(Message.class);
                                              msg.setDate(formattedDate);
                                              msg.setMessageDecrypted(message);
                                              msg.setMessageEncrypted(messageToDecrypt);
                                              msg.setMessageType("DeCrypted Data");

                                          }
                                      }, new Realm.Transaction.OnSuccess() {
                                          @Override
                                          public void onSuccess() {
                                              Toast.makeText(Decrypt.this, "Message Saved Successfully", Toast.LENGTH_SHORT).show();
                                              save.setVisibility(View.INVISIBLE);
                                          }
                                      }, new Realm.Transaction.OnError() {
                                          @Override
                                          public void onError(Throwable error) {
                                              Toast.makeText(Decrypt.this, "Error in saving Message", Toast.LENGTH_SHORT).show();
                                          }
                                      }
        );
    }


    @Override
    protected void onStop() {
        super.onStop();

        if (realmAsyncTask!=null && !realmAsyncTask.isCancelled())
        {
            realmAsyncTask.cancel();
        }

    }
}
