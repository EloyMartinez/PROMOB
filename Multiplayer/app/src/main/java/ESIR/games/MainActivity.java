package ESIR.games;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ESIR.games.QuizzGame.QuizzStarterActivity;

public class MainActivity extends AppCompatActivity {

    private int score1, score2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button goBtn = findViewById(R.id.goBtn);
        Button trainingBtn = findViewById(R.id.trainingBtn);

        goBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuizzStarterActivity.class);
            intent.putExtra("mode", "multi");
            intent.putExtra("score1", score1);
            intent.putExtra("score2", score2);
            startActivity(intent);
        });

        trainingBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, TrainingActivity.class);
            intent.putExtra("mode", "training");
            startActivity(intent);
        });

        score1 = 0;
        score2 = 0;
    }
}