package com.game.huyhoang.weirdchess;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by huyhoang on 12/07/2016.
 */
public class Row extends GameObject {

    private boolean blackFirst;
    public static final boolean BLACK_FIRST = true;
    public static final boolean WHITE_FIRST = false;

    private ArrayList<Tile> tiles;

    private Context context;
    private int index;

//    public Bitmap BLACK_BITMAP_TILE;
//    public Bitmap WHITE_BITMAP_TILE;

    public Row(Context context, boolean black, int x, int y, int tileWidth, int tileHeight, int index) {
        this.blackFirst = black;
        this.context = context;
        this.index = index;
        this.x = x;
        this.y = y;
        dy = GamePanel.gameSpeed;


//        BLACK_BITMAP_TILE = BitmapFactory.decodeResource(context.getResources(), R.drawable.black);
//        WHITE_BITMAP_TILE = BitmapFactory.decodeResource(context.getResources(), R.drawable.white);

        tiles = new ArrayList<>();
        if (blackFirst) {
//            for(int i = 0; i < ChessBoard.COLUMN_NUM; i ++){
//                Tile tile;
//                if((i % 2) == 0) {
//                    tile = new Tile(BLACK_BITMAP_TILE, x + i * tileWidth, y, tileWidth, tileHeight, true );
//                } else {
//                    tile = new Tile(WHITE_BITMAP_TILE, x + i * tileWidth, y, tileWidth, tileHeight, false );
//                }
//                tiles.add(i, tile);
//            }
            for (int i = 0; i < GamePanel.COLUMN_NUM; i++) {
                Tile tile;
                if ((i % 2) == 0) {
                    tile = new Tile(x + i * tileWidth, y, tileWidth, tileHeight, true, i, index);
                } else {
                    tile = new Tile(x + i * tileWidth, y, tileWidth, tileHeight, false, i, index);
                }
                tiles.add(i, tile);
            }
        } else {
//            for(int i = 0; i < ChessBoard.COLUMN_NUM; i ++){
//                Tile tile;
//                if((i % 2) == 0) {
//                    tile = new Tile(WHITE_BITMAP_TILE, x + i * tileWidth, y, tileWidth, tileHeight, false );
//                } else {
//                    tile = new Tile(BLACK_BITMAP_TILE, x + i * tileWidth, y, tileWidth, tileHeight, true );
//                }
//                tiles.add(i, tile);
//            }

            for (int i = 0; i < GamePanel.COLUMN_NUM; i++) {
                Tile tile;
                if ((i % 2) == 0) {
                    tile = new Tile(x + i * tileWidth, y, tileWidth, tileHeight, false, i, index);
                } else {
                    tile = new Tile(x + i * tileWidth, y, tileWidth, tileHeight, true, i, index);
                }
                tiles.add(i, tile);
            }
        }


    }

    public void draw(Canvas canvas) {
        for (int i = 0; i < tiles.size(); i++) {
            tiles.get(i).draw(canvas);
        }
    }

    public void update() {
        for (int i = 0; i < tiles.size(); i++) {
            tiles.get(i).update();
        }
        y += GamePanel.gameSpeed;
        for (int i = 0; i < tiles.size(); i++) {
            tiles.get(i).setRow(index);
        }
    }

    public boolean isBlackFirst() {
        return blackFirst;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public int getRow() {
        return index;
    }

    public void setRow(int index) {
        this.index = index;
    }
}
