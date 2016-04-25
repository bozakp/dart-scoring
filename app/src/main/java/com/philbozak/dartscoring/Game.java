package com.philbozak.dartscoring;

import android.util.Log;

public class Game {

    private static final String TAG = Game.class.getSimpleName();

    private PlayerScore player1, player2;
    private RoundScore roundScore;
    private boolean p1turn;

    public Game(PlayerScore player1, PlayerScore player2, RoundScore roundScore) {
        this.player1 = player1;
        this.player2 = player2;
        this.roundScore = roundScore;
        this.p1turn = true;
        updateTurn();
    }

    public void reportShot(int score) {
        Log.d(TAG, "Shot was reported: " + score);
        if (roundScore.shots.size() >= RoundScore.N_SHOTS) { return; }
        roundScore.shots.add(score);
        notifyBinds();
    }

    public void undoShot() {
        if (roundScore.shots.size() > 0) {
            roundScore.shots.remove(roundScore.shots.size() - 1);
        }
        notifyBinds();
    }

    public void roundDone() {
        if (roundScore.shots.size() != RoundScore.N_SHOTS) { return; }
        PlayerScore currentPlayer = p1turn ? player1 : player2;
        currentPlayer.addRound(roundScore.shots);
        roundScore.newRound();
        p1turn = !p1turn;
        updateTurn();
        notifyBinds();
    }

    private void updateTurn() {
        player1.now = p1turn;
        player2.now = !p1turn;
    }

    private void notifyBinds() {
        roundScore.notifyChange();
        player1.notifyChange();
        player2.notifyChange();
    }
}
