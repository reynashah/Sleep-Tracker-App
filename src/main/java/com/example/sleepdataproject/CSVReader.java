package com.example.sleepdataproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to help with CSV Files
 * @author Nimit Patel
 */
@SuppressWarnings("unused")
public class CSVReader {
    /**
     * Loads a CSV file into the provided {@link SQLiteDatabase}<br>
     * <b>Note:</b> This method will close the database after it is done<br>
     *
     * <b>ATTENTION: You MUST correctly modify the types of from lines 44-51 to match YOUR CSV File format.</b></B>
     * @param context the context
     * @param fileName the name of the file, relative from the assets folder
     * @param database the database to load the file into
     */
    public static void loadCSVFile(@NotNull Context context, @NotNull String fileName, @NotNull SQLiteDatabase database) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null; // allocate streams and readers for clean closing in finally block
        try {
            inputStream = context.getAssets().open(fileName);
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);

            reader.readLine(); // ignore the header (first) line

            String line; // placeholder for the current line

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(","); // read the comma-separated line
                // a line of bobby,18,happy becomes ["bobby", "18", "happy"]

                int id = Integer.parseInt(values[0]); // grab the appropriate values using indices
                int rating = Integer.parseInt(values[1]);
                String startTime = values[2];
                String endTime = values[3];

                ContentValues contentValues = new ContentValues();
                contentValues.put(SQLiteManager.ID_FIELD, id);
                contentValues.put(SQLiteManager.RATING_FIELD, rating);
                contentValues.put(SQLiteManager.START_FIELD, startTime);
                contentValues.put(SQLiteManager.END_FIELD, endTime);

                database.insert(SQLiteManager.TABLE_NAME, null, contentValues); // insert the values into the database
                Log.d("CSV Insert", "Inserting: " + contentValues);
                // content values are a map, but they make it easier opposed to doing it manually like INSERT INTO ...
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // free up resources by closing them
            // just good practice
            try {
                if (reader != null) reader.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (inputStream != null) inputStream.close();
                if (database != null) database.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Loads a CSV File and returns the contents as an {@link ArrayList} of {@link String} arrays<br>
     * @param context the context
     * @param fileName the name of the file, relative from the assets folder
     * @return the contents of the file as a String array
     */
    public static List<String[]> loadCSVFile(@NotNull Context context, @NotNull String fileName) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null; // allocate streams and readers for clean closing in finally block
        ArrayList<String[]> lines = new ArrayList<>();
        try {
            inputStream = context.getAssets().open(fileName);
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);

            reader.readLine(); // ignore the header (first) line

            String line; // placeholder for the current line

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(","); // read the comma-separated line
                lines.add(values);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // free up resources by closing them
            // just good practice
            try {
                if (reader != null) reader.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lines;
    }
}
