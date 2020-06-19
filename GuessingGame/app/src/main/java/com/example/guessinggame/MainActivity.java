package com.example.guessinggame;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // Variables
    private EditText txtGuess;
    private Button btnGuess;
    private TextView lblOutput;
    private int theNumber;
    private int range = 100;
    private int noTries = 0;
    private TextView lblRange;

    // Checks to see if the user guessed the correct number.
    public void checkGuess() {
        String guessText = txtGuess.getText().toString(); // EditText is a class, and needs .toString()
        String message = "";
        try {
            noTries++;
            int guess = Integer.parseInt(guessText); // Converts the guess to an int
            if (guess < theNumber)
                message = "Too low";
            else if (guess > theNumber)
                message = "Too high";
            else {
                message = "Correct! Number of tries: " + noTries + ".";
                // Makes a Toast pop-up at the bottom of the screen
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                // Stores the number in "memory", to be accessed between sessions
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this); // Få SharedPreferences fra this
                int gamesWon = preferences.getInt("gamesWon", 0) + 1; // Få tallet fra storage, 0 hvis den ikke eksisterer, og inkrementer
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("gamesWon", gamesWon); // Opdater tallet med det nye (+1)
                editor.apply();
                newGame();
            }
        } catch (Exception e) {
            message = "Enter a whole number between 1 and " + range + ".";
        } finally {
            lblOutput.setText(message);
            txtGuess.requestFocus();
            txtGuess.selectAll();
        }
    }

    public void newGame() {
        noTries = 0;
        theNumber = (int)(Math.random() * range + 1);
        lblRange.setText("Enter a number between 1 and " + range + ".");
        txtGuess.setText("" + range/2);
        txtGuess.requestFocus();
        txtGuess.selectAll();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Added
        /*
            R inside that function refers to a special file called R.java
            that Android Studio generates to make it possible to connect resources.
            The R is short for resources, usually stores in the res folder in the project.
        */
        txtGuess = (EditText) findViewById(R.id.txtGuess);  // Self-explanatory.
        btnGuess = (Button) findViewById(R.id.btnGuess);
        lblOutput = (TextView) findViewById(R.id.lblOutput);
        lblRange = (TextView) findViewById(R.id.txtRules);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        range = preferences.getInt("range", 100); // Get stored variable, otherwise give it 100
        newGame();
        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGuess();
            }
        }); // Når knappen bliver trykket, tjek om svaret er rigtigt.
        txtGuess.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                checkGuess();
                return true;
            }
        }); // Når ENTER bliver trykket, tjek om svaret er rigtigt.


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Added/Changed
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Switch/Case af de forskellige valg fra menuen.
        switch (item.getItemId()) {
            case R.id.action_settings:
                final CharSequence[] items = {
                        "1 to 10",
                        "1 to 100",
                        "1 to 1000"
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Select the Range:");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item) {
                            case 0:
                                range = 10;
                                storeRange(10);
                                newGame();
                                break;
                            case 1:
                                range = 100;
                                storeRange(100);
                                newGame();
                                break;
                            case 2:
                                range = 1000;
                                storeRange(1000);
                                newGame();
                                break;
                        }
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            case R.id.action_newgame:
                newGame();
                return true;
            case R.id.action_gamestats:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                int gamesWon = preferences.getInt("gamesWon", 0);
                AlertDialog statDialog = new AlertDialog.Builder(MainActivity.this).create();
                statDialog.setTitle("Guessing Game Stats");
                statDialog.setMessage("You have won "+gamesWon+ " games. Find a better game.");
                statDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                statDialog.show();
                return true;
            case R.id.action_about:
                AlertDialog aboutDialog = new AlertDialog.Builder(MainActivity.this).create();
                aboutDialog.setTitle("About Guessing Game");
                aboutDialog.setMessage("(c)2020 YOUR NAME HERE.");
                aboutDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                aboutDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    // Added
    public void storeRange(int newRange) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("range", newRange);
        editor.apply();

    }
}
