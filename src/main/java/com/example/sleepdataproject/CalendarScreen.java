package com.example.sleepdataproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarScreen extends AppCompatActivity {

    CalendarView calendar;
    TextView date_view, moodText, hoursText, wakeupText, bedtimeText, selectedDateText;
    String selectedDate, bedtime, waketime, hourNum;
    Button homeButton, calendarButton, settingButton, sleepQuality;

    boolean dateCheck = false;

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    DecimalFormat df_obj = new DecimalFormat("#.#");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_screen);

        calendar = (CalendarView) findViewById(R.id.calendar);
        settingButton = findViewById(R.id.settingsButton);
        homeButton = findViewById(R.id.homeButton);
        sleepQuality = findViewById(R.id.sleepQuality);

        moodText = (TextView) findViewById(R.id.moodText);
        hoursText = (TextView) findViewById(R.id.hoursText);
        wakeupText = (TextView) findViewById(R.id.wakeupText);
        bedtimeText = (TextView) findViewById(R.id.bedtimeText);
        selectedDateText = (TextView) findViewById(R.id.selectedDateText);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarScreen.this, MainActivity.class);

                startActivity(intent);
            }
        });
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarScreen.this, SettingsScreen.class);

                startActivity(intent);
            }
        });

        // Add Listener in calendar
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
                public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                String Date = dayOfMonth + "-" + (month + 1) + "-" + year;

                System.out.println(Date);

                Calendar c1 = Calendar.getInstance();

                // set Month
                // MONTH starts with 0 i.e. ( 0 - Jan)
                c1.set(Calendar.MONTH, month);

                // set Date
                c1.set(Calendar.DATE, dayOfMonth);

                // set Year
                c1.set(Calendar.YEAR, year);

                System.out.println(c1.get(Calendar.MONTH));
                System.out.println(c1.get(Calendar.DATE));
                System.out.println(c1.get(Calendar.YEAR));
                System.out.println(Note.noteArrayList.get(0).getStartTime());
                for(int i = 0; i < Note.noteArrayList.size(); i++) {
//                    System.out.println(dateFormat.format(Note.noteArrayList.get(i).getStartTime()));
//                    System.out.println(dateFormat.format(Note.noteArrayList.get(i).getEndTime()));
//                    System.out.println(dateFormat.format(Note.noteArrayList.get(i).getStartTime()).substring(0, 2));
                    //month
//                    System.out.println(dateFormat.format(Note.noteArrayList.get(i).getStartTime()).substring(3, 5));
                    //day
//                    System.out.println(dateFormat.format(Note.noteArrayList.get(i).getStartTime()).substring(6, 10));
                    //year

                    if(c1.get(Calendar.MONTH) + 1 == Integer.parseInt(dateFormat.format(Note.noteArrayList.get(i).getStartTime()).substring(0, 2)) &&
                            c1.get(Calendar.DATE) == Integer.parseInt(dateFormat.format(Note.noteArrayList.get(i).getStartTime()).substring(3, 5)) &&
                                    c1.get(Calendar.YEAR) == Integer.parseInt(dateFormat.format(Note.noteArrayList.get(i).getStartTime()).substring(6, 10))){
                        System.out.println("HELLO");
                        dateCheck = true;
                        System.out.println(" " + dateFormat.format(Note.noteArrayList.get(i).getStartTime()));


                        selectedDate = getMonthText(Integer.parseInt(dateFormat.format(Note.noteArrayList.get(i).getStartTime()).substring(0, 2))) + " " + dateFormat.format(Note.noteArrayList.get(i).getStartTime()).substring(3, 5) + ", " + dateFormat.format(Note.noteArrayList.get(i).getStartTime()).substring(6, 10);
                        System.out.println(selectedDate);
                        selectedDateText.setText(selectedDate);

                        if(Integer.parseInt(dateFormat.format(Note.noteArrayList.get(i).getStartTime()).substring(11, 13)) > 12){
                            int h = Integer.parseInt(dateFormat.format(Note.noteArrayList.get(i).getStartTime()).substring(11, 13)) - 12;
                            int m = Integer.parseInt(dateFormat.format(Note.noteArrayList.get(i).getStartTime()).substring(14, 16));
                            if(m < 10){
                                bedtime = h + ":0" + m + " PM";
                            }
                            else {
                                bedtime = h + ":" + m + " PM";
                            }
                        }
                        else {
                            int h = Integer.parseInt(dateFormat.format(Note.noteArrayList.get(i).getStartTime()).substring(11, 13));
                            int m = Integer.parseInt(dateFormat.format(Note.noteArrayList.get(i).getStartTime()).substring(14, 16));
                            if(m < 10){
                                bedtime = h + ":0" + m + " AM";
                            }
                            else {
                                bedtime = h + ":" + m + " AM";
                            }                        }
                        System.out.println(bedtime);
                        bedtimeText.setText(bedtime);


                        if(Integer.parseInt(dateFormat.format(Note.noteArrayList.get(i).getEndTime()).substring(11, 13)) > 12){
                            int h = Integer.parseInt(dateFormat.format(Note.noteArrayList.get(i).getEndTime()).substring(11, 13)) - 12;
                            int m = Integer.parseInt(dateFormat.format(Note.noteArrayList.get(i).getEndTime()).substring(14, 16));
                            if(m < 10){
                                waketime = h + ":0" + m + " PM";
                            }
                            else {
                                waketime = h + ":" + m + " PM";
                            }                        }
                        else {
                            int h = Integer.parseInt(dateFormat.format(Note.noteArrayList.get(i).getEndTime()).substring(11, 13));
                            int m = Integer.parseInt(dateFormat.format(Note.noteArrayList.get(i).getEndTime()).substring(14, 16));
                            if(m < 10){
                                waketime = h + ":0" + m + " AM";
                            }
                            else {
                                waketime = h + ":" + m + " AM";
                            }                        }
                        System.out.println(waketime);
                        wakeupText.setText(waketime);

                        hourNum = Double.parseDouble(df_obj.format(getHoursSlept(i))) + " hours";
                        System.out.println(hourNum);
                        hoursText.setText(hourNum);

                        moodText.setText(String.valueOf(Note.noteArrayList.get(i).getRating()));
                        if(Note.noteArrayList.get(i).getRating() > 7) {
                            sleepQuality.setBackgroundColor(getResources().getColor(R.color.green));
                        }
                        else if(Note.noteArrayList.get(i).getRating() > 3) {
                            sleepQuality.setBackgroundColor(getResources().getColor(R.color.yellow));
                        }
                        else {
                            sleepQuality.setBackgroundColor(getResources().getColor(R.color.red));
                        }
                    }
                }
                if(!dateCheck){
                    System.out.println("WORKSSS");
                    moodText.setText(" ");
                    hoursText.setText(" ");
                    wakeupText.setText(" ");
                    bedtimeText.setText(" ");
                    sleepQuality.setBackgroundColor(getResources().getColor(R.color.grey));

                    String displayDate = getMonthText(Integer.parseInt(String.valueOf(c1.get(Calendar.MONTH))) + 1) + " " + c1.get(Calendar.DATE) + ", " + c1.get(Calendar.YEAR);
                    selectedDateText.setText(displayDate);
                }
                dateCheck = false;
            }
        });
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
        System.out.println(hour/1.0 + m);

        return hour/1.0 + m;
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
}