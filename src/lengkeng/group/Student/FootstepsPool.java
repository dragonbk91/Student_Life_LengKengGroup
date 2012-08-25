package lengkeng.group.Student;

import java.util.ArrayList;

import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.pool.GenericPool;
/**
 * @author DRAGON
 * @version 1.0
 * @created 17-Thg7-2012 4:07:28 CH
 */
public class FootstepsPool extends GenericPool<Footsteps> {
	public float posX;
	public float posY;
	public int direction;
	public ArrayList<Footsteps> arrFootsteps;
	public boolean inScene;
	private TextureRegion mTextureRegion;
	
	public FootstepsPool (final TextureRegion tr) {
		posX = 0;
		posY = 0;
		direction = 0;
		arrFootsteps = new ArrayList<Footsteps>(); 
		inScene = false;
		mTextureRegion = tr;
	}
	
	@Override
	protected Footsteps onAllocatePoolItem() {
//		TextureRegion ttr = GameActivity.FootstepsTextureRegion.deepCopy();		
		Footsteps footsteps = new Footsteps(posX, posY, mTextureRegion.deepCopy());
		footsteps.setDirection(direction);
//		GameActivity.mScene.attachChild(footsteps);
		arrFootsteps.add(footsteps);
		return footsteps;
	}
	
	@Override
	/**
	 * reset lai object nhu ban dau
	 */
	public void onHandleObtainItem(final Footsteps footsteps) { 
		footsteps.reset();
	}
	
	public void recycleAll() {
		try {
			if (inScene)
			for (Footsteps footsteps : arrFootsteps) {
				this.recyclePoolItem(footsteps);
			}
		} catch (Exception e) {
			Debug.e(e);
		}
	}
	
	@Override 
	/**
	 * recycle 1 object cu the
	 */
	public void onHandleRecycleItem(final Footsteps footsteps){		
			footsteps.removeMe();		
	}
}
