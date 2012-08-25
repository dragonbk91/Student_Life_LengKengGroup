// date create 
// author Nguyen Thanh Linh
package lengkeng.group.Level_2;

import java.io.IOException;

import lengkeng.group.GeneralClass.AnimatedItem;
import lengkeng.group.GeneralClass.AnimatedItemPool;
import lengkeng.group.GeneralClass.StaticItem;
import lengkeng.group.Grid.Grid;
import lengkeng.group.LevelManager.ILevel;
import lengkeng.group.LevelManager.LevelLoading;
import lengkeng.group.LevelManager.LevelManager;
import lengkeng.group.LevelManager.ManageableLevel;
import lengkeng.group.LevelManager.RandomItem;
import lengkeng.group.SceneManager.MenuGame;
import lengkeng.group.SceneManager.SceneManager;
import lengkeng.group.Score.Score;
import lengkeng.group.Student.Student;
import lengkeng.group.Timer.Timer;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.audio.sound.SoundFactory;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.buffer.BufferObjectManager;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.Debug;

import android.graphics.Color;
import android.view.MotionEvent;

public class Level_2_Market_Scene extends ManageableLevel implements ILevel,
		IOnSceneTouchListener {
	
	 final static private String TAG_STUDENT = "student";
	 final static private String TAG_SCOREREQUIREMENTS = "ScoreRequirements";
	 final static private String TAG_TIME = "time";
	 final static private String TAG_TABLE = "house";
	 final static private String TAG_CAR_UP = "carUp";
	 final static private String TAG_CAR_LEFT = "carLeft";
	 final static private String TAG_CAR_RIGHT = "carRight";
	 final static private String TAG_CAR_DOWN = "carDown";
	   
	public static Student mStudent;

	private TiledTextureRegion DialogTextureRegion;
	private TextureRegion CarUpTextureRegion;
	private TextureRegion CarDownTextureRegion;
	private TextureRegion CarLeftTextureRegion;
	private TextureRegion CarRightTextureRegion;
	public static CarPool CarUpPool;
	public static CarPool CarDownPool;
	public static CarPool CarLeftPool;
	public static CarPool CarRightPool;
	;
	// combo score
	public static int carUp_velocity;
	public static int carDown_velocity;
	public static int carLeft_velocity;
	public static int carRight_velocity;	
	public static float carUp_mEffectSpawnDelay;
	public static float carLeft_mEffectSpawnDelay;
	public static float carRight_mEffectSpawnDelay;
	public static float carDown_mEffectSpawnDelay;
	
	public static  boolean carUp = false;
	public static  boolean carDown = false;
	public static  boolean carLeft = false;
	public static  boolean carRight = false;
	public static Sound soundEatItem;
	public static Sound soundTickTack;
	public static Music musicBackground;
	public static Sound soundCarCollides;
	public static Sound soundDialog;

	
	public static AnimatedItemPool dialog_Pool;
	// Score & Timer
	public static Score score;
	public static Timer timer;
	
	GameLoopUpdateHandler gameloop = new GameLoopUpdateHandler();
	public static boolean Touchable;	
	public RandomSprite randomSprite = new RandomSprite();		

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO onSceneTouchEvent
		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN)
		{														

		}
		if (((pSceneTouchEvent.getAction()&MotionEvent.ACTION_MASK)==TouchEvent.ACTION_MOVE) && Touchable){
			int x2=(int)pSceneTouchEvent.getX();
	        int y2= (int) pSceneTouchEvent.getY() ;
	        return mStudent.onSceneTouchEvent(x2, y2);
	               	
		} // end of move action
		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP){
			
		}
		return false;	
	}

	@Override
	public void loadResources(BaseGameActivity _context) {
		// TODO loadResources 
		this.isLoaded = true;
		Touchable = true;
		
		// load resources						
		loadBackground();
		
		loadTexture(_context);	
		// Timer
		timer = new Timer(LevelManager.getEngine().getCamera().getWidth()/2 - 50 , 20 ,this.mfont,"    ",10);
		mScene.attachChild(timer);
		
		// load level to Scene
		LevelLoading.loadLevel(_context);
		
		// init game																

		CarDownPool = new CarPool(CarDownTextureRegion);
		CarUpPool = new CarPool(CarUpTextureRegion);
		CarLeftPool = new CarPool(CarLeftTextureRegion) ;
		CarRightPool = new CarPool(CarRightTextureRegion);
		dialog_Pool = new AnimatedItemPool(DialogTextureRegion);	
			
		// Score
		score = new Score(10,10,this.mfont,"    ",10);		
		mScene.attachChild(score);
		loadMusic(_context);
		

		startLevel();
	}

	@Override
	public void loadBackground() {
		// TODO loadBackground			
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Level 2/");	
		bg_bitmapTextureAtlas= new BitmapTextureAtlas(1024,512,TextureOptions.DEFAULT); // luu anh vao bo nho
		bg_bitmapTextureAtlas.clearTextureAtlasSources();		
		bg_textureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bg_bitmapTextureAtlas,LevelManager.getBaseGameActivity(),"background.png",0,0);
		LevelManager.loadTexture( bg_bitmapTextureAtlas );		
		bg_sprite = new Sprite(0,0,bg_textureRegion);
		mScene.setBackground(new SpriteBackground(bg_sprite));
	}

	@Override
	public void loadTexture(BaseGameActivity context) {
		// TODO loadTexture
		this.sheetBitmapTextureAtlas = new BitmapTextureAtlas(512, 512);	
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Level 2/");		
		this.mFontTexture = new BitmapTextureAtlas(256,256,TextureOptions.DEFAULT);	
		DialogTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.sheetBitmapTextureAtlas, context, "dialog.png",256,0,2,5);
		CarDownTextureRegion =   BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.sheetBitmapTextureAtlas, context, "car down.png",0,0);
		CarLeftTextureRegion =   BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.sheetBitmapTextureAtlas, context, "car left.png",140,0);
		CarUpTextureRegion =   BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.sheetBitmapTextureAtlas, context, "car up.png",70,0);
		CarRightTextureRegion =   BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.sheetBitmapTextureAtlas, context, "car right.png",140,100);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/comboScore/");
		mfont = FontFactory.createFromAsset(mFontTexture, context, "comic2.ttf", 50, true, Color.rgb(111, 206, 255));
		LevelManager.loadFont(this.mfont);				
		LevelManager.loadTexture(this.mFontTexture);
		LevelManager.loadTexture(this.sheetBitmapTextureAtlas);
	}

	@Override
	public void loadEntity(String type, int pX, int pY, int velocity) {
		// TODO loadEntity
		if(type.equals(TAG_STUDENT)){
			mStudent = LevelManager.mStudent;
			mStudent.setPosition(Grid.COLUMN[pX], Grid.ROW[pY]);
			mStudent.setTouchEnable(false);
			mStudent.unregisterListener();
			mScene.attachChild(mStudent);
			mStudent.setVelocity(velocity);
			
		} else if(type.equals(TAG_TABLE)){
			Grid.setBlock(pY, pX, true);
			
		} else if(type.equals(TAG_SCOREREQUIREMENTS)){
			this.ScoreRequirements = pX;	
			score.setScoreRequirements(this.ScoreRequirements);
			
		} else if(type.equals(TAG_TIME)){
			timer.setTime(pX, pY); 
			
		} else if(type.equals(TAG_CAR_DOWN)){
			carDown_mEffectSpawnDelay = pX;
			carDown_velocity = velocity;
			carDown = true;
			
		} else if(type.equals(TAG_CAR_UP)){
			carUp_mEffectSpawnDelay = pX;
			carUp_velocity = velocity;
			carUp = true;
			
		} else if(type.equals(TAG_CAR_LEFT)){
			carLeft_mEffectSpawnDelay = pX;
			carLeft_velocity = velocity;
			carLeft = true;
			
		} else if(type.equals(TAG_CAR_RIGHT)){
			carRight_mEffectSpawnDelay = pX;
			carRight_velocity = velocity;
			carRight = true;
		} else {
			RandomItem.loadParam(type, pX, pY);
		}
	}

	@Override
	public void loadMusic(BaseGameActivity context) {
		// TODO Auto-generated method stub
		SoundFactory.setAssetBasePath("mfx/Level 2/");
        try {
        	
        	soundCarCollides = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "phanh xe oto.ogg");
        	soundDialog = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "Get Out Of My Way.ogg");

     	
        } catch (final IOException e) {
            Debug.e("Error", e);
            }
		SoundFactory.setAssetBasePath("mfx/Menu/");
        try {
        	soundTickTack = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "tick tack cuoi game.ogg");  	
        	soundEatItem = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "eat item_1.ogg");

        } catch (final IOException e) {
            Debug.e("Error", e);
            }
        MusicFactory.setAssetBasePath("mfx/Level 2/");
        try {
        	musicBackground = MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(), context,"lv1.ogg" );
        	musicBackground.setLooping(true);        	
        } catch (final IOException e) {
            Debug.e("Error", e);
            }
        int volume = MenuGame.volumeOn;
        soundTickTack.setVolume(0.5f * volume);
        soundCarCollides.setVolume(0.5f * volume);
        soundDialog.setVolume(0.5f * volume);
        soundEatItem.setVolume(0.5f * volume);
        musicBackground.setVolume(volume);

	}

	@Override
	public Scene run() {
		// TODO Auto-generated method stub
		return this.mScene;		
	}

	@Override
	public void startLevel() {
		// TODO Start and Update game
		LevelManager.countdown = new AnimatedItem(LevelManager.getCamera().getWidth()/2 - LevelManager.countdownTextureRegion.getWidth()/8, LevelManager.getCamera().getHeight()/2 - LevelManager.countdownTextureRegion.getHeight()/6, LevelManager.countdownTextureRegion);
		mScene.attachChild(LevelManager.countdown);
		LevelManager.countdown.animate(400, false, new IAnimationListener() {
			@Override
			public void onAnimationEnd(final AnimatedSprite pAnimatedSprite) {
				LevelManager.getEngine().runOnUpdateThread(new Runnable() {
					@Override
					public void run() {	
						// Touch Event		
						mScene.setOnSceneTouchListener(Level_2_Market_Scene.this);
						
						LevelManager.getEngine().registerUpdateHandler(gameloop);

						// creat random book
						randomSprite.createSpriteSpawnTimeHandler();

						// timer			
						timer.UpdateHandler();
						score.createComboScore(score, LevelManager.comboEffectPool, MenuGame.soundCombo);
						mStudent.setTouchEnable(true);
						mStudent.registerListener();
						musicBackground.play();						
						mScene.detachChild(LevelManager.countdown);
						}							
					});
				}			
		});	
				

	}

	@Override
	public void unloadResources(BaseGameActivity _context) {
		// TODO unloadResources				
		
		carDown = false;
		carLeft = false;
		carUp = false;
		carRight = false;
		
		recycleEntity();
		unloadTexture();
		unloadMusic();
		
		Grid.reset();		
		this.
		mScene.detachChildren();
		mScene.detachSelf();		
		mScene.clearUpdateHandlers();
		mScene.clearEntityModifiers();
		mScene.clearTouchAreas();
		
		mScene.reset();
		LevelManager.getScene().clearUpdateHandlers();
		LevelManager.getScene().unregisterUpdateHandler(gameloop);
		LevelManager.getEngine().clearUpdateHandlers();
		LevelManager.getEngine().unregisterUpdateHandler(gameloop);
		Touchable = true;

	}

	@Override
	public void unloadTexture() {
		// TODO unloadTexture
		this.sheetBitmapTextureAtlas.clearTextureAtlasSources();
		this.mFontTexture.clearTextureAtlasSources();
		bg_bitmapTextureAtlas.clearTextureAtlasSources();
		
		LevelManager.unloadTexture(mFontTexture);
		LevelManager.unloadTexture(sheetBitmapTextureAtlas);							
		SceneManager.unloadTexture(bg_bitmapTextureAtlas);

	}

	@Override
	public void unloadMusic() {
		// TODO Auto-generated method stub
		if (soundCarCollides != null){
			soundCarCollides.stop();
			soundCarCollides.release();
			soundCarCollides = null;
		}
		if (soundDialog != null){
			soundDialog.stop();
			soundDialog.release();
			soundDialog = null;
		}
		if (soundEatItem != null){
			soundEatItem.stop();
			soundEatItem.release();
			soundEatItem = null;
		}
		if (musicBackground != null){
			musicBackground.stop();
			musicBackground.release();
			musicBackground = null;
		}
		if (soundTickTack != null){

			soundTickTack.stop();
			soundTickTack.release();
			soundTickTack = null;
		}

	}

	@Override
	public void recycleEntity() {
		// // TODO recycleEntity
		try {
			for (StaticItem removeableBlock : arrRemoveableBlock) {
				if(removeableBlock.isAttachToScene){
				arrRemoveableBlock.remove(removeableBlock);
				removeableBlock.removeMe();
				}
			}
		} catch (Exception e) {
			Debug.e(e);
		}
		try {
			for (AnimatedItem removeableAnimatedBlock : arrRemoveableAnimatedBlock) {				
				arrRemoveableAnimatedBlock.remove(removeableAnimatedBlock);
				removeableAnimatedBlock.removeMe();
			}
		} catch (Exception e) {
			Debug.e(e);
		}
		
		dialog_Pool.recycleAll();
		CarUpPool.recycleAll();
		CarDownPool.recycleAll();
		CarLeftPool.recycleAll();
		CarRightPool.recycleAll();

		score.unload();
		timer.unload();
		
		BufferObjectManager.getActiveInstance().unloadBufferObject(mStudent.getVertexBuffer());
								
		mStudent.removeMe();
	}

}