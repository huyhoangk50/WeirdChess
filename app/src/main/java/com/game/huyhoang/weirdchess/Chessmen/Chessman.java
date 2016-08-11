package com.game.huyhoang.weirdchess.Chessmen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.game.huyhoang.weirdchess.GameObject;
import com.game.huyhoang.weirdchess.GamePanel;
import com.game.huyhoang.weirdchess.Tile;

/**
 * Created by huyhoang on 12/07/2016.
 */
public abstract class Chessman extends GameObject {
    protected Bitmap bitmap;
    protected boolean attacking;
    protected Tile tile;
    protected Context context;
    public String name;

    public Chessman(Context context, Tile tile, int w, int h){
        this.context = context;
        this.width = w;
        this.height = h;
        this.tile = tile;
        attacking = false;
        this.x = tile.getX() + (tile.getWidth() - width) / 2;
        this.y = tile.getY() + (tile.getHeight() - height) / 2;
    }

    public void update(){
        x = tile.getX() + (tile.getWidth() - width) / 2;
        y += GamePanel.gameSpeed;
    }

    public void moveToTile(Tile t){
        tile = t;
//        final Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for(int i = 0; i < 10; i ++){
//                    if(i == 9){
//                        tile = t;
//                        x = tile.getX() + (tile.getWidth() - width) / 2;
//                        y = tile.getY() + (tile.getHeight() - height) / 2;
//                    } else{
//                        tile.setX(tile.getX() + distanceX / 10);
//                        tile.setY(tile.getY() + distanceY / 10);
//                    }
//                    try {
//                        synchronized (this){
//                            wait(100);
//                        }
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        });
//        thread.start();
        x = tile.getX() + (tile.getWidth() - width) / 2;
        y = tile.getY() + (tile.getHeight() - height) / 2;
        attacking = false;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap,new Rect(0,0, bitmap.getWidth(),bitmap.getHeight()),new Rect( x, y, x + width, y + height), null);
    }

    public boolean isAttacking(){
        return attacking;
    }

    public void setAttacking(boolean b){
        attacking = b;
    }

    public Tile getTile(){
        return tile;
    }

    public abstract boolean canAttack(Tile tile);

}
