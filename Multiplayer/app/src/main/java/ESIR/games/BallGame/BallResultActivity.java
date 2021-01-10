package ESIR.games.BallGame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ESIR.games.MainActivity;
import ESIR.games.PingPongGame.PingPongMainActivity;
import ESIR.games.R;

public class BallResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball_result);

        TextView scoreLabel = findViewById(R.id.scoreLabel);
        int score = getIntent().getIntExtra("SCORE", 0);
        scoreLabel.setText(score + "");

        final Button button = findViewById(R.id.button_id);
        button.setOnClickListener(v -> endGame());

        LinearLayout layout = findViewById(R.id.layout);

        Button menu = new Button(this);
        menu.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        menu.setText(R.string.menu);
        menu.setTextColor(0xFFFFFFFF);
        menu.setTop(100);
        menu.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

        String mode = getIntent().getStringExtra("mode");
        if(mode.equals("training"))
            layout.addView(menu);
    }

    private void endGame() {
        Intent intent = new Intent(getApplicationContext(), PingPongMainActivity.class);
        intent.putExtra("score1", getIntent().getIntExtra("score1", 0));
        intent.putExtra("score2", getIntent().getIntExtra("score2", 0));
        startActivity(intent);
    }
}