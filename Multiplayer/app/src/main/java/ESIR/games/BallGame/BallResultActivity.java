package ESIR.games.BallGame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ESIR.games.PingPongGame.PingPongMainActivity;
import ESIR.games.QuizzGame.QuizzMainActivity;
import ESIR.games.R;

public class BallResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball_result);

        TextView scoreLabel = findViewById(R.id.scoreLabel);
        int score = getIntent().getIntExtra("SCORE", 0);
        scoreLabel.setText(score + "");

        //high score
        SharedPreferences sharedPreferences = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int highScore = sharedPreferences.getInt("HIGH_SCORE", 0);

        final Button button = findViewById(R.id.button_id);
        button.setOnClickListener(v -> endGame());

    }

    private void endGame() {
        Intent intent = new Intent(getApplicationContext(), PingPongMainActivity.class);
        System.out.println(getIntent().getIntExtra("score1", 0) + " - " + getIntent().getIntExtra("score2", 0));
        intent.putExtra("score1", getIntent().getIntExtra("score1", 0));
        intent.putExtra("score2", getIntent().getIntExtra("score2", 0));
        startActivity(intent);
    }
}