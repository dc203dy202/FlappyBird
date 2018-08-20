package com.example.dayeon.flappybird;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

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
    private long time= 0;
    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()-time>=2000){
            time=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로 버튼을 한번 더 누르면 종료합니다.",Toast.LENGTH_SHORT).show();
        }else if(System.currentTimeMillis()-time<2000){
            finishAffinity();
        }
    }
}
