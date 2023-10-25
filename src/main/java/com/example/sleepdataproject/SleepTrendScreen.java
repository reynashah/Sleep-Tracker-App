package com.example.sleepdataproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SleepTrendScreen extends AppCompatActivity {

    Button homeButton, settingButton, logSleepButton;

    double avgSleepTime, optSleepTime, totalHoursSlept;
    TextView avgSleepText, optSleepText, totalText;

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    DecimalFormat df_obj = new DecimalFormat("#.#");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_trend_screen);

        settingButton = findViewById(R.id.settingsButton);
        homeButton = findViewById(R.id.homeButton);
        logSleepButton = findViewById(R.id.logsleepButton);

        avgSleepText = findViewById(R.id.avgSleepText);
        optSleepText = findViewById(R.id.optSleepText);
        totalText = findViewById(R.id.totalText);

        setTotalHours();
        getAvgSleep();
        getOptSleep();

        avgSleepText.setText("Average Sleep Time: " + avgSleepTime + " hours");
        optSleepText.setText("Optimal Sleep Time: " + optSleepTime + " hours");
        totalText.setText("Total Hours Logged: " + totalHoursSlept + " hours");

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SleepTrendScreen.this, MainActivity.class);

                startActivity(intent);
            }
        });
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SleepTrendScreen.this, SettingsScreen.class);

                startActivity(intent);
            }
        });
        logSleepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SleepTrendScreen.this, LogSleepScreen.class);

                Date currentTime = Calendar.getInstance().getTime();

                String d = currentTime.toString();

                startActivity(intent);
            }
        });
    }

    public void getAvgSleep(){
        avgSleepTime = totalHoursSlept / Note.noteArrayList.size();
        avgSleepTime = Double.parseDouble(df_obj.format(avgSleepTime));

    }

    public void getOptSleep(){
        int count = 0;
        int total = 0;
        for (int i = 0; i < Note.noteArrayList.size(); i++) {
            if(Note.noteArrayList.get(i).getRating() > 8){
                count++;
                total += getHoursSlept(i);
            }
        }
        System.out.println(total + " / " + count);
        double t = ((double)total) / ((double)count);
        System.out.println(t);
        optSleepTime = Double.parseDouble(df_obj.format(t));
    }

    public double getHoursSlept(int i){
        int hour = 0;
        int hS = Integer.parseInt(dateFormat.format(Note.noteArrayList.get(i).getStartTime()).substring(11, 13));
        int hE = Integer.parseInt(dateFormat.format(Note.noteArrayList.get(i).getEndTime()).substring(11, 13));

        if(hS > 12){
            hour = (24 - hS) + hE;
        }
        if(hS <= 12){
            hour = hE - hS;
        }

        int minute = 0;
        int mS = Integer.parseInt(dateFormat.format(Note.noteArrayList.get(i).getStartTime()).substring(14, 16));
        int mE = Integer.parseInt(dateFormat.format(Note.noteArrayList.get(i).getEndTime()).substring(14, 16));

        if(mE >= mS){
            minute = mE - mS;
        }

        if(mE < mS){
            hour = hour - 1;
            minute = (mE + 60) - mS;
        }
        double m = ((double) minute) / 60.0 ;
        System.out.println(hour + m);

        return Double.parseDouble(df_obj.format((hour/1.0 + m)));
    }

    public void setTotalHours() {
        for (int i = 0; i < Note.noteArrayList.size(); i++) {
            totalHoursSlept += getHoursSlept(i);
        }

        totalHoursSlept = Double.parseDouble(df_obj.format(totalHoursSlept));
    }
}