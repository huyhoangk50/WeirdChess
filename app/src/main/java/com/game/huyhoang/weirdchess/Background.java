package com.game.huyhoang.weirdchess;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.game.huyhoang.weirdchess.GameObject;

/**
 * Created by huyhoang on 13/07/2016.
 */
public class Background extends GameObject {
    private Bitmap bitmap;

    public Background(Bitmap bitmap, int x, int y, int w, int h){
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap,
                new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(x, y, x + width, y + height), null);
    }
}
