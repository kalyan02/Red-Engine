package red.game.display.factory;

import android.graphics.Bitmap;
import android.graphics.Point;
import red.game.display.DisplayComponent;
import red.game.display.DisplayContainer;

public class DisplayFactory {
	public static <Type extends DisplayComponent> Type create( Class<Type> containerType, int w, int h ) {
		Type instance = null;
		try {
			instance = containerType.newInstance() ;
			instance.init(w, h);
		} catch( Exception e ) {
			//Boo
		}
		
		return instance;
	}
	
	public static <Type extends DisplayComponent> Type create( Class<Type> containerType, int w, int h, Point pivot) {
		Type instance = create( containerType, w, h );
		if( instance!=null ) {
			instance.pivot.set( pivot.x, pivot.y );
		}
		return instance;
	}
	
//	public <Type extends DisplayContainer> Type create( Class<Type> containerType, int w, int h, Bitmap bitmap ) {
//		Type instance = create(containerType, w, h);
//		if( instance!= null ) {
//			instance.setDefaultBitmap(bitmap);
//		}
//		return instance;
//	}
	
//	public <Type extends DisplayContainer> Type create( Class<Type> containerType, int w, int h, String backgroundResource ) {
//		return create(containerType, w, h);
//	}
}
