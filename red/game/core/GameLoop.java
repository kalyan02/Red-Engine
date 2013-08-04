package red.game.core;

import red.game.screens.*;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {
	private SurfaceHolder surfaceHolder;
	private GameView gameView;
	
	private boolean isRunning = false;
	
	private long mLastTime = 0;
	private int framesSamplesCollected = 0;
	private int framesSamplesTime = 0;
	private int fps = 0;
	
	public GameLoop( SurfaceHolder surfaceHolder, GameView gameView ) {
		this.surfaceHolder = surfaceHolder;
		this.gameView = gameView;
	}
	
	public void setRunning( boolean isRunning ) {
		this.isRunning = isRunning;
	}
	
	public void calcFrameRate() {
		long now = System.currentTimeMillis();
		if( mLastTime != 0 ) {
			int time = (int)(now - mLastTime);
			framesSamplesTime += time;
			framesSamplesCollected++;
			
			if(framesSamplesCollected == 10) {
				fps = (int)(10000/framesSamplesTime);
				
				GameInfo.fps = fps;
				
				framesSamplesCollected = 0;
				framesSamplesTime = 0;
			}
		}
		
		mLastTime = now;
	}
	
	public void run() {
		Canvas gameCanvas;
		while( isRunning ) {
			gameCanvas = null;
			try {
				gameCanvas = surfaceHolder.lockCanvas(null);
				synchronized(surfaceHolder) {
					calcFrameRate();
					gameView.onDraw(gameCanvas);
				}
			} finally {
				if( gameCanvas!=null ) {
					surfaceHolder.unlockCanvasAndPost(gameCanvas);
				}
			}
		}
	}
}
