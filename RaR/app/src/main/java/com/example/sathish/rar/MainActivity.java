package com.example.sathish.rar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    Timer timer;
    //MediaPlayer mysong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

      //  mysong = MediaPlayer.create(MainActivity.this,R.raw.audio);
       // mysong.start();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
                finish();
            }
        },7000);

        ConstraintLayout constraintLayout;

        constraintLayout = findViewById(R.id.Layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
    }
        /*protected void onPause(){
        super.onPause();
        mysong.release();
        finish();

    }*/

}
