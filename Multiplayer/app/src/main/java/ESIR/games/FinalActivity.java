package ESIR.games;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FinalActivity extends AppCompatActivity {

    TextView score1, score2, winner, player1, player2;
    Button restart;
    FirebaseDatabase database;
    DatabaseReference messageRef;
    DatabaseReference oppRef;
    DatabaseReference oppname;
    DatabaseReference roomref;
    String playerName = "";
    String roomName = "";
    String role = "";
    String comp = "";
    String scoreopp = "";
    String nameopp = "";
    String score = "";
    int sopp ;
    int sc;

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
        restart = findViewById(R.id.restart);
        //restart.setVisibility(restart.INVISIBLE);


        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        roomName = preferences.getString("roomName", "");

        playerName = preferences.getString("playerName", "");

        preferences.edit().remove("roomName").apply();
        score = Integer.toString(getIntent().getIntExtra("score1", 0));




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
        oppname =  database.getReference("rooms/" + roomName + "/" + comp);
        roomref =  database.getReference("rooms/" + roomName);

        int score1Value = getIntent().getIntExtra("score1", 0);
        messageRef.setValue(Integer.toString(score1Value));

        ValueEventListener a =new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("TEST");
                if(snapshot.exists()) {
                    System.out.println("from snapshot"+snapshot.getValue());
                    scoreopp = snapshot.getValue().toString();
                    System.out.println("inside if" +scoreopp);
                    score2.setText(scoreopp);
                    sc = Integer.parseInt(score);
                    sopp=Integer.parseInt(scoreopp);
                    if(sc<sopp){
                        winner.setText("YOU LOST");

                    }else if (sc ==sopp){
                        winner.setText("ITS A TIE!");

                    }else{
                        winner.setText("YOU WON!");
                        MediaPlayer ring= MediaPlayer.create(FinalActivity.this,R.raw.musiquevictor);
                        ring.start();
                    }
                    restart.setVisibility(restart.VISIBLE);


                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }};

        oppRef.addValueEventListener(a);

        ValueEventListener b = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("TEST");
                if(snapshot.exists()) {
                    System.out.println("from snapshot"+snapshot.getValue());
                    nameopp = snapshot.getValue().toString();
                    player2.setText(nameopp);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }};

        oppname.addValueEventListener(b);





        restart.setOnClickListener(v -> {
            oppRef.removeEventListener(a);
            oppname.removeEventListener(b);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("pastRoomName", roomName);
            editor.commit();


            Intent intent = new Intent(this, InitActivity.class);
            startActivity(intent);
        });

        messageRef.setValue(Integer.toString(getIntent().getIntExtra("score1", 0)));

        score2.setText("Waiting for Oponent");
        player1.setText(playerName);
        score1.setText(score);
    }
}
