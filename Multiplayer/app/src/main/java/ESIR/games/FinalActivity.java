package ESIR.games;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FinalActivity extends AppCompatActivity {

    TextView score1, score2, winner, player1, player2;
    FirebaseDatabase database;
    DatabaseReference messageRef;
    DatabaseReference oppRef;
    String playerName = "";
    String roomName = "";
    String role = "";
    String comp = "";
    String scoreopp = "";

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_activity);
        score1 = findViewById(R.id.score1);
        score2 = findViewById(R.id.score2);
        winner = findViewById(R.id.winner);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);

        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        roomName = preferences.getString("roomName", "");

        playerName = preferences.getString("playerName", "");

        score1.setText("hello");
        preferences.edit().remove("roomName").apply();

        if (roomName.equals(playerName)) {
            role = "player1";
            comp = "player2";
        } else {
            role = "player2";
            comp = "player1";
        }

        database = FirebaseDatabase.getInstance();

        messageRef = database.getReference("rooms/" + roomName + "/" + role + "score");
        oppRef = database.getReference("rooms/" + roomName + "/" + comp + "score");

        int score1Value = getIntent().getIntExtra("score1", 0);
        messageRef.setValue(Integer.toString(score1Value));

        oppRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("TEST");
                if(snapshot.exists()) {
                    System.out.println("from snapshot"+snapshot.getValue());
                    scoreopp = snapshot.getValue().toString();
                    System.out.println("inside if" +scoreopp);
                    score2.setText(scoreopp);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }});


        messageRef.setValue(Integer.toString(getIntent().getIntExtra("score1", 0)));

        score2.setText("waiting for oponent");
        score1.setText(Integer.toString(getIntent().getIntExtra("score1", 0)));
    }
}
