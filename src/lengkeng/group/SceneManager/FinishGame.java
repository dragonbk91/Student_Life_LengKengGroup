package lengkeng.group.SceneManager;



import java.io.IOException;

import lengkeng.group.LevelManager.LevelManager;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.buffer.BufferObjectManager;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.HorizontalAlign;

import android.graphics.Color;

public class FinishGame extends ManageableScene implements IManageableScene{
	private BitmapTextureAtlas bg_bitmapTextureAtlas;
	private TextureRegion bg_textureRegion; // luu khi load anh
	private Music musicBackground;
	private Sprite bg_sprite; // sprite lam anh nen			
	private static BitmapTextureAtlas mFontTexture; // font vao bbo 			
	public static Font mfont; // luu lai font
	
	private Text Back;

	
	private boolean isTouch = false;
	
	public FinishGame(BaseGameActivity context) {
		super();
		// TODO Auto-generated constructor stub
	}	

	@Override
	public void loadResources(BaseGameActivity context) {	
		mFontTexture = new BitmapTextureAtlas(256,256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);		
		FontFactory.setAssetBasePath("font/");        
        mfont = FontFactory.createFromAsset(mFontTexture, SceneManager.getBaseGameActivity(), "BRADHITC.TTF", 60, true, Color.rgb(85, 91, 87));
      //load
    	SceneManager.loadTexture(mFontTexture);    	
    	SceneManager.loadFont(mfont);
    	
		this.isLoaded = true;		
		this.isTouch = false;
		mScene.setTouchAreaBindingEnabled(true);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game script/finish game/");
		// background

		bg_bitmapTextureAtlas = new BitmapTextureAtlas(1024,512,TextureOptions.DEFAULT); // luu anh vao bo nho
		bg_textureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bg_bitmapTextureAtlas,context,"background.png",0,0);
		
		SceneManager.loadTexture( bg_bitmapTextureAtlas );				
		
		bg_sprite = new Sprite(0,0,bg_textureRegion);
		mScene.setBackground(new SpriteBackground(bg_sprite));
		loadMusic(context);
		musicBackground.play();
	}
	
	private void loadTexture(){
		this.Back = new Text(LevelManager.getCamera().getWidth()/2,150, mfont, "Back",HorizontalAlign.CENTER){	    	
	    	@Override
	    	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX,final float pTouchAreaLocalY){
	    		if(pSceneTouchEvent.isActionDown()&& !isTouch){
    				isTouch = true;					
            		MenuGame.soundPushButton.play();
            		mScene.attachChild(MenuGame.explosion);
            		MenuGame.explosion.setPosition(Back.getX()- Back.getWidth()/2 , Back.getY() - Back.getHeight()/2);
            		MenuGame.explosion.animate(50, false, new IAnimationListener () {

            			@Override
            			public void onAnimationEnd(
            					AnimatedSprite pAnimatedSprite) {            				
		            				mScene.detachChild(MenuGame.explosion);
		            				SceneManager.last_Menu_id = SceneManager.INTRODUCTION;
            	    				SceneManager.Menu_id = SceneManager.MENUSCENE;
            	    				SceneManager.load();
            	    				SceneManager.setScene(SceneManager.run());	
            	    				unloadMusic();
            	    				
            			}	    					
            		});	  
            							
			        }
			    return true;
				}
			  };
	    this.Back.setPosition(LevelManager.getCamera().getWidth() - this.Back.getWidth()  - 50, LevelManager.getCamera().getHeight() - this.Back.getHeight() - 20);
	    
        mScene.attachChild(Back);
      
        mScene.registerTouchArea(Back);

	}
	
	@Override
	public Scene run() {
		loadTexture();
		return this.mScene;
	}

	@Override
	public void unloadResources(BaseGameActivity context) {
		// TODO Auto-generated method stub
	
		mScene.detachChildren();
		mScene.detachSelf();		
		mScene.clearUpdateHandlers();
		mScene.clearEntityModifiers();
		mScene.clearTouchAreas();
		mScene.clearUpdateHandlers();
		unloadMusic();
		SceneManager.unloadTexture(mFontTexture);
		SceneManager.unloadTexture(bg_bitmapTextureAtlas);
		mScene.reset();	
	}
	
	
	public void removeButton(Text button){
		BufferObjectManager.getActiveInstance().unloadBufferObject(button.getVertexBuffer());
		button.setIgnoreUpdate(true);
		button.setVisible(false);
		button.reset();
		button.detachSelf();				
	}
	public void loadMusic(BaseGameActivity context) {
		// TODO loadMusic
        MusicFactory.setAssetBasePath("mfx/Menu/");
        try {
        	musicBackground = MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(), context,"win.ogg" );
        	musicBackground.setLooping(true);        	
        } catch (final IOException e) {
            Debug.e("Error", e);
            }
        int volume = MenuGame.volumeOn;

        musicBackground.setVolume(volume);	
	}
	public void unloadMusic(){
		musicBackground.stop();
		musicBackground.release();
	}
}

