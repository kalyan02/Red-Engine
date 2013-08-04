package red.game.core;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import red.game.display.DisplayContainer;
import red.game.screens.GameView;

public class GameStage {
	
	protected DisplayContainer display;
	protected GameView gameView;
	
	public GameView getGameView() {
		return gameView;
	}

	public GameStage(GameView gameView) {
		System.out.println ( gameView.getWidth() + "::" + gameView.getHeight() );
		
		this.gameView = gameView;
		display = new DisplayContainer( gameView.getWidth(), gameView.getHeight() );
	}
	
	public void drawStage(Canvas canvas) {
		
		Paint p = new Paint();
		p.setStyle(Paint.Style.FILL);
		p.setColor(Color.rgb(200, 200, 200) );
		p.setAntiAlias(true);

		    		
		canvas.drawColor(Color.BLACK);
		display.render(canvas);
			
		canvas.drawText("H:" + canvas.getHeight() + " W:" + canvas.getWidth() + " FPS:" + GameInfo.fps, 10, 10, p);
	}
	
	public void prepStage(Canvas canvas) {
		
	}

	public void onTouch(MotionEvent event) {

		
	}
	
}
