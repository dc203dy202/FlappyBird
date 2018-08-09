package com.example.dayeon.flappybird;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.view.View;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class GameView extends View {

    //this is our custom View class
    Handler handler;    //handler is required to schedule a runnable after some delay
    Runnable runnable;
    final int UPDATE_MILLS = 30;
    Bitmap background;
    Display display;
    Point point;
    int dWidth, dHeight; //Device width and height respectively
    Rect rect;


    //lets create a bitmap array for the bird
    Bitmap birds[];
    //We need an integer variable to keep track of bird image frame
    int birdFrame = 0;
    int velocity = 0, gravity = 3; //Lets play around with these values

    public GameView(Context context){
        super(context);
        handler = new Handler() {
            @Override
            public void publish(LogRecord record) {

            }

            @Override
            public void flush() {

            }

            @Override
            public void close() throws SecurityException {

            }
        };
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate(); //This will call onDraw()
            }
        };
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getSize(point);
        dWidth = point.x;
        dHeight = point.y;
        rect = new Rect(0, 0, dWidth, dHeight);
        birds = new Bitmap[2];
        birds[0] = BitmapFactory.decodeResource(getResources(), R.drawable.bird);
        birds[1] = BitmapFactory.decodeResource(getResources(), R.drawable.bird2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //We'll draw our view inside onDraw()
        //Draw the background on canvas
//        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawBitmap(background, null, rect, null);//fixed
        if(birdFrame == 0){
            birdFrame = 1;
        }
        else{
            birdFrame = 0;
        }
        //We want the bird to be displayed at the centre of the screen
        //Both birds[0] and birds[1] have same dimension
        canvas.drawBitmap(birds[birdFrame], dWidth/2 - birds[0].getWidth()/2, dHeight/2 - birds[0].getHeight()/2, null);
        postDelayed(runnable, UPDATE_MILLS);
    }
}




