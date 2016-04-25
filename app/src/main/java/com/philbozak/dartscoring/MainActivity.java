package com.philbozak.dartscoring;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.philbozak.dartscoring.databinding.PlayerScoreBinding;
import com.philbozak.dartscoring.databinding.RoundScoreBinding;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private Game game;

    @Bind(R.id.p1Score) ViewGroup p1ScoreView;
    @Bind(R.id.p2Score) ViewGroup p2ScoreView;
    @Bind(R.id.round) ViewGroup roundView;
    @Bind(R.id.hitNumberTextView) TextView hitNumberTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        PlayerScoreBinding player1Binding = PlayerScoreBinding.inflate(getLayoutInflater(),
                p1ScoreView, true);
        player1Binding.setPlayer(new PlayerScore("P1"));

        PlayerScoreBinding player2Binding = PlayerScoreBinding.inflate(getLayoutInflater(),
                p2ScoreView, true);
        player2Binding.setPlayer(new PlayerScore("P2"));

        RoundScoreBinding roundBinding = RoundScoreBinding.inflate(getLayoutInflater(),
                roundView, true);
        roundBinding.setRound(new RoundScore());

        game = new Game(player1Binding.getPlayer(), player2Binding.getPlayer(), roundBinding.getRound());
    }

    @OnClick({ R.id.x1Button, R.id.x2Button, R.id.x3Button })
    public void multiplierClicked(Button button) {
        int multiplier;
        switch (button.getId()) {
            case R.id.x1Button:
                multiplier = 1;
                break;
            case R.id.x2Button:
                multiplier = 2;
                break;
            default:
                multiplier = 3;
        }
        registerShot(multiplier);
    }

    @OnClick(R.id.undoBtn)
    public void undoClicked() {
        game.undoShot();
    }

    @OnClick(R.id.doneBtn)
    public void doneClicked() {
        game.roundDone();
    }

    private void registerShot(int multiplier) {
        int hit = Integer.valueOf((String) hitNumberTextView.getText());

        game.reportShot(hit * multiplier);
    }
}
