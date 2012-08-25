package lengkeng.group.GeneralClass;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.buffer.BufferObjectManager;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

public class StaticItem extends Sprite {			
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
	public StaticItem(float pX, float pY, TextureRegion pTextureRegion){
		super(pX, pY, pTextureRegion);
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
}
