package lengkeng.group.SceneManager;

import lengkeng.group.SceneManager.ManageableScene;
import lengkeng.group.SceneManager.SceneManager;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;


public class SplashScene extends ManageableScene implements IManageableScene{
	
	/**
	 * thoi gian loading
	 */
	private final float SplashDisplayTime = 3.0f;
	private BitmapTextureAtlas SplashTexture;
	private TextureRegion SplashTextureRegion;
	
	public SplashScene(BaseGameActivity context) {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Load the scene and any assets we need.
	 */
	@Override
	public void loadResources(BaseGameActivity context) {
		this.isLoaded = true;	
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game script/splash scene/");
		this.SplashTexture = new BitmapTextureAtlas( 1024, 512, TextureOptions.DEFAULT );
		this.SplashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset( this.SplashTexture,
				context, "Splash_Background.png", 0, 0 );
		SceneManager.loadTexture( this.SplashTexture );
		
		final Sprite splashSprite = new Sprite( 0, 0, this.SplashTextureRegion );
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
		
	}	
	
	public void update(BaseGameActivity context){
		context.getEngine().registerUpdateHandler( new TimerHandler( this.SplashDisplayTime, true, new ITimerCallback() {
			
			@Override
			public void onTimePassed( TimerHandler pTimerHandler ) {
			
				/*
				 * Unregister handler so this will only hit once
				 */
				SceneManager.getEngine().unregisterUpdateHandler( pTimerHandler );
				
				/*
				 * After splash, load next scene (root menu)
				 */				
				
				SceneManager.Menu_id = SceneManager.MENUSCENE;
				SceneManager.load();
				SceneManager.setScene(SceneManager.run());				
				
			}
		} ) );
	}	
}
