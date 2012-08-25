package lengkeng.group.LevelManager;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.ui.activity.BaseGameActivity;

public interface ILevel {
	/**
	 * Load all Resources to Scene
	 * @param _context
	 */
	public void loadResources(BaseGameActivity _context);
	
	/**
	 * Load background Resources to Scene 
	 */
	public void loadBackground();
	
	/**
	 * Load all BitmapTextureAtlas and TiledTextureRegion and TextureRegion to Scene
	 * @param _context
	 */
	public void loadTexture(BaseGameActivity context);
	
	/**
	 * Load all Entity to Scene
	 * @param type
	 * @param pX
	 * @param pY
	 */
	public void loadEntity(final String type, final int pX, final int pY, final int id);
	
	/**
	 * Load Music and Sound
	 * @param context
	 */
	public void loadMusic(BaseGameActivity context);	
	
	/**
	 * get Scene
	 * @return
	 */
	public  Scene run();
	
	/**
	 * Start current level
	 */
	public void startLevel();
	
	/**
	 * unload all Resources
	 * @param _context
	 */
	public void unloadResources(BaseGameActivity _context);
	
	/**
	 * Unload all BitmapTextureAtlas and TiledTextureRegion and TextureRegion to Scene
	 * @param _context
	 */
	public void unloadTexture();
	
	/**
	 * Unload all Music and Sound to Scene
	 * @param _context
	 */
	public void unloadMusic();
	
	/**
	 * Recycle all Entity in Level
	 */
	public void recycleEntity();			
}
