package red.wordblocks.game.core;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Interpolator;
import android.graphics.Matrix;
import android.graphics.Point;
import android.location.Address;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import red.game.core.PlayStateManager;
import red.game.core.PlayState;
import red.game.core.GameStage;
import red.game.display.DisplayContainer;
import red.game.display.factory.DisplayFactory;

import red.wordblocks.R;
import red.wordblocks.config.GameConfig;
import red.wordblocks.game.factory.TileFactory;
import red.wordblocks.game.mode.GMTilePick;
import red.wordblocks.game.tween.TileTween;

import red.game.screens.GameView;
import red.game.tween.*;
import red.game.util.ResourceManager;
import red.game.wordblocks.util.NumberUtil;

public class WordStage extends GameStage {
	// DisplayManager display;
	// GameView gameView;
	
	TileLayer tiles;
	BackgroundLayer backgroundLayer;
	InfoLayer infoLayer;
	TileUnderlay underlay;
	TileOverlay overlay;
	
	MotionEvent activeEvent;

	int w, h;
	
	public WordStage(GameView gameView) {
		super(gameView);
		
		initStageDimensions();
		initGameResources(gameView);
		initDisplayLayers();
		
		PlayStateManager.init(new GMTilePick(this,tiles));
	}

	private void initDisplayLayers() {
		tiles = DisplayFactory.create(TileLayer.class, display.w, display.h);
		tiles.w = GameConfig.TILE_X * GameConfig.TILE_WIDTH;
		tiles.h = GameConfig.TILE_Y * GameConfig.TILE_HEIGHT;
		tiles.pivot.x = ( display.w - tiles.w ) / 2;
		tiles.pivot.y = ( display.h - tiles.h - GameConfig.BORDER_OFFSET);

		TileFactory.populateTiles( tiles, 0.3f );		
		
		backgroundLayer = DisplayFactory.create( BackgroundLayer.class, display.w, display.h );
		infoLayer = DisplayFactory.create( InfoLayer.class, display.w, display.h );
		underlay = DisplayFactory.create( TileUnderlay.class, display.w, display.h );
		underlay.setTilesPivot(tiles.pivot);
		overlay = DisplayFactory.create( TileOverlay.class, display.w, display.h );
		
		display.add( backgroundLayer );
		display.add( infoLayer );
		display.add( underlay );
		display.add( tiles );
		display.add( overlay );
	}

	private void initGameResources(GameView gameView) {
		/*
		 * TODO: Create ThemeManager, Theme() which parses the res types from XML config!
		 */
		
		Tile.bitmapActive = BitmapFactory.decodeResource( gameView.getResources(), R.drawable.tile_active );
		Tile.bitmapNormal = BitmapFactory.decodeResource( gameView.getResources(), R.drawable.tile_normal );
		BackgroundLayer.bitmapBackground = BitmapFactory.decodeResource( gameView.getResources(), R.drawable.background );
		TileUnderlay.imgArrow = BitmapFactory.decodeResource( gameView.getResources(), R.drawable.arrow );
		TileUnderlay.imgTarget = BitmapFactory.decodeResource( gameView.getResources(), R.drawable.target );
		TileUnderlay.imgStart = BitmapFactory.decodeResource( gameView.getResources(), R.drawable.start );
		TileUnderlay.imgDots = BitmapFactory.decodeResource( gameView.getResources(), R.drawable.dots );
	}

	private void initStageDimensions() {
		GameConfig.TILE_WIDTH = (int) Math.floor( (double)(display.w - 2*GameConfig.BORDER_OFFSET) / GameConfig.TILE_X );
		GameConfig.TILE_HEIGHT = GameConfig.TILE_WIDTH;
		
		GameConfig.TILE_Y = (int) Math.floor( (display.h - GameConfig.TOP_OFFSET_MIN) / GameConfig.TILE_HEIGHT );
		GameConfig.TILE_Y = NumberUtil.clamp( GameConfig.TILE_Y, GameConfig.TILE_X, GameConfig.TILE_Y);
	}

	//TODO: Move it into GameView
	public void onTouch(MotionEvent event) {
		activeEvent = event;
		if( !PlayStateManager.isLocked() ) {
			if( event.getAction() == MotionEvent.ACTION_DOWN ) {
				PlayStateManager.activeState().onTouchStart(event);
			}
			if( event.getAction() == MotionEvent.ACTION_UP ) {
				PlayStateManager.activeState().onTouchEnd(event);
			}
			if( event.getAction() == MotionEvent.ACTION_MOVE) {
				PlayStateManager.activeState().onTouchMove(event);
			}
		}
	}
	
	public TileLayer getTilesLayer() { return tiles; }
	public TileUnderlay getTileUnderlay() { return underlay; }
	public TileOverlay getTileOverlay() { return overlay; }
}


//ThemeManager.load( new DefaultTheme() );
//class Theme {
//	Dictionary<String, Integer> resources;
//	//public int get(string)
//}
//class ThemeManager {
//	Theme instance;
//	public void load( Theme theme ) {
//		instance = theme;
//	}
//	public int get(String key) {
//		
//	}
//}
//
