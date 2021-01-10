package ESIR.games.Multiplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ESIR.games.MainActivity;
import ESIR.games.R;

public class MultiRooms extends AppCompatActivity {

    ListView listView;
    Button button;

    List<String> roomsList;

    String playerName = "";
    String roomName = "";

    FirebaseDatabase database;
    DatabaseReference roomRef;
    DatabaseReference roomsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_rooms);

        database = FirebaseDatabase.getInstance();

        // Récupère le nom du joueur et assigne le nom du room au nom de joueur
        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        playerName = preferences.getString("playerName", "");
        roomName = playerName;

        listView = findViewById(R.id.listView);
        button = findViewById(R.id.button);
        roomsList = new ArrayList<>();

        button.setOnClickListener(v -> {
            button.setEnabled(false);
            roomName = playerName;
            roomRef = database.getReference("rooms/" + roomName + "/player1"); ///if you create a group you are player1

            addRoomEventListener();
            roomRef.setValue(playerName);
        });
        listView.setOnItemClickListener((parent, view, position, id) -> {
            roomName = roomsList.get(position);
            roomRef = database.getReference("rooms/" + roomName + "/player2");  //if you join a groupe you are player 2
            addRoomEventListener();
            roomRef.setValue(playerName);
        });
        addRoomsEventListener();
    }

    private void addRoomEventListener() {
        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                button.setEnabled(true);
                button.setText(R.string.create_group);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                SharedPreferences preferences = getSharedPreferences("PREFS", 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("roomName", roomName);
                editor.apply();
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MultiRooms.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addRoomsEventListener() {
        roomsRef = database.getReference("rooms");
        roomsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomsList.clear();
                Iterable<DataSnapshot> rooms = snapshot.getChildren();
                for (DataSnapshot snapshots : rooms) {
                    roomsList.add(snapshots.getKey());
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MultiRooms.this, android.R.layout.simple_list_item_1, roomsList);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}