package com.example.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Loser_act extends AppCompatActivity implements View.OnClickListener {

    private Button goBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
