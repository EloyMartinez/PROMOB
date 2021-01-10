package ESIR.games.PenduGame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import ESIR.games.InitActivity;
import ESIR.games.R;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PenduMainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout container;
    private TextView letters_taping;
    private ImageView image;
    private EditText et_letter;
    private TextView timerTextView;

    private long startTime = 0;
    private String word;
    private int found;
    private int error;
    private final List<Character> listOfLetters = new ArrayList<>();

    private List<String> wordList = new ArrayList<>();

    private int seconds;
    private int minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendu_main);

        container = findViewById(R.id.word_container);
        Button btn_send = findViewById(R.id.btn_send);
        et_letter = findViewById(R.id.et_letter);
        image = findViewById(R.id.iv_pendu);
        letters_taping = findViewById(R.id.tv_letters_taping);
        timerTextView = findViewById(R.id.timer);

        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);

        RelativeLayout layout = findViewById(R.id.layout);

        Button menu = new Button(this);
        menu.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 100));
        menu.setText(R.string.menu);
        menu.setTextColor(0xFFFFFFFF);
        menu.setTop(100);
        menu.setBackgroundColor(0xFF6200EE);
        menu.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), InitActivity.class);
            startActivity(intent);
        });

        layout.addView(menu);

        initGame();

        btn_send.setOnClickListener(this);
    }

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @SuppressLint("DefaultLocale")
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            seconds = (int) (millis / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;

            timerTextView.setText(String.format("%d:%02d", minutes, seconds));

            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
    }

    public void initGame() {
        word = generateWord();
        error = 0;
        found = 0;
        letters_taping.setText("");
        listOfLetters.clear();
        image.setBackgroundResource(R.drawable.first);
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);

        container.removeAllViews();

        for(int i = 0; i < word.length(); i++){
            @SuppressLint("InflateParams") TextView oneLetter = (TextView) getLayoutInflater().inflate(R.layout.pendu_textview, null);
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
            timerHandler.removeCallbacks(timerRunnable);
            createDialog(true);
        }

        if(!word.contains(letterFromInput)){ // Si le lettre n'est pas dans le mot
            error++;
        }
        setImage(error);
        if(error == 6){ // Si la partie est perdue
            timerHandler.removeCallbacks(timerRunnable);
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
        StringBuilder chain = new StringBuilder();

        for(int i = 0; i < listOfLetters.size(); i++){
            chain.append(listOfLetters.get(i)).append("\n");
        }
        if(!chain.toString().equals("")){
            letters_taping.setText(chain.toString());
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

        String winString = "Vous avez gagné en ";
        if(minutes > 1 && seconds > 1){
            winString += minutes + " minutes et " + seconds + " secondes !";
        } else if(minutes > 1){
            winString += minutes + " minutes et " + seconds + " seconde !";
        } else if(seconds > 1){
            winString += minutes + " minute et " + seconds + " secondes !";
        } else {
            winString += minutes + " minute et " + seconds + " seconde !";
        }
        builder.setTitle(winString);

        if(!win){
            builder.setTitle("Vous avez perdu...");
            builder.setMessage("Le mot à trouver était : " + word);
        }
        builder.setPositiveButton(R.string.replay, (dialog, which) -> initGame());

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