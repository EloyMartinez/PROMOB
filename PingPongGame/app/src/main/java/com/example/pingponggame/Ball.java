package com.example.pingponggame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

public class Ball extends View {

    ImageView box1 = MainActivity.getBox1();
    ImageView box2 = MainActivity.getBox2();

    private int xMin;
    private int xMax;
    private int yMin;
    private int yMax;
    private final float ballRadius = 20; // Rayon de la balle
    private float ballX = ballRadius + 10; // Centre de la balle (x,y)
    private float ballY = ballRadius + 10;
    private float ballSpeedX = 20; // Vitesse de la balle (x,y)
    private float ballSpeedY = 10;
    private static RectF ballBounds; // Nécessaire pour Canvas.drawOval
    private final Paint paint; // Couleur de la balle

    public Ball(Context context) {
        super(context);
        ballBounds = new RectF();
        paint = new Paint();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onDraw(Canvas canvas){
        // Dessine la balle
        ballBounds.set(ballX-ballRadius, ballY-ballRadius, ballX+ballRadius, ballY+ballRadius);
        paint.setColor(Color.WHITE);
        canvas.drawOval(ballBounds, paint);

        update(); // Met à jour la position de la balle

        // Délai
        try {
            Thread.sleep(20);
        } catch (InterruptedException ignored){}

        invalidate(); // Force à redessiner
    }

    // Détecte les collisions et met à jour la position de la balle
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void update(){
        // Prend les nouvelles positions (x,y)
        ballX += ballSpeedX;
        ballY += ballSpeedY;

        // Détecte les collisions et réagit
        if(ballX + ballRadius > xMax){
            ballSpeedX = -ballSpeedX;
            ballX = xMax - ballRadius;
        } else if(ballX - ballRadius < xMin){
            ballSpeedX = -ballSpeedX;
            ballX = xMin + ballRadius;
        }
        if(ballY + ballRadius > yMax){
            ballSpeedY = -ballSpeedY;
            ballY = yMax - ballRadius;
        } else if(ballY - ballRadius < yMin){
            ballSpeedY = -ballSpeedY;
            ballY = yMin + ballRadius;
        }

        setXMin(0);
        setXMax(Math.round(MainActivity.getFrame().getWidth()));

        touchBoxes();

        MainActivity.goal();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void touchBoxes(){
        int[] location1 = new int[2];
        box1.getLocationInSurface(location1);
        int[] location2 = new int[2];
        box2.getLocationInSurface(location2);

        if(((box2.getLeft() - ballBounds.right) < 10) && ((location2[1] - box2.getHeight()/2) <= ballBounds.top)
                && ((location2[1] + box2.getHeight()/2) >= ballBounds.bottom)){
            setXMax(Math.round(box2.getLeft()));
        } else if(((ballBounds.left - box1.getRight()) < 10) && ((location1[1] - box1.getHeight()/2) <= ballBounds.top)
                && ((location1[1] + box1.getHeight()/2) >= ballBounds.bottom)){
            setXMin(Math.round(box1.getRight()));
        }
    }

    public static RectF getBallBounds() {
        return ballBounds;
    }

    public void setXMin(int xMin){
        this.xMin = xMin;
    }

    public void setYMin(int yMin){
        this.yMin = yMin;
    }

    public void setXMax(int xMax){
        this.xMax = xMax;
    }

    public void setYMax(int yMax){
        this.yMax = yMax;
    }

}
