/**
 * Menu/Splash Activity
 * 
 * @author kchakravarthy
 */
package red.wordblocks.screens;

import red.game.screens.GameActivity;
import red.wordblocks.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;

public class SplashActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_menu);
        
        View view = new View(this);
        view.setBackgroundResource(R.drawable.background);
        
        ImageView titleImage = (ImageView)findViewById(R.id.title_image);
        //titleImage.setLayoutParams(new LayoutParams((int) (view.getWidth() * 0.4), LayoutParams.FILL_PARENT));
        titleImage.setMaxWidth( 100 );
        
        Button gameStart = (Button)findViewById(R.id.button_start);
        gameStart.setOnClickListener( new OnStartClickListener() );        
    }
	
	/*
	 * Click listener
	 */
	class OnStartClickListener implements View.OnClickListener {
		public void onClick(View view) {
			Intent startGameIntent = new Intent(SplashActivity.this,GameActivity.class);
			startActivity(startGameIntent);
		}
	}
}
