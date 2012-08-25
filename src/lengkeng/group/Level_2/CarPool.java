// date create 
// author Nguyen Thanh Linh
package lengkeng.group.Level_2;

import java.util.LinkedList;
import lengkeng.group.LevelManager.LevelManager;

import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.pool.GenericPool;

public class CarPool extends GenericPool<Car> {
	public float posX;
	public float posY;
	public LinkedList<Car> arrBike;
	public boolean inScene;		
	private TextureRegion mTextureRegion;
	
	public CarPool (TextureRegion ttr) {
		posX = 0;
		posY = 0;
		arrBike = new LinkedList<Car>(); 
		inScene = true;	
		mTextureRegion = ttr;
	}
	
	@Override
	/**
	 * tao 1 object gan vao mScene ( khi ko co object nao trong Pool )
	 */
	protected Car onAllocatePoolItem() {
		inScene = true;				
		Car animatedItem = new Car(posX, posY, mTextureRegion.deepCopy());
		LevelManager.getScene().attachChild(animatedItem);
		animatedItem.setAttachToScene(true);		
		arrBike.add(animatedItem);
		return animatedItem;
	}
	
	@Override
	/**
	 * reset lai object nhu ban dau
	 */
	public void onHandleObtainItem(final Car animatedItem) { 
		animatedItem.reset();
	}
	
	@Override 
	/**
	 * recycle 1 object cu the
	 */
	public void onHandleRecycleItem(final Car animatedItem){
		animatedItem.removeMe();			
	}
	
	public void recycleAll() {
		try {
			if (inScene){
				for (Car animatedItem : arrBike) {
					if(animatedItem!=null){
						this.recyclePoolItem(animatedItem);
	//					this.arrAniamatedItem.remove(animatedItem);
					}
				}
				inScene = false;
			}

		} catch (Exception e) {
			Debug.e(e);
		}
	}	
}
