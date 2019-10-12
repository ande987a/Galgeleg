package com.example.galgeleg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Galgelogik logik = new Galgelogik();
    private TextView wrongLetterText, wordToGuess;
    private Button guessButton;
    private EditText letterReciever;
    private ImageView iv;
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wrongLetterText = findViewById(R.id.wrongLetter);
        wordToGuess = findViewById(R.id.word);
        //TODO Flyt updateScreen i bunden af onCreate så det her bliver unødvendigt
        wordToGuess.setText("Ordet du skal gætte er: "+logik.getSynligtOrd());

        guessButton = findViewById(R.id.button);
        guessButton.setOnClickListener(this);

        letterReciever = findViewById(R.id.insertLetter);

        iv = findViewById(R.id.galge);

        dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(false);

    }

    @Override
    public void onClick(View view) {
        if(view==guessButton) {

            String letter = letterReciever.getText().toString();
            if (letter.length() != 1){
                letterReciever.setError("Indtast kun ét bogstav");
                return;
            }
            logik.gætBogstav(letter);
            letterReciever.setText("");
            updateScreen();

        }
    }
    private void updateScreen(){
        wordToGuess.setText("Ordet du skal gætte er: "+logik.getSynligtOrd());
        wrongLetterText.setText("Du har svaret forkert " + logik.getAntalForkerteBogstaver() + " gange og du har brugt bogstaverne: "+logik.getBrugteBogstaver());

        if (logik.erSpilletVundet()){
            dialog.setTitle("Du vandt!");
            dialog.setMessage("Du gættede ordet: "+logik.getOrdet());
            dialog.setPositiveButton("Nyt spil", new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    logik.nulstil();
                    updateScreen();
                    iv.setImageResource(R.drawable.galge);
                }
            });
            dialog.show();
        }

        switch (logik.getAntalForkerteBogstaver()){
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
                dialog.setMessage("Ordet var: "+logik.getOrdet());
                dialog.setPositiveButton("Prøv igen", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        logik.nulstil();
                        updateScreen();
                        iv.setImageResource(R.drawable.galge);
                    }
                });
                dialog.show();

                break;

        }

    }
}
