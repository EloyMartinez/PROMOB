package com.example.pendugame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout container;
    private Button btn_send;
    private TextView lettres_tapees;
    private ImageView image;
    private EditText letter;

    private String word;
    private int found;
    private int error;
    private List<Character> listOfLetters = new ArrayList<Character>();
    private boolean win;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = (LinearLayout) findViewById(R.id.word_container);
        btn_send = (Button) findViewById(R.id.btn_send);
        letter = (EditText) findViewById(R.id.et_letter);
        image = (ImageView) findViewById(R.id.iv_pendu);
        lettres_tapees = (TextView) findViewById(R.id.tv_lettres_tapees);
    }

}