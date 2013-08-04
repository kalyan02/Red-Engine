package red.game.display;

import java.util.ArrayList;
import java.util.Iterator;

import red.wordblocks.game.core.Tile;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Debug;

/*
 * TODO: Refactor DisplayComponent into DisplayContainer
 *  - that will allow multiple levels of children 
 *  - but performance may be affected;
 */
public class DisplayContainer extends DisplayComponent {
	protected ArrayList<DisplayComponent> children;
	
	public DisplayContainer() {
		children = new ArrayList<DisplayComponent>();
	}

	public DisplayContainer( int w, int h ) {
		super(w,h);
		children = new ArrayList<DisplayComponent>();
		
//		this.w = w;
//		this.h = h;
//		
//		bitmapCache = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
//		canvas = new Canvas(bitmapCache);		
//		bitmapDefault = Bitmap.createBitmap(bitmapCache);
	}

	public void add( DisplayContainer obj ) {
		children.add(obj);
		setDirty(true);
	}
	public void add( DisplayComponent obj ) {
		children.add(obj);
		setDirty(true);
	}
	public void add( DisplayComponent obj, boolean link) {
		add(obj);
		if( link )
			obj.parent = this;
	}

	public void render(Canvas canvas) { 
		//if(!isDirty()) {
			Iterator<DisplayComponent> items = children.iterator();
			//canvas.drawBitmap(bitmapDefault, 0, 0, null);
			while( items.hasNext() ) {
				DisplayComponent item = items.next();
				//Debug.startMethodTracing(item.name);
				item.render(canvas);
				//Debug.stopMethodTracing();
				//canvas.drawBitmap( item.render(), item.pivot.x, item.pivot.y, null);
			}
			
			Paint p = new Paint();
			p.setStyle(Paint.Style.STROKE);
			p.setColor(Color.rgb(255, 0, 0));
			canvas.drawRect( _x(0), _y(0), _x(w), _y(h), p);
			
		//}
		//return bitmapCache;
	}
}
