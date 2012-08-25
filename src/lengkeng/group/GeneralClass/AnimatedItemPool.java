package lengkeng.group.GeneralClass;

import java.util.ArrayList;
import lengkeng.group.LevelManager.LevelManager;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.pool.GenericPool;

public class AnimatedItemPool extends GenericPool<AnimatedItem> {
	public float posX;
	public float posY;
	public ArrayList<AnimatedItem> arrAniamatedItem;
	public boolean inScene;		
	private TiledTextureRegion mTextureRegion;
	
	public AnimatedItemPool (TiledTextureRegion ttr) {
		posX = 0;
		posY = 0;
		arrAniamatedItem = new ArrayList<AnimatedItem>(); 
		inScene = true;	
		mTextureRegion = ttr;
	}
	
	@Override
	/**
	 * tao 1 object gan vao mScene ( khi ko co object nao trong Pool )
	 */
	protected AnimatedItem onAllocatePoolItem() {
		inScene = true;				
		AnimatedItem animatedItem = new AnimatedItem(posX, posY, mTextureRegion.deepCopy());
		LevelManager.getScene().attachChild(animatedItem);
		animatedItem.setAttachToScene(true);		
		arrAniamatedItem.add(animatedItem);
		return animatedItem;
	}
	
	@Override
	/**
	 * reset lai object nhu ban dau
	 */
	public void onHandleObtainItem(final AnimatedItem animatedItem) { 
		animatedItem.reset();
	}
	
	@Override 
	/**
	 * recycle 1 object cu the
	 */
	public void onHandleRecycleItem(final AnimatedItem animatedItem){
		animatedItem.removeMe();			
	}
	
	public void recycleAll() {
		try {
			if (inScene){
				for (AnimatedItem animatedItem : arrAniamatedItem) {
					if(animatedItem!=null){
						this.recyclePoolItem(animatedItem);

					}					
				}
				inScene = false;
				arrAniamatedItem = null;
			}			
		} catch (Exception e) {
			Debug.e(e);
		}
	}	
}
