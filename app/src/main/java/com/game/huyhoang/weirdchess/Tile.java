package com.game.huyhoang.weirdchess;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by huyhoang on 12/07/2016.
 */
public class Tile extends GameObject{
    public boolean isBlack;
    private int columnIndex;
    private int rowIndex;
    private Paint blackPaint;
    private Paint whitePaint;

    public Tile(int x, int y, int w, int h, boolean isBlack, int column, int row){
        this.isBlack = isBlack;
        this.x = x;
        this.y = y;
        this.width =w;
        this.height = h;
        columnIndex = column;
        rowIndex = row;
        dy = GamePanel.gameSpeed;

        blackPaint = new Paint();
        blackPaint.setStyle(Paint.Style.FILL);
        blackPaint.setColor(Color.BLACK);

        whitePaint = new Paint();
        whitePaint.setStyle(Paint.Style.FILL);
        whitePaint.setColor(Color.WHITE);
    }

    public void update(){
        y += GamePanel.gameSpeed;
    }

    public void draw(Canvas canvas){
        if(isBlack){
            canvas.drawRect(x, y, x + width, y + width, blackPaint);
        } else {
            canvas.drawRect(x, y, x + width, y + width, whitePaint);
        }

    }

    public int getColumn(){
        return columnIndex;
    }

    public int getRow(){
        return rowIndex;
    }

    public void setRow(int row){
        rowIndex = row;
    }

    public void setColumn(int column){
        columnIndex = column;
    }
}
