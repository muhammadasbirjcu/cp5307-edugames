package com.asbir.cp5307.edugames.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.asbir.cp5307.edugames.game.Difficulty;
import com.asbir.cp5307.edugames.models.Score;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper sInstance;

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME ="asbirEduGame.db";
    public static final String TABLE_NAME = "scores";

    public static final String ID_COL = "_id";
    public static final String PLAYER_COL = "player";
    public static final String DIFFICULTY_COL = "difficulty";
    public static final String DATE_COL = "date";
    public static final String SCORE_COL = "score";
    public static final String NUM_QUESTIONS_COL = "num_questions";
    public static final String TIME_REMAINING_COL = "timeRemaining";

    public DBHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION );
    }

    // Singleton
    public static synchronized DBHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "create table " + TABLE_NAME
                + "("
                + ID_COL + " integer primary key autoincrement,"
                + PLAYER_COL + " text not null,"
                + DIFFICULTY_COL + " int not null default 0,"
                + TIME_REMAINING_COL + " int not null default 0,"
                + NUM_QUESTIONS_COL + " int not null default 0,"
                + DATE_COL + " timestamp default CURRENT_TIMESTAMP,"
                + SCORE_COL + " int default 0"
                + ")";
        db.execSQL(create_table);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " +  TABLE_NAME);
        onCreate(db);
    }

    public boolean insert(Score score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLAYER_COL, score.getPlayer());
        values.put(DIFFICULTY_COL, score.getDifficulty().ordinal());
        values.put(SCORE_COL, score.getScore());
        values.put(TIME_REMAINING_COL, score.getTimeElapsed());
        values.put(NUM_QUESTIONS_COL, score.getNumQuestions());
        db.insert(TABLE_NAME, null, values);
        return true;
    }

    @SuppressLint("Range")
    public ArrayList<Score> getLeaderboard(int limit){
        ArrayList<Score> scores = new ArrayList<>();
        String sqlStr = "SELECT * FROM " + TABLE_NAME
                + " ORDER BY " + SCORE_COL + " DESC "
                + "LIMIT 0," + String.valueOf(limit);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlStr, null);

        try{
            if(cursor.moveToFirst()){
                do {
                    Score score = new Score(
                            cursor.getString(cursor.getColumnIndex(PLAYER_COL)),
                            Difficulty.values()[cursor.getInt(cursor.getColumnIndex(DIFFICULTY_COL))],
                            cursor.getString(cursor.getColumnIndex(DATE_COL)),
                            cursor.getInt(cursor.getColumnIndex(SCORE_COL)),
                            cursor.getInt(cursor.getColumnIndex(TIME_REMAINING_COL)),
                            cursor.getInt(cursor.getColumnIndex(NUM_QUESTIONS_COL))
                    );
                    score.setId(cursor.getInt(cursor.getColumnIndex(ID_COL)));
                    scores.add(score);
                } while (cursor.moveToNext());
            }
        }catch (Exception ex){
            Log.d("DBHelper", "Error while trying to retrieve leaderboard from database");
        }finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return scores;
    }
}
