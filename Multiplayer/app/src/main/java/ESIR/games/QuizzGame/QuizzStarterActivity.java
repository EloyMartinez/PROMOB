package ESIR.games.QuizzGame;

import androidx.appcompat.app.AppCompatActivity;
import ESIR.games.R;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizzStarterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz_starter);


        final Button button = findViewById(R.id.button_id);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                start();
            }
        });

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/FredokaOneRegular.ttf");

        TextView text = findViewById(R.id.text);
        text.setTypeface(typeface);
    }


    public void start(){
        Intent intent = new Intent(getApplicationContext(), QuizzMainActivity.class);
        System.out.println(getIntent().getIntExtra("score1", 0) + " - " + getIntent().getIntExtra("score2", 0));
        intent.putExtra("score1", getIntent().getIntExtra("score1", 0));
        intent.putExtra("score2", getIntent().getIntExtra("score2", 0));
        startActivity(intent);
    }
}