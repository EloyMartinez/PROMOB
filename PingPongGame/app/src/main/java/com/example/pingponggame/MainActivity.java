package com.example.pingponggame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("StaticFieldLeak")
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // Elements
    private static TextView scoreLabel;
    private static ImageView box1, box2;
    private static Ball ball;
    private static FrameLayout frame;
    private Button startBtn;

    // Dimensions frame
    private float frameHeight, frameWidth;

    // Dimensions boxs
    private float box1Height, box2Height;

    // Positions
    private float box1Y, box2Y;

    // Scores
    private static int score1, score2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        box1 = (ImageView) findViewById(R.id.box1);
        box2 = (ImageView) findViewById(R.id.box2);
        frame = (FrameLayout) findViewById(R.id.frame);

        ball = new Ball(this);
        frame.addView(ball);
        ball.setVisibility(View.INVISIBLE);

        initGame();

        startBtn.setOnClickListener(this);
    }

    public void initGame(){
        score1 = 0;
        score2 = 0;

        startBtn = (Button) findViewById(R.id.startBtn); // Bouton de lancement de partie
    }

    @Override
    public void onClick(View v) {
        startBtn.setX(-500); // Sors le bouton du cadre pour le rendre invisible et inclicable

        ball.setVisibility(View.VISIBLE);

        box1Height = box1.getHeight();
        box2Height = box2.getHeight();

        frameHeight = frame.getHeight();
        frameWidth = frame.getWidth();

        box1Y = (frameHeight - box1Height)/2;
        box2Y = (frameHeight - box2Height)/2;

        box1.setY(box1Y);
        box2.setY(box2Y);
        //ball.setX(frameWidth/2);
        //ball.setY(frameHeight/2);

        ball.setXMin(Math.round(0)); // Défini la bordure de gauche à ne pas dépasser par la balle
        ball.setYMin(Math.round(0)); // Défini la bordure du haut à ne pas dépasser par la balle
        ball.setXMax(Math.round(frameWidth)); // Défini la bordure de droite à ne pas dépasser par la balle
        ball.setYMax(Math.round(frameHeight)); // Défini la bordure du bas à ne pas dépasser par la balle

        moveBoxes();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void moveBoxes(){
        frame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getX() < frameWidth/2){
                    box1Y = event.getY() - box1Height/2;
                    if(event.getAction() == MotionEvent.ACTION_MOVE) {
                        if(!(box1Y + box1Height > frameHeight) && !(box1Y < 0)){
                            box1.setY(box1Y);
                        }
                    }
                } else {
                    box2Y = event.getY() - box2Height/2;
                    if(event.getAction() == MotionEvent.ACTION_MOVE) {
                        if(!(box2Y + box2Height > frameHeight) && !(box2Y < 0)){
                            box2.setY(box2Y);
                        }
                    }
                }
                return true;
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public static void goal(){
        if(Ball.getBallBounds().right >= frame.getWidth()){
            score1++;
        } else if(Ball.getBallBounds().left <= 0){
            score2++;
        }
        scoreLabel.setText(score1 + "   —   " + score2);
    }

    public void endGame(){
        if(score1 == 11 || score2 == 11){
            frame.removeView(ball);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            if(score1 == 11){
                builder.setTitle(R.string.win1);
            } else {
                builder.setTitle(R.string.win2);
            }

            builder.setPositiveButton(getResources().getString(R.string.replay), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    initGame();
                }
            });

            builder.create().show();
        }
    }

    public static ImageView getBox1(){
        return box1;
    }

    public static ImageView getBox2(){
        return box2;
    }

    public static FrameLayout getFrame() {
        return frame;
    }

}