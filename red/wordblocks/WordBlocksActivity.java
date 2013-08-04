/**
 * The Game
 * 
 * @author kchakravarthy
 */
package red.wordblocks;

import red.game.screens.GameActivity;
import red.wordblocks.screens.SplashActivity;
import red.wordblocks.screens.WordGameActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

public class WordBlocksActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Intent splashIntent = new Intent(WordBlocksActivity.this,SplashActivity.class);
        Intent splashIntent = new Intent(WordBlocksActivity.this,WordGameActivity.class);
        startActivity(splashIntent);
        
        finish();
    }
}