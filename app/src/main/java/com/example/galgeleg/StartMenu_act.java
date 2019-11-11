package com.example.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StartMenu_act extends AppCompatActivity implements View.OnClickListener {
    private Button playButton, helpButton, resetButton, drButton;
    private TextView winsView;
    private int totalWinsInt;
    private String totalwins;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu_akt);

        playButton =findViewById(R.id.startGame);
        helpButton =findViewById(R.id.help);
        resetButton =findViewById(R.id.reset);
        drButton =findViewById(R.id.DR);

        winsView = findViewById(R.id.wins);

        playButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        drButton.setOnClickListener(this);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        totalWinsInt =prefs.getInt("wins",0);
        totalwins =Integer.toString(totalWinsInt);
        winsView.setText(totalwins);

    }

    @Override
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
            Game_act.logik.nulstil();
            Toast t = Toast.makeText(StartMenu_act.this, "Spillet blev nulstillet!", Toast.LENGTH_SHORT);
            t.setGravity(200,0, 20);
            t.show();
        }
        if (v==drButton) {
            playButton.setClickable(false);
            playButton.setText("Henter ord fra DR...");

            class GetWordsFromDR extends AsyncTask {
                @Override
                protected String doInBackground(Object... arg0) {
                    try {
                        Game_act.logik.hentOrdFraDr();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return "finished";
                }

                @Override
                protected void onPostExecute(Object result) {
                    drButton.setVisibility(View.GONE);
                    playButton.setClickable(true);
                    playButton.setText("Start spillet");
                }
            }
            new GetWordsFromDR().execute();

        }
    }
    @Override
    public void onResume(){
        super.onResume();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        totalWinsInt =prefs.getInt("wins",0);
        totalwins =Integer.toString(totalWinsInt);
        winsView.setText(totalwins);
    }


}

