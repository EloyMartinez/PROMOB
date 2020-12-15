package ESIR.games;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import ESIR.games.QuizzGame.QuizzStarterActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int score1, score2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button goBtn = findViewById(R.id.goBtn);

        goBtn.setOnClickListener(this);

        score1 = 0;
        score2 = 0;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, QuizzStarterActivity.class);
        intent.putExtra("score1", score1);
        intent.putExtra("score2", score2);
        startActivity(intent);
    }
}