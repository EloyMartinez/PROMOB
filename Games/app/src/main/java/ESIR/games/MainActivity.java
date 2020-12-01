package ESIR.games;

import androidx.appcompat.app.AppCompatActivity;
import ESIR.games.BallGame.BallMainActivity;
import ESIR.games.PenduGame.PenduMainActivity;
import ESIR.games.PingPongGame.PingPongMainActivity;
import ESIR.games.QuizzGame.QuizzMainActivity;
import ESIR.games.TicTacToeGame.TicTacToeMainActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnBall, btnPendu, btnPingPong, btnQuizz, btnTicTacToe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // A changer

        btnBall = findViewById(R.id.btnBall);
        btnPendu = findViewById(R.id.btnPendu);
        btnPingPong = findViewById(R.id.btnPingPong);
        btnQuizz = findViewById(R.id.btnQuizz);
        btnTicTacToe = findViewById(R.id.btnTicTacToe);

        btnBall.setOnClickListener(this);
        btnPendu.setOnClickListener(this);
        btnPingPong.setOnClickListener(this);
        btnQuizz.setOnClickListener(this);
        btnTicTacToe.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnBall:
                Intent intent1 = new Intent(this, BallMainActivity.class);
                startActivity(intent1);
                break;
            case R.id.btnPendu:
                Intent intent2 = new Intent(this, PenduMainActivity.class);
                startActivity(intent2);
                break;
            case R.id.btnPingPong:
                Intent intent3 = new Intent(this, PingPongMainActivity.class);
                startActivity(intent3);
                break;
            case R.id.btnQuizz:
                Intent intent4 = new Intent(this, QuizzMainActivity.class);
                startActivity(intent4);
                break;
            case R.id.btnTicTacToe:
                Intent intent5 = new Intent(this, TicTacToeMainActivity.class);
                startActivity(intent5);
                break;
            default:
                break;
        }
    }
}