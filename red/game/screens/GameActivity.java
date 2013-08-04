/**
 * The Game Activity
 * 
 */
package red.game.screens;

import red.wordblocks.R;
import red.wordblocks.screens.WordGameView;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class GameActivity extends Activity {
	
	protected GameView gameView;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setup();
        setContentView(gameView);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
	
	public void setup() {
		gameView = new GameView(this);
	}
}
