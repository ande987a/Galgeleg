package com.example.galgeleg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class Highscore_act extends AppCompatActivity {

    private SharedPreferences prefs;
    

    RecyclerView recyclerView;
    String jsonText;
    ArrayList<String> names;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore_act);

        recyclerView = new RecyclerView(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        setContentView(recyclerView);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        jsonText = prefs.getString("nameList",null);
        if (jsonText == null){
            names = new ArrayList<String>();
            names.add("");
        }else{
            names = new ArrayList<String>(Arrays.asList(gson.fromJson(jsonText, String[].class)));
        }
    }

    
    RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
        @Override
        public int getItemCount()  {
            return names.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (names.get(position).startsWith("*")) return 1; //TODO Make it usefull or delete, cleanup needed
            else return 0;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType==0) {
                View itemView = getLayoutInflater().inflate(R.layout.activity_highscore_act, parent, false);
                ListeelemViewholder vh = new ListeelemViewholder(itemView);
                vh.highscoreName = itemView.findViewById(R.id.playerHighscoreName);
                vh.highscoreWins = itemView.findViewById(R.id.playerHighscoreCount);
                return vh;
            } else {
                View itemView = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
                return new RecyclerView.ViewHolder(itemView) {};
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder vh0, int position) {
            if (getItemViewType(position)==0) {
                ListeelemViewholder vh = (ListeelemViewholder) vh0;
                if (!names.get(position).equals("")){
                    vh.highscoreName.setText("Navn: "+names.get(position));
                    vh.highscoreWins.setText("Antal vundne spil: " + prefs.getInt(names.get(position),0));
                }else{
                    vh.highscoreName.setText("Ingen spillere på high score listen endnu");
                    vh.highscoreWins.setText("");
                }
            } else {
                TextView tv = vh0.itemView.findViewById(android.R.id.text1);
                tv.setTextSize(36);
                tv.setText(names.get(position));
            }

        }
    };

    class ListeelemViewholder extends RecyclerView.ViewHolder {
        TextView highscoreName;
        TextView highscoreWins;

        public ListeelemViewholder(View itemView) {
            super(itemView);
        }
    }
}
