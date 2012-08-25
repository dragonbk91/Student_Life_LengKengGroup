package lengkeng.group.LevelManager;

import java.util.Random;

import lengkeng.group.Game.Activity.GameActivity;
import lengkeng.group.GeneralClass.AnimatedItem;
import lengkeng.group.GeneralClass.AnimatedItemPool;
import lengkeng.group.Grid.Grid;
import lengkeng.group.Level_1.Level_1_Class_Scene;
import lengkeng.group.Level_2.Level_2_Market_Scene;
import lengkeng.group.Level_3.Level_3_Park_Scene;
import lengkeng.group.Level_4.Level_4_Kitchen_Scene;
import lengkeng.group.SceneManager.SceneManager;
import lengkeng.group.Student.Student;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.HorizontalAlign;

public class LevelManager {
	public final static int NUM_LEVEL = 4;
	public final static int NUM_SUBLEVEL = 4;
	 // level
	public final static int FINISH_LEVEL= -1;	
	public final static int SPLASHSCENE= 0;	
	public final static int LEVEL_1_CLASS = 1;
    public final static int LEVEL_2_MARKET = 2;
    public final static int LEVEL_3_PARK = 3;
    public final static int LEVEL_4_KITCHEN = 4;
    
    public static int Level = 1;
    public static int subLevel = 1;
    public static int Level_id = 0;
    public static int subLevel_id = 0;
    public static int last_Level_id = -1;
    public static int last_subLevel_id = -1;
    
    public static BaseGameActivity context;
	public static boolean isLoaded = false;
	
	public static SplashSceneLoadingLevel splash ;
	public static Level_1_Class_Scene Level_1_Class;
	public static Level_2_Market_Scene Level_2_Market;
	public static Level_3_Park_Scene Level_3_Park;	
	public static Level_4_Kitchen_Scene Level_4_Kitchen;
	public static FinishLevelScene finishLevel;
		
	private static BitmapTextureAtlas sheetBitmapTextureAtlas;
	public static TiledTextureRegion countdownTextureRegion;
	public static AnimatedItem countdown;

	
	// item
	public static TiledTextureRegion bookTextureRegion;
	public static TiledTextureRegion moneyTexttureRegion;
	public static TiledTextureRegion giftTexttureRegion;
	public static TiledTextureRegion clockTexttureRegion;
	public static TiledTextureRegion shoesTexttureRegion;
	public static TiledTextureRegion explosion_1TexttureRegion;
	public static TiledTextureRegion explosion_2TexttureRegion;
	public static TiledTextureRegion explosion_3TexttureRegion;
	public static TiledTextureRegion comboEffectTexttureRegion;
	public static TiledTextureRegion studentTexttureRegion;
	public static TextureRegion footStepTexttureRegion;
	public static AnimatedItemPool bookPool;
	public static AnimatedItemPool moneyPool;
	public static AnimatedItemPool giftPool;
	public static AnimatedItemPool clockPool;
	public static AnimatedItemPool shoesPool;
	public static AnimatedItemPool explosion_1Pool;
	public static AnimatedItemPool explosion_2Pool;
	public static AnimatedItemPool explosion_3Pool;
	public static AnimatedItemPool comboEffectPool;
	public static Student mStudent;
	
	
	private static BitmapTextureAtlas fireworkBitmapTextureAtlas;
	private static AnimatedItem[] firework = new AnimatedItem[4];	
	private static Text finish_Text;
			
	private static TiledTextureRegion[] fireworkTextureRegion = new TiledTextureRegion[4];	
	
	
	public static void init(GameActivity base)
	{			
		context = base;
		splash = new SplashSceneLoadingLevel(context);
		Level_1_Class = new Level_1_Class_Scene();
		Level_2_Market = new Level_2_Market_Scene();
		Level_3_Park = new Level_3_Park_Scene();
		Level_4_Kitchen = new Level_4_Kitchen_Scene();
		
		finishLevel = new FinishLevelScene(context);
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		sheetBitmapTextureAtlas = new BitmapTextureAtlas(2048, 2048);
		countdownTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(sheetBitmapTextureAtlas, context, "count down.png",0,0,4,3);
		studentTexttureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(sheetBitmapTextureAtlas, context, "Student.png",1000,0,4,4);
		footStepTexttureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(sheetBitmapTextureAtlas, context, "footstep.png", 1280, 0);
		// item 
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game script/item/");
		bookTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(sheetBitmapTextureAtlas, context, "book1.png",0,750,5,1);
		moneyTexttureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(sheetBitmapTextureAtlas, context, "coin.png",0,810,4,2);
		giftTexttureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(sheetBitmapTextureAtlas, context, "gift.png",1000,336,1,5);
		clockTexttureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(sheetBitmapTextureAtlas, context, "clock.png",300,830,4,1);
		shoesTexttureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(sheetBitmapTextureAtlas, context, "shoes.png",300,750,4,1);
		explosion_1TexttureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(sheetBitmapTextureAtlas, context, "explosion_1.png",1350,0,5,4);
		explosion_2TexttureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(sheetBitmapTextureAtlas, context, "explosion_2.png",1350,360,5,2);
		explosion_3TexttureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(sheetBitmapTextureAtlas, context, "explosion_3.png",0,950,5,5);
		comboEffectTexttureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(sheetBitmapTextureAtlas, context, "effect_004.png",1000,686,5,4);
		
		loadTexture(sheetBitmapTextureAtlas);
		bookPool = new AnimatedItemPool(bookTextureRegion);
		moneyPool = new AnimatedItemPool(moneyTexttureRegion);
		giftPool = new AnimatedItemPool(giftTexttureRegion);
		shoesPool = new AnimatedItemPool(shoesTexttureRegion);
		clockPool = new AnimatedItemPool(clockTexttureRegion);
		explosion_1Pool = new AnimatedItemPool(explosion_1TexttureRegion);
		explosion_2Pool = new AnimatedItemPool(explosion_2TexttureRegion);
		explosion_3Pool = new AnimatedItemPool(explosion_3TexttureRegion);
		comboEffectPool = new AnimatedItemPool( comboEffectTexttureRegion);
		mStudent = new Student(0, 0, studentTexttureRegion);
		mStudent.setFootStepsPoolTexture(footStepTexttureRegion);
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game script/finish level/");
		fireworkBitmapTextureAtlas = new BitmapTextureAtlas(2048, 2048);		
		
		fireworkTextureRegion[0] =  BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(fireworkBitmapTextureAtlas, context, "effect_004.png",0,0,5,4);
		fireworkTextureRegion[1] =  BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(fireworkBitmapTextureAtlas, context, "effect_005.png",960,0,5,4);
		fireworkTextureRegion[2] =  BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(fireworkBitmapTextureAtlas, context, "effect_006.png",0,768,5,6);
		fireworkTextureRegion[3] =  BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(fireworkBitmapTextureAtlas, context, "effect_010.png",960,768,5,6);			
		loadTexture(fireworkBitmapTextureAtlas);					    	    	
    		
    	finish_Text = new Text(LevelManager.getCamera().getWidth()/2-50,LevelManager.getCamera().getHeight()/2, SceneManager.mfontCombo, "FINISH",HorizontalAlign.CENTER);
	}
	
	public static void load(){		
		isLoaded = true;
		switch(Level_id){
			case SPLASHSCENE: 
				if(!splash.isLoaded)
					splash.loadResources(context);
				 else splash.update(context);
				break;
			case FINISH_LEVEL: 
				finishLevel.isLoaded = true;
				finishLevel.loadResources(context); // finish level
				break;			
			case LEVEL_1_CLASS: 		
				Level_1_Class.isLoaded = true;
				Level_1_Class.loadResources(context); // level 1 - class
				break;
			case LEVEL_2_MARKET: 				
				Level_2_Market.isLoaded = true;
				Level_2_Market.loadResources(context); // level 2 - market
				break;
			case LEVEL_3_PARK: 				
				Level_3_Park.isLoaded = true;
				Level_3_Park.loadResources(context); // level 3 - park
				break;
			case LEVEL_4_KITCHEN:
				Level_4_Kitchen.isLoaded = true;
				Level_4_Kitchen.loadResources(context); // level 4 - kitchen
				break;
		}		
	}
	
	public static Scene run() {
		if (Level_id == SPLASHSCENE)
			return splash.run();
		unloadLastScene();
		switch(Level_id){	
		case FINISH_LEVEL: 
			return finishLevel.run();
		case LEVEL_1_CLASS: 
			return Level_1_Class.run(); // level 1 - class
		case LEVEL_2_MARKET:
			return Level_2_Market.run();
		case LEVEL_3_PARK:
			return Level_3_Park.run();
		case LEVEL_4_KITCHEN:
			return Level_4_Kitchen.run();
		default: 
			return null;
		}
		
	}
	
	
	public static void unload() {
		
		switch(Level_id){
		case SPLASHSCENE: 
			splash.unloadResources(context);
			splash.isLoaded = false;
			break;	
		case FINISH_LEVEL: 
			finishLevel.isLoaded = false;
			finishLevel.unloadResources(context); // finish level
			break;
		case LEVEL_1_CLASS: 
			Level_1_Class.unloadResources(context); 
			Level_1_Class.isLoaded = false;
			break;
		case LEVEL_2_MARKET:
			Level_2_Market.unloadResources(context);
			Level_2_Market.isLoaded = false;
			break;
		case LEVEL_3_PARK:
			Level_3_Park.unloadResources(context);
			Level_3_Park.isLoaded = false;
			break;
		case LEVEL_4_KITCHEN:
			Level_4_Kitchen.unloadResources(context);
			Level_4_Kitchen.isLoaded = false;
			break;
		}
		System.gc();
	}
	
	public static void unloadLastScene() {
		switch(last_Level_id){
		case FINISH_LEVEL: 
			finishLevel.isLoaded = true;
			finishLevel.unloadResources(context); // finish level
			break;	
		case LEVEL_1_CLASS: 
			Level_1_Class.unloadResources(context); 
			Level_1_Class.isLoaded = false;
			break;
		case LEVEL_2_MARKET:
			Level_2_Market.unloadResources(context);
			Level_2_Market.isLoaded = false;
			break;
		case LEVEL_3_PARK:
			Level_3_Park.unloadResources(context);
			Level_3_Park.isLoaded = false;
			break;
		case LEVEL_4_KITCHEN:
			Level_4_Kitchen.unloadResources(context);
			Level_4_Kitchen.isLoaded = false;
			break;
		}
		System.gc();
	}	
	
	/**
	 * animation when finish level
	 */
	public static void finishLevel(){
		Random ran = new Random();
		float pX, pY;
		
		for(int i = 0; i < 3; i++){
			pX = Grid.getPosX(ran.nextInt(Grid.NUM_COLUMN-3));
			pY = Grid.getPosY(ran.nextInt(Grid.NUM_ROW-3));
		
			firework[i] = new AnimatedItem(pX, pY, fireworkTextureRegion[i]);
			getScene().attachChild(firework[i]);
			firework[i].animate(100, true);
		}
		
		pX = -fireworkTextureRegion[3].getWidth()/10 + context.getEngine().getCamera().getWidth()/2;
		pY = -fireworkTextureRegion[3].getHeight()/12 + context.getEngine().getCamera().getHeight()/2;
	
		firework[3] = new AnimatedItem(pX, pY, fireworkTextureRegion[3]);
		getScene().attachChild(firework[3]);
		firework[3].animate(100, false, new IAnimationListener() {
			@Override
			public void onAnimationEnd(final AnimatedSprite pAnimatedSprite) {
				LevelManager.getEngine().runOnUpdateThread(new Runnable() {
					@Override
					public void run() {
						LevelManager.last_Level_id = LevelManager.Level_id; // last_level_id = level 1
						LevelManager.Level_id = LevelManager.FINISH_LEVEL; // level_id = finish scene						
											
	    				LevelManager.load();
	    				LevelManager.setScene(LevelManager.run());
						}							
					});
				}			
		});	
								
		getScene().attachChild(finish_Text);
	}
	
	/**
	 * animation when start level
	 */
	public static void startLevel(){
		countdown = new AnimatedItem(context.getEngine().getCamera().getWidth()/2 - countdownTextureRegion.getWidth()/2, context.getEngine().getCamera().getHeight()/2 - countdownTextureRegion.getHeight()/2, countdownTextureRegion);
		getScene().attachChild(countdown);
		countdown.animate(100, false, new IAnimationListener() {
			@Override
			public void onAnimationEnd(final AnimatedSprite pAnimatedSprite) {
				LevelManager.getEngine().runOnUpdateThread(new Runnable() {
					@Override
					public void run() {	
						switch(last_Level_id){
						case LEVEL_1_CLASS: 
							Level_1_Class.startLevel(); 							
							break;
						case LEVEL_2_MARKET:
							Level_2_Market.startLevel();
							break;
						case LEVEL_3_PARK:
							Level_3_Park.startLevel();
							break;
						case LEVEL_4_KITCHEN:
							Level_4_Kitchen.startLevel();
							break;
						}				    				
						}							
					});
				}			
		});	
	}
	
	/**
	 * Save scoer
	 */
	public static void saveScore(int score){
		GameActivity.mHighScore.setScore(score, LevelManager.Level, LevelManager.subLevel, LevelManager.getBaseGameActivity());
	}
		
	public static Engine getEngine(){
		return context.getEngine();
	}
	public static BaseGameActivity getBaseGameActivity(){
		return context;
	}
	public static Scene getScene(){
		return context.getEngine().getScene();
	}
	public static Camera getCamera(){
		return context.getEngine().getCamera();
	}
	/**
	 * setScene() is the function we'll be using to switch from one
	 * screen to another.
	 */
	public static void setScene(Scene scene)
	{		
		context.getEngine().setScene(scene);
	}
	
	// load/unload texture/ font
	public static void loadTexture(Texture texture)
	{
		context.getEngine().getTextureManager().loadTexture(texture);
	}
	public static void unloadTexture(Texture texture)
	{
		context.getEngine().getTextureManager().unloadTexture(texture);		
	}
	
	public static void loadFont(Font font)
	{
		context.getEngine().getFontManager().loadFont(font);
	}
	
	/**
	 * load all Entity to current Level's Scene
	 * @param type
	 * @param pX
	 * @param pY
	 */
	public static void loadEntity(final String type, final int pX, final int pY, final int id){
		switch(Level_id){	
		case LEVEL_1_CLASS: 
			Level_1_Class.loadEntity(type, pX, pY, id); // MenuGame			
			break;
		case LEVEL_2_MARKET:
			Level_2_Market.loadEntity(type, pX, pY, id);
			break;
		case LEVEL_3_PARK:
			Level_3_Park.loadEntity(type, pX, pY, id);
			break;
		case LEVEL_4_KITCHEN:
			Level_4_Kitchen.loadEntity(type, pX, pY, id);			
			break;
		}
	}
	public static void pause(){
		switch(Level_id){
		case SPLASHSCENE: break;				
		case FINISH_LEVEL:break;			
		case LEVEL_1_CLASS: 		
			Level_1_Class_Scene.musicBackground.pause();
			break;
		case LEVEL_2_MARKET: 				
			Level_2_Market_Scene.musicBackground.pause();				
			break;
		case LEVEL_3_PARK: 				
			Level_3_Park_Scene.musicBackground.pause();
			break;
		case LEVEL_4_KITCHEN:
			Level_4_Kitchen_Scene.musicBackground.pause();
			break;			
		}
	}
	public static void resumPause(){
		switch(Level_id){
		case SPLASHSCENE: break;				
		case FINISH_LEVEL:break;			
		case LEVEL_1_CLASS:
			Level_1_Class_Scene.musicBackground.resume();
				
			break;
		case LEVEL_2_MARKET: 				
			Level_2_Market_Scene.musicBackground.resume();				
			break;
		case LEVEL_3_PARK: 				
			Level_3_Park_Scene.musicBackground.resume();
			break;
		case LEVEL_4_KITCHEN:
			Level_4_Kitchen_Scene.musicBackground.resume();
			break;			
		}
	}
	public static void unloadItem(){
		bookPool.recycleAll();
		moneyPool.recycleAll();
		giftPool.recycleAll();
		shoesPool.recycleAll();
		clockPool.recycleAll();
		explosion_1Pool.recycleAll();
		explosion_2Pool.recycleAll();
		explosion_3Pool.recycleAll();	
		comboEffectPool.recycleAll();
		
	}
}