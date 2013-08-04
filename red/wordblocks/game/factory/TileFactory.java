package red.wordblocks.game.factory;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import red.game.display.DisplayContainer;
import red.wordblocks.config.GameConfig;
import red.wordblocks.game.core.Tile;
import red.wordblocks.game.core.TileLayer;

public class TileFactory {
	public static Tile createTile( int x, int y, char c ) {
		
		Tile t = new Tile();

		t.init( GameConfig.TILE_WIDTH, GameConfig.TILE_HEIGHT, new Point(x * GameConfig.TILE_WIDTH, y * GameConfig.TILE_HEIGHT) );
		t.c = c;
		t.x = x;
		t.y = y;

		return t;
	}

	/**
	 * 
	 * @param tiles
	 * @param f probability of tile vs blank
	 */
	public static void populateTiles(TileLayer tiles, float alphaTileProbability) {
		String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random randomGenerator = new Random( System.currentTimeMillis() );
		
		tiles.clear();
		
		for( int i=0; i<GameConfig.TILE_X; i++ ) {
			for( int j=0; j<GameConfig.TILE_Y; j++ ) {
				if( randomGenerator.nextFloat() > alphaTileProbability ) {
					char c = allChars.charAt( randomGenerator.nextInt(allChars.length()) );
					tiles.add( createTile(i, j, c), true );
				}
			}
		}

	}

}
