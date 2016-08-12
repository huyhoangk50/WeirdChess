package com.game.huyhoang.weirdchess;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by huyhoang on 13/07/2016.
 */
public class Player extends GameObject {

    private long startTime;
    private boolean playing;
    private Bitmap bitmap;
    private boolean moving;
    private Tile tile;
    private int score;

    public Player(Bitmap bitmap, Tile tile, int w, int h) {
        this.bitmap = bitmap;
        this.width = w;
        this.height = h;
        this.tile = tile;
        moving = false;
        playing = false;
        this.x = tile.getX() + (tile.getWidth() - width) / 2;
        this.y = tile.getY() + (tile.getHeight() - height) / 2;
        score = 0;
        startTime = System.nanoTime();
    }

    public void update() {
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if (elapsed > 100) {
            score++;
            startTime = System.nanoTime();
        }
        x = tile.getX() + (tile.getWidth() - width) / 2;
        y = tile.getY() + (tile.getHeight() - height) / 2;
    }

    public void moveToTile(Tile t) {
        tile = t;
        x = tile.getX() + (tile.getWidth() - width) / 2;
        y = tile.getY() + (tile.getHeight() - height) / 2;
        moving = false;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(x, y, x + GamePanel.TILE_WIDTH, y + GamePanel.TILE_HEIGHT), null);
    }

    public boolean isPlaying() {
        return playing;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setPlaying(boolean b) {
        playing = b;
    }

    public void setMoving(boolean b) {
        moving = b;
    }

    public Tile getTile() {
        return tile;
    }

    public int getScore() {
        return score;
    }

    public void reset() {
        this.score = 0;
        this.startTime = 0;
        this.moving = false;
        this.playing = false;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
