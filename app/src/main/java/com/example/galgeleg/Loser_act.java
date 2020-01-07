package com.example.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class Loser_act extends AppCompatActivity implements View.OnClickListener {
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
        setContentView(R.layout.activity_loser_act);

        goBackButton = findViewById(R.id.loserGoBackButton2);
        goBackButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == goBackButton) {
            Intent i = new Intent(this, StartMenu_act.class);
            startActivity(i);
        }
    }
}
