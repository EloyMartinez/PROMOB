package ESIR.games;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ESIR.games.QuizzGame.QuizzStarterActivity;

public class MainActivity extends AppCompatActivity {

    private int score1, score2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button goBtn = findViewById(R.id.goBtn);

        SharedPreferences preferences = getSharedPreferences("PREFS", 0);




        goBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuizzStarterActivity.class);
            intent.putExtra("score1", score1);
            intent.putExtra("score2", score2);
            startActivity(intent);
        });



        score1 = 0;
        score2 = 0;
    }
}