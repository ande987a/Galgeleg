package com.example.galgeleg;

import java.util.Comparator;

public class Player {
    private int wins;
    private String name;

    public Player(int wins, String name) {
        this.wins = wins;
        this.name = name;
    }

    public int getWins() {
        return wins;
    }

    public String getName() {
        return name;
    }
}

class SortByWins implements Comparator<Player> {
    public int compare(Player a, Player b) {
        return b.getWins() - a.getWins();
    }
}