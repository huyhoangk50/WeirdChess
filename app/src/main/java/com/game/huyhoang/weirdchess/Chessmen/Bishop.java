package com.game.huyhoang.weirdchess.Chessmen;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.game.huyhoang.weirdchess.R;
import com.game.huyhoang.weirdchess.Tile;

/**
 * Created by huyhoang on 15/07/2016.
 */
public class Bishop extends Chessman {
    public Bishop(Context context, Tile tile, int w, int h) {
        super(context, tile, w, h);
        name = "Bishop";
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bishop);
    }

    @Override
    public boolean canAttack(Tile t) {
        if(Math.abs(tile.getColumn() - t.getColumn())==(Math.abs(tile.getRow()  - t.getRow()))) return true;
        return false;
    }
}
