package com.philbozak.dartscoring;

import android.databinding.BaseObservable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PlayerScore extends BaseObservable {
    private static final int STARTING_SCORE = 501;

    public final List<List<Integer>> rounds;
    public String name;
    public boolean now;

    public PlayerScore(String name) {
        rounds = new ArrayList<>();
        this.name = name;
    }

    public int getScore() {
        int score = STARTING_SCORE;
        for (List<Integer> round : rounds) {
            int roundSum = 0;
            for (Integer shot : round) {
                if (shot == null) { continue; }
                roundSum += shot;
            }
            if (roundSum >= 0) {
                score -= roundSum;
            }
        }
        return score;
    }

    public void addRound(List<Integer> round) {
        this.rounds.add(round);
    }

}
