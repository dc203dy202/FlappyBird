package com.example.dayeon.flappybird;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class StartGame extends Activity {
    public static final int REQUEST_CODE_MENU = 101;
    GameView gameView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(gameView);

    }

    public void loadScoreView(){
        Intent intent = new Intent(getApplicationContext(), ScoreActivity.class);
        startActivityForResult(intent, REQUEST_CODE_MENU);
    }
}
