package lengkeng.group.Level_3;

import java.util.LinkedList;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.pool.GenericPool;
import lengkeng.group.LevelManager.LevelManager;


public class MovingGirlPool extends GenericPool<MovingGirl> {
	public float posX;
	public float posY;
	public LinkedList<MovingGirl> arrMovingGirl;
	public boolean inScene;		
	private TiledTextureRegion mTextureRegion;
	
	public MovingGirlPool (TiledTextureRegion ttr) {
		posX = 0;
		posY = 0;
		arrMovingGirl = new LinkedList<MovingGirl>(); 
		inScene = true;	
		mTextureRegion = ttr;
	}
	
	@Override
	/**
	 * tao 1 object gan vao mScene ( khi ko co object nao trong Pool )
	 */
	protected MovingGirl onAllocatePoolItem() {
		inScene = true;				
		MovingGirl movingGirl = new MovingGirl(posX, posY, mTextureRegion.deepCopy());
		LevelManager.getScene().attachChild(movingGirl);
		movingGirl.setAttachToScene(true);		
		arrMovingGirl.add(movingGirl);
		return movingGirl;
	}
	
	@Override
	/**
	 * reset lMovingGirl object nhu ban dau
	 */
	public void onHandleObtainItem(final MovingGirl movingGirl) { 
		movingGirl.reset();
	}
	
	@Override 
	/**
	 * recycle 1 object cu the
	 */
	public void onHandleRecycleItem(final MovingGirl MovingGirl){
		MovingGirl.removeMe();			
	}
	
	public void recycleAll() {
		try {
			if (inScene){
				for (MovingGirl animatedItem : arrMovingGirl) {
					if(animatedItem!=null){
						this.recyclePoolItem(animatedItem);
	//					this.arrMovingGirl.remove(animatedItem);
					}
				}
				inScene = false;
			}
//				while(!this.arrMovingGirl.isEmpty()){
//					MovingGirl movingGirl = this.arrMovingGirl.poll();
//					this.recyclePoolItem(movingGirl);					
//				}
		} catch (Exception e) {
			Debug.e(e);
		}
	}	
}
