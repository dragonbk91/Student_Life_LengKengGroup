package lengkeng.group.SceneManager;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.ui.activity.BaseGameActivity;

public interface IManageableScene {
	public void loadResources(BaseGameActivity _context);
	public  Scene run();
	public void unloadResources(BaseGameActivity _context);	
}
