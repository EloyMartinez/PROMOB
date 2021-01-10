package ESIR.games.TicTacToeGame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import ESIR.games.InitActivity;
import ESIR.games.R;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TicTacToeMainActivity extends AppCompatActivity implements View.OnClickListener {

    private final Button[] buttons = new Button[10];
    private boolean player1Turn = true;
    private int roundCount;
    private int player1Points;
    private int player2Points;
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tictactoe_main);
        textViewPlayer1 = findViewById(R.id.textViewP1);
        textViewPlayer2 = findViewById(R.id.textViewP2);

        player1Points = 0;
        player2Points = 0;
        updatePointsText();

        for (int i = 1; i < 10; i++) {
            String buttonID = "button" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = findViewById(resID);
            buttons[i].setOnClickListener(this);
        }

        Button buttonReset = findViewById(R.id.resetButton);
        buttonReset.setOnClickListener(v -> resetGame());

        Button menu = findViewById(R.id.menu);
        menu.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), InitActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onClick(View v) {
        if (((Button) v).getText().toString().equals("")) {
            if (player1Turn) {
                ((Button) v).setText("X");
            } else {
                ((Button) v).setText("O");
            }
            roundCount++;
            if (checkForWin()) {
                if (player1Turn) {
                    player1Wins();
                } else {
                    player2Wins();
                }
            } else if (roundCount == 9) {
                resetBoard();
            } else {
                player1Turn = !player1Turn;
            }
        }
    }

    private boolean checkForWin() {
        String[] field = new String[10];
        for (int i = 1; i < 10; i++) {
            field[i] = buttons[i].getText().toString();
        }
        for (int i = 1; i < 10; i = i+3) { // Traitement horizontal
            if (field[i].equals(field[i+1]) && field[i].equals(field[i+2]) && !field[i].equals("")) {
                return true;
            }
        }
        for (int i = 1; i < 4; i++) { // Traitement vertical
            if (field[i].equals(field[i+3]) && field[i].equals(field[i+6]) && !field[i].equals("")) {
                return true;
            }
        }
        if (field[1].equals(field[5]) && field[1].equals(field[9]) && !field[1].equals("")) { // Diagonale 1
            return true;
        } else if (field[3].equals(field[5]) && field[3].equals(field[7]) && !field[3].equals("")) { // Diagonale 2
            return true;
        }
        return false;
    }

    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, R.string.player1Win, Toast.LENGTH_LONG).show();
        updatePointsText();
        resetBoard();
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, R.string.player2Win, Toast.LENGTH_LONG).show();
        updatePointsText();
        resetBoard();
    }

    @SuppressLint("SetTextI18n")
    private void updatePointsText() {
        textViewPlayer1.setText(this.getResources().getString(R.string.player1) + " " + player1Points);
        textViewPlayer2.setText(this.getResources().getString(R.string.player2) + " " + player2Points);
    }

    private void resetBoard() {
        for (int i = 1; i < 10; i++) {
            buttons[i].setText("");
        }
        roundCount = 0;
        player1Turn = true;
    }

    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }

}