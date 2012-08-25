package lengkeng.group.GeneralClass;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.buffer.BufferObjectManager;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class AnimatedItem extends AnimatedSprite{
	public boolean isAttachToScene=false;		
	
	public AnimatedItem(float pX, float pY, TiledTextureRegion pTiledTextureRegion) {
		super(pX, pY, pTiledTextureRegion);
		isAttachToScene = true;
		this.animate(100);
	}
	
	public boolean isAttachToScene() {
		return isAttachToScene;
	}
	
	public void setAttachToScene(boolean isAttachToScene) {
		this.isAttachToScene = isAttachToScene;
	}
	
	public void removeMe() {		
		this.setIgnoreUpdate(true);
		this.setVisible(false);
		this.reset();
		this.detachSelf();
		BufferObjectManager.getActiveInstance().unloadBufferObject(this.getVertexBuffer());
		isAttachToScene = false;
	}
}
