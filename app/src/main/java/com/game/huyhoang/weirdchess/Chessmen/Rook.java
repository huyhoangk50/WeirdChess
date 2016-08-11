package com.game.huyhoang.weirdchess.Chessmen;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.game.huyhoang.weirdchess.R;
import com.game.huyhoang.weirdchess.Tile;

/**
 * Created by huyhoang on 15/07/2016.
 */
public class Rook extends Chessman{
    public Rook(Context context, Tile tile, int w, int h) {
        super(context, tile, w, h);
        name = "Rook";
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.rook);
    }

    @Override
    public boolean canAttack(Tile t) {
        if(tile.getRow() == t.getRow()) return true;
        if(tile.getColumn() == t.getColumn()) return true;
        return false;
    }
}
