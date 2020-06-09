package com.example.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private TextView textViewTimer;
    Button buttonStart;
    Button buttonPause;
    Button buttonReset;

    private int seconds = 0;
    private boolean isRunning = false;
    private boolean wasRunning = false;  // хранит состояние таймера до метода onStop();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTimer = findViewById(R.id.textViewTimer);
        buttonStart = findViewById(R.id.buttonStart);
        buttonPause = findViewById(R.id.buttonPause);
        buttonReset = findViewById(R.id.buttonReset);
        if (savedInstanceState != null) {  // проверяем, есть ли сохранённая активность
            seconds = savedInstanceState.getInt("seconds");
            isRunning = savedInstanceState.getBoolean("isRunning");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();

    }

  

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = isRunning;
        isRunning = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = wasRunning;
    }

    // сохраняем активность
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);  // ключ - значение
        outState.putBoolean("isRunning", isRunning);
        outState.putBoolean("wasRunning", wasRunning);
    }

    public void OnClickStart(View view) {
        isRunning = true;
    }

    public void OnClickPause(View view) {
        isRunning = false;
    }

    public void OnClickReset(View view) {
        isRunning = false;
        seconds = 0;
    }

    private void runTimer () {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);
                textViewTimer.setText(time);

                if (isRunning) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });

    }
}
