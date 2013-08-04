package red.wordblocks.game.core;

import java.util.ArrayList;
import java.util.Iterator;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import red.game.display.DisplayComponent;
import red.game.display.DisplayContainer;

public class TileOverlay extends DisplayComponent {
	
	ArrayList<Tile> wordTiles = null;
	public TileOverlay() {
//		name = "overlay";
	}
	protected void render(Canvas canvas) {
		
		try {
		Paint overlayColor = new Paint();
		overlayColor.setColor(Color.RED);
		overlayColor.setStyle(Paint.Style.STROKE);
		
		
		if( wordTiles!=null && wordTiles.size()>0 ) {
			Iterator<Tile> it = wordTiles.iterator();
			Tile lastTile = null;
			while( it.hasNext() ) {
				Tile t = it.next();
				canvas.drawRect( new Rect( t.x(-3), t.y(-3), t.x(t.w+3), t.y(t.h+3)), overlayColor);
				
				if( lastTile!=null ) {
					//Draw a triangle pointer
					Point pointerCenter = new Point();
					pointerCenter.set( ( lastTile.x() + t.x() ) / 2, ( lastTile.y() + t.y() ) / 2 );
					
					Point pointerDirection = new Point();
					Point pointerHeading = new Point();
					
					Path pointer = new Path();
					pointer.moveTo(-3, -1);
					pointer.lineTo(0, 3);
					pointer.lineTo(3, -1 );
					pointer.lineTo(-3, -1);
					
					Matrix mat = new Matrix();
					if( lastTile.x == t.x ) {
						if( lastTile.y < t.y ) { //Moving up
							mat.postTranslate(pointerCenter.x, pointerCenter.y);
						} else {
							mat.postRotate(180);
							mat.postTranslate(pointerCenter.x, pointerCenter.y);
						}
						
					} else
					if( lastTile.y == t.y ) {
						if( lastTile.x < t.x ) {
							mat.postRotate(90);
							mat.postTranslate(pointerCenter.x, pointerCenter.y);
						} else {
							mat.postRotate(-90);
							mat.postTranslate(pointerCenter.x, pointerCenter.y);
						}
					}
					pointer.transform(mat);
					
					canvas.drawPath( pointer, overlayColor );
				}
				lastTile = t; 
			}
		}
		} catch(Exception e) {
			System.out.print(e);
		}
	}


	public void setOverlayTiles(ArrayList<Tile> wordTiles) {
		this.wordTiles = wordTiles;
	}
}
