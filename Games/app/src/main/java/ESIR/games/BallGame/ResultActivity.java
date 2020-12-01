package ESIR.games.BallGame;

import androidx.appcompat.app.AppCompatActivity;
import ESIR.games.R;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    Button delButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball_result);

        TextView scoreLabel = findViewById(R.id.scoreLabel);
        TextView highScoreLabel = findViewById(R.id.highScoreLabel);
        int score = getIntent().getIntExtra("SCORE",0);
        scoreLabel.setText(score + "");

        //high score
        SharedPreferences sharedPreferences = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int highScore = sharedPreferences.getInt("HIGH_SCORE",0);

        final Button button = findViewById(R.id.button_id);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                tryAgain();
            }
        });

        if(score>highScore){
            //update Highscore
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("HIGH_SCORE",score);
            editor.apply();


            highScoreLabel.setText("High score : " + score);
        }else{
            highScoreLabel.setText("High Score : "+ highScore);
        }
    }



    public void tryAgain(){
        startActivity(new Intent(getApplicationContext(), BallMainActivity.class));
    }
}