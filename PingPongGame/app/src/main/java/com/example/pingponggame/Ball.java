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

    private int xMin = 0;
    private int xMax;
    private int yMin = 0;
    private int yMax;
    private float ballRadius = 20; // Rayon de la balle
    private float ballX = ballRadius + 10; // Centre de la balle (x,y)
    private float ballY = ballRadius + 10;
    private float ballSpeedX = 20; // Vitesse de la balle (x,y)
    private float ballSpeedY = 0;
    private RectF ballBounds; // Nécessaire pour Canvas.drawOval
    private Paint paint; // Couleur de la balle

    public Ball(Context context) {
        super(context);
        ballBounds = new RectF();
        paint = new Paint();

        // Pour keypad
        this.setFocusable(true);
        this.requestFocus();
    }

    // Inutile dans notre cas
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event){
        switch (keyCode){
            case KeyEvent.KEYCODE_DPAD_RIGHT: // Augmente la vitesse vers la droite
                ballSpeedY = 0;
                if(ballSpeedX <= 0)
                    ballSpeedX = 10;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT: // Augmente la vitesse vers la gauche
                ballSpeedY = 0;
                if(ballSpeedX >= 0)
                    ballSpeedX = -10;
                break;
            case KeyEvent.KEYCODE_DPAD_UP: // Augmente la vitesse vers le haut
                ballSpeedX = 0;
                if(ballSpeedX >= 0)
                    ballSpeedX = -10;
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN: // Augmente la vitesse vers le bas
                ballSpeedX = 0;
                if(ballSpeedX >= 0)
                    ballSpeedX = 10;
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER: // Stop
                ballSpeedY = 0;
                ballSpeedX = 0;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + keyCode);
        }
        return true;
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

    // Appelée quand la vue est crée ou que sa taille change
    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH){
        // Crée le mouvement de rebondissement pour la balle
        xMax = w - 1;
        yMax = h - 1;
    }

}
