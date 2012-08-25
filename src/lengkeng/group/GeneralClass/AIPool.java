package lengkeng.group.GeneralClass;

import java.util.LinkedList;

import lengkeng.group.LevelManager.LevelManager;
import lengkeng.group.mobileBlock.AI;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.pool.GenericPool;

public class AIPool extends GenericPool<AI> {
	public float posX;
	public float posY;
	public LinkedList<AI> arrAI;
	public boolean inScene;		
	private TiledTextureRegion mTextureRegion;
	
	public AIPool (TiledTextureRegion ttr) {
		posX = 0;
		posY = 0;
		arrAI = new LinkedList<AI>(); 
		inScene = true;	
		mTextureRegion = ttr;
	}
	
	@Override
	/**
	 * tao 1 object gan vao mScene ( khi ko co object nao trong Pool )
	 */
	protected AI onAllocatePoolItem() {
		inScene = true;				
		AI ai = new AI(posX, posY, mTextureRegion.deepCopy());
		LevelManager.getScene().attachChild(ai);
		ai.setAttachToScene(true);		
		arrAI.add(ai);
		return ai;
	}
	
	@Override
	/**
	 * reset lai object nhu ban dau
	 */
	public void onHandleObtainItem(final AI ai) { 
		ai.reset();
	}
	
	@Override 
	/**
	 * recycle 1 object cu the
	 */
	public void onHandleRecycleItem(final AI ai){
		ai.removeMe();			
	}
	
	public void recycleAll() {
		try {
			if (inScene){
				for (AI animatedItem : arrAI) {
					if(animatedItem!=null){
						this.recyclePoolItem(animatedItem);
					}				
				}
				inScene = false;
				arrAI = null;
			}

		} catch (Exception e) {
			Debug.e(e);
		}
		
	}	
}