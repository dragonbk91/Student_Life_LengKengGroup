package lengkeng.group.Level_1;

import java.io.IOException;

import lengkeng.group.GeneralClass.AIPool;
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
import lengkeng.group.mobileBlock.AI;

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

public class Level_1_Class_Scene extends ManageableLevel implements ILevel,IOnSceneTouchListener{
		
   final static private String TAG_STUDENT = "student";
   final static private String TAG_SCOREREQUIREMENTS = "ScoreRequirements";
   final static private String TAG_TIME = "time";
   final static private String TAG_TABLE = "table";
   final static private String TAG_TEACHER = "teacher";  

	public static Sound soundExplosion;
	public static Music musicBackground;
	public static Sound soundTeacher;
	public static Sound soundTickTack;	

	public static Student mStudent;	
	public static AIPool teacherPool;	
	private TiledTextureRegion TeacherTextureRegion;
	private TiledTextureRegion DialogTextureRegion;
	public static AnimatedItemPool dialog_Pool;

	// Score & Timer
	public static Score score;
	public static Timer timer;
	
	GameLoopUpdateHandler gameloop = new GameLoopUpdateHandler();
	
	public RandomSprite randomSprite = new RandomSprite();	
	
	public static boolean Touchable;
	
	
	public Level_1_Class_Scene() {
		super();
	}
	
	/**
	 * Load the scene and any assets we need.
	 */
	@Override
	public void loadResources(BaseGameActivity context) {			
		// TODO loadResources 		
		
		this.isLoaded = true;
		Touchable = true;
		
		// load resources						
		loadBackground();
		
		loadTexture(context);								
		
		// Timer
		timer = new Timer(LevelManager.getCamera().getWidth()/2 - 50 , 20 ,this.mfont,"    ",10);
		mScene.attachChild(timer);
				
		// init game																		
		dialog_Pool = new AnimatedItemPool(DialogTextureRegion);				
		teacherPool = new AIPool(TeacherTextureRegion);						
		
		// Score
		score = new Score(10,10,this.mfont,"    ",10);		
		mScene.attachChild(score);											
		loadMusic(context);		
		load();		
	}
	
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
						mScene.setOnSceneTouchListener(Level_1_Class_Scene.this);
						
						LevelManager.getEngine().registerUpdateHandler(gameloop);

						// creat random book
						randomSprite.createSpriteSpawnTimeHandler();

						// timer			
						timer.UpdateHandler();
								
						// random teacher's direction
						for (AI teacher : teacherPool.arrAI){
							teacher.changeDirectionRanDom();
							teacher.registerListener();
						}
						score.createComboScore(score, LevelManager.comboEffectPool, MenuGame.soundCombo);
						mStudent.registerListener();
						mStudent.setTouchEnable(true);
														
						mScene.detachChild(LevelManager.countdown);
						musicBackground.play();
						}							
					});
				}			
		});	
		
	}
	
	@Override
	public Scene run() {		
		return this.mScene;		
	}

	@Override
	public void unloadResources(BaseGameActivity context) {
		// TODO unloadResources				
		
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
	
	
	// load Block
	@Override
	public void loadEntity(final String type, final int pX, final int pY, final int velocity){
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
			
		} else if(type.equals(TAG_TEACHER)){
			AI teacher = teacherPool.obtainPoolItem();
			if (!teacher.isAttachToScene()) {
				mScene.attachChild(teacher);
				teacher.setAttachToScene(true);
			}
			teacher.setPosition(Grid.COLUMN[pX] , Grid.ROW[pY]);
			teacher.setVelocity(velocity);
			teacher.unregisterListener();
			
		} else if(type.equals(TAG_TIME)){
			timer.setTime(pX, pY); 
		} else {
			RandomItem.loadParam(type, pX, pY);
		}
	}
	
	@Override
	public void loadBackground(){
		// TODO loadBackground			
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Level 1/");	
		bg_bitmapTextureAtlas= new BitmapTextureAtlas(1024,512,TextureOptions.DEFAULT); // luu anh vao bo nho
		bg_bitmapTextureAtlas.clearTextureAtlasSources();		
		bg_textureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bg_bitmapTextureAtlas,LevelManager.getBaseGameActivity(),"background.png",0,0);
		LevelManager.loadTexture( bg_bitmapTextureAtlas );		
		bg_sprite = new Sprite(0,0,bg_textureRegion);
		mScene.setBackground(new SpriteBackground(bg_sprite));
	}
	
	@Override
	public void loadTexture(BaseGameActivity context){
		// TODO loadTexture
		this.sheetBitmapTextureAtlas = new BitmapTextureAtlas(2048, 2048);		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Level 1/");		
		this.mFontTexture = new BitmapTextureAtlas(256,256,TextureOptions.DEFAULT);		
		DialogTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.sheetBitmapTextureAtlas, context, "dialog.png",280,0,2,5);		
		TeacherTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.sheetBitmapTextureAtlas, context, "teacher.png",0,0,4,4);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/comboScore/");
		mfont = FontFactory.createFromAsset(mFontTexture, context, "comic2.ttf", 50, true, Color.RED);
		LevelManager.loadFont(this.mfont);						
		LevelManager.loadTexture(this.mFontTexture);
		LevelManager.loadTexture(this.sheetBitmapTextureAtlas);
	}
	
	@Override
	public void recycleEntity(){
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
		if(dialog_Pool.arrAniamatedItem != null)
			dialog_Pool.recycleAll();

		score.unload();
		
		timer.unload();		
		BufferObjectManager.getActiveInstance().unloadBufferObject(mStudent.getVertexBuffer());									
		mStudent.removeMe();
		if(teacherPool.arrAI != null)
			teacherPool.recycleAll();
	}
	

	@Override
	public void loadMusic(BaseGameActivity context) {
		// TODO loadMusic
		// TODO loadMusic
		SoundFactory.setAssetBasePath("mfx/Level 1/");
        try {
        	soundTeacher = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "teacher_baoluc.ogg");
        } catch (final IOException e) {
            Debug.e("Error", e);
            }
		SoundFactory.setAssetBasePath("mfx/Menu/");
        try {        	
        	soundTickTack = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "tick tack cuoi game.ogg");  	
        	soundExplosion = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "eat item_1.ogg");
        } catch (final IOException e) {
            Debug.e("Error", e);
            }
        MusicFactory.setAssetBasePath("mfx/Level 1/");
        try {
        	musicBackground = MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(), context,"lv1.ogg" );
        	musicBackground.setLooping(true);        	
        } catch (final IOException e) {
            Debug.e("Error", e);
            }
        int volume = MenuGame.volumeOn;
		musicBackground.setVolume(1*volume);
		soundExplosion.setVolume(0.5f*volume);
		soundTeacher.setVolume(0.5f*volume);
		soundTickTack.setVolume(1*volume);	
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
		// TODO unloadMusic
		if (soundExplosion != null){
			soundExplosion.stop();
			soundExplosion.release();
		}
		if (soundTeacher != null){
			soundTeacher.stop();
			soundTeacher.release();
			
		}
		if(musicBackground != null){
			musicBackground.stop();
			musicBackground.release();
			musicBackground = null;
		}
		if(soundTickTack != null){
			soundTickTack.stop();
			soundTickTack.release();	
		}		
		System.gc();		
	}
	
	public void load(){
		startLevel();
		// load level to Scene
		LevelLoading.loadLevel(LevelManager.getBaseGameActivity());
	}
}

