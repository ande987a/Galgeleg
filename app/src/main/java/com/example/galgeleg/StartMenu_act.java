package com.example.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartMenu_act extends AppCompatActivity implements View.OnClickListener {
    private Button playButton, helpButton, resetButton;
    private Game_act game = new Game_act();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu_akt);

        playButton =findViewById(R.id.startGame);
        helpButton =findViewById(R.id.help);
        resetButton =findViewById(R.id.reset);

        playButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
    }

    public void onClick(View v) {

        if (v==playButton){
            Intent i = new Intent(this, Game_act.class);
            startActivity(i);
        }

        if (v==helpButton){
            Intent i = new Intent(this, Help_act.class);
            startActivity(i);
        }

        if (v==resetButton){
            game.reset();
            Toast t = Toast.makeText(StartMenu_act.this, "Spillet blev nulstillet!", Toast.LENGTH_SHORT);
            t.setGravity(200,0, 20);
            t.show();

        }
    }
}
