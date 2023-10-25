package com.example.sleepdataproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button logsleepButton, settingButton, calendarButton, analyzeButton;
    private ProgressBar progressBar, monthProgressbar;
    private TextView progressText, DateText, DayText, monthlyText, dailyText;
    int i = 0, j = 0;
    public static double hoursSlept, monthHoursSlept;

    String displayDate, displayDay;

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    DecimalFormat df_obj = new DecimalFormat("#.#");

    int monthlyGoal = 200, dailyGoal = 8, bedhour, bedmin, wakehour, wakemin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logsleepButton = findViewById(R.id.logsleepButton);
        settingButton = findViewById(R.id.settingsButton);
        calendarButton = findViewById(R.id.calendarButton);
        analyzeButton = findViewById(R.id.analyzeButton);

        DateText = findViewById(R.id.DateText);
        DayText = findViewById(R.id.DayText);

        progressBar = findViewById(R.id.Dailyprogress_bar);
        progressText = findViewById(R.id.progress_text);

        monthProgressbar = findViewById(R.id.Monthlyprogress_bar);
        monthlyText = findViewById(R.id.monthlyText);
        dailyText = findViewById(R.id.dailyText);

        monthlyGoal = SettingsScreen.monthlyGoal;
        dailyGoal = SettingsScreen.dailyGoal;
        bedhour = SettingsScreen.bedhour;
        bedmin = SettingsScreen.bedmin;
        wakehour = SettingsScreen.wakehour;
        wakemin = SettingsScreen.wakemin;

        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);


        CSVReader.loadCSVFile(MainActivity.this, "sample.csv", sqLiteManager.getWritableDatabase());
        //Toast.makeText(this, "Loaded CSV file into Database", Toast.LENGTH_SHORT).show();

        sqLiteManager.populateNoteListArray();

        while(Note.noteArrayList.size() > 31){
            Note.noteArrayList.remove(0);
        }

        setDates();

        i = dailyProgress();
        System.out.println("dp" + dailyProgress());
        j = monthlyProgress();
        System.out.println("mp" + monthlyProgress());
        hoursSlept = Double.parseDouble(df_obj.format(hoursSlept));
        monthHoursSlept = Double.parseDouble(df_obj.format(monthHoursSlept));
        System.out.println(hoursSlept + "hours");
        System.out.println(monthHoursSlept + "hours");
        progressBar.setMax(100);
        monthProgressbar.setMax(100);
        if (i <= 100) {
            progressText.setText(hoursSlept + " hours slept");
            dailyText.setText("Goal: " + dailyGoal + " hours");
            progressBar.setProgress(i);
        }
        else {
            progressBar.setProgress(100);
            progressText.setText(hoursSlept + " hours slept");
            dailyText.setText("Goal: " + dailyGoal + " hours");
        }

        if (j <= 100) {
            monthProgressbar.setProgress(j);
            monthlyText.setText(monthHoursSlept + "/" + monthlyGoal + " Hours Slept");
        }
        else {
            monthProgressbar.setProgress(100);
            monthlyText.setText(monthHoursSlept + "/" + monthlyGoal + " Hours Slept");
        }

        logsleepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LogSleepScreen.class);

                Date currentTime = Calendar.getInstance().getTime();

                String d = currentTime.toString();

                startActivity(intent);
            }
        });
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsScreen.class);

                startActivity(intent);
            }
        });
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CalendarScreen.class);

                startActivity(intent);
            }
        });
        analyzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SleepTrendScreen.class);
                startActivity(intent);
            }
        });
    }

    public int monthlyProgress(){
        monthHoursSlept = 0;
        Calendar c1 = Calendar.getInstance();
        for (int i = 0; i < Note.noteArrayList.size(); i++) {
            if (c1.get(Calendar.MONTH) + 1 == Integer.parseInt(dateFormat.format(Note.noteArrayList.get(i).getStartTime()).substring(0, 2))) {
                monthHoursSlept += getHoursSlept(i);
            }
        }
        double m = (monthHoursSlept/monthlyGoal) * 100;
        return (int) m;
    }

    public int dailyProgress() {
        Calendar c1 = Calendar.getInstance();
        double h = 0;

        for (int i = 0; i < Note.noteArrayList.size(); i++) {
            if (c1.get(Calendar.MONTH) + 1 == Integer.parseInt(dateFormat.format(Note.noteArrayList.get(i).getStartTime()).substring(0, 2)) &&
                    c1.get(Calendar.DATE) == Integer.parseInt(dateFormat.format(Note.noteArrayList.get(i).getStartTime()).substring(3, 5)) &&
                    c1.get(Calendar.YEAR) == Integer.parseInt(dateFormat.format(Note.noteArrayList.get(i).getStartTime()).substring(6, 10))) {
                System.out.println("HELLO");

                hoursSlept = getHoursSlept(i);
                h = hoursSlept/dailyGoal;
            }
        }
        return (int) (h*100);
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

        return (hour/1.0 + m);
    }

    public String getMonthText(int m){
        if(m == 1){
            return "January";
        }
        if(m == 2){
            return "February";
        }
        if(m == 3){
            return "March";
        }
        if(m == 4){
            return "April";
        }
        if(m == 5){
            return "May";
        }
        if(m == 6){
            return "June";
        }
        if(m == 7){
            return "July";
        }
        if(m == 8){
            return "August";
        }
        if(m == 9){
            return "September";
        }
        if(m == 10){
            return "October";
        }
        if(m == 11){
            return "November";
        }
        return "December";
    }

    public void setDates(){
        Date c1 = new Date();
        displayDate = getMonthText(Integer.parseInt(dateFormat.format(c1).substring(0, 2))) + " " + dateFormat.format(c1).substring(3, 5) + ", " + dateFormat.format(c1).substring(6, 10);
        System.out.println(displayDate);
        DateText.setText(displayDate);

        System.out.println(c1);
    }
}