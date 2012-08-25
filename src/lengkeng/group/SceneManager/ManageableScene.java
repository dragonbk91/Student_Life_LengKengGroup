package lengkeng.group.SceneManager;

import org.anddev.andengine.entity.scene.Scene;

public class ManageableScene {	
	protected Scene mScene;
	public boolean isLoaded;
	
	public ManageableScene (){
		mScene = new Scene();
		isLoaded = false;
	}		
}
