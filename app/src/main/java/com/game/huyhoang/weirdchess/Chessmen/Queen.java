package com.game.huyhoang.weirdchess.Chessmen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.game.huyhoang.weirdchess.R;
import com.game.huyhoang.weirdchess.Tile;

/**
 * Created by huyhoang on 15/07/2016.
 */
public class Queen extends Chessman {
    public Queen(Context context, Tile tile, int w, int h) {
        super(context, tile, w, h);
        name = "Queen";
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.queen);
    }

    @Override
    public boolean canAttack(Tile t) {
        if(tile.getRow() == t.getRow()) return true;
        if(tile.getColumn() == t.getColumn()) return true;
        if(Math.abs(tile.getColumn() - t.getColumn())==(Math.abs(tile.getRow()  - t.getRow()))) return true;
        return false;
    }
}
