package ESIR.games.BallGame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ESIR.games.QuizzGame.QuizzMainActivity;
import ESIR.games.R;

public class BallStarterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball_starter);

        final Button button = findViewById(R.id.button_id);
        button.setOnClickListener(v -> start());
    }

    public void start() {
        Intent intent = new Intent(getApplicationContext(), BallMainActivity.class);
        intent.putExtra("score1", getIntent().getIntExtra("score1", 0));
        intent.putExtra("score2", getIntent().getIntExtra("score2", 0));
        startActivity(intent);
    }
}