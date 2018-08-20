package com.example.dayeon.flappybird;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    TextView scoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Intent intent = getIntent();

        scoreView = findViewById(R.id.scoreView);
        scoreView.setText("score : " + intent.getStringExtra("score"));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public void onBtnRestartClicked(View v){
        Intent intent = new Intent(this, StartGame.class);
        startActivity(intent);
        finish();
    }
}
