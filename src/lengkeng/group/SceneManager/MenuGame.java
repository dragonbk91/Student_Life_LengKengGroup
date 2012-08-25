package lengkeng.group.SceneManager;

import java.io.IOException;

import lengkeng.group.GeneralClass.AnimatedItem;
import lengkeng.group.LevelManager.LevelManager;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.audio.sound.SoundFactory;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.anddev.andengine.entity.sprite.TiledSprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.buffer.BufferObjectManager;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.HorizontalAlign;


public class MenuGame extends ManageableScene implements IManageableScene{
	private BitmapTextureAtlas bg_bitmapTextureAtlas;
	private TextureRegion bg_textureRegion; // luu khi load anh
	private Sprite bg_sprite; // sprite lam anh nen			

	private BitmapTextureAtlas sheetBitmapTextureAtlas;
	private TiledTextureRegion explosionTextureRegion;
	public static AnimatedItem explosion;
	private TiledTextureRegion speakerTextureRegion;
	public static int volumeOn = 1;
	//sound and music
	public static Sound soundPushButton = null;
	public static Music musicBackground = null;
	public static Sound soundEatItem = null;
	public static Sound soundCombo = null;	
	
	private Text StartText;
	private Text ExitText;
	private Text IntroductionText;
	
	TiledSprite pButtonSpeaker;
	private boolean isTouch = false;
	
	public MenuGame(BaseGameActivity context) {
		super();
	}
	
	/**
	 * Load the scene and any assets we need.
	 */
	@Override
	public void loadResources(final BaseGameActivity context) {		
		this.isLoaded = true;
		isTouch = false;
		this.mScene.setTouchAreaBindingEnabled(true);		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game script/menu game/");				
		
		bg_bitmapTextureAtlas = new BitmapTextureAtlas(1024,512,TextureOptions.DEFAULT); // luu anh vao bo nho
		sheetBitmapTextureAtlas= new BitmapTextureAtlas(2048,1024,TextureOptions.DEFAULT); // luu anh vao bo nho
		bg_textureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bg_bitmapTextureAtlas,SceneManager.getBaseGameActivity(),"menu_background.png",0,0);
		explosionTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(sheetBitmapTextureAtlas,SceneManager.getBaseGameActivity(),"explosion.png",0,0,5,4);
		speakerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(sheetBitmapTextureAtlas,SceneManager.getBaseGameActivity(),"speaker(1).png", 1024, 0,2,1);
		loadMusic(context);
		
		if(!musicBackground.isPlaying()){
			musicBackground.resume();
			musicBackground.play();
		}
		SceneManager.loadTexture( bg_bitmapTextureAtlas );
		SceneManager.loadTexture( sheetBitmapTextureAtlas );
		
		bg_sprite = new Sprite(0,0,bg_textureRegion);
		mScene.setBackground(new SpriteBackground(bg_sprite));
		pButtonSpeaker = new TiledSprite(661, 16, speakerTextureRegion){
			@Override
			public boolean onAreaTouched (final TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY){
				if (pSceneTouchEvent.isActionDown() && volumeOn == 1){
					// turn on Volumn
					this.setCurrentTileIndex(1);
					volumeOn = 0;
					soundPushButton.setVolume(0);
					musicBackground.setVolume(0);
					soundEatItem.setVolume(0);
					soundCombo.setVolume(0);
				}
				else if(pSceneTouchEvent.isActionDown() && volumeOn == 0){
					this.setCurrentTileIndex(0);
					volumeOn = 1;
					soundPushButton.setVolume(1);
					musicBackground.setVolume(1);
					soundEatItem.setVolume(1);
					soundCombo.setVolume(0.5f);
				}
				return true;
			}
		};
		mScene.attachChild(pButtonSpeaker);
		mScene.registerTouchArea(pButtonSpeaker);    	
		explosion = new AnimatedItem(0, 0, explosionTextureRegion);
		
		 this.StartText = new Text(300, 130, SceneManager.mfont2, "Start",HorizontalAlign.CENTER){
	    	
	    	// chuyen sang chon level
	    	@Override
	    	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX,final float pTouchAreaLocalY){
	    			if(pSceneTouchEvent.getAction()== TouchEvent.ACTION_DOWN && !isTouch){
	    				soundPushButton.play();
	    				isTouch = true;
	    				mScene.attachChild(explosion);
	    				explosion.setPosition(StartText.getX()- StartText.getWidth()/2 , StartText.getY() - StartText.getHeight()/2);
	    				explosion.animate(50, false, new IAnimationListener () {

							@Override
							public void onAnimationEnd(
									AnimatedSprite pAnimatedSprite) {
										SceneManager.last_Menu_id = SceneManager.MENUSCENE;
					    				SceneManager.Menu_id = SceneManager.CHOOSE_LEVEL;
					    				SceneManager.load();
					    				SceneManager.setScene(SceneManager.run());	
								
							}	    					
	    				});	    					    					    				
	    			}
	    			return true;
	    		}
	    	};	    
	    	
	    	this.IntroductionText = new Text(390, 240, SceneManager.mfont2, "Introduction",HorizontalAlign.CENTER){
		    	
		    	// chuyen sang chon level
		    	@Override
		    	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX,final float pTouchAreaLocalY){
		    			if(pSceneTouchEvent.getAction()== TouchEvent.ACTION_DOWN && !isTouch){
		    				soundPushButton.play();
		    				isTouch = true;
		    				mScene.attachChild(explosion);
		    				explosion.setPosition(IntroductionText.getX() + IntroductionText.getWidth()/4 , IntroductionText.getY() - IntroductionText.getHeight()/2);
		    				explosion.animate(50, false, new IAnimationListener () {

								@Override
								public void onAnimationEnd(
										AnimatedSprite pAnimatedSprite) {
											SceneManager.last_Menu_id = SceneManager.MENUSCENE;
						    				SceneManager.Menu_id = SceneManager.INTRODUCTION;
						    				SceneManager.load();
						    				SceneManager.setScene(SceneManager.run());	
									
								}	    					
		    				});	    					    					    				
		    			}
		    			return true;
		    		}
		    	};	 
	   
	    this.ExitText = new Text(480, 350, SceneManager.mfont2, "Exit",HorizontalAlign.CENTER){
	    	
	    	// Thoat khoi game	   
	    	@Override
	    	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX,final float pTouchAreaLocalY){
	    			if(pSceneTouchEvent.getAction()== TouchEvent.ACTION_DOWN && !isTouch){
	    				isTouch = true;
	    				mScene.attachChild(explosion);
	    				explosion.setPosition(ExitText.getX()- ExitText.getWidth()/2 , ExitText.getY() - ExitText.getHeight()/2);
	    				explosion.animate(50, false, new IAnimationListener () {

							@Override
							public void onAnimationEnd(
									AnimatedSprite pAnimatedSprite) {
										SceneManager.unload();
										unloadMusic();
										LevelManager.unloadItem();
										SceneManager.getBaseGameActivity().finish();
										android.os.Process.killProcess(android.os.Process.myPid());
							}	    					
	    				});	    					    					    				
	    			}
	    			return true;
	    		}
	    	};	
	    if (volumeOn==0)
	    	pButtonSpeaker.setCurrentTileIndex(1);
	    else
	    	pButtonSpeaker.setCurrentTileIndex(0);
        mScene.attachChild(StartText);
        mScene.attachChild(ExitText);
        mScene.attachChild(IntroductionText);
                
        mScene.registerTouchArea(StartText);
        mScene.registerTouchArea(ExitText);
        mScene.registerTouchArea(IntroductionText);
	}

	@Override
	public Scene run() {
		return this.mScene;
	}

	@Override
	public void unloadResources(BaseGameActivity context) {
		// TODO Auto-generated method stub
		BufferObjectManager.getActiveInstance().unloadBufferObject(bg_sprite.getVertexBuffer());		
		bg_bitmapTextureAtlas.clearTextureAtlasSources();		
		SceneManager.unloadTexture(bg_bitmapTextureAtlas);

		removeButton(ExitText);
		removeButton(IntroductionText);
		removeButton(StartText);
		BufferObjectManager.getActiveInstance().unloadBufferObject(pButtonSpeaker.getVertexBuffer());
		pButtonSpeaker.setVisible(false);
		pButtonSpeaker.reset();
		pButtonSpeaker.detachSelf();
		mScene.detachChild(pButtonSpeaker);
		
		explosion.removeMe();
		
		mScene.detachChildren();
		mScene.detachSelf();		
		mScene.clearUpdateHandlers();
		mScene.clearEntityModifiers();
		mScene.clearTouchAreas();
		mScene.clearUpdateHandlers();
		mScene.reset();
//		unloadMusic();
		
		System.out.println("unload menu");
	}
	
	public void removeButton(Text button){
		BufferObjectManager.getActiveInstance().unloadBufferObject(button.getVertexBuffer());
		button.setIgnoreUpdate(true);
		button.setVisible(false);
		button.reset();
		button.detachSelf();				
	}
	private void loadMusic(BaseGameActivity context){
		if (soundPushButton == null){
			SoundFactory.setAssetBasePath("mfx/Menu/");
	        try {
	        	soundPushButton = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "explosive.ogg");
	        	soundEatItem = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "eat item_1.ogg");
	        	soundCombo = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "comboGood.ogg");
	        } catch (final IOException e) {
	            Debug.e("Error", e);
	            }
		}
	        
		if (soundPushButton == null){
			SoundFactory.setAssetBasePath("mfx/Menu/");
	        try {
	        	soundPushButton = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "explosive.ogg");
	        	soundEatItem = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "eat item_1.ogg");
	        } catch (final IOException e) {
	            Debug.e("Error", e);
	            }	
		}
		if(musicBackground == null){
			MusicFactory.setAssetBasePath("mfx/Menu/");
	        try {
	        	musicBackground = MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(), context,"menu.ogg" );
	        	musicBackground.setLooping(true);        	
	        } catch (final IOException e) {
	            Debug.e("Error", e);
	            }	
		}
        
	}
	private void unloadMusic(){
		soundPushButton.stop();
		soundPushButton.release();
		soundEatItem.stop();
		soundEatItem.release();
		musicBackground.stop();
		musicBackground.release();
		System.gc();
	}

}

