package red.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceManager {
	protected static String APP_SHARED_PREFS = "red.data.Prefs";
	private SharedPreferences appSharedPrefs;
	private Editor prefsEditor;
	
	private static PreferenceManager instance;
	
	public PreferenceManager(Context ctx) {
		appSharedPrefs = ctx.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
		prefsEditor = appSharedPrefs.edit();
	}
	
	public String getString( String key, String defaultValue ) {
		return appSharedPrefs.getString(key, defaultValue);
	}
	
	public long getLong( String key, long defValue ) {
		return appSharedPrefs.getLong(key, defValue);
	}
	
	public boolean getBoolean( String key, boolean defValue ) {
		return appSharedPrefs.getBoolean(key, defValue);
	}
	
	public boolean setString( String key, String value ) {
		prefsEditor.putString(key, value);
		return prefsEditor.commit();
	}
	
	public boolean setBoolean( String key, boolean value ) {
		prefsEditor.putBoolean(key, value);
		return prefsEditor.commit();
	}
	
	public boolean setLong( String key, Long value ) {
		prefsEditor.putLong(key, value);
		return prefsEditor.commit();
	}

}
