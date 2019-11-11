package com.example.galgeleg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Game_act extends AppCompatActivity implements View.OnClickListener {

    static Galgelogik logik = new Galgelogik();
    private TextView wrongLetterText, wordToGuess;
    private Button guessButton;
    private EditText letterReciever;
    private ImageView iv;
    private AlertDialog.Builder dialog;
    private SharedPreferences prefs;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        wrongLetterText = findViewById(R.id.wrongLetter);
        wordToGuess = findViewById(R.id.word);

        guessButton = findViewById(R.id.button);
        guessButton.setOnClickListener(this);

        letterReciever = findViewById(R.id.insertLetter);

        iv = findViewById(R.id.galge);

        dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(false);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        updateScreen();
    }

    @Override
    public void onClick(View view) {
        if (view == guessButton) {

            String letter = letterReciever.getText().toString();
            if (letter.length() != 1) {
                letterReciever.setError("Indtast kun ét bogstav");
                return;
            }
            logik.gætBogstav(letter);
            letterReciever.setText("");
            updateScreen();

        }
    }

    private void updateScreen() {
        wordToGuess.setText("Ordet du skal gætte er: " + logik.getSynligtOrd());
        wrongLetterText.setText("Du har svaret forkert " + logik.getAntalForkerteBogstaver() + " gange og du har brugt bogstaverne: " + logik.getBrugteBogstaver());

        if (logik.erSpilletVundet()) {
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            dialog.setView(input);
            dialog.setTitle("Du vandt!");
            dialog.setMessage("Du gættede ordet: " + logik.getOrdet() + " efter " + logik.getBrugteBogstaver().size() + " forsøg!\n\nIndtast navn: ");
            dialog.setPositiveButton("OK", new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    name = input.getText().toString();
                    logik.nulstil();
                    updateScreen();
                    iv.setImageResource(R.drawable.galge);
                    Intent winnerIntent = new Intent(Game_act.this, Winner_act.class);
                    winnerIntent.putExtra("name", name);
                    startActivity(winnerIntent);
                }
            });
            prefs.edit().putInt("wins", prefs.getInt("wins", 0) + 1).apply();
            dialog.show();
        }

        switch (logik.getAntalForkerteBogstaver()) {
            case 0:
                break;
            case 1:
                iv.setImageResource(R.drawable.forkert1);
                break;
            case 2:
                iv.setImageResource(R.drawable.forkert2);
                break;
            case 3:
                iv.setImageResource(R.drawable.forkert3);
                break;
            case 4:
                iv.setImageResource(R.drawable.forkert4);
                break;
            case 5:
                iv.setImageResource(R.drawable.forkert5);
                break;
            case 6:
                iv.setImageResource(R.drawable.forkert6);
                break;
            case 7:
                dialog.setTitle("Du Tabte!");
                dialog.setMessage("Ordet var: " + logik.getOrdet());
                dialog.setPositiveButton("OK", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        logik.nulstil();
                        updateScreen();
                        iv.setImageResource(R.drawable.galge);
                        Intent loserIntent = new Intent(Game_act.this, Loser_act.class);
                        startActivity(loserIntent);
                    }
                });
                dialog.show();

                break;
        }
    }
}
