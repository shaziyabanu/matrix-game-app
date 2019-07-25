package com.agmosoft.shaziya.matrixgame;

/**
 * DatabaseHandler to save the scores of the player in level 1
 */
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHandler extends SQLiteOpenHelper {
    //Declaring and Assigning global variables
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "scoresManager";
    private static final String TABLE_SCORES = "scores";
    private static final String KEY_ID="id";
    private static final String KEY_SCORE = "score";
    private static final String KEY_NAME = "name";
    private static final String KEY_TIME = "time";
    //constructor for database handler
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables for score level 1
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SCORES + "("
                + KEY_ID +"INTEGER PRIMARY KEY, "+ KEY_NAME + " TEXT,"
                + KEY_TIME + " TEXT," + KEY_SCORE+ " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);

        // Create tables again
        onCreate(db);
    }

    // code to add the new score
    void addScore(Score score) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, score.getName());
        values.put(KEY_TIME, score.getTime());
        values.put(KEY_SCORE, score.getScores());

        // Inserting Row
        db.insert(TABLE_SCORES, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
    //delete score
    public void deleteScore(Score score) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SCORES, KEY_SCORE + " = ?",
                new String[] { String.valueOf(score.getScores()) });
        db.close();
    }

    // code to get all scores in a list view
    public List<Score> getAllScores() {
        List<Score> scoreList = new ArrayList<Score>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SCORES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Score score = new Score();
                score.setName(cursor.getString(1));
                score.setTime(cursor.getString(2));
                score.setScores(cursor.getString(3));

                // Adding score to list
                scoreList.add(score);
            } while (cursor.moveToNext());
        }

        // return score list
        return scoreList;
    }

    // Getting scores Count
    public int getScoresCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SCORES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }
}
