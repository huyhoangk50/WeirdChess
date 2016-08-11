package com.game.huyhoang.weirdchess.Chessmen;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.game.huyhoang.weirdchess.R;
import com.game.huyhoang.weirdchess.Tile;

/**
 * Created by huyhoang on 15/07/2016.
 */
public class Knight extends Chessman{
    public Knight(Context context, Tile tile, int w, int h) {
        super(context, tile, w, h);
        name = "Knight";
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.knight);
    }

    @Override
    public boolean canAttack(Tile t) {
        if((Math.abs(tile.getColumn() - t.getColumn()) + Math.abs(tile.getRow() - t.getRow()) == 3)
                &&(tile.getRow() != t.getRow())
                && (tile.getColumn() != t.getColumn())){
            return true;
        }
        return false;
    }
}
