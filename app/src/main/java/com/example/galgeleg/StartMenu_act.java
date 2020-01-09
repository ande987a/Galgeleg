package com.example.galgeleg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class StartMenu_act extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Button playButton, helpButton, resetButton, drButton, highscoreButton;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getInt("theme", 0) == 0) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.AppThemeDark);
        }
        setContentView(R.layout.activity_start_menu_act);

        playButton = findViewById(R.id.startGame);
        helpButton = findViewById(R.id.help);
        resetButton = findViewById(R.id.reset);
        drButton = findViewById(R.id.DR);
        highscoreButton = findViewById(R.id.highscore);
        Spinner dropdown = findViewById(R.id.themeSelector);

        playButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        drButton.setOnClickListener(this);
        highscoreButton.setOnClickListener(this);

        String[] items = new String[]{"Light theme", "Dark theme"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setSelection(prefs.getInt("theme", 0), false); //set the item to be selected to prevent onItemSelected running when this activity is created
        dropdown.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == playButton) {
            Intent i = new Intent(this, Game_act.class);
            startActivity(i);
        }

        if (v == helpButton) {
            Intent i = new Intent(this, Help_act.class);
            startActivity(i);
        }

        if (v == highscoreButton) {
            Intent i = new Intent(this, Highscore_act.class);
            startActivity(i);
        }

        if (v == resetButton) {
            Game_act.logik.nulstil();
            Toast t = Toast.makeText(StartMenu_act.this, "Spillet blev nulstillet!", Toast.LENGTH_SHORT);
            t.setGravity(200, 0, 20);
            t.show();
        }
        if (v == drButton) {
            playButton.setClickable(false);
            playButton.setText("Henter ord fra DR...");

            class GetWordsFromDR extends AsyncTask {
                @Override
                protected String doInBackground(Object... arg0) {
                    try {
                        Game_act.logik.hentOrdFraDr();
                    } catch (Exception e) {
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0 && prefs.getInt("theme", 0) == 1) {
            //change from dark theme to light theme
            prefs.edit().putInt("theme", 0).apply();
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
        else if (position == 1 && prefs.getInt("theme", 0) == 0) {
            //change from light theme to dark theme
            prefs.edit().putInt("theme", 1).apply();
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}

