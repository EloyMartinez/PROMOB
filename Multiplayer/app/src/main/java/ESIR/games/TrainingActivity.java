package ESIR.games;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ESIR.games.BallGame.BallStarterActivity;
import ESIR.games.PenduGame.PenduMainActivity;
import ESIR.games.PingPongGame.PingPongMainActivity;
import ESIR.games.QuizzGame.QuizzStarterActivity;
import ESIR.games.TicTacToeGame.TicTacToeMainActivity;

public class TrainingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        Button btnBall = findViewById(R.id.btnBall);
        Button btnPendu = findViewById(R.id.btnPendu);
        Button btnPingPong = findViewById(R.id.btnPingPong);
        Button btnQuizz = findViewById(R.id.btnQuizz);
        Button btnTicTacToe = findViewById(R.id.btnTicTacToe);

        btnBall.setOnClickListener(v -> {
            Intent intent = new Intent(this, BallStarterActivity.class);
            startActivity(intent);
        });
        btnPendu.setOnClickListener(v -> {
            Intent intent = new Intent(this, PenduMainActivity.class);
            startActivity(intent);
        });
        btnPingPong.setOnClickListener(v -> {
            Intent intent = new Intent(this, PingPongMainActivity.class);
            startActivity(intent);
        });
        btnQuizz.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuizzStarterActivity.class);
            startActivity(intent);
        });
        btnTicTacToe.setOnClickListener(v -> {
            Intent intent = new Intent(this, TicTacToeMainActivity.class);
            startActivity(intent);
        });
    }
}
