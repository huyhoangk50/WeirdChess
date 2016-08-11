package com.game.huyhoang.weirdchess;

import android.graphics.Canvas;
import android.provider.Settings;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

/**
 * Created by huyhoang on 12/07/2016.
 */
public class MainThread extends Thread {

    public static final int FTS = 30;

    private boolean running;
    private Canvas canvas;

    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }


    @Override
    public void run() {
        super.run();
        long startTime;
        long miliTime;
        long waitTime;
        long targetTime = 1000/FTS;

        while (running){
            startTime = System.nanoTime();
            canvas = null;
            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){

                    gamePanel.update();
                    gamePanel.draw(canvas);
                }
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                if(canvas != null){
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            miliTime = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - miliTime;

            if(waitTime > 0){
                try{
                    this.sleep(waitTime);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

        if(!running){
            try {
                this.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gamePanel.goToHomeActivity();
        }
    }

    public void setRunning(boolean running){
        this.running = running;
    }
}
