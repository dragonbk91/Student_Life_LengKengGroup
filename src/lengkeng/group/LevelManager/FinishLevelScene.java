package lengkeng.group.LevelManager;

import java.io.IOException;

import lengkeng.group.SceneManager.IManageableScene;
import lengkeng.group.SceneManager.MenuGame;
import lengkeng.group.SceneManager.SceneManager;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.audio.sound.SoundFactory;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.buffer.BufferObjectManager;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.HorizontalAlign;

import android.graphics.Color;

public class FinishLevelScene extends ManageableLevel implements IManageableScene {
	private BitmapTextureAtlas bg_bitmapTextureAtlas;
	private TextureRegion bg_textureRegion; // luu khi load anh
	private Sprite bg_sprite; // sprite lam anh nen	
	
	private String result;
	private String score;
	
	private Text ResultText;
	private Text ScoreText;
	private Text buttonMenu;
	private Text buttonReplay;
	private Text buttonNext;
	private int X = 100;
	private Music musicBackgroundWin;
	private Music musicBackgroundLose;
	private Sound soundPushButton;
	private boolean pass = false;
	
	private boolean isTouch = false;
	public FinishLevelScene(BaseGameActivity context) {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void loadResources(BaseGameActivity _context) {
		// TODO Auto-generated method stub
				
		mScene.setTouchAreaBindingEnabled(true);
		isTouch = false;
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game script/menu game/");
		// background

		bg_bitmapTextureAtlas = new BitmapTextureAtlas(1024,512,TextureOptions.DEFAULT); // luu anh vao bo nho
		bg_textureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bg_bitmapTextureAtlas,_context,"menu_background.png",0,0);
		LevelManager.loadTexture( bg_bitmapTextureAtlas );				
		bg_sprite = new Sprite(0,0,bg_textureRegion);
		mScene.setBackground(new SpriteBackground(bg_sprite));
		
    	this.mFontTexture = new BitmapTextureAtlas(512,512,TextureOptions.DEFAULT);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("font/");
    	mfont = FontFactory.createFromAsset(mFontTexture, LevelManager.getBaseGameActivity(), "BRADHITC.TTF", 70, true, Color.BLACK);
		LevelManager.loadFont(this.mfont);				
		LevelManager.loadTexture(this.mFontTexture);
    	
        this.ResultText = new Text(LevelManager.getCamera().getWidth()/2,LevelManager.getCamera().getHeight()/5, mfont, this.result,HorizontalAlign.CENTER);
        this.ResultText.setPosition(LevelManager.getCamera().getWidth()/2 - this.ResultText.getWidth()/2 +X, LevelManager.getCamera().getHeight()/5 + 20);
        
        this.ScoreText = new Text(LevelManager.getCamera().getWidth()/2,LevelManager.getCamera().getHeight()/5, mfont, this.score,HorizontalAlign.CENTER);
        this.ScoreText.setPosition(LevelManager.getCamera().getWidth()/2 - this.ScoreText.getWidth()/2+X, LevelManager.getCamera().getHeight()/5*2);
        loadMusic(_context);
        if(pass)
        	musicBackgroundWin.play();
        else
        	musicBackgroundLose.play();       
        this.buttonMenu = new Text(LevelManager.getCamera().getWidth()/2,LevelManager.getCamera().getHeight()/5, mfont, "Menu",HorizontalAlign.CENTER){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
                float pTouchAreaLocalX, float pTouchAreaLocalY) {
       
            	if(pSceneTouchEvent.isActionDown()&& !isTouch){
            		soundPushButton.play();
    				isTouch = true;
    				mScene.attachChild(MenuGame.explosion);
    				MenuGame.explosion.setPosition(buttonMenu.getX()- buttonMenu.getWidth()/2 , buttonMenu.getY() - buttonMenu.getHeight()/2);
    				MenuGame.explosion.animate(50, false, new IAnimationListener () {

						@Override
						public void onAnimationEnd(
								AnimatedSprite pAnimatedSprite) {
									LevelManager.last_Level_id = LevelManager.Level_id;
				            		LevelManager.Level_id = LevelManager.FINISH_LEVEL;
									SceneManager.last_Menu_id = SceneManager.Menu_id;
									SceneManager.Menu_id = SceneManager.MENUSCENE;		
									mScene.detachChild(MenuGame.explosion);
									SceneManager.load();
									SceneManager.setScene(SceneManager.run());    
									isTouch = false;							
								}	    					
		    				});
    					return true;    
		    			}		    			         				        
			    return true;				
			  }
        };

        this.buttonMenu.setPosition(LevelManager.getCamera().getWidth()/2 - this.buttonMenu.getWidth()/2+X, LevelManager.getCamera().getHeight()/5*4);
        
        this.buttonNext = new Text(LevelManager.getCamera().getWidth()/2,LevelManager.getCamera().getHeight()/5, mfont, "Next",HorizontalAlign.CENTER){
        	@Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
                float pTouchAreaLocalX, float pTouchAreaLocalY) {
       
            	if(pSceneTouchEvent.isActionDown()&& !isTouch){
            		soundPushButton.play();
    				isTouch = true;
    				mScene.attachChild(MenuGame.explosion);
    				MenuGame.explosion.setPosition(buttonNext.getX()- buttonNext.getWidth()/2 , buttonNext.getY() - buttonNext.getHeight()/2);
    				MenuGame.explosion.animate(50, false, new IAnimationListener () {

						@Override
						public void onAnimationEnd(
								AnimatedSprite pAnimatedSprite) {
									LevelManager.last_Level_id = LevelManager.FINISH_LEVEL;
									if(LevelManager.Level ==4 & LevelManager.subLevel==4){
										LevelManager.Level_id = LevelManager.FINISH_LEVEL;
										LevelManager.Level = 1;
										LevelManager.subLevel = 1;
										SceneManager.last_Menu_id = SceneManager.Menu_id;
										SceneManager.Menu_id = SceneManager.FINISHGAME;
										SceneManager.load();
										SceneManager.setScene(SceneManager.run());
									}
									else
									if(LevelManager.subLevel == 4){
										LevelManager.Level_id = LevelManager.FINISH_LEVEL;
										LevelManager.Level++;
										LevelManager.subLevel = 1;
										SceneManager.last_Menu_id = SceneManager.Menu_id;
										SceneManager.Menu_id = SceneManager.CHOOSE_SUBLEVEL;
										SceneManager.load();
										SceneManager.setScene(SceneManager.run());
									}
										else{
											LevelManager.subLevel++;						
											LevelManager.Level_id = LevelManager.Level;						
											LevelManager.load();
											LevelManager.setScene(LevelManager.run());
										}
									isTouch = false;	
									mScene.detachChild(MenuGame.explosion);
								}	    					
		    				});
    					return true;    
		    			}		    			         				        
			    return true;				
			  }
        };
        	        	
        this.buttonNext.setPosition(LevelManager.getCamera().getWidth()/2 - this.buttonNext.getWidth()/2+2*X, LevelManager.getCamera().getHeight()/5*3);
        
        this.buttonReplay = new Text(LevelManager.getCamera().getWidth()/2,LevelManager.getCamera().getHeight()/5, mfont, "Replay",HorizontalAlign.CENTER){
        	  @Override
              public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
                  float pTouchAreaLocalX, float pTouchAreaLocalY) {
         
              	if(pSceneTouchEvent.isActionDown()&& !isTouch){
              		soundPushButton.play();
      				isTouch = true;
      				mScene.attachChild(MenuGame.explosion);
      				MenuGame.explosion.setPosition(buttonReplay.getX()- buttonReplay.getWidth()/2 , buttonReplay.getY() - buttonReplay.getHeight()/2);
      				MenuGame.explosion.animate(50, false, new IAnimationListener () {

  						@Override
  						public void onAnimationEnd(
  								AnimatedSprite pAnimatedSprite) {
			  							isTouch = true;
			  		            		LevelManager.last_Level_id = LevelManager.FINISH_LEVEL;
			  							LevelManager.Level_id = LevelManager.Level;
			  		            		
			  							LevelManager.load();
			  							LevelManager.setScene(LevelManager.run());		  					        
			  							isTouch = false;	
										mScene.detachChild(MenuGame.explosion);
  								}	    					
  		    				});
      					return true;    
  		    			}		    			         				        
  			    return true;				
  			  }
          };
        	
        	
        this.buttonReplay.setPosition(LevelManager.getCamera().getWidth()/2 - this.buttonReplay.getWidth()/2 - X/4, LevelManager.getCamera().getHeight()/5*3);
        
        mScene.attachChild(ResultText);
        mScene.attachChild(ScoreText);
        mScene.attachChild(buttonMenu);
        mScene.attachChild(buttonNext);
        mScene.attachChild(buttonReplay);
                
        mScene.registerTouchArea(buttonMenu);
        mScene.registerTouchArea(buttonNext);
        mScene.registerTouchArea(buttonReplay);
	}
	@Override
	public Scene run() {
		// TODO Auto-generated method stub		
		return mScene;
	}
	@Override
	public void unloadResources(BaseGameActivity _context) {
		// TODO Auto-generated method stub		
		isTouch = false;
		removeButton(this.buttonMenu);
		removeButton(this.buttonNext);
		removeButton(buttonReplay);
		removeButton(ResultText);
		removeButton(ScoreText);
		unloadMusic(_context);
		
		this.result = "";
		this.score = "";	
		this.mFontTexture.clearTextureAtlasSources();
		LevelManager.unloadTexture(mFontTexture);
		LevelManager.unloadTexture(bg_bitmapTextureAtlas);
	}
	
	public void setResult(String _result, String _score, boolean _pass){		
		this.result = _result;
		this.score = _score;
		this.pass = _pass;
	}
	
	public void removeButton(Text button){
		BufferObjectManager.getActiveInstance().unloadBufferObject(button.getVertexBuffer());
		button.setIgnoreUpdate(true);
		button.setVisible(false);
		button.reset();
		button.detachSelf();
		BufferObjectManager.getActiveInstance().unloadBufferObject(button.getVertexBuffer());
	}
	public void loadMusic(BaseGameActivity context){
		SoundFactory.setAssetBasePath("mfx/Menu/");
        try {
        	this.soundPushButton = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "explosive.ogg");
        } catch (final IOException e) {
            Debug.e("Error", e);
            }
        MusicFactory.setAssetBasePath("mfx/Menu/");
        try {
        	this.musicBackgroundLose = MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(), context,"lose.ogg" );
        	this.musicBackgroundLose.setLooping(true);
        	musicBackgroundWin = MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(), context,"win.ogg" );
        	musicBackgroundWin.setLooping(true);
        } catch (final IOException e) {
            Debug.e("Error", e);
            }
        soundPushButton.setVolume(0.5f * MenuGame.volumeOn);
        musicBackgroundLose.setVolume(MenuGame.volumeOn);
        musicBackgroundWin.setVolume(MenuGame.volumeOn);
	}
	public void unloadMusic(BaseGameActivity context){
		this.soundPushButton.stop();
		this.soundPushButton.release();
		musicBackgroundLose.stop();
		musicBackgroundLose.release();
		musicBackgroundWin.stop();
		musicBackgroundWin.release();
	}
}
