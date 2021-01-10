package ESIR.games.BallGame;

import androidx.appcompat.app.AppCompatActivity;

import ESIR.games.QuizzGame.QuizzMainActivity;
import ESIR.games.R;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class BallMainActivity extends AppCompatActivity {

    private float x1, x2, y1, y2;
    static final int MIN_DISTANCE = 150;

    //Elements
    private TextView scoreLabel, startLabel;
    private ImageView box, orange, pink, black, red, black1;

    //size
    private int frameHeight;
    private int frameWidth;
    private int boxSize;
    private int blackSize;
    private int black1Size;
    private int screenHeight;
    private int screenWidth;

    //Position
    private float boxY;
    private float boxX;
    private float orangeX, orangeY;
    private float pinkX, pinkY;
    private float blackX, blackY;
    private float black1X, black1Y;
    private float redX, redY;

    //score
    private int score;
    //Timer
    private Timer timer = new Timer();
    private Handler handler = new Handler();

    //Status
    private boolean action_flg = false;
    private boolean start_flg = false;
    private String swipe = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball_main);


        scoreLabel = findViewById(R.id.scoreLabel);
        startLabel = findViewById(R.id.startLabel);
        box = findViewById(R.id.box);
        orange = findViewById(R.id.orange);
        pink = findViewById(R.id.pink);
        black = findViewById(R.id.black);
        red = findViewById(R.id.red);
        black1 = findViewById(R.id.black1);

        //screen size
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;


        //initial position
        orange.setX(-80.0f);
        orange.setY(-80.0f);
        pink.setX(-80.0f);
        pink.setY(-80.0f);
        black.setX(-80.0f);
        black.setY(-80.0f);
        red.setX(-80.0f);
        red.setY(-80.0f);
        black1.setY(1640f);
        black1.setX(1640f);
        blackY = black1.getY();

        //Temporary
        boxY = 0;

        scoreLabel.setText("Score: " + score);
    }

    public void changePos() {

        hitCheck();

        //Orange
        orangeX -= 12;
        if (orangeX < 0) {
            orangeX = screenWidth + 20;
            orangeY = (float) Math.floor(Math.random() * frameHeight - orange.getHeight());
        }
        orange.setX(orangeX);
        orange.setY(orangeY);

        //black
        blackX -= 10;
        if (blackX < 0) {
            blackX = screenWidth + 10;
            blackY = (float) Math.floor(Math.random() * frameHeight - black.getHeight());
        }
        black.setY(blackY);
        black.setX(blackX);

        //black1
        black1Y += 10;
        float tmp = black1.getY();
        black1Size = black.getHeight();
        if (black1.getY() > frameHeight - black1Size) {

            black1X = (float) Math.floor(Math.random() * frameWidth - black1.getWidth());
            black1Y = -10;

        }
        black1.setY(black1Y);
        black1.setX(black1X);

        //pink
        pinkX -= 30;
        if (pinkX < 0) {
            pinkX = screenWidth + 200;
            pinkY = (float) Math.floor(Math.random() * frameHeight - orange.getHeight());
        }
        pink.setX(pinkX);
        pink.setY(pinkY);


        //red
        redX -= 20;
        if (redX < 0) {
            redX = screenWidth + 100;
            redY = (float) Math.floor(Math.random() * frameHeight - orange.getHeight());
        }
        red.setX(redX);
        red.setY(redY);


        if (swipe.equals("d")) {
            boxY -= 20;
        } else if (swipe.equals("u")) {
            boxY += 20;
        } else if (swipe.equals("l")) {
            boxX -= 20;
        } else if (swipe.equals("r")) {
            boxX += 20;
        }

        if (boxY < 0)
            boxY = 0;   //// boxY = 0 est la partie la plus haute de la view ( indice 0 en y dans la matrice de pixels)
        if (boxX < 0)
            boxX = 0;   //// boxx = 0 est la partie la plus a gauche de la view ( indice 0 en x dans la matrice de pixels)
        /// 0 en x et 0 en y correspondent a la coordone (0,0) CAD en haut a gauche dans la matrice de pixels
        if (boxY > frameHeight - boxSize) boxY = frameHeight - boxSize;
        if (boxX > frameWidth - boxSize) boxX = frameWidth - boxSize;

        box.setY(boxY);
        box.setX(boxX);

        scoreLabel.setText("Score: " + score);
    }

    public void hitCheck() {
        //orange
        float orangecenterX = orangeX + orange.getWidth() / 2.0f;
        float orangecenterY = orangeY + orange.getHeight() / 2.0f;

        if (boxX <= orangecenterX && orangecenterX <= boxSize + boxX && boxY <= orangecenterY && orangecenterY <= boxY + boxSize) {
            orangeX = -100.0f;
            score += 10;
        }

        //pink
        float pinkcenterX = pinkX + pink.getWidth() / 2.0f;
        float pinkcenterY = pinkY + pink.getHeight() / 2.0f;

        if (boxX <= pinkcenterX && pinkcenterX <= boxSize + boxX && boxY <= pinkcenterY && pinkcenterY <= boxY + boxSize) {
            pinkX = -100.0f;
            score += 100;
        }

        //black
        float blackcenterX = blackX + black.getWidth() / 2.0f;
        float blackcenterY = blackY + black.getHeight() / 2.0f;

        if (boxX <= blackcenterX && blackcenterX <= boxSize + boxX && boxY <= blackcenterY && blackcenterY <= boxY + boxSize) {
            timer.cancel();
            timer = null;

            int score1 = getIntent().getIntExtra("score1", 0) + score;
            int score2 = getIntent().getIntExtra("score2", 0);
            Intent intent = new Intent(this, BallResultActivity.class);
            intent.putExtra("SCORE", score);
            intent.putExtra("score1", score1);
            intent.putExtra("score2", score2);
            startActivity(intent);
        }

        //black
        float blackcenter1X = black1X + black1.getWidth() / 2.0f;
        float blackcenter1Y = black1Y + black1.getHeight() / 2.0f;

        if (boxX <= blackcenter1X && blackcenter1X <= boxSize + boxX && boxY <= blackcenter1Y && blackcenter1Y <= boxY + boxSize) {
            timer.cancel();
            timer = null;

            Intent intent = new Intent(this, BallResultActivity.class);
            intent.putExtra("SCORE", score);
            startActivity(intent);
        }
        //red
        float redcenterX = redX + red.getWidth() / 2.0f;
        float redcenterY = redY + red.getHeight() / 2.0f;

        if (boxX <= redcenterX && redcenterX <= boxSize + boxX && boxY <= redcenterY && redcenterY <= boxY + boxSize) {
            redX = -100.0f;
            score -= 100;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!start_flg) {
            start_flg = true;

            FrameLayout frameLayout = findViewById(R.id.frame);
            frameHeight = frameLayout.getHeight();
            frameWidth = frameLayout.getWidth();

            boxY = box.getY();
            boxX = box.getX();
            boxSize = box.getHeight();

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
            }, 0, 20);

        } else {
//            if(event.getAction()== MotionEvent.ACTION_DOWN){
//                action_flg=true;
//            }else if(event.getAction() == MotionEvent.ACTION_UP){
//                action_flg=false;
//            }
//        }

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x1 = event.getX();
                    y1 = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    x2 = event.getX();
                    y2 = event.getY();
                    float deltaX = x2 - x1;
                    float deltaY = y2 - y1;
                    if ((deltaX) > MIN_DISTANCE) {
                        //Toast.makeText(this, "left2right swipe", Toast.LENGTH_SHORT).show ();
                        swipe = "r";
                    } else if ((deltaX) < -MIN_DISTANCE) {
                        //Toast.makeText(this, "right2left swipe", Toast.LENGTH_SHORT).show ();
                        swipe = "l";
                    } else if ((deltaY) > MIN_DISTANCE) {
                        //Toast.makeText(this, "up2down swipe", Toast.LENGTH_SHORT).show ();
                        swipe = "u";
                    } else if ((deltaY) < -MIN_DISTANCE) {
                        //Toast.makeText(this, "down2up swipe", Toast.LENGTH_SHORT).show ();
                        swipe = "d";
                    }
                    break;
            }
        }

        return super.onTouchEvent(event);
    }
}