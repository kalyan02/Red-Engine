package red.wordblocks.screens;

import red.game.screens.GameActivity;

public class WordGameActivity extends GameActivity {
	@Override
	public void setup() {
		/*
		GameManager.init( this );
		
		GameManager.setup( new PreferenceManager() );
		GameManager.setup( new AppInstallManager() );
		GameManager.setup( new RedDataManager( { "words.db", "scores.db" } ) );
		GameManager.setup( new RedSoundManager() );
		GameManager.setup( new AccelerometerManager() );
		GameManager.setup( new GPSManager() );
		
		GameManager.runSetup();
		GameManager.runShutdown();
		GameManager.runInit();
		GameManager.runPostInit();
		
		interface ISetupTask {
			public void init();
			public boolean canRun();
		}
		interface IInitTask {
		}
		interface I
		
		class PreferenceManager implements ISetupTask {
		}
		
		class AppInstallManager implements ISetupTask, IShutdownTask, IPostInitTask {
		}
		
		class RedSoundManager implements ISetupTask {
		}
		
		RedSoundManager.play()
		PreferenceManager.getBoolean("Foo",true);
		RedDataManager.query();
		RedDataManager.db("words.db")
		
		*/
		
		gameView = new WordGameView(this);
	}
}
