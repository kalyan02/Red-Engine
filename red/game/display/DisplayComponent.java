package red.game.display;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

public abstract class DisplayComponent {
	public DisplayComponent parent=null;
	public boolean isDirty = true;
	public Bitmap bitmapCache = null;
	public Bitmap bitmapDefault = null;
	public Canvas canvas = null;
	
	public Point pivot;
	public int w, h;
	
	//public String name = "c";
	
	public DisplayComponent() {
		
	}
	
	public DisplayComponent( int w, int h ) {
		init(w, h);
	}
	
	public void init( int w, int h ) {
		init( w, h, new Point(0,0) );
	}
	public void init( Point p, int w, int h ) {
		init( w, h, p );
	}
	public void init( int w, int h, Point p ) {
		pivot = p;
		
		this.w = w;
		this.h = h;
		this.isDirty = true;
		
//		bitmapCache = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
//		canvas = new Canvas(bitmapCache);		
//		bitmapDefault = Bitmap.createBitmap(bitmapCache);	
	}
	
	public void setDefaultBitmap(Bitmap defaultBitmap) {
		this.bitmapDefault = Bitmap.createBitmap(defaultBitmap);
	}
	
	protected void render(Canvas canvas) {	
		//return bitmapCache;
	}
	public boolean isDirty() { return isDirty; }
	public void setDirty(boolean isDirty) { this.isDirty = isDirty; }
	public int getWidth() { return w; }
	public int getHeight() { return h; }
	
	
	/*
	 * Some utility methods to get x() and y();
	 * 
	 * Should probably get rid of _x() and _y() or make them protected;
	 */
	public int _x(int x) { 
		if( parent!= null )
			return parent._x( pivot.x + x );
		return pivot.x + x;
	}
	
	public int _y(int y) {
		if( parent!=null )
			return parent._y( pivot.y + y );
		return pivot.y + y;
	}
	public int x() { return _x(0); }
	public int y() { return _y(0); }
	
	public int x(int ox) { return _x(ox); }
	public int y(int oy) { return _y(oy); }
	
	
	
}
