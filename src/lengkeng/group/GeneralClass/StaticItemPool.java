package lengkeng.group.GeneralClass;

import java.util.LinkedList;

import lengkeng.group.LevelManager.LevelManager;

import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.pool.GenericPool;

public class StaticItemPool extends GenericPool<StaticItem> {
	public float posX;
	public float posY;
	public LinkedList<StaticItem> arrStaticItem;
	public boolean inScene;
	private TextureRegion mTextureRegion;
	
	public StaticItemPool (final TextureRegion tr) {
		posX = 0;
		posY = 0;
		arrStaticItem = new LinkedList<StaticItem>(); 
		inScene = false;
		mTextureRegion = tr;
	}
	
	@Override
	protected StaticItem onAllocatePoolItem() {
		inScene = true;
		StaticItem staticItem = new StaticItem(posX, posY, mTextureRegion.deepCopy());
		arrStaticItem.add(staticItem);
		staticItem.setAttachToScene(true);
		LevelManager.getScene().attachChild(staticItem);		
		return staticItem;
	}
	
	@Override
	/**
	 * reset lai object nhu ban dau
	 */
	public void onHandleObtainItem(final StaticItem staticItem) { 
		staticItem.reset();
	}
	
	public void recycleAll() {
		try {
			if (inScene)

				while(!this.arrStaticItem.isEmpty()){
					StaticItem staticItem = this.arrStaticItem.poll();
					this.recyclePoolItem(staticItem);					
			}
		} catch (Exception e) {
			Debug.e(e);
		}
	}
	
	@Override 
	/**
	 * recycle 1 object cu the
	 */
	public void onHandleRecycleItem(final StaticItem staticItem){		
		staticItem.removeMe();		
	}
}
