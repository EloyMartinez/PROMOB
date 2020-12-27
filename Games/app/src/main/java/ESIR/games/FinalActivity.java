package ESIR.games;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FinalActivity extends AppCompatActivity {

    TextView score1, score2;

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_activity);

        score1 = findViewById(R.id.score1);
        score2 = findViewById(R.id.score2);

        score1.setText(Integer.toString(getIntent().getIntExtra("score1", 0)));
        score2.setText(Integer.toString(getIntent().getIntExtra("score2", 0)));
    }
}
