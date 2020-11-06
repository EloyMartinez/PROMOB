package com.example.pingponggame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.KeyEvent;
import android.view.View;

public class Ball extends View {

    private int xMin;
    private int xMax;
    private int yMin;
    private int yMax;
    private float ballRadius = 20; // Rayon de la balle
    private float ballX = ballRadius + 10; // Centre de la balle (x,y)
    private float ballY = ballRadius + 10;
    private float ballSpeedX = 20; // Vitesse de la balle (x,y)
    private float ballSpeedY = 10;
    private RectF ballBounds; // Nécessaire pour Canvas.drawOval
    private Paint paint; // Couleur de la balle

    public Ball(Context context) {
        super(context);
        ballBounds = new RectF();
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas){
        // Dessine la balle
        ballBounds.set(ballX-ballRadius, ballY-ballRadius, ballX+ballRadius, ballY+ballRadius);
        paint.setColor(Color.WHITE);
        canvas.drawOval(ballBounds, paint);

        update(); // Met à jour la position de la balle

        // Délai
        try {
            Thread.sleep(30);
        } catch (InterruptedException ignored){}

        invalidate(); // Force à redessiner
    }

    // Détecte les collisions et met à jour la position de la balle
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
    }

    public int getXMin(){
        return xMin;
    }

    public void setXMin(int xMin){
        this.xMin = xMin;
    }

    public int getYMin(){
        return yMin;
    }

    public void setYMin(int yMin){
        this.yMin = yMin;
    }

    public int getXMax(){
        return xMax;
    }

    public void setXMax(int xMax){
        this.xMax = xMax;
    }

    public int getYMax(){
        return xMax;
    }

    public void setYMax(int yMax){
        this.yMax = yMax;
    }

}
