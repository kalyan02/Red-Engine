package red.game.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

public class ResourceManager {
	public static View view;
	
	private static Context ctx;
	public static void init(View view) {
		ResourceManager.view = view;
	}
	public static void load(Bitmap bitmap, int resId) {
		load(bitmap,resId,false);
	}
	private static void load(Bitmap bitmap, int resId, boolean b) {
		ctx = view.getContext();
		bitmap = BitmapFactory.decodeResource(ctx.getResources(), resId);
	}
	public static boolean ok() {
		return false;
	}

}
