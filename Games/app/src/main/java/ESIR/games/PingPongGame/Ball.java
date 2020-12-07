package ESIR.games.PingPongGame;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import ESIR.games.FinalActivity;
import ESIR.games.R;

@SuppressLint("ViewConstructor")
public class Ball extends View {

    private final ImageView box1 = PingPongMainActivity.getBox1();
    private final ImageView box2 = PingPongMainActivity.getBox2();
    private final FrameLayout frame = PingPongMainActivity.getFrame();

    private final Context context;
    private final Intent intent;

    private static int score1, score2;
    private int xMin, xMax, yMin, yMax;
    private final static float ballRadius = 20; // Rayon de la balle
    private static float ballX = ballRadius + 10; // Centre de la balle (x,y)
    private static float ballY = ballRadius + 10;
    private float ballSpeedX; // Vitesse de la balle (x,y)
    private float ballSpeedY;
    private static RectF ballBounds; // Nécessaire pour Canvas.drawOval
    private final Paint paint; // Couleur de la balle

    public Ball(Context context, Intent intent) {
        super(context);
        this.context = context;
        this.intent = intent;
        ballBounds = new RectF();
        paint = new Paint();

        initGame();
    }

    public void initGame() {
        score1 = 0;
        score2 = 0;

        ballSpeedX = 20 + Math.round(Math.random() * 20);
        ballSpeedY = 10 + Math.round(Math.random() * 10);
        if (Math.random() < 0.5) { // Sens de départ au hasard
            ballSpeedX = -ballSpeedX;
            ballSpeedY = -ballSpeedY;
        }

        Button startBtn = PingPongMainActivity.getStartBtn();
        float frameWidth = frame.getWidth();
        float startBtnWidth = startBtn.getWidth();
        startBtn.setX(frameWidth / 2 - startBtnWidth / 2); // Remet le bouton de démarrage à sa position initiale
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Dessine la balle
        ballBounds.set(ballX - ballRadius, ballY - ballRadius, ballX + ballRadius, ballY + ballRadius);
        paint.setColor(Color.WHITE);
        canvas.drawOval(ballBounds, paint);

        update(); // Met à jour la position de la balle

        // Délai
        try {
            Thread.sleep(20);
        } catch (InterruptedException ignored) {
        }

        invalidate(); // Force à redessiner
    }

    // Détecte les collisions et met à jour la position de la balle
    private void update() {
        // Prend les nouvelles positions (x,y)
        ballX += ballSpeedX;
        ballY += ballSpeedY;

        // Détecte les collisions et réagit
        if (ballX + ballRadius > xMax) {
            ballSpeedX = -ballSpeedX;
            ballX = xMax - ballRadius;
        } else if (ballX - ballRadius < xMin) {
            ballSpeedX = -ballSpeedX;
            ballX = xMin + ballRadius;
        }
        if (ballY + ballRadius > yMax) {
            ballSpeedY = -ballSpeedY;
            ballY = yMax - ballRadius;
        } else if (ballY - ballRadius < yMin) {
            ballSpeedY = -ballSpeedY;
            ballY = yMin + ballRadius;
        }

        setXMin(0);
        setXMax(Math.round(PingPongMainActivity.getFrame().getWidth()));

        touchBoxes();

        goal();

        endGame();
    }

    public void touchBoxes() {
        int[] location1 = new int[2];
        box1.getLocationOnScreen(location1);
        int[] location2 = new int[2];
        box2.getLocationOnScreen(location2);

        float box1Height = Math.round(box2.getHeight());
        float box2Height = Math.round(box2.getHeight());

        if (((box2.getLeft() - ballBounds.right) < 10) && ((location2[1] - box2Height / 2) <= ballBounds.top)
                && ((location2[1] + box2Height / 2) >= ballBounds.bottom)) {
            setXMax(Math.round(box2.getLeft()));
        } else if (((ballBounds.left - box1.getRight()) < 10) && ((location1[1] - box1Height / 2) <= ballBounds.top)
                && ((location1[1] + box1Height / 2) >= ballBounds.bottom)) {
            setXMin(Math.round(box1.getRight()));
        }
    }

    public void goal() {
        if (Ball.getBallBounds().right >= frame.getWidth()) {
            score1++;
        } else if (Ball.getBallBounds().left <= 0) {
            score2++;
        }
        PingPongMainActivity.setScoreLabel(score1 + "   —   " + score2);
    }

    public void endGame() {
        if (score1 == 11 || score2 == 11) {
            frame.removeView(this);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            if (score1 == 11) {
                builder.setTitle(R.string.win1);
            } else {
                builder.setTitle(R.string.win2);
            }

            builder.setPositiveButton(R.string.end, (dialog, which) -> {
                Intent intent1 = new Intent(context.getApplicationContext(), FinalActivity.class);
                System.out.println(intent.getIntExtra("score1", 0) + " - " + intent.getIntExtra("score2", 0));
                intent1.putExtra("score1", intent.getIntExtra("score1", 0));
                intent1.putExtra("score2", intent.getIntExtra("score2", 0));
                context.startActivity(intent1);
            });

            builder.create().show();
        }
    }

    public static RectF getBallBounds() {
        return ballBounds;
    }

    public void setXMin(int xMin) {
        this.xMin = xMin;
    }

    public void setYMin(int yMin) {
        this.yMin = yMin;
    }

    public void setXMax(int xMax) {
        this.xMax = xMax;
    }

    public void setYMax(int yMax) {
        this.yMax = yMax;
    }

    public void setBallX(float xPosition) {
        ballX = xPosition;
    }

    public void setBallY(float yPosition) {
        ballY = yPosition;
    }

}
