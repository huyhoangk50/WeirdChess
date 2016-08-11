package com.game.huyhoang.weirdchess.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

/**
 * Created by huyhoang on 24/07/2016.
 */
public class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {
    public SQLiteOpenHelper(Context context, String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table Score (id integer primary key, High_Score integer)");
        try{
            sqLiteDatabase.execSQL("insert into Score (id, High_Score) VALUES (1,0)");
        } catch (SQLiteException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Score");
        onCreate(sqLiteDatabase);
    }

    public int getHighScore(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select High_Score from Score where id="+id+"", null );
        res.moveToFirst();
        try{
            int highScore = res.getInt(0);
            return highScore;
        } catch (Exception e){
            e.printStackTrace();
        }

        return 0;
    }


    public boolean updateHightScore (int highScore)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("High_Score", highScore);
        db.update("Score", contentValues, "id = ? ", new String[] { Integer.toString(1) } );
        return true;
    }

}
