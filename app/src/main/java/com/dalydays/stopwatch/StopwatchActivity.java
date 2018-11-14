package com.dalydays.stopwatch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StopwatchActivity extends AppCompatActivity {


    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        //проверяем сохрянли мы что либо в бандл если да , то инициализируем переменные, в ином случае пропускаем
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        //запускаем метод который начинает отчет таймера
        runTimer();
    }

    // запускает таймер когда была нажата кнопка приостановить , этот метод отвечает за нажатие клавиши старт
    public void onClickStart(View view) {
        running = true;
    }

    // останавливает таймер когда была нажата кнопка старт , этот метод отвечает за нажатие клавиши стоп
    public void onClickStop(View view) {
        running = false;
    }

    // сбрасывает таймер когда была нажата кнопка старт , этот метод отвечает за нажатие клавиши сбросить
    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    //переопределяем данный метод который отвечает за сохранение значение переменных при завершении работы приложения , в нащем случае нам надо
    //сохранить текущее значение секунды
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    // переопределяем данный метод который вызывается при открытий приложения
    @Override
    public void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }
    // переопределяем данный метод который вызывается когда приложение перестает быть интерактивным
    @Override
    public void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.time_view);
        // handler это обьект которые будет отвечать за нашу перерисовку экрана в нашем случае инкрементации секунд каждый промежуток
        // времени в нашем случае 1 секунда , время мы указали ниже
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                //тут простая математика
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                // задаем нашему тексту новое значение
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                // вот вызов которй обновляет экран каждые 1000 миллисекунд
                handler.postDelayed(this, 1000);
            }
        });
    }
}
