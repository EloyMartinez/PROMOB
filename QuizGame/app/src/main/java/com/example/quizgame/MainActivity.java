package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int pressCounter=0;
    private int maxPressCounter=8;
    private String[] keys = {"C","O","O","L","F","F","I","N","M","B"};
    private String textAnswer  = "COOL";
    TextView textScreen, textQuestion, textTitle;
    Button delButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        keys = shuffleArray(keys);

        EditText editText = findViewById(R.id.editText);
        LinearLayout linearLayout = findViewById(R.id.layoutParent);
        LinearLayout linearLayout2=findViewById(R.id.layoutParent2);

        int cont =0;
        for(String key : keys){

            if( linearLayout.getChildCount()<5){
            addView(linearLayout, key, editText);
            cont++;}
            else {
                addView(linearLayout2, key, editText);
            cont++;
            }
        }

        maxPressCounter=8;
    }

    private void addView(LinearLayout viewParent, final String text, final EditText editText) {
        LinearLayout.LayoutParams linearLayoutParams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);

    linearLayoutParams.rightMargin=20;
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

        textQuestion = (TextView) findViewById(R.id.textQuestion);
        textScreen = (TextView) findViewById(R.id.textScreen);
        textTitle = (TextView) findViewById(R.id.textTitle);
        delButton = (Button) findViewById(R.id.button_id);

    textQuestion.setTypeface(typeface);
    textScreen.setTypeface(typeface);
    textTitle .setTypeface(typeface);
    editText.setTypeface(typeface);
    textView.setTypeface(typeface);
    delButton.setTypeface(typeface);

        final Button button = findViewById(R.id.button_id);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editText.setText("");
                empty();
            }
        });

    textView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
             if(pressCounter<maxPressCounter){
                 if(pressCounter==0){
                     editText.setText("");
                 }

                     editText.setText( editText.getText().toString() + text );
                     //textView.startAnimation(bigsmallforth);
                     textView.animate().alpha(0).setDuration(300); ///quita la letra al cliquearla
                    textView.setClickable(false);
                     pressCounter++;

                 ViewGroup vg = (ViewGroup)(v.getParent());  ///remove current view
                 vg.removeView(v);

                 if(pressCounter<maxPressCounter){
                         partialValidate();
                     }
                     if(pressCounter==maxPressCounter){
                          doValidate();}


             }
        }
    });

   viewParent.addView(textView);

    }

    private void empty() {
        EditText editText = findViewById(R.id.editText);
        LinearLayout linearLayout = findViewById(R.id.layoutParent);
        LinearLayout linearLayout2=findViewById(R.id.layoutParent2);
        pressCounter=0;
        keys=shuffleArray(keys);
        linearLayout.removeAllViews();
        linearLayout2.removeAllViews();

        int cont=0;

        for(String key: keys){
            if(cont<5) {
                addView(linearLayout, key, editText);
                cont++;
            }else{
                addView(linearLayout2, key, editText);
                cont++;
            }
        }
    }

    private void partialValidate() {
        EditText editText = findViewById(R.id.editText);
        LinearLayout linearLayout = findViewById(R.id.layoutParent);
        LinearLayout linearLayout2=findViewById(R.id.layoutParent2);
       String b= editText.getText().toString();
       boolean a= editText.getText().toString().equals(textAnswer.toString());

        if(editText.getText().toString().equals(textAnswer.toString())){
            Toast.makeText(MainActivity.this,"Correct",Toast.LENGTH_SHORT).show();
            editText.setText("");
            pressCounter=0;

            keys=shuffleArray(keys);
            linearLayout.removeAllViews();
            linearLayout2.removeAllViews();
            int cont=0;

            for(String key: keys){
                if(cont<5) {
                    addView(linearLayout, key, editText);
                    cont++;
                }else{
                    addView(linearLayout2, key, editText);
                    cont++;
                }
            }
    }}

    private void doValidate() {
        pressCounter=0;
        EditText editText = findViewById(R.id.editText);
    LinearLayout linearLayout = findViewById(R.id.layoutParent);
    LinearLayout linearLayout2=findViewById(R.id.layoutParent2);

    if(editText.getText().toString().equals(textAnswer)){
        Toast.makeText(MainActivity.this,"Correct",Toast.LENGTH_SHORT).show();
        editText.setText("");
    }else{
        Toast.makeText(MainActivity.this,"Wrong",Toast.LENGTH_SHORT).show();

        editText.setText("");
    }
    keys=shuffleArray(keys);
    linearLayout.removeAllViews();
    linearLayout2.removeAllViews();
    int cont=0;

    for(String key: keys){
        if(cont<5) {
            addView(linearLayout, key, editText);
            cont++;
        }else{
            addView(linearLayout2, key, editText);
            cont++;
        }
    }

    }

    private String[] shuffleArray(String[] ar){
        Random rnd = new Random();
        for(int i =ar.length-1; i >0; i--){
            int index=rnd.nextInt(i+1);
            String a = ar[index];
            ar[index] = ar[i];
            ar[i]=a;
        }
        return ar;
    }

    
}