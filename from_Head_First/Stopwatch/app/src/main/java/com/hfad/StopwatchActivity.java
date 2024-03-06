package com.hfad;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class StopwatchActivity extends Activity {
    private int seconds = 0;
    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
    }

    public void onClickStart(View view){
        running = true;
    }
    public void onClickStop(View view){
        running = false;
    }
    public void onClickReset(View view){
        running = false;
        seconds = 0;
    }

    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.time_view);

        int hours = seconds/3600;
        int minutes = (seconds%3600)/60;
        int secs = seconds%60;

        String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
        timeView.setText(time);
        if (running) {
            seconds++;
        }
    }
}