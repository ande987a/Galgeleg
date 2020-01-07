package com.example.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.jinatonic.confetti.CommonConfetti;

public class Winner_act extends AppCompatActivity implements View.OnClickListener{
    private Button goBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getInt("theme", 0) == 0) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.AppThemeDark);
        }
        setContentView(R.layout.activity_winner_act);

        TextView playerName = findViewById(R.id.playerName);

        Handler handler = new Handler();
        Runnable confettiWait;

        goBackButton = findViewById(R.id.winnerGoBackButton);
        goBackButton.setOnClickListener(this);

        final ViewGroup startMenuView = findViewById(R.id.container);
        final MediaPlayer winnerSound = MediaPlayer.create(this, R.raw.winner_sound);

        String name;
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            name = null;
        } else {
            name = extras.getString("name");
        }

        playerName.setText("Tillykke "+name+"!");

        confettiWait = new Runnable() {
            public void run() {
                CommonConfetti.rainingConfetti(startMenuView, new int[] { Color.BLACK, Color.RED, Color.GREEN })
                        .infinite();
            }
        };
        handler.postDelayed(confettiWait, 500);

        winnerSound.start();
    }

    @Override
    public void onClick(View view) {
        if (view == goBackButton) {
            Intent i = new Intent(this, StartMenu_act.class);
            startActivity(i);
        }
    }
}
