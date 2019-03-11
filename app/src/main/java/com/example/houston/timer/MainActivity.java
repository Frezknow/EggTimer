package com.example.houston.timer;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekBar;
    TextView timerTextView;
    Button controlTimerButton;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;
    public void resetTimer(){
        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        countDownTimer.cancel();
        controlTimerButton.setText("Go");
        timerSeekBar.setEnabled(true);
        counterIsActive = false;
    }
    public void updateTimer(int secondsLeft){
        int minutes = (int) secondsLeft /60;
        int seconds =  secondsLeft-minutes*60;
        String secondString = Integer.toString(seconds);
        if(seconds<=9){
            secondString = "0"+secondString;
        }
        timerTextView.setText(Integer.toString(minutes)+":"+secondString);
    }
    public void controlTimer(View view){
        if(counterIsActive==false) {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            controlTimerButton = (Button)findViewById(R.id.controlTimerButton);
            controlTimerButton.setText("Stop");
            Log.i("Button pressed", "pressed.");
           countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    resetTimer();
                    final AssetFileDescriptor afd = getApplicationContext().getResources().openRawResourceFd(R.raw.airhorn);
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mplayer.start();


                }
            }.start();
        }else{
            resetTimer();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerSeekBar = (SeekBar)findViewById(R.id.timerSeekBar);
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
               updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
