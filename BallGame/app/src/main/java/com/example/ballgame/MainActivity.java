package com.example.ballgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private float x1,x2,y1,y2;
    static final int MIN_DISTANCE = 150;

    //Elements
    private TextView scoreLabel, startLabel;
    private ImageView box,orange,pink,black;

    //Position
    private float boxY;
    private float boxX;

    //Timer
    private Timer timer = new Timer();
    private Handler handler = new Handler();

    //Status
    private boolean action_flg =false;
    private boolean start_flg = false;
    private String swipe = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        scoreLabel = findViewById(R.id.scoreLabel);
        startLabel = findViewById(R.id.startLabel);
        box = findViewById(R.id.box);
        orange = findViewById(R.id.orange);
        pink = findViewById(R.id.pink);
        black = findViewById(R.id.black);


        //initial position
        orange.setX(-80.0f);
        orange.setY(-80.0f);
        pink.setX(-80.0f);
        pink.setY(-80.0f);
        black.setX(-80.0f);
        black.setY(-80.0f);

        //Temporary
         boxY=500.0f;


    }

//        public void changePos(){
//
//        if(action_flg){
//            //touching
//            boxY-=20;
//        }else{
//            //releasing
//            boxY+=20;
//        }
//            box.setY(boxY);
//
//        }


    public void changePos(){

        if(swipe=="d"){
            //touching
            boxY-=20;
        }else if(swipe=="u"){
            //releasing
            boxY+=20;
        }else if(swipe=="l"){
            boxX-=20;
        }else if(swipe=="r"){
            boxX+=20;
        }
        box.setY(boxY);
        box.setX(boxX);


    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if(!start_flg){
            start_flg=true;
            startLabel.setVisibility(View.GONE);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            },0,20);

        }else{
//            if(event.getAction()== MotionEvent.ACTION_DOWN){
//                action_flg=true;
//            }else if(event.getAction() == MotionEvent.ACTION_UP){
//                action_flg=false;
//            }
//        }


            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    x1 = event.getX();
                    y1 = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    x2 = event.getX();
                    y2 = event.getY();
                    float deltaX = x2 - x1;
                    float deltaY = y2 - y1;
                    if ((deltaX) > MIN_DISTANCE)
                    {
                        Toast.makeText(this, "left2right swipe", Toast.LENGTH_SHORT).show ();
                        swipe = "r";
                    }
                    else if ((deltaX) < -MIN_DISTANCE)
                    {
                        Toast.makeText(this, "right2left swipe", Toast.LENGTH_SHORT).show ();
                        swipe = "l";
                    }
                    else if((deltaY) > MIN_DISTANCE)
                    {
                        Toast.makeText(this, "up2down swipe", Toast.LENGTH_SHORT).show ();
                        swipe = "u";

                    }
                    else if((deltaY) < -MIN_DISTANCE)
                    {
                        Toast.makeText(this, "down2up swipe", Toast.LENGTH_SHORT).show ();
                        swipe = "d";

                    }
                    break;
            }}





        return super.onTouchEvent(event);
    }
}