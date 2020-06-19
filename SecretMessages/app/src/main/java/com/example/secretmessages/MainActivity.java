package com.example.secretmessages;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText txtIn;
    EditText txtEncode;
    EditText txtOut;
    SeekBar sbEncode;
    Button btnEncode;

    public String encode(String message, int keyVal) {
        String output = "";
        char key = (char) keyVal;
        try {
            if (keyVal >= -25 && keyVal <= 25) {
                key = (char) keyVal;
            }
        } catch (Exception e) {
        }

        for (int i = 0; i < message.length(); i++) {
            char input = message.charAt(i);
            message.indexOf(i);
            if (input >= 'A' && input <= 'Z') {
                input = applyKey(input, 'A', 'Z', key, 26);
            }
            else if (input >= 'a' && input <= 'z') {
                input = applyKey(input, 'a', 'z', key, 26);
            }
            else if (input >= '0' && input <= '9') {
                input = applyKey(input, '0', '9', key, 10);
            }
            output += input;
        }
        return output;
    }

    private static char applyKey(char input, char cond1, char cond2, char key, int foo) {
        if (input >= cond1 && input <= cond2) {
            input += key;
            if (input > cond2)
                input -= foo;
            if (input < cond1)
                input += foo;
        }
        return input;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Added
        txtIn = (EditText)findViewById(R.id.txtIn);
        txtOut = (EditText)findViewById(R.id.txtOut);
        txtEncode = (EditText)findViewById(R.id.txtEncode);
        sbEncode = (SeekBar) findViewById(R.id.sbEncode);
        btnEncode = (Button) findViewById(R.id.btnEncode);

        Intent receivedIntent = getIntent(); // variable to accept the incoming intent message from another app.
        String receivedText = receivedIntent.getStringExtra(Intent.EXTRA_TEXT); // The apprts of the message is called "EXTRA TEXT"
        if (receivedText != null)
            txtIn.setText(receivedText);


        sbEncode.setProgress(13);
        txtEncode.setText("" + sbEncode.getProgress());
        btnEncode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int key = Integer.parseInt(txtEncode.getText().toString());
                    txtOut.setText(encode(txtIn.getText().toString(), key));
                } catch (Exception e) { }

            }
        });

        sbEncode.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int key = sbEncode.getProgress() - 13;
                txtOut.setText(encode(txtIn.getText().toString(), key));
                txtEncode.setText("" + key);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND); // an Intent in Android is an activity that we want to start or launch email, twitter, camera.
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Secret Message " + DateFormat.getDateTimeInstance().format(new Date()));
                shareIntent.putExtra(Intent.EXTRA_TEXT, txtOut.getText().toString());
                try {
                    startActivity(Intent.createChooser(shareIntent, "Share message..."));
                    finish();
                } catch (android.content.ActivityNotFoundException e) {
                    Toast.makeText(MainActivity.this, "Error: Couldn't share.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
