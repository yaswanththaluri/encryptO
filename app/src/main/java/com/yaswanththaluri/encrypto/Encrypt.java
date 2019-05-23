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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmAsyncTask;

public class Encrypt extends AppCompatActivity {


    private ImageView goBack;
    private TextView head;
    private TextView hint, keyHint;
    private ImageView submitMessage, copy, clear, save;
    private LinearLayout pinLayout;
    private EditText pin1, pin2, pin3, msgField;
    private ImageView encrypt;
    private String messageToEncrypt, decryptedMsg;
    private Realm realm;
    private RealmAsyncTask realmAsyncTask;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);


        realm = Realm.getDefaultInstance();


        pinLayout = findViewById(R.id.pinLayout);
        msgField = findViewById(R.id.msg);

        copy = findViewById(R.id.copy);
        clear = findViewById(R.id.clear);

        save = findViewById(R.id.saveMsg);


        pin1 = findViewById(R.id.pin1);
        pin2 = findViewById(R.id.pin2);
        pin3 = findViewById(R.id.pin3);


        head = findViewById(R.id.head);
        Typeface typeface2 = Typeface.createFromAsset(getAssets(), "fonts/macondo.ttf");
        head.setTypeface(typeface2);

        hint = findViewById(R.id.hintEncrypt);
        keyHint = findViewById(R.id.keyHint);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/stylish.ttf");
        keyHint.setTypeface(typeface);
        hint.setTypeface(typeface);

        goBack = findViewById(R.id.goHome);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Encrypt.this, MainActivity.class);
                Pair[] p = new Pair[1];
                p[0] = new Pair(head, "trans1");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Encrypt.this, p);
                startActivity(i, options.toBundle());
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


        submitMessage = findViewById(R.id.encrypt);

        submitMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                messageToEncrypt = msgField.getText().toString();

                if (!messageToEncrypt.equals(""))
                {
                    pinLayout.setVisibility(View.VISIBLE);
                    messageToEncrypt = msgField.getText().toString();
                    submitMessage.setVisibility(View.INVISIBLE);
                }
                else
                {
                    Toast.makeText(Encrypt.this, "Message field should not e Empty", Toast.LENGTH_SHORT).show();
                }

            }
        });



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


        encrypt = findViewById(R.id.encryptMessage);

        encrypt.setOnClickListener(new View.OnClickListener() {
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


                    encryptMessage(messageToEncrypt, a, b, c);

                    pin1.setText("");
                    pin2.setText("");
                    pin3.setText("");

                }
                else
                {
                    Toast.makeText(Encrypt.this, "PIN Should be of lenght '3'", Toast.LENGTH_SHORT).show();
                }

            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                msgField.setFocusableInTouchMode(true);
                msgField.setText("");

                clear.setVisibility(View.INVISIBLE);
                copy.setVisibility(View.INVISIBLE);
                submitMessage.setVisibility(View.VISIBLE);

                save.setVisibility(View.INVISIBLE);

                hint.setText("Enter Message to Encrypt:");

            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Encrypted Text", decryptedMsg);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(Encrypt.this, "Message Copied...!", Toast.LENGTH_SHORT).show();

            }
        });




        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveMessageToDatabase(decryptedMsg);

            }
        });




    }

    @Override
    public void onBackPressed() {

        Toast.makeText(Encrypt.this, "Back Button Disabled! Use arrow to go back", Toast.LENGTH_SHORT).show();

    }


    private void encryptMessage(String message, int d, int e, int f)
    {

        int n = 127-33;
        ArrayList<Character> c = new ArrayList<Character>();
        int j = 33;
        for(int i=0; i<n;i++)
        {
            c.add((char)j);
            j++;
        }


        int iter = (int) ((d*(Math.sqrt(n))-e)*f);


        String newStr = "";

        for (int i=0; i<message.length(); i++)
        {
            int asc = (int)message.charAt(i);
            int ind = c.indexOf(message.charAt(i));

            for (int k = 0; k<iter; k++)
            {
                if(ind == n-1)
                {
                    ind = 0;
                }
                else
                {
                    ind = ind + 1;
                }
            }

            newStr = newStr + c.get(ind);
        }

        decryptedMsg = newStr;


        hint.setText("Your Encrypted Message");


        msgField.setText(newStr);

        msgField.setFocusableInTouchMode(false);

        copy.setVisibility(View.VISIBLE);

        clear.setVisibility(View.VISIBLE);

        pinLayout.setVisibility(View.INVISIBLE);

        save.setVisibility(View.VISIBLE);

        Toast.makeText(Encrypt.this, "Message Encrypted", Toast.LENGTH_SHORT).show();

    }




    private void saveMessageToDatabase(final String message)
    {


        realmAsyncTask = realm.executeTransactionAsync(new Realm.Transaction() {
                                          @Override
                                          public void execute(Realm realm) {

                                              String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

                                              Message msg = realm.createObject(Message.class);
                                              msg.setDate(formattedDate);
                                              msg.setMessageEncrypted(message);
                                              msg.setMessageDecrypted(messageToEncrypt);
                                              msg.setMessageType("EnCrypted Data");

                                          }
                                      }, new Realm.Transaction.OnSuccess() {
                                          @Override
                                          public void onSuccess() {
                                              Toast.makeText(Encrypt.this, "Message Saved Successfully", Toast.LENGTH_SHORT).show();
                                              save.setVisibility(View.INVISIBLE);
                                          }
                                      }, new Realm.Transaction.OnError() {
                                          @Override
                                          public void onError(Throwable error) {
                                              Toast.makeText(Encrypt.this, "Error in saving Message", Toast.LENGTH_SHORT).show();
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
