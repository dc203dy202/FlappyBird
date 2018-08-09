package com.example.dayeon.flappybird;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    void startGame(View v){
        Intent intent = new Intent(this, StartGame.class);
        startActivity(intent);
        finish();
    }
}
