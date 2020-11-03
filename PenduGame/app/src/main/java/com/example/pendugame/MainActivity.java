package com.example.pendugame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    private List<String> wordList = new ArrayList<>();

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
        word = generateWord();
        win = false;
        error = 0;
        found = 0;
        letters_taping.setText("");
        listOfLetters.clear();
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
            createDialog(true);
            win = true;
        }

        if(!word.contains(letterFromInput)){ // Si le lettre n'est pas dans le mot
            error++;
        }
        setImage(error);
        if(error == 6){ // Si la partie est perdue
            createDialog(false);
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

    public void setImage(int error){
        switch(error){
            case 1:
                image.setBackgroundResource(R.drawable.second);
                break;
            case 2:
                image.setBackgroundResource(R.drawable.third);
                break;
            case 3:
                image.setBackgroundResource(R.drawable.fourth);
                break;
            case 4:
                image.setBackgroundResource(R.drawable.fifth);
                break;
            case 5:
                image.setBackgroundResource(R.drawable.sixth);
                break;
            case 6:
                image.setBackgroundResource(R.drawable.seventh);
                break;
        }
    }

    public void createDialog(boolean win){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Vous avez gagné !");

        if(!win){
            builder.setTitle("Vous avez perdu...");
            builder.setMessage("Le mot à trouver était : " + word);
        }
        builder.setPositiveButton(getResources().getString(R.string.replay), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initGame();
            }
        });

        builder.create().show();
    }

    public List<String> getListOfWords(){
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(getAssets().open("pendu_liste.txt")));
            String line;
            while((line = buffer.readLine()) != null){
                wordList.add(line);
            }
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordList;
    }

    public String generateWord(){
        wordList = getListOfWords();
        int random = (int) (Math.floor(Math.random() * wordList.size()));
        return wordList.get(random).trim();
    }

}