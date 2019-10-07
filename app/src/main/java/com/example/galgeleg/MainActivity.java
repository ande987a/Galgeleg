package com.example.galgeleg;

import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wrongLetterText = findViewById(R.id.wrongLetter);
        wordToGuess = findViewById(R.id.word);
        wordToGuess.setText("Ordet du skal gætte er: "+logik.getSynligtOrd());

        guessButton = findViewById(R.id.button);
        guessButton.setOnClickListener(this);

        letterReciever = findViewById(R.id.insertLetter);

        iv = findViewById(R.id.galge);
        //iv.setImageResource(R.drawable.forkert1);

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

                logik.nulstil();
                break;

        }

    }
}
