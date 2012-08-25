package lengkeng.group.LevelManager;

import lengkeng.group.GeneralClass.StaticItem;
import lengkeng.group.SceneManager.IManageableScene;
import lengkeng.group.SceneManager.ManageableScene;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;


public class SplashSceneLoadingLevel extends ManageableScene implements IManageableScene{
	
	/**
	 * thoi gian loading
	 */
	private final float SplashDisplayTime = 2.0f;
	private BitmapTextureAtlas SplashTexture;
	private TextureRegion SplashTextureRegion;
	private StaticItem splashSprite;
	
	public SplashSceneLoadingLevel(BaseGameActivity context) {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Load the scene and any assets we need.
	 */
	@Override
	public void loadResources(BaseGameActivity context) {	

		this.isLoaded = true;
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.SplashTexture = new BitmapTextureAtlas( 1024, 512, TextureOptions.DEFAULT );
		this.SplashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset( this.SplashTexture,
				context, "game script/splash scene/Splash_Background.png", 0, 0 );
		LevelManager.loadTexture( this.SplashTexture );
		
		splashSprite = new StaticItem( 0, 0, this.SplashTextureRegion);
		mScene.setBackground(new SpriteBackground(splashSprite));

		update(context);
	}

	@Override
	public Scene run() {
		return this.mScene;
	}
	
	/**
	 * Unload any assets here - to be called later.
	 */
	@Override
	public void unloadResources(BaseGameActivity context) {
		// TODO Auto-generated method stub
//		splashSprite.removeMe();
//		mScene.detachChildren();
//		mScene.detachSelf();
//		mScene.clearUpdateHandlers();
//		mScene.reset();
//		this.SplashTexture.clearTextureAtlasSources();
//		LevelManager.unloadTexture(SplashTexture);
	}	
	
	public void update(BaseGameActivity context){
		LevelManager.getEngine().registerUpdateHandler( new TimerHandler( this.SplashDisplayTime, true, new ITimerCallback() {
			
			@Override
			public void onTimePassed( TimerHandler pTimerHandler ) {
			
				/*
				 * Unregister handler so this will only hit once
				 */
				LevelManager.getEngine().unregisterUpdateHandler( pTimerHandler );
				
				/*
				 * After splash, load next scene (root menu)
				 */				
				LevelManager.last_Level_id = LevelManager.Level_id;
				LevelManager.Level_id = LevelManager.Level;
//				SceneManager.last_Menu_id = SceneManager.Menu_id;
//				SceneManager.Menu_id = SceneManager.GAME_PLAY;
				LevelManager.load();
				LevelManager.setScene(LevelManager.run());				
				
			}
		} ) );
	}	
}
