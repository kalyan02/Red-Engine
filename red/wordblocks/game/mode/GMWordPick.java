package red.wordblocks.game.mode;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Point;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.widget.Toast;
import red.game.core.PlayState;
import red.game.core.PlayStateManager;
import red.game.wordblocks.util.Dir;
import red.game.wordblocks.util.Util;
import red.wordblocks.game.core.WordStage;
import red.wordblocks.game.core.Tile;
import red.wordblocks.game.core.TileLayer;
import red.wordblocks.game.core.TileOverlay;
import red.wordblocks.game.core.TileUnderlay;
import red.wordblocks.game.tween.TileTween;

/*
 * Should extend GMTilePick
 */
public class GMWordPick extends PlayState {

	List<Tile> wordTiles;
	WordStage stage;
	TileLayer tiles;
	TileUnderlay underlay;
	TileOverlay overlay;
	
	Tile  lastTile = null;
	Point lastTilePivot = new Point();
	Point touchCurrent = new Point();
	
	public GMWordPick(WordStage stage, Tile activeTile, Dir movingOutDir) {
		wordTiles = new ArrayList<Tile>();
		wordTiles.add(activeTile);
		this.stage = stage;
		this.tiles = stage.getTilesLayer();
		this.underlay = stage.getTileUnderlay();
		this.overlay = stage.getTileOverlay();
		
		lastTile = activeTile;
		lastTilePivot.set(activeTile.x(), activeTile.y());
	}
	
	@Override
	public void onTouchEnd(MotionEvent event) {
		// On mouse up. Get the word and gtfo
		if( wordTiles!=null && wordTiles.size()>1 ) {
			Context ctx = stage.getGameView().getContext();
			StringBuilder str = new StringBuilder();
			
			
			for( int i=0; i<wordTiles.size(); i++ ) {
				wordTiles.get(i).isActive = false;
				str.append( wordTiles.get(i).getValue() );
			}
			
			CharSequence charStr = "Your Word:" + str;
			Toast.makeText(ctx, str, 5).show();
		}
		
		for( int i=0; i<wordTiles.size(); i++ ) {
			//if( lastTile!=null )
			//	lastTile.isHighlighted(false);
			Tile t = wordTiles.get(i);
			t.isHighlighted(false);
			t.isActive = false;
		}
		
		wordTiles = null;
		overlay.setOverlayTiles(null);
		
		
		
		// Remove thyself
		PlayStateManager.pop();
		// Propagate back
		PlayStateManager.activeState().onTouchEnd(event);
		
		
	}
	
	@Override
	public void onTouchMove(MotionEvent event) {
		
		touchCurrent = new Point();
		touchCurrent.x = (int) event.getX() - tiles.pivot.x;
		touchCurrent.y = (int) event.getY() - tiles.pivot.y;
		
		// If we moved onto a new tile
		if( !Util.isLocalInsideTile(lastTile, touchCurrent) ) {
			Tile t = tiles.getAtLocalXY(touchCurrent.x, touchCurrent.y);
			
			//@Removing: all tiles are always highlighted!!
			//if( lastTile!=null )		
			//	lastTile.isHighlighted(false);
			
			
			if( t!=null && wordTiles!=null ) {
				// Prevent multi touch from ruining the party
				// All tiles should be adjacent to each other
				if( Math.abs(lastTile.x-t.x) <=1 && Math.abs(lastTile.y-t.y) <=1 ) {
					lastTile = t;
					lastTile.isHighlighted(true);
					lastTile.isActive = true;
					
					// Just in case if we want the tile to expand, then render it last!
					tiles.moveFront(lastTile);

					// If its not there in the list, add it
					if( wordTiles.indexOf(t) < 0 ) {
						wordTiles.add(t);
					}
					
					
//					Context ctx = stage.getGameView().getContext();
//					Vibrator v = (Vibrator)ctx.getSystemService(Context.VIBRATOR_SERVICE);
//					v.vibrate(10);
				}
				

				
				// If there is, then reduce the list till there
				if( wordTiles.indexOf(t) >= 0 ) {
					try {
					int index = wordTiles.indexOf(t);
					// anything but not the first one					
					for( int i=index+1; i<wordTiles.size(); i++ ) {
						wordTiles.get(i).isHighlighted(false);
						wordTiles.get(i).isActive = false;
					}
					wordTiles = wordTiles.subList(0, index+1);
					

					} catch( Exception e ) {
						System.out.println(e);
					}
				}
			}

		} else {
			// Still inside last tile. So expand it
			//if( lastTile!=null ) {
			//	lastTile.isHighlighted(true);
			//}
		}
		
		//overlay.setOverlayTiles(wordTiles);
		super.onTouchMove(event);
	}

}
