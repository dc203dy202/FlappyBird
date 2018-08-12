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
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class GameView extends View {

    //this is our custom View class
    Handler handler;    //handler is required to schedule a runnable after some delay
    Runnable runnable;
    final int UPDATE_MILLS = 30;
    Bitmap background;
    Bitmap toptube, bottomtube;
    Display display;
    Point point;
    int dWidth, dHeight; //Device width and height respectively
    Rect rect;


    //lets create a bitmap array for the bird
    Bitmap birds[];
    //We need an integer variable to keep track of bird image frame
    int birdFrame = 0;
    int velocity = 0, gravity = 3; //Lets play around with these values
    // We need to keep track of bird position
    int birdX, birdY;
    boolean gameState = false;
    int gap = 400; //Gap between top tube and bottom tube
    int minTubeOffset, maxTubeOffset;
    int numberOfTubes = 4;
    int distanceBetweenTubes;
    int tubeX[] = new int[numberOfTubes];
    int topTubeY[] = new int[numberOfTubes];
    Random random;
    int tubeVelocity = 8;

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
        toptube = BitmapFactory.decodeResource(getResources(), R.drawable.toptube);
        bottomtube = BitmapFactory.decodeResource(getResources(), R.drawable.bottomtube);
        display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getSize(point);
        dWidth = point.x;
        dHeight = point.y;
        rect = new Rect(0, 0, dWidth, dHeight);
        birds = new Bitmap[2];
        birds[0] = BitmapFactory.decodeResource(getResources(), R.drawable.bird);
        birds[1] = BitmapFactory.decodeResource(getResources(), R.drawable.bird2);
        birdX = dWidth/2 - birds[0].getWidth()/2; //Initially bird will be on center
        birdY = dHeight/2 - birds[0].getHeight()/2;
        distanceBetweenTubes = dWidth * 3 / 4; //Our assumption
        minTubeOffset = gap / 2;
        maxTubeOffset = dHeight - minTubeOffset - gap;
        random = new Random();

        for(int i = 0; i < numberOfTubes; i++){
            tubeX[i] = dWidth + i * distanceBetweenTubes;
            topTubeY[i] = minTubeOffset + random.nextInt(maxTubeOffset - minTubeOffset + 1); //topTubeY will vary between minTubeOffset and maxTubeOffset
        }
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
        if(gameState){
            //The bird should be on the screen
            if(birdY < dHeight - birds[0].getHeight() || velocity < 0){ //화면 밖으로 나가지 않게
                velocity += gravity; //As the bird falls, it gets faster and faster as the velocity value increments by gravity each time
                birdY += velocity;
            }
            for(int i = 0; i < numberOfTubes; i++){
                tubeX[i] -= tubeVelocity;
                if(tubeX[i] < -toptube.getWidth()){
                    tubeX[i] += numberOfTubes * distanceBetweenTubes;
                    topTubeY[i] = minTubeOffset + random.nextInt(maxTubeOffset - minTubeOffset + 1); //topTubeY will vary between minTubeOffset and maxTubeOffset
                }
                canvas.drawBitmap(toptube, tubeX[i], topTubeY[i] - toptube.getHeight(), null);
                canvas.drawBitmap(bottomtube, tubeX[i], topTubeY[i] + gap, null);
            }
        }


        //We want the bird to be displayed at the centre of the screen
        //Both birds[0] and birds[1] have same dimension
        canvas.drawBitmap(birds[birdFrame], birdX, birdY, null);
        postDelayed(runnable, UPDATE_MILLS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){ //That is tap is detected on screen
            //Here we want the bird to move upwards by some unit
            velocity = -30; //Lets say, 30 units on upward dtirection
            gameState = true;
//            topTubeY = minTubeOffset + random.nextInt(maxTubeOffset - minTubeOffset + 1);
        }

        return true; //By returning true indicates that we've done with touch event and no further action is required by Android
    }
}




