package red.wordblocks.game.core;

import java.util.Collections;
import java.util.Iterator;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Debug;
import red.game.display.DisplayComponent;
import red.game.display.DisplayContainer;
import red.wordblocks.config.GameConfig;

public class TileLayer extends DisplayContainer {
	
	@Override
	public void render(Canvas canvas) {
		//Debug.startMethodTracing("tiles");
		//canvas.drawARGB(0, 255, 255, 255);
		super.render(canvas);
		//Debug.stopMethodTracing();
	}
	
	public Tile getAt( int x, int y ) {
		Tile tile = null;
		Iterator<DisplayComponent> it = children.iterator();
		while( it.hasNext() ) {
			Tile t = (Tile)it.next();
			if( t.x == x && t.y == y ) {
				tile = t;
				break;
			}
		}
		return tile;
	}

	public Tile getAtLocalXY(float x, float y) {
		Tile tile = null;
		Iterator<DisplayComponent> it = children.iterator();
		while( it.hasNext() ) {
			Tile t = (Tile)it.next();
			if( t.pivot.x < x && x < t.pivot.x + t.w 
				&& t.pivot.y < y && y < t.pivot.y + t.h ) {
				tile = t;
				break;
			}
		}
		return tile;
	}
	
	/*
	 * Get the 4 corners/wayward tiles from the given theTile
	 * 
	 * Though minX implies the min from theTile, the logic to compute it reverses, 
	 * because we are looking for the tile with max X towards the left.
	 * Similarly maxX implies we should look for min Tile on the right side.
	 */
	public Tile[] get4sides(Tile theTile) {

		int minY = -1000, minX = -1000, maxY = 1000, maxX = 1000;
		Tile minYTile=null, minXTile=null, maxYTile=null, maxXTile=null;
		
		Iterator<DisplayComponent> it = children.iterator();
		while( it.hasNext() ) {
			Tile tile = (Tile)it.next();
			if( tile.x == theTile.x ) {
				if( minY < tile.y && tile.y < theTile.y ) {
					minY = tile.y;
					minYTile = tile;
				}
				if( maxY > tile.y && tile.y > theTile.y ) {
					maxY = tile.y;
					maxYTile = tile;
				}
			}
			if( tile.y == theTile.y ) {
				if( minX < tile.x && tile.x < theTile.x ) {
					minX = tile.x;
					minXTile = tile;
				}
				if( maxX > tile.x && tile.x > theTile.x ) {
					maxX = tile.x;
					maxXTile = tile;
				}
			}
			
		}
		
		return new Tile[] { minYTile, maxXTile, maxYTile, minXTile }; 
	}
	
	
	public int[] get4bounds(Tile theTile) {
		
		int minY = 0, minX = 0, maxY = h - GameConfig.TILE_HEIGHT, maxX = w - GameConfig.TILE_WIDTH;
		Tile minYTile=null, minXTile=null, maxYTile=null, maxXTile=null;
		
		Iterator<DisplayComponent> it = children.iterator();
		while( it.hasNext() ) {
			Tile tile = (Tile)it.next();
			if( tile.x == theTile.x ) {
				if( minY < tile.y && tile.y < theTile.y ) {
					minY = tile.y;
					minYTile = tile;
				}
				if( maxY > tile.y && tile.y > theTile.y ) {
					maxY = tile.y;
					maxYTile = tile;
				}
			}
			if( tile.y == theTile.y ) {
				if( minX < tile.x && tile.x < theTile.x ) {
					minX = tile.x;
					minXTile = tile;
				}
				if( maxX > tile.x && tile.x > theTile.x ) {
					maxX = tile.x;
					maxXTile = tile;
				}
			}
			
		}
		
		
//		minX = fourSides[LEFT]==null ? 0 : fourSides[LEFT].pivot.x + fourSides[LEFT].w; 			//0, max-1
//		maxX = fourSides[RIGHT]==null ? tiles.w - GameConfig.TILE_WIDTH : fourSides[RIGHT].pivot.x - GameConfig.TILE_WIDTH;
//		minY = fourSides[TOP]==null ? 0 : fourSides[TOP].pivot.y + fourSides[TOP].h;
//		maxY = fourSides[BOTTOM]==null ? tiles.h - GameConfig.TILE_HEIGHT : fourSides[BOTTOM].pivot.y - GameConfig.TILE_HEIGHT;
		
		return new int[] { minY, maxX, maxY, minX }; 
	}

	public int size() {
		return this.children.size();
	}

	public void clear() {
		children.clear();
	}

	public void moveFront(Tile activeTile) {
		Collections.swap(children, children.indexOf(activeTile), size()-1 );
	}
}
