package com.example.sleepdataproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class LogSleepScreen extends AppCompatActivity {

    Button homeButton, settingsButton, logSleepButton, rateButton1, rateButton2, rateButton3, rateButton4, rateButton5,
            rateButton6, rateButton7, rateButton8, rateButton9, rateButton10;

    TextView rateText, rateText1, rateText10;

    int logStart = 0;

    Date sleepStart, sleepEnd;

    int sleepRating;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_sleep_screen);

        homeButton = findViewById(R.id.homeButton);
        settingsButton = findViewById(R.id.settingsButton);
        logSleepButton = findViewById(R.id.logsleepButton);

        rateButton1 = findViewById(R.id.rateButton1);
        rateButton2 = findViewById(R.id.rateButton2);
        rateButton3 = findViewById(R.id.rateButton3);
        rateButton4 = findViewById(R.id.rateButton4);
        rateButton5 = findViewById(R.id.rateButton5);
        rateButton6 = findViewById(R.id.rateButton6);
        rateButton7 = findViewById(R.id.rateButton7);
        rateButton8 = findViewById(R.id.rateButton8);
        rateButton9 = findViewById(R.id.rateButton9);
        rateButton10 = findViewById(R.id.rateButton10);

        rateText = findViewById(R.id.rateText);
        rateText1 = findViewById(R.id.rateText1);
        rateText10 = findViewById(R.id.rateText10);

        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);


//        CSVReader.loadCSVFile(LogSleepScreen.this, "sample.csv", sqLiteManager.getWritableDatabase());
//        Toast.makeText(this, "Loaded CSV file into Database", Toast.LENGTH_SHORT).show();
//
//        sqLiteManager.populateNoteListArray();
//
//        while(Note.noteArrayList.size() > 14){
//            Note.noteArrayList.remove(0);
//        }

//        Toast.makeText(this, "hi " + String.valueOf(Note.noteArrayList.get(1).getId()), Toast.LENGTH_LONG).show();
//        Toast.makeText(this, "hi " + String.valueOf(Note.noteArrayList.get(1).getRating()), Toast.LENGTH_LONG).show();
//        Toast.makeText(this, "hi " + String.valueOf(Note.noteArrayList.get(1).getStartTime()), Toast.LENGTH_LONG).show();
//        Toast.makeText(this, "hi " + String.valueOf(Note.noteArrayList.get(1).getEndTime()), Toast.LENGTH_LONG).show();

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogSleepScreen.this, MainActivity.class);

                startActivity(intent);
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogSleepScreen.this, SettingsScreen.class);

                startActivity(intent);
            }
        });

        rateButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sleepRating = 1;
            }
        });
        rateButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sleepRating = 2;
            }
        });
        rateButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sleepRating = 3;
            }
        });
        rateButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sleepRating = 4;
            }
        });
        rateButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sleepRating = 5;
            }
        });
        rateButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sleepRating = 6;
            }
        });
        rateButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sleepRating = 7;
            }
        });
        rateButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sleepRating = 8;
            }
        });
        rateButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sleepRating = 9;
            }
        });
        rateButton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sleepRating = 10;
            }
        });

        logSleepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (logStart == 0){
                    sleepRating = 0;

                    rateButton1.setBackgroundColor(getResources().getColor(R.color.grey));
                    rateButton2.setBackgroundColor(getResources().getColor(R.color.grey));
                    rateButton3.setBackgroundColor(getResources().getColor(R.color.grey));
                    rateButton4.setBackgroundColor(getResources().getColor(R.color.grey));
                    rateButton5.setBackgroundColor(getResources().getColor(R.color.grey));
                    rateButton6.setBackgroundColor(getResources().getColor(R.color.grey));
                    rateButton7.setBackgroundColor(getResources().getColor(R.color.grey));
                    rateButton8.setBackgroundColor(getResources().getColor(R.color.grey));
                    rateButton9.setBackgroundColor(getResources().getColor(R.color.grey));
                    rateButton10.setBackgroundColor(getResources().getColor(R.color.grey));

                    rateText.setTextColor(getResources().getColor(R.color.grey));
                    rateText1.setTextColor(getResources().getColor(R.color.white));
                    rateText10.setTextColor(getResources().getColor(R.color.white));

                    sleepStart = new Date();

                    logSleepButton.setText("End Sleep Session");
                    logStart = 1;
                    //sleep session started
                }
                else if(logStart == 1){
                    if(sleepRating == 0){
                        Toast.makeText(LogSleepScreen.this, "Rate your Sleep Quality before ending session", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        sleepEnd = new Date();

                        int id = Note.noteArrayList.size();
                        Note newNote = new Note(id, sleepRating, sleepStart, sleepEnd);
                        Note.noteArrayList.add(newNote);
                        sqLiteManager.addNoteToDatabase(newNote);

                        logSleepButton.setText("Start Sleep Session");

                        rateButton1.setBackgroundColor(getResources().getColor(R.color.blue));
                        rateButton2.setBackgroundColor(getResources().getColor(R.color.blue));
                        rateButton3.setBackgroundColor(getResources().getColor(R.color.blue));
                        rateButton4.setBackgroundColor(getResources().getColor(R.color.blue));
                        rateButton5.setBackgroundColor(getResources().getColor(R.color.blue));
                        rateButton6.setBackgroundColor(getResources().getColor(R.color.blue));
                        rateButton7.setBackgroundColor(getResources().getColor(R.color.blue));
                        rateButton8.setBackgroundColor(getResources().getColor(R.color.blue));
                        rateButton9.setBackgroundColor(getResources().getColor(R.color.blue));
                        rateButton10.setBackgroundColor(getResources().getColor(R.color.blue));

                        rateText.setTextColor(getResources().getColor(R.color.blue));
                        rateText1.setTextColor(getResources().getColor(R.color.blue));
                        rateText10.setTextColor(getResources().getColor(R.color.blue));

                        logStart = 0;
                        //sleep session ended
                    }
                }
            }
        });

    }
}