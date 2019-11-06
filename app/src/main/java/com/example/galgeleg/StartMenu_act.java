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
    private MyAsyncTask at = new MyAsyncTask();
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
            new Handler().postDelayed(new Runnable() {
                public void run()
                {
                    playButton.setClickable(true);
                    playButton.setText("Start spillet");
                }
                }, 2000    //Could also just stop runnable when MyAsyncTask finishes by returning something in an onPostExecute() and constantly check for it in the runnable.
            );
            at.execute();
            drButton.setVisibility(View.GONE);
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

    private static class MyAsyncTask extends AsyncTask<Void,Void,String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                Game_act.logik.hentOrdFraDr();
            }catch (Exception e){
                e.printStackTrace();
            }
            return "Done";
        }
    }
}

