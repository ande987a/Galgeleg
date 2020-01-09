package com.example.galgeleg;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Highscore_act extends AppCompatActivity {

    private ArrayList<String> names;
    private ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getInt("theme", 0) == 0) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.AppThemeDark);
        }
        setContentView(R.layout.activity_highscore_act);

        RecyclerView recyclerView = new RecyclerView(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        setContentView(recyclerView);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String jsonText = prefs.getString("nameList", null);
        if (jsonText == null) {
            names = new ArrayList<String>();
            names.add("");
        } else {
            names = new ArrayList<String>(Arrays.asList(gson.fromJson(jsonText, String[].class)));

            players = new ArrayList<Player>();
            for (String name : names) {
                players.add(new Player(prefs.getInt(name, 0), name));
            }
            Collections.sort(players, new SortByWins());
        }
    }

    private RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
        @Override
        public int getItemCount() {
            return names.size();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = getLayoutInflater().inflate(R.layout.activity_highscore_act, parent, false);
            return new RecyclerView.ViewHolder(itemView) {
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {
            TextView highscoreName = vh.itemView.findViewById(R.id.playerHighscoreName);
            TextView highscoreWins = vh.itemView.findViewById(R.id.playerHighscoreCount);
            if (!names.get(position).equals("")) {
                highscoreName.setText("Navn: " + players.get(position).getName());
                highscoreWins.setText("Spil vundet: " + players.get(position).getWins());
            } else {
                highscoreName.setText("Ingen spillere på high score listen endnu");
                highscoreWins.setText("");
            }
        }
    };
}
