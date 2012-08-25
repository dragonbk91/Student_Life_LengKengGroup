package lengkeng.group.Level_3;

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
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.Debug;

import android.graphics.Color;
import android.view.MotionEvent;

public class Level_3_Park_Scene extends ManageableLevel implements ILevel,IOnSceneTouchListener{
	 final static private String TAG_STUDENT = "student";
	 final static private String TAG_SCOREREQUIREMENTS = "ScoreRequirements";
	 final static private String TAG_TIME = "time";
	 final static private String TAG_TABLE = "block";
	 final static private String TAG_SEESAW = "seesaw";
	 final static private String TAG_GIRL = "girl";
	 final static private String TAG_THUNDER1 = "thunder1";
	 final static private String TAG_THUNDER2 = "thunder2";
	 final static private String TAG_THUNDER3 = "thunder3";	

	public static Student mStudent;	
	public static MovingGirlPool movingGirlpool;
	public static Sound soundEatItem;
	public static Sound soundKiss;
	public static Sound soundTickTack;
	public static Sound soundThunder;
	public static Music musicBackground;

	private TiledTextureRegion heart_1TextureRegion;
	private TiledTextureRegion ThunderTextureRegion;
	private TiledTextureRegion SeesawTextureRegion;	
	private TiledTextureRegion movingGirlTextureRegion;	
	public static AnimatedItemPool HeartPool;
	public static AnimatedItemPool ThunderPool;	
	// Score & Timer
	public static Score score; // co the co nhieu loai score
	public static Timer timer;	
	GameLoopUpdateHandler gameloop = new GameLoopUpdateHandler();	
	public RandomSprite randomSprite = new RandomSprite();		
	public static boolean Touchable;
	public static int[] Thunder_Col = new int[3];
	public static int[] Thunder_Row = new int[3];

	// combo score

	
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
	public void loadResources(BaseGameActivity context) {
		// TODO Auto-generated method stub
		this.isLoaded = true;
		Touchable = true;
		
		// load resources						
		loadBackground();
		
		loadTexture(context);		
		
		// load level to Scene
		LevelLoading.loadLevel(context);
		
		// init game																			
		
		HeartPool = new AnimatedItemPool(heart_1TextureRegion);
		ThunderPool = new AnimatedItemPool(ThunderTextureRegion);

		movingGirlpool = new MovingGirlPool(movingGirlTextureRegion);	

		// Score
		score = new Score(10,10,this.mfont,"    ",10);		
		mScene.attachChild(score);
		
		// Timer
		timer = new Timer(LevelManager.getCamera().getWidth()/2 - 50 , 20 ,this.mfont,"    ",10);
		mScene.attachChild(timer);
		timer.setTime(1, 10); // 3 min 00 second
					

		loadMusic(context);
		startLevel();
	}

	@Override
	public void loadBackground() {
		// TODO loadBackground			
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Level 3/");	
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
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.sheetBitmapTextureAtlas = new BitmapTextureAtlas(512, 1024);		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Level 3/");		
		SeesawTextureRegion =    BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.sheetBitmapTextureAtlas, context, "seesaw.png",0,400,2,5);
		heart_1TextureRegion =   BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.sheetBitmapTextureAtlas, context, "heart_1.png",180,736,2,4);
		ThunderTextureRegion =   BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.sheetBitmapTextureAtlas, context, "thunder.png",0,0,5,4);
		movingGirlTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.sheetBitmapTextureAtlas, context, "girl.png",180,400,4,4);
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/comboScore/");
		
		this.mFontTexture = new BitmapTextureAtlas(256,256,TextureOptions.DEFAULT);										
																	
		mfont = FontFactory.createFromAsset(mFontTexture, context, "comic2.ttf", 50, true, Color.RED);
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
			
		} else if(type.equals(TAG_SEESAW)){
			Grid.setBlock(pY, pX, true);
			AnimatedItem seesaw = new AnimatedItem(Grid.COLUMN[pX], Grid.ROW[pY], SeesawTextureRegion);
			arrRemoveableAnimatedBlock.add(seesaw);
			mScene.attachChild(seesaw);
			seesaw.animate(200, true);			
			
		} else if(type.equals(TAG_GIRL)){
			MovingGirl girl = movingGirlpool.obtainPoolItem();
			if (!girl.isAttachToScene()) {
				mScene.attachChild(girl);
				girl.setAttachToScene(true);
			}
			girl.setPosition(Grid.COLUMN[pX] , Grid.ROW[pY]);
			girl.setVelocity(velocity);
			girl.unregisterListener();
		
		} else if(type.equals(TAG_THUNDER1)){
			Thunder_Col[0] = pX ;
			Thunder_Row[0] = pY;
		
		} else if(type.equals(TAG_THUNDER2)){
			Thunder_Col[1] = pX ;
			Thunder_Row[1] = pY;
		
		} else if(type.equals(TAG_THUNDER3)){
			Thunder_Col[2] = pX ;
			Thunder_Row[2] = pY;
		} else {
			RandomItem.loadParam(type, pX, pY);
		}
	}

	@Override
	public void loadMusic(BaseGameActivity context) {
		// TODO Auto-generated method stub
		SoundFactory.setAssetBasePath("mfx/Level 3/");
        try {
        	
        
        	soundKiss = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "bike dam nguoi.ogg");
        	soundThunder = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "tia chop.ogg");
        	
     	
        } catch (final IOException e) {
            Debug.e("Error", e);
            }
		SoundFactory.setAssetBasePath("mfx/Menu/");
        try {

        	soundTickTack = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "tick tack cuoi game.ogg");  	
        	soundEatItem = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "eat item_1.ogg");
        	//this.mTickTac.setLooping(true);
     	
        } catch (final IOException e) {
            Debug.e("Error", e);
            }
        MusicFactory.setAssetBasePath("mfx/Level 3/");
        try {
        	musicBackground = MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(), context,"lv1.ogg" );
        	musicBackground.setLooping(true);        	
        } catch (final IOException e) {
            Debug.e("Error", e);
            }
        int volume = MenuGame.volumeOn;        		
        soundEatItem.setVolume(0.5f * volume);
        soundKiss.setVolume(0.5f * volume);
        soundThunder.setVolume(0.5f * volume);
        soundTickTack.setVolume(0.5f * volume);
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
								mScene.setOnSceneTouchListener(Level_3_Park_Scene.this);
								
								LevelManager.getEngine().registerUpdateHandler(gameloop);

								// creat random book
								randomSprite.createSpriteSpawnTimeHandler();

								// timer			
								timer.UpdateHandler();									
								
								mStudent.registerListener();
								mStudent.setTouchEnable(true);		
								
								for (MovingGirl movingGirl : movingGirlpool.arrMovingGirl){
									movingGirl.changeDirectionRanDom();
									movingGirl.registerListener();
								}	
								score.createComboScore(score, LevelManager.comboEffectPool, MenuGame.soundCombo);
								// add musicbackground
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
		
		recycleEntity();
		unloadTexture();
		
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
		unloadMusic();
	}

	@Override
	public void unloadTexture() {
		// TODO unloadTexture
		this.sheetBitmapTextureAtlas.clearTextureAtlasSources();
		this.mFontTexture.clearTextureAtlasSources();
		bg_bitmapTextureAtlas.clearTextureAtlasSources();
		
		LevelManager.unloadTexture(mFontTexture);
		LevelManager.unloadTexture(sheetBitmapTextureAtlas);							
		SceneManager.unloadTexture(bg_bitmapTextureAtlas);// TODO Auto-generated method stub
		
	}

	@Override
	public void unloadMusic() {
		// TODO Auto-generated method stub.
		if(soundKiss != null){
			soundKiss.stop();
			soundKiss.release();
			soundKiss = null;
		}
		if (musicBackground != null){
			musicBackground.stop();
			musicBackground.release();
			musicBackground = null;	
		}
		if(soundEatItem != null){
			soundEatItem.stop();
			soundEatItem.release();
			soundEatItem = null;
		}
		if(soundTickTack != null){
			soundTickTack.stop();
			soundTickTack.release();
			soundTickTack = null;
		}
		if(soundThunder != null){
			soundThunder.stop();
			soundThunder.release();			
			soundThunder = null;
		}		
		System.gc();
	}

	@Override
	public void recycleEntity() {
		// TODO recycleEntity
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
				
//				ClockPool.recycleAll();
				
//				ItemPool.recycleAll();
				HeartPool.recycleAll();
				ThunderPool.recycleAll();
				movingGirlpool.recycleAll();				
				score.unload();
				timer.unload();
				
				BufferObjectManager.getActiveInstance().unloadBufferObject(mStudent.getVertexBuffer());												
				mStudent.removeMe();				
	}

}
