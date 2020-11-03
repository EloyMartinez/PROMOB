package com.example.pendugame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout container;
    private Button btn_send;
    private TextView letters_taping;
    private ImageView image;
    private EditText et_letter;

    private String word;
    private int found;
    private int error;
    private List<Character> listOfLetters = new ArrayList<>();
    private boolean win;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = (LinearLayout) findViewById(R.id.word_container);
        btn_send = (Button) findViewById(R.id.btn_send);
        et_letter = (EditText) findViewById(R.id.et_letter);
        image = (ImageView) findViewById(R.id.iv_pendu);
        letters_taping = (TextView) findViewById(R.id.tv_letters_taping);

        initGame();

        btn_send.setOnClickListener(this);
    }

    public void initGame() {
        word = "ORDINATEUR";
        win = false;
        error = 0;
        found = 0;
        letters_taping.setText("");
        image.setBackgroundResource(R.drawable.first);

        container.removeAllViews();

        for(int i = 0; i < word.length(); i++){
            TextView oneLetter = (TextView) getLayoutInflater().inflate(R.layout.textview, null);
            container.addView(oneLetter);
        }
    }

    @Override
    public void onClick(View v) {
        String letterFromInput = et_letter.getText().toString().toUpperCase();
        et_letter.setText("");

        if(letterFromInput.length() > 0){
            if(!letterAlreadyUsed(letterFromInput.charAt(0), listOfLetters)){
                listOfLetters.add(letterFromInput.charAt(0));
                checkIfLetterIsInWord(letterFromInput, word);
            }
        }

        if(found == word.length()){ // Si la partie est gagnée
            win = true;
            Toast.makeText(getApplicationContext(), "Victoire !", Toast.LENGTH_LONG).show();
        }

        if(!word.contains(letterFromInput)){ // Si le lettre n'est pas dans le mot
            error++;
        }
        if(error == 6){ // Si la partie est perdue
            win = false;
            Toast.makeText(getApplicationContext(), "C'est perdu...", Toast.LENGTH_LONG).show();
        }

        showAllLetters(listOfLetters); // Affiche les lettre entrées
    }

    public boolean letterAlreadyUsed(char a, List<Character> listOfLetters) {
        for(int i = 0; i < listOfLetters.size(); i++){
            if(listOfLetters.get(i) == a){
                Toast.makeText(getApplicationContext(), "Vous avez déjà essayé cette lettre", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    public void checkIfLetterIsInWord(String letter, String word) {
        for(int i = 0; i < word.length(); i++){
            if(letter.equals(String.valueOf(word.charAt(i)))){
                TextView tv = (TextView) container.getChildAt(i);
                tv.setText(String.valueOf(word.charAt(i)));
                found++;
            }
        }
    }

    public void showAllLetters(List<Character> listOfLetters){
        String chain = "";

        for(int i = 0; i < listOfLetters.size(); i++){
            chain += listOfLetters.get(i) + "\n";
        }
        if(!chain.equals("")){
            letters_taping.setText(chain);
        }
    }

}