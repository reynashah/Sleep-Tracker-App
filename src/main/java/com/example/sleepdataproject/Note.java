package com.example.sleepdataproject;

import java.util.ArrayList;
import java.util.Date;

public class Note
{
    public static ArrayList<Note> noteArrayList = new ArrayList<>();
    public static String NOTE_EDIT_EXTRA =  "noteEdit";

    private int id;

    private int rating;

    private Date startTime;

    private Date endTime;

    public Note(int id, int rating, Date startTime, Date endTime)
    {
        this.id = id;
        this.rating = rating;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static Note getNoteForID(int passedNoteID)
    {
        for (Note note : noteArrayList)
        {
            if(note.getId() == passedNoteID)
                return note;
        }

        return null;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}
