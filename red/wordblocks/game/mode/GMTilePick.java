package red.wordblocks.game.mode;

import java.util.Vector;

import android.content.Context;
import android.graphics.Point;
import android.os.Vibrator;
import android.view.MotionEvent;
import red.game.core.PlayState;
import red.game.core.PlayStateManager;
import red.game.display.DisplayContainer;
import red.game.geom.Point2;
import red.game.tween.Tween;
import red.game.tween.TweenEventAdapter;
import red.game.wordblocks.util.Dir;
import red.game.wordblocks.util.NumberUtil;
import red.wordblocks.config.GameConfig;
import red.wordblocks.game.core.WordStage;
import red.wordblocks.game.core.Tile;
import red.wordblocks.game.core.TileLayer;
import red.wordblocks.game.core.TileUnderlay;
import red.wordblocks.game.tween.TileTween;

public class GMTilePick extends PlayState {
	WordStage stage;
	TileLayer tiles;
	TileUnderlay underlay;
	
	Tile activeTile;
	Point activePivot;
	
	final int LEFT = 3;
	final int TOP = 0;
	final int RIGHT = 1;
	final int BOTTOM = 2;
	
	Point touchStart = new Point(), touchCurrent = new Point(), touchEnd = new Point();
	
	//int canMove;
	Dir dir = new Dir();
	
	boolean hasTileBeenDisturbed = false;
	
	Tile fourSides[] = null;
	
	public GMTilePick(WordStage stage, TileLayer tiles) {
		this.stage = stage;
		this.tiles = tiles;
		
		this.underlay = stage.getTileUnderlay();
	}
	
	int minX, minY, maxX, maxY;
	
	@Override
	public void onTouchStart(MotionEvent event) {
		touchStart = getTilesPoint(event);
		
		Tile t = tiles.getAtLocalXY(touchStart.x, touchStart.y);
		
		if( t!= null ) {
			activeTile = t;
			activeTile.isDirty = true;
			activeTile.isActive = true;
			
			activeTile.isHighlighted(true);
			tiles.moveFront(activeTile);
			
//			Context ctx = stage.getGameView().getContext();
//			Vibrator v = (Vibrator)ctx.getSystemService(Context.VIBRATOR_SERVICE);
//			v.vibrate(10);
			
			// Store the current pivot state
			activePivot = Point2.clone(activeTile.pivot);
			
			// Get 4 neighbours
			//if( fourSides == null )
			fourSides = tiles.get4sides(activeTile);
			
			// Compute the max and min x, y points 
			minX = fourSides[LEFT]==null ? 0 : fourSides[LEFT].pivot.x + fourSides[LEFT].w; 			//0, max-1
			maxX = fourSides[RIGHT]==null ? tiles.w - GameConfig.TILE_WIDTH : fourSides[RIGHT].pivot.x - GameConfig.TILE_WIDTH;
			minY = fourSides[TOP]==null ? 0 : fourSides[TOP].pivot.y + fourSides[TOP].h;
			maxY = fourSides[BOTTOM]==null ? tiles.h - GameConfig.TILE_HEIGHT : fourSides[BOTTOM].pivot.y - GameConfig.TILE_HEIGHT;
			
			//TODO:Test accuracy and remove
			//int fourBounds[] = tiles.get4bounds(activeTile);
			
			underlay.showHelper( activeTile, activePivot, minX, minY, maxX, maxY, dir.ALL );
		}

	}

	@Override
	public void onTouchMove(MotionEvent event) {
		touchCurrent = getTilesPoint(event);
		
		int abs_dx, dx = touchCurrent.x - touchStart.x;
		int abs_dy, dy = touchCurrent.y - touchStart.y;
		
		abs_dx = dx < 0 ? -dx : dx;
		abs_dy = dy < 0 ? -dy : dy;
		
		boolean canSelect = true;
		
		int threshold_dx = GameConfig.TILE_WIDTH/5;
		int threshold_dy = GameConfig.TILE_HEIGHT/5;
		
		if( activeTile!=null ) {
			
			if( abs_dx > threshold_dx || abs_dy > threshold_dy ) {
				// dir.dir need not be 0, as if the tile is near its starting point, reset direction!!!
				//if( dir.dir == 0 || true ) {
				
				int the_dir = dir.dir;
				if( abs_dx > abs_dy ) {
					dir.reset( dx < 0 ? dir.LEFT : dir.RIGHT );		// now moving horizontally
					activeTile.pivot.y = activePivot.y;				// Reset y coords
				}
				else {
					dir.reset( dy > 0 ? dir.BOTTOM : dir.TOP );		// moving vertically
					activeTile.pivot.x = activePivot.x;				// Reset x coords
				}
				
				
				hasTileBeenDisturbed = true;
				underlay.showHelper( activeTile, activePivot, minX, minY, maxX, maxY, dir.dir );
					
				//}
			} else {
				// direction reset
				underlay.showHelper( activeTile, activePivot, minX, minY, maxX, maxY, dir.ALL );
			}
			
			
			// Compute the offset from tile to touch start, so that tile's edge doesn't snap to current position
			// Gives better feel of holding the tile rather than an edge
			// Check for Finger moving "onto another tile"
			boolean isFingerMovingOut = false;
			Dir movingOutDir = new Dir();
			if( dir.has(dir.LEFT|dir.RIGHT) ) {
				int x = touchCurrent.x - (touchStart.x - activePivot.x);
				activeTile.pivot.x = NumberUtil.clamp(x, minX, maxX);
				
				if( x < minX ) { movingOutDir.set(Dir.LEFT); }
				if( x > maxX ) { movingOutDir.set(Dir.RIGHT); }
				
			}
			if( dir.has(dir.TOP|dir.BOTTOM) ) {
				int y = touchCurrent.y - (touchStart.y - activePivot.y);
				activeTile.pivot.y = NumberUtil.clamp(y, minY, maxY);
				
				if( y < minY ) { movingOutDir.set(Dir.TOP); }
				if( y > maxY ) { movingOutDir.set(Dir.BOTTOM); }
			}
			
			// Most likely, finger is moving out AND tile hasn't been disturbed
			// Switch Game Mode
			if( movingOutDir.has() ) {
				int shift_x = activePivot.x - activeTile.pivot.x; shift_x *= shift_x < 0 ? -1 : 1;
				int shift_y = activePivot.y - activeTile.pivot.y; shift_y *= shift_y < 0 ? -1 : 1;
				
				// To switch game mode, the tile shouldn't have moved
				if( shift_x < GameConfig.TILE_WIDTH/2 && shift_y < GameConfig.TILE_HEIGHT/2 ) {
					underlay.hideHelper();
					PlayStateManager.init( new GMWordPick(stage,activeTile,movingOutDir) );
				}
			}			
		} else {
			//Tile t = tiles.getAtLocalXY(touchCurrent.x, touchCurrent.y);
			//if( t!=null ) {
				onTouchStart(event);
			//}
		}
	}
	
	TileTween tweener;
	public void onTouchEnd(MotionEvent event) {
		super.onTouchEnd(event);
		
		if( activeTile!=null ) {
			touchEnd = getTilesPoint(event);

			// Do we have significant tile displacement ?
			// If not don't move
			if( Math.abs(touchStart.x - touchEnd.x) > GameConfig.TILE_WIDTH*2/3 || Math.abs(touchStart.y - touchEnd.y) > GameConfig.TILE_HEIGHT*2/3 ) { 
				Point newXY = new Point();
				newXY.x = activeTile.x;
				newXY.y = activeTile.y;
				
				if( dir.has(dir.LEFT) ) { newXY.x = minX/GameConfig.TILE_WIDTH; }
				else
				if( dir.has(dir.RIGHT) ) { newXY.x = maxX/GameConfig.TILE_WIDTH; }
				else
				if( dir.has(dir.TOP) ) { newXY.y = minY/GameConfig.TILE_HEIGHT; }
				else
				if( dir.has(dir.BOTTOM) ) { newXY.y = maxY/GameConfig.TILE_HEIGHT; }
				
				Point newPivot = new Point();
				newPivot.x = newXY.x * GameConfig.TILE_WIDTH;
				newPivot.y = newXY.y * GameConfig.TILE_HEIGHT;
				
				tweener = new TileTween(activeTile, newXY, 500, 30, dir.has(Dir.TOP|Dir.BOTTOM) ? TileTween.VERT : TileTween.HORIZ );
				tweener.start();
			} else {
				Point newXY = new Point();
				newXY.x = activeTile.x;
				newXY.y = activeTile.y;
				
				// Tween back for a nice effect!
				// TODO: might be perf intensive. check.
				tweener = new TileTween(activeTile, newXY, 50, 3, dir.has(Dir.TOP|Dir.BOTTOM) ? TileTween.VERT : TileTween.HORIZ );
				tweener.start();
			}
			
			resetState();
		}
	}

	private Point getTilesPoint(MotionEvent event) {
		Point touchPoint = new Point();
		touchPoint.x = (int) event.getX() - tiles.pivot.x;
		touchPoint.y = (int) event.getY() - tiles.pivot.y;
		return touchPoint;
	}

	private void resetState() {
		// Reset
		dir.reset();
		activeTile.isDirty = true;
		activeTile.isActive = false;
		activeTile.isHighlighted(false);
		fourSides = null;
		activeTile = null;
		activePivot = null;
		//canMove = 0;
		underlay.hideHelper();
		hasTileBeenDisturbed = false;
	}
	
	/*
	 * @see red.game.core.PlayState#canChange()
	 */
	@Override
	public boolean canChange() {
		return false;
	}

}

