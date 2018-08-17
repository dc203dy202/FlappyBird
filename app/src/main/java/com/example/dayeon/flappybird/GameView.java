package com.example.dayeon.flappybird;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import android.os.Handler;
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
    int birdWidth, birdHeight;
    //We need an integer variable to keep track of bird image frame
    int birdFrame = 0;
    int velocity = 0, gravity = 3; //Lets play around with these values
    // We need to keep track of bird position
    int birdX, birdY;
    boolean gameState = false;
    int gap = 250; //Gap between top tube and bottom tube
    int minTubeOffset, maxTubeOffset;
    int numberOfTubes = 4;
    int distanceBetweenTubes;
    int tubeX[] = new int[numberOfTubes];
    int topTubeY[] = new int[numberOfTubes];
    Random random;
    int tubeVelocity = 8;

    boolean alive = true;
    boolean firstSet = true;
    int scoreCheck = 0;
    int score = 0;




    public GameView(Context context){
        super(context);
        handler = new Handler();
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
        birdWidth = birds[0].getWidth();
        birdHeight = birds[0].getHeight();
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

    @SuppressLint("DrawAllocation")
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

        Paint texts = new Paint();
        texts.setTextSize(50);

        if(gameState){
            //The bird should be on the screen
            if(birdY < dHeight - birdHeight || velocity < 0){ //화면 밖으로 나가지 않게
                velocity += gravity; //As the bird falls, it gets faster and faster as the velocity value increments by gravity each time
                birdY += velocity;
            }

            for(int i = 0; i < numberOfTubes; i++) {
//                if(((        birdX + birds[i].getWidth() > tubeX[i])
//                        && ((birdY < topTubeY[i
// ]) && birdX < tubeX[i] + toptube.getWidth())||
//                           ((birdY > topTubeY[i] + gap)))){
//                    Toast.makeText(getContext(), "bb", Toast.LENGTH_SHORT).show();
//                }
//                alive = !(((birdY < topTubeY[i]) || (birdY > topTubeY[i] + gap)) &&
//                            ((birdX + birds[i].getWidth() > tubeX[i]) && birdX < tubeX[i] + toptube.getWidth()));

//                alive = !((birdX + birds[0].getWidth() > tubeX[i]) && birdX < tubeX[i] + toptube.getWidth() &&
//                          ((birdY < topTubeY[i]) || (birdY + birds[i].getHeight() > topTubeY[i] + gap)));
//
//                if(alive == false)
//                {
//                    Toast.makeText(getContext(), "dd", Toast.LENGTH_SHORT).show();
//                }
                if(tubeX[0] < birdX + birdWidth && firstSet){
                    score ++;
                    firstSet = false;
                    scoreCheck = 0;
                }
                tubeX[i] -= tubeVelocity;
                if(tubeX[i] < -(toptube.getWidth() + toptube.getWidth() / 2)){
                    tubeX[i] += numberOfTubes * distanceBetweenTubes;
                    topTubeY[i] = minTubeOffset + random.nextInt(maxTubeOffset - minTubeOffset + 1); //topTubeY will vary between minTubeOffset and maxTubeOffset
                    score++;
                    scoreCheck = i == 3 ? 0 : i + 1;
                }
                canvas.drawBitmap(toptube, tubeX[i], topTubeY[i] - toptube.getHeight(), null);
                canvas.drawBitmap(bottomtube, tubeX[i], topTubeY[i] + gap, null);
            }
            canvas.drawText(String.valueOf(tubeX[scoreCheck]) + ", " + String.valueOf(scoreCheck) + ", " + String.valueOf(birdX + birdWidth), 100, 100, texts);
            if((((birdX + birdWidth) > tubeX[scoreCheck])) && ((birdX < (tubeX[scoreCheck] + toptube.getWidth()))) &&
                    ((birdY < topTubeY[scoreCheck]) || ((birdY + birdHeight) > (topTubeY[scoreCheck] + gap)))){
                Toast.makeText(getContext(), String.valueOf(scoreCheck), Toast.LENGTH_SHORT).show();
            }
        }


        //We want the bird to be displayed at the centre of the screen
        //Both birds[0] and birds[1] have same dimension
        canvas.drawBitmap(birds[birdFrame], birdX, birdY, null);
        Paint paint = new Paint();
        paint.setTextSize(50);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(String.valueOf(score), dWidth / 2, dHeight / 2 - 100, paint);
        handler.postDelayed(runnable, UPDATE_MILLS);
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




