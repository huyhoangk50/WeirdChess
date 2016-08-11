package com.game.huyhoang.weirdchess.Chessmen;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.game.huyhoang.weirdchess.R;
import com.game.huyhoang.weirdchess.Tile;

/**
 * Created by huyhoang on 15/07/2016.
 */
public class Pawn extends Chessman {
    public Pawn(Context context, Tile tile, int w, int h) {
        super(context, tile, w, h);
        name = "Pawn";
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pawn);
    }

    @Override
    public boolean canAttack(Tile t) {
        if(((tile.getRow() - t.getRow()) == - 1) &&((tile.getColumn() - t.getColumn()) == -1)) return true;
        return false;
    }
}