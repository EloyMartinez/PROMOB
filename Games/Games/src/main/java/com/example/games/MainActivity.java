package com.example.games;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button ballGameBtn, penduGameBtn, pingPongGameBtn, quizzGameBtn, ticTacToeGameBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ballGameBtn = findViewById(R.id.ballGameBtn);
        penduGameBtn = findViewById(R.id.penduGameBtn);
        pingPongGameBtn = findViewById(R.id.pingPongGameBtn);
        quizzGameBtn = findViewById(R.id.quizzGameBtn);
        ticTacToeGameBtn = findViewById(R.id.ticTacToeGameBtn);

        ballGameBtn.setOnClickListener(this);
        penduGameBtn.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ballGameBtn:
                Intent intent1 = new Intent(this, com.example.ballgame.MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.penduGameBtn:
                Intent intent2 = new Intent(this, com.example.pendugame.MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.pingPongGameBtn:
                Intent intent3 = new Intent(this, com.example.pingponggame.MainActivity.class);
                startActivity(intent3);
                break;
            case R.id.quizzGameBtn:
                Intent intent4 = new Intent(this, com.example.quizgame.MainActivity.class);
                startActivity(intent4);
                break;
            case R.id.ticTacToeGameBtn:
                Intent intent5 = new Intent(this, com.example.tictactoegame.MainActivity.class);
                startActivity(intent5);
                break;
            default:
                break;
        }
    }
}