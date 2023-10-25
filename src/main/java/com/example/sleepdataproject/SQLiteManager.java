package com.example.sleepdataproject;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SQLiteManager extends SQLiteOpenHelper
{
    private static SQLiteManager sqLiteManager;

    public static final String DATABASE_NAME = "NoteDB";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Note";
    private static final String COUNTER = "Counter";

    public static final String ID_FIELD = "id";
    public static final String START_FIELD = "start";
    public static final String END_FIELD = "end";
    public static final String RATING_FIELD = "rating";

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    public SQLiteManager(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context)
    {
        if(sqLiteManager == null)
            sqLiteManager = new SQLiteManager(context);

        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(RATING_FIELD)
                .append(" INT, ")
                .append(START_FIELD)
                .append(" TEXT, ")
                .append(END_FIELD)
                .append(" TEXT)");

        sqLiteDatabase.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
//        switch (oldVersion)
//        {
//            case 1:
//                sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + NEW_COLUMN + " TEXT");
//            case 2:
//                sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + NEW_COLUMN + " TEXT");
//        }
    }

    public void addNoteToDatabase(Note note)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, note.getId());
        contentValues.put(RATING_FIELD, note.getRating());
        contentValues.put(START_FIELD, getStringFromDate(note.getStartTime()));
        contentValues.put(END_FIELD, getStringFromDate(note.getEndTime()));

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public void populateNoteListArray()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        int i = 0;

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null))
        {
            if(result.getCount() != 0)
            {
                while (result.moveToNext())
                {
                    int id = result.getInt(1);
                    int rating = result.getInt(2);
                    String start = result.getString(3);
                    String end = result.getString(4);
                    Date startTime = getDateFromString(start);
                    Date endTime = getDateFromString(end);
                    Note note = new Note(id,rating,startTime,endTime);
                    Note.noteArrayList.add(note);
                    i++;
                }
            }
        }
    }

    private String getStringFromDate(Date date)
    {
        if(date == null)
            return null;
        return dateFormat.format(date);
    }

    private Date getDateFromString(String string)
    {
        try
        {
            return dateFormat.parse(string);
        }
        catch (ParseException | NullPointerException e)
        {
            return null;
        }
    }
}
