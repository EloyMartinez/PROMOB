package ESIR.games.QuizzGame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import ESIR.games.BallGame.BallStarterActivity;
import ESIR.games.FinalActivity;
import ESIR.games.R;

public class QuizzMainActivity extends AppCompatActivity {
    private int pressCounter = 0;
    private final int maxPressCounter = 8;
    private String[] keys;
    private String textAnswer;
    private final String[] dogAR = {"D", "O", "A", "L", "F", "X", "I", "G", "M", "B"};
    private final String dog = "DOG";
    private final String[] horseAR = {"H", "O", "R", "S", "E", "P", "I", "G", "M", "O"};
    private final String horse = "HORSE";
    private final String[] fishAR = {"F", "I", "S", "H", "E", "M", "T", "G", "M", "O"};
    private final String fish = "FISH";
    private final String[] birdAR = {"H", "L", "W", "S", "E", "B", "I", "R", "D", "O"};
    private final String bird = "BIRD";
    private final String[] pigAR = {"A", "J", "I", "P", "E", "B", "I", "G", "D", "O"};
    private final String pig = "PIG";
    private final String[][] keyset = {dogAR, horseAR, fishAR, birdAR, pigAR};
    private final String[] answerset = {dog, horse, fish, bird, pig};
    private int counter = 0;
    private final int maxCounter = 5;
    private long startTime = 0;
    private int seconds;
    private int minutes;

    TextView textScreen, textQuestion, textTitle, timerTextView;
    Button delButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz_main);
        keys = keyset[counter];
        textAnswer = answerset[counter];
        timerTextView = (TextView) findViewById(R.id.timer);

        keys = shuffleArray(keys);

        EditText editText = findViewById(R.id.editText);
        LinearLayout linearLayout = findViewById(R.id.layoutParent);
        LinearLayout linearLayout2 = findViewById(R.id.layoutParent2);

        for (String key : keys) {
            if (linearLayout.getChildCount() < maxCounter) {
                addView(linearLayout, key, editText);
            } else {
                addView(linearLayout2, key, editText);
            }
        }

        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
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

    private void addView(LinearLayout viewParent, final String text, final EditText editText) {
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        linearLayoutParams.rightMargin = 20;
        final TextView textView = new TextView(this);

        textView.setLayoutParams(linearLayoutParams);
        textView.setBackground(this.getResources().getDrawable(R.drawable.buttons));
        textView.setTextColor(this.getResources().getColor(R.color.colorBlue));
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setTextSize(32);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/FredokaOneRegular.ttf");

        textQuestion = findViewById(R.id.textQuestion);
        textScreen = findViewById(R.id.textScreen);
        textTitle = findViewById(R.id.textTitle);
        delButton = findViewById(R.id.button_id);

        textQuestion.setTypeface(typeface);
        textScreen.setTypeface(typeface);
        textTitle.setTypeface(typeface);
        editText.setTypeface(typeface);
        textView.setTypeface(typeface);
        delButton.setTypeface(typeface);

        delButton.setOnClickListener(v -> {
            editText.setText("");
            empty();
        });

        textView.setOnClickListener(v -> {
            if (pressCounter < maxPressCounter) {
                if (pressCounter == 0) {
                    editText.setText("");
                }

                editText.setText(editText.getText().toString() + text);
                //textView.startAnimation(bigsmallforth);
                textView.animate().alpha(0).setDuration(300); ///quita la letra al cliquearla
                textView.setClickable(false);
                pressCounter++;

                ViewGroup vg = (ViewGroup) (v.getParent());  ///remove current view
                vg.removeView(v);

                if (pressCounter < maxPressCounter) {
                    partialValidate();
                }
                if (pressCounter == maxPressCounter) {
                    doValidate();
                }
            }
        });

        viewParent.addView(textView);

    }

    private void empty() {
        EditText editText = findViewById(R.id.editText);
        LinearLayout linearLayout = findViewById(R.id.layoutParent);
        LinearLayout linearLayout2 = findViewById(R.id.layoutParent2);
        pressCounter = 0;
        keys = shuffleArray(keys);
        linearLayout.removeAllViews();
        linearLayout2.removeAllViews();

        int cont = 0;

        for (String key : keys) {
            if (cont < maxCounter) {
                addView(linearLayout, key, editText);
                cont++;
            } else {
                addView(linearLayout2, key, editText);
                cont++;
            }
        }
    }

    private void partialValidate() {
        EditText editText = findViewById(R.id.editText);
        LinearLayout linearLayout = findViewById(R.id.layoutParent);
        LinearLayout linearLayout2 = findViewById(R.id.layoutParent2);
        String b = editText.getText().toString();
        boolean a = editText.getText().toString().equals(textAnswer);

        if (editText.getText().toString().equals(textAnswer)) {
            createToast("That's right!");

            editText.setText("");
            pressCounter = 0;
            if (counter == maxCounter-1) {
                endGame();
                return;
            } else {
                counter++;
                keys = keyset[counter];
                textAnswer = answerset[counter];

                keys = shuffleArray(keys);
                linearLayout.removeAllViews();
                linearLayout2.removeAllViews();
                nextOne();

                int cont = 0;

                for (String key : keys) {
                    if (cont < maxCounter) {
                        addView(linearLayout, key, editText);
                        cont++;
                    } else {
                        addView(linearLayout2, key, editText);
                        cont++;
                    }
                }
            }
        }
    }

    private void doValidate() {
        pressCounter = 0;
        EditText editText = findViewById(R.id.editText);
        LinearLayout linearLayout = findViewById(R.id.layoutParent);
        LinearLayout linearLayout2 = findViewById(R.id.layoutParent2);

        if (editText.getText().toString().equals(textAnswer)) {
            createToast("That's right!");
            editText.setText("");
            if (counter == maxCounter-1) {
                endGame();
                return;
            } else {
                counter++;
                keys = keyset[counter];
                textAnswer = answerset[counter];
            }
        } else {
            createToast("That's not right...");
            editText.setText("");
        }
        keys = shuffleArray(keys);
        linearLayout.removeAllViews();
        linearLayout2.removeAllViews();
        nextOne();
        int cont = 0;

        for (String key : keys) {
            if (cont < 5) {
                addView(linearLayout, key, editText);
                cont++;
            } else {
                addView(linearLayout2, key, editText);
                cont++;
            }
        }
    }

    private void endGame() {
        int score1 = getIntent().getIntExtra("score1", 0);
        int score2 = getIntent().getIntExtra("score2", 0);
        switch (minutes) {
            case 0:
                if(seconds < 30) {
                    score1 += 750;
                } else {
                    score1 += 500;
                }
                break;
            case 1:
                if(seconds < 30) {
                    score1 += 400;
                } else {
                    score1 += 250;
                }
                break;
            case 2:
                if(seconds < 30) {
                    score1 += 100;
                }
                break;
            default:
                break;
        }

        Intent intent = new Intent(getApplicationContext(), FinalActivity.class);
        System.out.println(score1 + " - " + score2);
        intent.putExtra("score1", score1);
        intent.putExtra("score2", score2);
        startActivity(intent);
    }

    private String[] shuffleArray(String[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        return ar;
    }

    private void createToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.quizz_custom_toast, (ViewGroup) findViewById(R.id.custom_toast_container));
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/FredokaOneRegular.ttf");

        TextView text = layout.findViewById(R.id.text);
        text.setTypeface(typeface);
        text.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    private void nextOne() {
        TextView editText = findViewById(R.id.textScreen);
        editText.setText(counter + "/5");
    }


}