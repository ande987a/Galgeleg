package com.example.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Winner_act extends AppCompatActivity implements View.OnClickListener{

    private Button goBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_act);

        TextView playerName = findViewById(R.id.playerName);

        goBackButton = findViewById(R.id.winnerGoBackButton);
        goBackButton.setOnClickListener(this);

        String name;
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            name = null;
        } else {
            name = extras.getString("name");
        }

        playerName.setText("Tillykke "+name+"!");
    }

    @Override
    public void onClick(View view) {
        if (view == goBackButton) {
            Intent i = new Intent(this, StartMenu_act.class);
            startActivity(i);
        }
    }
}
