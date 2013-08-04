package red.wordblocks.game.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Point;
import android.graphics.Rect;
import red.game.display.DisplayComponent;
import red.wordblocks.config.GameConfig;

public class Tile extends DisplayComponent {
	public char c;
	int score;
	
	public static Bitmap bitmapNormal;
	public static Bitmap bitmapActive;

	Paint paintStyle;
	
	private Bitmap tileNormal, tileActive;
	
	boolean isStatic = false;
	/*
	 * Grid positions
	 */
	public int x;
	public int y;
	
	public boolean isActive = false;
	public boolean isHightligted = false;
	
	public Tile() { 
		super(); 
	}
	
	public Tile( Point pos, char c ) {
		super( GameConfig.TILE_WIDTH, GameConfig.TILE_WIDTH );
		
		this.c = c;
		score = 1;
		pivot = pos;
		
		//name = "tile";
	}
	
	public Tile( int x, int y, char c ) {
		//this( new Point(x, y), c );
		
		w = GameConfig.TILE_WIDTH;
		h = GameConfig.TILE_HEIGHT;

		init( GameConfig.TILE_WIDTH, GameConfig.TILE_WIDTH, new Point(x, y));
	}
	
	@Override
	protected void render(Canvas canvas) {

			Bitmap tileType = null;
			
			//canvas.drawRect(pivot.x, pivot.y, pivot.x+w, pivot.y+h, p);
			if( isHightligted || isActive ) {
				if( tileActive==null ) {
					tileActive = createTileBitmapFrom(bitmapActive);
				}
				tileType = tileActive;
			}
			else {
				if( tileNormal==null ) {
					tileNormal = createTileBitmapFrom(bitmapNormal);					
				}				
				tileType = tileNormal;				
			}
			
			if (tileType != null) {
				canvas.drawBitmap( tileType, _x(0), _y(0), null );
			}
			
			setDirty(false);
	}

	private Bitmap createTileBitmapFrom(Bitmap baseBitmap) {
		Bitmap theTile = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas theTileCanvas = new Canvas(theTile);
		Rect srcRect = new Rect( 0, 0, baseBitmap.getWidth(), baseBitmap.getHeight() );
		Rect dstRect = new Rect( 0, 0, w, h );
		theTileCanvas.drawBitmap(baseBitmap, srcRect, dstRect,null);
		
		Paint tp = new Paint();
		tp.setStyle(Paint.Style.FILL);
		tp.setTextAlign(Align.CENTER);
		tp.setTextSize(h/3);
		tp.setColor(Color.rgb(0, 0, 0));
		tp.setAntiAlias(true);
		
		theTileCanvas.drawText( new Character(c).toString(), w/2, h/2 + 4, tp);
		return theTile;
	}
	
	public char getValue() {
		return c;
	}
	
	public boolean isHighlighted(boolean isIt) {
		isHightligted = isIt;
		
		return isHightligted;
	}
	
	public boolean isHighlighted() {
		return isHightligted;
	}
	
}
