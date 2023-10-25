package com.example.sleepdataproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;

public class SettingsScreen extends AppCompatActivity {

    Button homeButton, calendarButton;

    Dialog myDialog;

    TextView wakeupText, bedtimeText, monthlyText, dailytext;

    public static int monthlyGoal = 200, dailyGoal = 8, bedhour = 22, bedmin = 30, wakehour = 7, wakemin = 30;

    String wakeGoal, bedtimeGoal;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);
        myDialog = new Dialog(this);

        homeButton = findViewById(R.id.homeButton);
        calendarButton = findViewById(R.id.calendarButton);

        monthlyText = findViewById(R.id.monthlyText);
        dailytext = findViewById(R.id.dailyText);
        bedtimeText = findViewById(R.id.bedtimeText);
        wakeupText = findViewById(R.id.wakeupText);

        monthlyGoal = 200;
        dailyGoal = 8;
        bedhour = 22;
        bedmin = 30;
        wakehour = 7;
        wakemin = 30;

        monthlyText.setText("Monthly Goal: " + monthlyGoal + " hours");
        dailytext.setText("Daily Goal: " + dailyGoal + " hours");
        bedtimeText.setText("Bedtime Goal: 11:30 PM");
        wakeupText.setText("Wakeup Goal: 7:30 AM");


        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsScreen.this, MainActivity.class);

                startActivity(intent);
            }
        });
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsScreen.this, CalendarScreen.class);

                startActivity(intent);
            }
        });
    }

    public void ShowPopup1(View v) {
        Button txtclose, enter;
        EditText newGoal;
        myDialog.setContentView(R.layout.popup);
        txtclose = (Button) myDialog.findViewById(R.id.txtclose);
        enter = (Button) myDialog.findViewById(R.id.enterButton);
        newGoal = (EditText) myDialog.findViewById(R.id.newGoal);
        txtclose.setText("M");
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();

                monthlyGoal = Integer.parseInt(String.valueOf(newGoal.getText()));
                System.out.println(monthlyGoal);
                monthlyText.setText(monthlyGoal);
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void ShowPopup2(View v) {
        Button txtclose, enter;
        EditText newGoal;
        myDialog.setContentView(R.layout.popup);
        txtclose = (Button) myDialog.findViewById(R.id.txtclose);
        enter = (Button) myDialog.findViewById(R.id.enterButton);
        newGoal = (EditText) myDialog.findViewById(R.id.newGoal);
        txtclose.setText("M");
//        Log.d(ToastAG, "ShowPopup2: ");
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();

                dailyGoal = Integer.parseInt(String.valueOf(newGoal.getText()));
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void ShowPopup3(View v) {
        Button txtclose, enter;
        EditText newGoal;
        myDialog.setContentView(R.layout.popup);
        txtclose = (Button) myDialog.findViewById(R.id.txtclose);
        enter = (Button) myDialog.findViewById(R.id.enterButton);
        newGoal = (EditText) myDialog.findViewById(R.id.newGoal);
        txtclose.setText("M");
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();

                bedtimeGoal = String.valueOf(newGoal.getText());
                setTimes(bedtimeGoal, true);
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void ShowPopup4(View v) {
        Button txtclose, enter;
        EditText newGoal;
        myDialog.setContentView(R.layout.popup);
        txtclose = (Button) myDialog.findViewById(R.id.txtclose);
        enter = (Button) myDialog.findViewById(R.id.enterButton);
        newGoal = (EditText) myDialog.findViewById(R.id.newGoal);
        System.out.println("hi loser");
        txtclose.setText("M");txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        System.out.println("hi loser 2");
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();

                wakeGoal = String.valueOf(newGoal.getText());
                setTimes(wakeGoal, false);
            }
        });
        System.out.println("hi loser 3");
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void setTimes(String inputText, boolean a){
        if(inputText.charAt(1) == ':' || inputText.charAt(2) == ':'){
            if(inputText.charAt(1) == ':') {
                if(a){
                    bedhour = Integer.parseInt(inputText.substring(0, 1));
                    bedmin = Integer.parseInt(inputText.substring(2, 4));
                }
                else{
                    wakehour = Integer.parseInt(inputText.substring(0, 1));
                    wakemin = Integer.parseInt(inputText.substring(2, 4));
                }
            }
            if(inputText.charAt(2) == ':') {
                if (a) {
                    bedhour = Integer.parseInt(inputText.substring(0, 2));
                    bedmin = Integer.parseInt(inputText.substring(3, 5));
                }
                else {
                    wakehour = Integer.parseInt(inputText.substring(0, 2));
                    wakemin = Integer.parseInt(inputText.substring(3, 5));
                }
            }
        }
    }
}