package ESIR.games.PingPongGame;

import androidx.appcompat.app.AppCompatActivity;

import ESIR.games.InitActivity;
import ESIR.games.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("StaticFieldLeak")
public class PingPongMainActivity extends AppCompatActivity implements View.OnClickListener, SensorEventListener {

    // Elements
    private static TextView scoreLabel;
    private static ImageView box1, box2;
    private static Ball ball;
    private static FrameLayout frame;
    private static Button startBtn;

    // Dimensions frame
    private double frameHeight, frameWidth;

    // Dimensions boxs
    private double box1Height, box2Height;

    // Positions
    private double box1Y, box2Y;

    // Pour IA
    private static boolean follow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pingpong_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        scoreLabel = findViewById(R.id.scoreLabel);
        box1 = findViewById(R.id.box1);
        box2 = findViewById(R.id.box2);
        frame = findViewById(R.id.frame);
        startBtn = findViewById(R.id.startBtn);

        ball = new Ball(this, getIntent());

        follow = true;

        startBtn.setOnClickListener(this);

        LinearLayout layout = findViewById(R.id.layout);

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
    }

    @Override
    public void onClick(View v) {
        startBtn.setX(-500); // Sors le bouton du cadre pour le rendre invisible et inclicable

        frame.addView(ball);

        box1Height = box1.getHeight();
        box2Height = box2.getHeight();

        frameHeight = frame.getHeight();
        frameWidth = frame.getWidth();

        box1Y = Math.round((frameHeight - box1Height) / 2);
        box2Y = Math.round((frameHeight - box2Height) / 2);

        box1.setY(Math.round(box1Y));
        box2.setY(Math.round(box2Y));
        ball.setBallX(Math.round(frameWidth / 2));
        ball.setBallY(Math.round(frameHeight / 2));

        ball.setXMin(Math.round(0)); // Défini la bordure de gauche à ne pas dépasser par la balle
        ball.setYMin(Math.round(0)); // Défini la bordure du haut à ne pas dépasser par la balle
        ball.setXMax((int) frameWidth); // Défini la bordure de droite à ne pas dépasser par la balle
        ball.setYMax((int) frameHeight); // Défini la bordure du bas à ne pas dépasser par la balle

        moveBoxes();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double incline = event.values[2];
        incline -= 22 * incline / 100; // Pour ne pas sortir de la frame (inconvénient : ralentit quand on approche des bords)
        box1Y = frameHeight / 2 - (incline * frameHeight) / (9.81 * 2);
        box1.setY(Math.round(box1Y - box1Height / 2));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public static void IA() {
        double random = Math.random() * 100000;
        if (random < 10) {
            follow = false;
        }

        double boxHeight = box2.getHeight();
        float ballPosition = ball.getBallY();

        if ((ballPosition + boxHeight / 2) <= frame.getHeight() &&
                (ballPosition - boxHeight / 2) >= 0 && follow) {
            box2.setY(Math.round(ballPosition - boxHeight / 2));
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void moveBoxes() {
        frame.setOnTouchListener((v, event) -> {
            if (event.getX() < frameWidth / 2) {
                box1Y = event.getY() - box1Height / 2;
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (box1Y + box1Height <= frameHeight && box1Y >= 0) {
                        box1.setY(Math.round(box1Y));
                    }
                }
            } else {
                box2Y = event.getY() - box2Height / 2;
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (box2Y + box2Height <= frameHeight && box2Y >= 0) {
                        box2.setY(Math.round(box2Y));
                    }
                }
            }
            return true;
        });
    }

    public static ImageView getBox1() {
        return box1;
    }

    public static ImageView getBox2() {
        return box2;
    }

    public static FrameLayout getFrame() {
        return frame;
    }

    public static void setScoreLabel(String text) {
        scoreLabel.setText(text);
    }

    public static Button getStartBtn() {
        return startBtn;
    }

}