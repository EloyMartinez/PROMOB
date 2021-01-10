package ESIR.games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ESIR.games.Multiplayer.MultiSetUp;

public class init extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        TextView text = findViewById(R.id.start);
        Button but = findViewById(R.id.button);
        String delete;

        Button trainingBtn = findViewById(R.id.trainingBtn);
        trainingBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, TrainingActivity.class);
            startActivity(intent);
        });

        SharedPreferences preferences = getSharedPreferences("PREFS", 0);

        delete = preferences.getString("pastRoomName", "");
        System.out.println("HERE WE ARE "+delete);
        if (!delete.equals("")) {
            FirebaseDatabase database;
            database = FirebaseDatabase.getInstance();
            DatabaseReference roomref = database.getReference("rooms/" + delete);
            roomref.removeValue();
           // preferences.edit().remove("delete").apply(); // On enlève le nom de l'utilisateur de la memoire de l'apareil
        }

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/FredokaOneRegular.ttf");
        text.setTypeface(typeface);
        but.setTypeface(typeface);
        but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MultiSetUp.class);
                startActivity(intent);
            }

    });
}}