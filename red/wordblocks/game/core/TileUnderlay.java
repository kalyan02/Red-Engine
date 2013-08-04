package red.wordblocks.game.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Debug;
import red.game.display.DisplayComponent;
import red.game.display.DisplayContainer;
import red.game.wordblocks.util.Dir;
import red.wordblocks.config.GameConfig;
import red.wordblocks.game.tween.HelperOpacityTween;

public class TileUnderlay extends DisplayComponent {
	
	public static Bitmap imgArrow=null;
	public static Bitmap imgStart=null;
	public static Bitmap imgTarget=null;
	public static Bitmap imgDots=null;
	
	private static int ARROW=0;
	private static int START=1;
	private static int TARGET=2;
	private static int DOTS=3;
	
	private Bitmap[][] bitmaps;
	private Bitmap[] baseBitmaps;
	
	private int minX, minY, maxX, maxY;
	private Dir dir, lastDir;
	private Point activePivot;
	private Tile activeTile;
	private Point tilePivot;
	
	public boolean isActive;
	public int leftOp = 0, leftOpTarget=255;
	public int rightOp = 0, rightOpTarget=(255);
	public int bottomOp = 0, bottomOpTarget=(255);
	public int topOp = 0, topOpTarget=(255);
	
	HelperOpacityTween opacityTweener;
	
	public TileUnderlay() {
//		name = "underlay";
		isActive = false;
		dir = new Dir();
		lastDir = new Dir();
		
		tilePivot = new Point();
		tilePivot.set(0, 0);
		
		//opacityTweener = new HelperOpacityTween(this, 0, 255, 2000, 20);
		
		bitmaps = new Bitmap[4][4];
		for(int i=0; i<bitmaps.length;i++) {
			for( int j=0; j<bitmaps[i].length; j++ )
				bitmaps[i][j] = null;
		}
	}
	
	protected void render(Canvas canvas) {
		
		
		
		if( !isActive || imgArrow==null || imgStart==null || imgTarget==null )
			return;
		
//Debug.startMethodTracing("underlay");
		
		int diff = 0, stepX, stepY;
		
		
		Rect dst = new Rect( tilePivot.x + activePivot.x, 
								tilePivot.y + activePivot.y, 
								tilePivot.x + activePivot.x + activeTile.w,
								tilePivot.y + activePivot.y + activeTile.h );
		
		
//		Paint alphaPaint = new Paint();
//		alphaPaint.setAlpha( maxOp() );
		
		Rect src = new Rect( 0, 0, imgArrow.getWidth(), imgArrow.getHeight() );
//		canvas.drawBitmap(imgStart, src, dst, null );
		
	
		int tileW = GameConfig.TILE_WIDTH;
		int tileH = GameConfig.TILE_HEIGHT;
		
		if( dir.has(dir.LEFT) || dir.has(dir.RIGHT) ) {
			for( int x=minX; x<=maxX; x+= tileW ) {
				// First => target
				int theDir = 0;
				Paint theAlphaPaint = new Paint();
				if( x < activePivot.x ) {
					theDir = dir.LEFT;
//					theAlphaPaint.setAlpha(leftOp);
				}
				if( x > activePivot.x ) {
					theDir = dir.RIGHT;
//					theAlphaPaint.setAlpha(rightOp);
				}

				// Disabled as we have alpha tween
				//if( dir.has(dir.LEFT) && !dir.has(dir.RIGHT) && x > activePivot.x ) continue;
				//if( !dir.has(dir.LEFT) && dir.has(dir.RIGHT) && x < activePivot.x ) continue;
				
				if( x==activePivot.x ) {
					canvas.drawBitmap( imgStart, src, getTileRect(x, activePivot.y), null );
				}
				else
				if( x==minX ) {
					canvas.drawBitmap( getBitmap(TARGET,dir.LEFT), src, getTileRect(x, activePivot.y), null );
				}
				else
				if( x==maxX ) {
					canvas.drawBitmap( getBitmap(TARGET,dir.RIGHT), src, getTileRect(x, activePivot.y), null );
				}
//				else
//				if( x == activePivot.x-tileW || x == activePivot.x+tileW ) {
//					canvas.drawBitmap( getBitmap(ARROW,theDir), src, getTileRect(x, activePivot.y), theAlphaPaint );
//				}
				else {
					canvas.drawBitmap( getBitmap(DOTS,theDir), src, getTileRect(x, activePivot.y), null );
				}
			}
		}
		
		if( dir.has(dir.TOP) || dir.has(dir.BOTTOM) ) {
			for( int y=minY; y<=maxY; y+=tileH ) {
				// First => target
				int theDir = 0;
				Paint theAlphaPaint = new Paint();
				if( y < activePivot.y ) {
					theDir = dir.TOP;
					theAlphaPaint.setAlpha(topOp);
				}
				if( y > activePivot.y ) {
					theDir = dir.BOTTOM;
					theAlphaPaint.setAlpha(bottomOp);
				}

			
				if( dir.has(dir.TOP) && !dir.has(dir.BOTTOM) && y > activePivot.y ) continue;
				if( !dir.has(dir.TOP) && dir.has(dir.BOTTOM) && y < activePivot.y ) continue;
				
				if( y==activePivot.y ) {
					canvas.drawBitmap( imgStart, src, getTileRect(activePivot.x, y), null );
				}
				else
				if( y==minY ) {
					canvas.drawBitmap( getBitmap(TARGET,dir.TOP), src, getTileRect(activePivot.x, y), null );
				}
				else
				if( y==maxY ) {
					canvas.drawBitmap( getBitmap(TARGET,dir.BOTTOM), src, getTileRect(activePivot.x, y), null );
				}
				//else
				//if( y == activePivot.y-tileW || y == activePivot.y+tileW ) {
				//	canvas.drawBitmap( getBitmap(ARROW,theDir), src, getTileRect(activePivot.x, y), null );
				//}
				else {
					canvas.drawBitmap( getBitmap(DOTS,theDir), src, getTileRect(activePivot.x, y), null );
				}
			}
		}
		
		//Debug.stopMethodTracing();
	}

	private Bitmap getBitmap(int type, int dir) {
		int dirOffsetMap[] = { 0,      0,     1, 0,      2, 0, 0, 0,    3, 0, 0, 0, 0, 0, 0, 0, 0 };
		int dirOffset = dirOffsetMap[ dir ];
		
		Bitmap baseBitmap = imgStart;
		
		//Starting point is not dependent on rotation
		if( type == START )
			return imgStart;
		
		if( bitmaps[type][dirOffset] == null ) {
			if( type == ARROW )
				baseBitmap = imgArrow;
			if( type == TARGET )
				baseBitmap = imgTarget;
			if( type == START )
				baseBitmap = imgStart;
			if( type == DOTS )
				baseBitmap = imgDots;
			
			int rotateBy = 0;
			if( dir == Dir.LEFT ) rotateBy = 0;
			if( dir == Dir.TOP ) rotateBy = 90;
			if( dir == Dir.RIGHT ) rotateBy = 180;
			if( dir == Dir.BOTTOM ) rotateBy = 270;
			
			int baseW = baseBitmap.getWidth();
			int baseH = baseBitmap.getHeight();
					
			Bitmap reqBitmap = Bitmap.createBitmap( baseW, baseH, baseBitmap.getConfig() );
			Canvas rotStage = new Canvas(reqBitmap);
			Matrix mat = new Matrix();			
			mat.setRotate( rotateBy, baseW/2, baseH/2 );
			rotStage.drawBitmap(baseBitmap, mat, null);
			
			bitmaps[type][dirOffset] = reqBitmap;
		}
		
		return bitmaps[type][dirOffset];
	}
	
	private Rect getTileRect(int x, int y) {
		x += tilePivot.x;
		y += tilePivot.y;
		return new Rect( x, y, x + GameConfig.TILE_WIDTH, y + GameConfig.TILE_HEIGHT );
	}
	private Rect moveRect(Rect r, int x, int y) {
		return new Rect( r.left + x, r.top + y, r.right + x, r.bottom + y );
		//return r;
	}

	public void hideHelper() {
		isActive = false;
		lastDir.dir = 0;
		
//		setTopOpTarget(0);
//		setBottomOpTarget(0);
//		setLeftOpTarget(0); 
//		setRightOpTarget(0);
//		
//		opacityTweener.unTween();
	}
	
	public void showHelper(Tile activeTile, Point activePivot, int minX,
			int minY, int maxX, int maxY, int dir_val) {
		
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.dir = new Dir(dir_val);
		this.activeTile = activeTile;
		this.activePivot = activePivot;
		this.isActive = true;
		
//		if( dir.dir != lastDir.dir ) {
//			//retween
//			if( dir.has(dir.ALL) ) {
//				setTopOpTarget(255);
//				setBottomOpTarget(255);
//				setLeftOpTarget(255); 
//				setRightOpTarget(255);
//			} 
//			else {
//				if( dir.has(dir.RIGHT) )
//					setRightOpTarget(255);
//				else
//					setRightOpTarget(0);
//				
//				if( dir.has(dir.LEFT) )
//					setLeftOpTarget(255);
//				else
//					setLeftOpTarget(0);
//				
//				if( dir.has(dir.BOTTOM) )
//					setBottomOpTarget(255);
//				else
//					setBottomOpTarget(0);
//				
//				if( dir.has(dir.TOP) )
//					setTopOpTarget(255);
//				else
//					setTopOpTarget(0);
//			}
//			
//			opacityTweener.reTween(true);
//		}
		
//		lastDir.dir = dir.dir;
	}
	
	private int maxOp() {
		int g1 = leftOp > rightOp ? leftOp : rightOp;
		int g2 = topOp > bottomOp ? topOp : bottomOp;
		return g1 > g2 ? g1 : g2;
	}

	public void setTilesPivot(Point pivot) {
		this.tilePivot = new Point();
		this.tilePivot.set(pivot.x, pivot.y);
	}

	public synchronized int getLeftOpTarget() {
		return leftOpTarget;
	}

	public synchronized void setLeftOpTarget(int leftOpTarget) {
		this.leftOpTarget = leftOpTarget;
	}

	public synchronized int getRightOpTarget() {
		return rightOpTarget;
	}

	public synchronized void setRightOpTarget(int rightOpTarget) {
		this.rightOpTarget = rightOpTarget;
	}

	public synchronized int getBottomOpTarget() {
		return bottomOpTarget;
	}

	public synchronized void setBottomOpTarget(int bottomOpTarget) {
		this.bottomOpTarget = bottomOpTarget;
	}

	public synchronized int getTopOpTarget() {
		return topOpTarget;
	}

	public synchronized void setTopOpTarget(int topOpTarget) {
		this.topOpTarget = topOpTarget;
	}

	
}
