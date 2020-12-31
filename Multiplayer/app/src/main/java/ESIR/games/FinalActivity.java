package ESIR.games;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FinalActivity extends AppCompatActivity {

    TextView score1, score2;
    FirebaseDatabase database;
    DatabaseReference messageRef;
    String playerName = "";
    String roomName = "";
    String role = "";
    String message = "";


    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_activity);
        score1 = findViewById(R.id.score1);
        score2 = findViewById(R.id.score2);


        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        roomName = preferences.getString("roomName", "");

        playerName = preferences.getString("playerName", "");

        score1.setText("hello");
        preferences.edit().remove("roomName").apply(); // On enlève le nom de l'utilisateur de la memoire de l'apareil


            if (roomName.equals(playerName)) {
                role = "player1";
            } else {
                role = "player2";
            }


        database = FirebaseDatabase.getInstance();


        messageRef = database.getReference("rooms/" + roomName + "/"+role+"score");
        messageRef.setValue(Integer.toString(getIntent().getIntExtra("score1", 0)));

       score1.setText(Integer.toString(getIntent().getIntExtra("score1", 0)));
       score2.setText(Integer.toString(getIntent().getIntExtra("score2", 0)));
    }
}
