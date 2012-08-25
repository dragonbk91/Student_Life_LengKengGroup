package lengkeng.group.Student;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.buffer.BufferObjectManager;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

/**
 * @author DRAGON
 * @version 1.0
 * @created 17-Thg7-2012 4:07:28 CH
 */
public class Footsteps extends Sprite {	
	
	/**
	 * huong
	 */
	private int Direction = 0;
	public boolean isAttachToScene=false;	
	
	public void finalize() throws Throwable {
		super.finalize();
	}
	/**
	 * 
	 * @param pX
	 * @param pY
	 * @param pTiledTextureRegion
	 */
	public Footsteps(float pX, float pY, TextureRegion pTextureRegion){
		super(pX, pY, pTextureRegion);
//		this.setRotationCenter(this.getWidth()/2, this.getHeight()/2);
	}

	public int getDirection(){
		return Direction;
	}	

	/**
	 * 
	 * @param direction
	 */
	public void setDirection(int direction){
		Direction = direction;		
		this.setRotation(direction * 90);
	}	
	
	public boolean isAttachToScene() {
		return isAttachToScene;
	}
	
	public void setAttachToScene(boolean isAttachToScene) {
		this.isAttachToScene = isAttachToScene;
	}
	
	
	public void removeMe(){
		this.setIgnoreUpdate(true);
		this.setVisible(false);
		this.reset();
		this.detachSelf();
		BufferObjectManager.getActiveInstance().unloadBufferObject(this.getVertexBuffer());
		this.isAttachToScene = false;
	}
		
			
}//end Footsteps