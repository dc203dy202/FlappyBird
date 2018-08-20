package com.example.dayeon.flappybird;

import android.app.Activity;
import android.widget.Toast;

public class ActivityOff extends Activity{


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
