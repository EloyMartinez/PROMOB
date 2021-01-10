package ESIR.games.Multiplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ESIR.games.R;


public class MultiSetUp extends AppCompatActivity {

    EditText editText;
    Button button;

    String playerName = "";

    FirebaseDatabase database;
    DatabaseReference playerRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_set_up);

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);

        database = FirebaseDatabase.getInstance();



        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        preferences.edit().remove("playerName").apply(); // On enlève le nom de l'utilisateur de la memoire de l'apareil




        playerName = preferences.getString("playerName", "");
        if (!playerName.equals("")) {
            playerRef = database.getReference("players/" + playerName);
            addEventListener();
            playerRef.setValue("");
        }

        button.setOnClickListener(v -> {
            playerName = editText.getText().toString();
            editText.setText("");
            if (!playerName.equals("")) {
                button.setText(R.string.logging_in);
                button.setEnabled(false);
                playerRef = database.getReference("players/" + playerName);
                addEventListener();
                playerRef.setValue("");
            }
        });

    }

    private void addEventListener() {
        playerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!playerName.equals("")) {
                    SharedPreferences preferences = getSharedPreferences("PREFS", 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("playerName", playerName);
                    editor.apply();

                    startActivity(new Intent(getApplicationContext(), MultiRooms.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                button.setText(R.string.log_in);
                button.setEnabled(true);
                Toast.makeText(MultiSetUp.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}