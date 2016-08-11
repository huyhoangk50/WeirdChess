package com.game.huyhoang.weirdchess.Chessmen;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.game.huyhoang.weirdchess.R;
import com.game.huyhoang.weirdchess.Tile;

/**
 * Created by huyhoang on 15/07/2016.
 */
public class King extends Chessman {
    public King(Context context, Tile tile, int w, int h) {
        super(context, tile, w, h);
        name = "King";
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.king);
    }

    @Override
    public boolean canAttack(Tile t) {
        if((Math.abs(tile.getColumn() - t.getColumn() )>1)){
            return false;
        }

        if(Math.abs(tile.getRow() - t.getRow()) > 1){
            return false;
        }

        if(tile.getRow()==t.getRow() && tile.getColumn() == t.getColumn()){
            return false;
        }

        return true;
    }
}
