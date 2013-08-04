package red.wordblocks.game.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import red.game.display.DisplayComponent;
import red.game.display.DisplayContainer;
import red.game.screens.GameView;
import red.wordblocks.R;
import red.wordblocks.config.GameConfig;

public class BackgroundLayer extends DisplayComponent {
	public static Bitmap bitmapBackground;
	
	private Bitmap backgroundImage;
	GameView view = null;
	
	public BackgroundLayer() {
//		name = "bg";
	}
	
	public void setView(GameView view) {
		this.view = view;
	}
	
	protected void render(Canvas canvas) {
		if( backgroundImage==null ) {
			backgroundImage = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
			Canvas painter = new Canvas(backgroundImage);
			painter.drawBitmap( bitmapBackground, new Rect(0, 0, bitmapBackground.getWidth(), bitmapBackground.getWidth()), new Rect(0, 0, w, h), null );
		}
		canvas.drawBitmap(backgroundImage, 0, 0, null);
			//canvas.drawBitmap( bitmapBackground, new Rect(0, 0, bitmapBackground.getWidth(), bitmapBackground.getWidth()), new Rect(0, 0, w, h), null );
		//}
		super.render(canvas);
	}
}
