package com.game.huyhoang.weirdchess;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.game.huyhoang.weirdchess.Activity.HomeActivity;
import com.game.huyhoang.weirdchess.Chessmen.Bishop;
import com.game.huyhoang.weirdchess.Chessmen.Chessman;
import com.game.huyhoang.weirdchess.Chessmen.King;
import com.game.huyhoang.weirdchess.Chessmen.Knight;
import com.game.huyhoang.weirdchess.Chessmen.Pawn;
import com.game.huyhoang.weirdchess.Chessmen.Queen;
import com.game.huyhoang.weirdchess.Chessmen.Rook;

import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {




    public static final int ROW_NUM = 12;
    public static final int COLUMN_NUM = 8;
    public static final int MAX_SPEED = 10;
    public static final int MIN_ROW_NUM_FOR_CREATING_A_CHESSMAN = 4;
    public static final int CHESSMAN_TYPE_NUM = 6;

    public static int TILE_WIDTH = 128;
    public static int TILE_HEIGHT = 128;

    public static int gameSpeed = 3;

    private MainThread thread;

    private Background middleBackground;
    private Background topBackground;
    private Background bottomBackground;

    private Context context;
    private Player player;
    private Tile tileDestination;

    private ArrayList<Row> rows;
    private ArrayList<Chessman> chessmen;

    private int chessboardX;
    private int chessboardY;

    private int screenWidth;
    private int screenHeight;

    private int count;
    private int rowNumForCreatingAChessman;

    private boolean isStarted;
    private boolean isLost;

    private Paint scorePaint;
    private Paint helpPaint;



    Random random = new Random();

    public GamePanel(Activity activity) {
        super(activity);

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        int tileWidth = screenWidth/COLUMN_NUM;
        int tileHeight = screenHeight/(ROW_NUM + 1);
        Log.d("GamePanel", "Screen width : " + screenWidth);
        Log.d("GamePanel", "Screen height : " + screenHeight);

        if(tileWidth > tileHeight) {
            tileWidth = tileHeight;
        }

        if(tileWidth > 128) TILE_WIDTH = 128;
        TILE_HEIGHT = TILE_WIDTH;


        isStarted = true;
        isLost = false;
        gameSpeed = 3;



        scorePaint = new Paint();
        scorePaint.setStyle(Paint.Style.STROKE);
        scorePaint.setColor(Color.YELLOW);
        scorePaint.setTextSize(50);

        helpPaint = new Paint();
        helpPaint.setStyle(Paint.Style.FILL);
        helpPaint.setColor(Color.BLUE);
        helpPaint.setTextSize(50);


        this.context = activity;
        count = 0;
        rowNumForCreatingAChessman = 6;

        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        //        chessBoard = new ChessBoard(context, getWidth()/2 - ChessBoard.COLUMN_NUM * ChessBoard.TILE_WIDTH /2, 200);
        chessboardX = getWidth() / 2 - COLUMN_NUM * TILE_WIDTH / 2;
        chessboardY = TILE_HEIGHT;

        rows = new ArrayList<>();
        chessmen = new ArrayList<>();

        initRows();

        //Background
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        middleBackground = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.top_background, options), 0, chessboardY, getWidth(), getChessboardHeight());
        topBackground = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.top_background, options), 0, 0, getWidth(), chessboardY);
        bottomBackground = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.top_background, options), 0, chessboardY + getChessboardHeight(), getWidth(), getHeight() - chessboardY - getChessboardHeight());

        Tile tile = rows.get(ROW_NUM - 3).getTiles().get((int) (random.nextDouble() * COLUMN_NUM));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.knight_player), tile, TILE_WIDTH, TILE_HEIGHT);

        thread.setRunning(true);
        try{
            thread.start();
        }catch(Exception ex){

        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        int counter = 0;
        while (retry && counter < 1000) {
            counter++;
            try {
                thread.setRunning(false);
//                thread.interrupt();
                thread.join();
                retry = false;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (isStarted && !isLost) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (!player.isPlaying()) {
                    player.setPlaying(true);
                } else {
                    int column = (int) ((event.getX() - chessboardX) / TILE_WIDTH);
                    int row = (int) ((event.getY() - rows.get(0).getY()) / TILE_HEIGHT);

                    try {

//                        Log.e("Touch", "Column = " + column + ", Row = " + row);

                        if ((column < COLUMN_NUM) || (row < ROW_NUM) && (column >= 0) && (row >= 0)) {
                            tileDestination = rows.get(row).getTiles().get(column);

                            if ((Math.abs(tileDestination.getColumn() - player.getTile().getColumn()) + Math.abs(tileDestination.getRow() - player.getTile().getRow()) == 3)
                                    && (row != player.getTile().getRow())
                                    && (column != player.getTile().getColumn())) {
                                player.setMoving(true);
                            }
                        }
                    } catch (Exception e) {
//                        Log.e("value", "row : " + row + " column : " + column);
                    }

                }
                return true;
            }
        }

        return super.onTouchEvent(event);
    }

    public void update() {
        //update chessboard
        if (player.isPlaying() && !isLost) {


            rowNumForCreatingAChessman = 8 - player.getScore()/50;

            if (rowNumForCreatingAChessman < MIN_ROW_NUM_FOR_CREATING_A_CHESSMAN) {
                rowNumForCreatingAChessman = MIN_ROW_NUM_FOR_CREATING_A_CHESSMAN;
            }
            gameSpeed = player.getScore() / 50 + 3;

            if (gameSpeed > MAX_SPEED) {
                gameSpeed = MAX_SPEED;
            }
            //update chessmen

            for (int i = 0; i < chessmen.size(); i++) {
                if (chessmen.get(i).canAttack(player.getTile()) && chessmen.get(i).getTile().getRow() > 0) {
                    chessmen.get(i).moveToTile(player.getTile());
                    player.setPlaying(false);
                    isLost = true;
                    break;
                }
                chessmen.get(i).update();

                if (chessmen.get(i).getTile().getY() + TILE_HEIGHT > getChessboardHeight() + chessboardY) {
                    chessmen.remove(i);
                }
            }
//            chessBoard.update();
            for (int i = 0; i < rows.size(); i++) {
                rows.get(i).update();
            }

//        Log.e("Y", "" + rows.get(0).getY());

            if (rows.get(0).getY() - chessboardY > 0) {
                Row row;
                if (rows.get(0).isBlackFirst()) {
                    row = new Row(context, Row.WHITE_FIRST, chessboardX, rows.get(0).getY() - TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT, 0);
                } else {
                    row = new Row(context, Row.BLACK_FIRST, chessboardX, rows.get(0).getY() - TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT, 0);
                }
                rows.add(0, row);

                for (int i = 1; i < rows.size(); i++) {
                    rows.get(i).setRow(i);
                }
                if (count % rowNumForCreatingAChessman == 0) {
                    int num = (int) (new Random().nextDouble() * COLUMN_NUM);
                    int chessOption = (int) (new Random().nextDouble() * CHESSMAN_TYPE_NUM);

                    Chessman chessman;
                    switch (chessOption) {
                        case 0:
                            chessman = new Bishop(context, rows.get(0).getTiles().get(num), TILE_WIDTH, TILE_HEIGHT);
                            break;
                        case 1:
                            chessman = new King(context, rows.get(0).getTiles().get(num), TILE_WIDTH, TILE_HEIGHT);
                            break;
                        case 2:
                            chessman = new Knight(context, rows.get(0).getTiles().get(num), TILE_WIDTH, TILE_HEIGHT);
                            break;
                        case 3:
                            chessman = new Pawn(context, rows.get(0).getTiles().get(num), TILE_WIDTH, TILE_HEIGHT);
                            break;
                        case 4:
                            chessman = new Queen(context, rows.get(0).getTiles().get(num), TILE_WIDTH, TILE_HEIGHT);
                            break;
                        default:
                            chessman = new Rook(context, rows.get(0).getTiles().get(num), TILE_WIDTH, TILE_HEIGHT);
                            break;
                    }

                    chessmen.add(chessman);
                }
                count++;
            }
            Row finalRow = rows.get(rows.size() - 1);
            if (finalRow.getY() - chessboardY > getChessboardHeight()) {
                rows.remove(rows.size() - 1);
            }

            if (!player.isMoving()) {
                player.update();
            } else {
                player.moveToTile(tileDestination);
                for (int i = 0; i < chessmen.size(); i++) {
                    if (player.getTile() == chessmen.get(i).getTile()) {
                        chessmen.remove(i);
                        player.setScore(player.getScore() + 10);
                    }
                }
            }

            if (player.getTile().getY() + TILE_HEIGHT  > getChessboardHeight() + chessboardY) {
                isLost = true;
            }

            if (isLost) {
                isStarted = false;
                thread.setRunning(false);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        middleBackground.draw(canvas);
        //draw chessboard
        for (Row row : rows) {
            row.draw(canvas);
        }

        for (Chessman chessman : chessmen) {
            chessman.draw(canvas);
        }
        //draw background
        topBackground.draw(canvas);
        bottomBackground.draw(canvas);

        player.draw(canvas);

        canvas.drawText("Score : " + player.getScore(), 50, TILE_HEIGHT/2, scorePaint);

        if(!player.isPlaying()){
            canvas.drawText("Tab to start!!", TILE_WIDTH, screenHeight/2 - TILE_HEIGHT, helpPaint);
            canvas.drawText("Move your Knight to attack other chessman!!", TILE_WIDTH, screenHeight/2 + TILE_HEIGHT, helpPaint);
        }
    }

    public void goToHomeActivity() {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra("score", player.getScore());
        context.startActivity(intent);
    }

    public int getChessboardHeight() {
        return ROW_NUM * TILE_HEIGHT;
    }

    public void initRows() {
        for (int i = 0; i < ROW_NUM; i++) {
            if (i % 2 == 0) {
                Row row = new Row(context, Row.BLACK_FIRST, chessboardX, chessboardY + i * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT, i);
                rows.add(i, row);
            } else {
                Row row = new Row(context, Row.WHITE_FIRST, chessboardX, chessboardY + i * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT, i);
                rows.add(i, row);
            }
            count++;
        }
    }
}