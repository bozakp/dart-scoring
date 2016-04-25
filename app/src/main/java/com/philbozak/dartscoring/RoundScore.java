package com.philbozak.dartscoring;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.ArrayList;
import java.util.List;

public class RoundScore extends BaseObservable {

    public static final int N_SHOTS = 3;

    public List<Integer> shots;

    public RoundScore() {
        newRound();
    }

    public void newRound() {
        shots = new ArrayList<>(3);
    }
}
