package lengkeng.group.SceneManager;

import lengkeng.group.Game.Activity.GameActivity;
import lengkeng.group.LevelManager.LevelManager;

import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.graphics.Color;

/**
 * SceneManager
 * 
 * This SceneManager class is designed to handle the switching of 
 * scenes (or 'screens') within our game. Given that this class has access to the
 * core game activity, we're also going to use it for the loading and unloading of 
 * our assets, however you may wish to do this in a separate class in your own code.
 */

public class SceneManager {
	
	public final static int SPLASHSCENE= 0;	
	public final static int MENUSCENE = 1;
	public final static int CHOOSE_LEVEL = 2;
	public final static int CHOOSE_SUBLEVEL = 3;
	public final static int GAME_PLAY = 4;	
	public final static int INTRODUCTION = 5;	
	public final static int FINISHGAME = 6;
	
	public static BaseGameActivity context;
	
	public static SplashScene splash ;
	public static LevelSelector levelSelector;
	public static SubLevelSelector subLevelSelector;
	public static MenuGame menuGame;	
	public static Introduction introduction;
	public static FinishGame finishGame;
	
	public static int Menu_id = 0;	
	public static int last_Menu_id = 0;	
	
	public static BitmapTextureAtlas mFontTexture2; // font vao bbo nho	
	public static BitmapTextureAtlas mFontTexture3; // font vao bbo nho //	public static Font mfont; // luu lai font
	public static Font mfont2; // luu lai font
	public static Font mfontCombo;
	
	public static void init(GameActivity base)
	{
		context = base;		
		splash = new SplashScene(context);
		levelSelector = new LevelSelector(context);
		subLevelSelector = new SubLevelSelector(context);
		menuGame = new MenuGame(context);		
		introduction = new Introduction(context);
		finishGame = new FinishGame(context);
		
		Menu_id = 0;
		
		mFontTexture2 = new BitmapTextureAtlas(256,256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mFontTexture3 = new BitmapTextureAtlas(512,512,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		FontFactory.setAssetBasePath("font/");
        mfont2 = FontFactory.createFromAsset(mFontTexture2, context, "BRADHITC.TTF", 60, true, Color.rgb(85, 91, 87));
        mfontCombo = FontFactory.createFromAsset(mFontTexture3, context, "comic2.ttf", 60, true, Color.BLACK);
    	//load
    	loadTexture(mFontTexture2);
    	loadTexture(mFontTexture3);
    	loadFont(mfont2);
    	loadFont(mfontCombo);
    	
		LevelManager.init(base);
	}
	
	public static void load(){
		switch(Menu_id){
			case SPLASHSCENE:
				if(!splash.isLoaded) splash.loadResources(context);		
				else splash.update(context);
				break;
			case MENUSCENE: 
				menuGame.loadResources(context); // MenuGame
				break;
			case CHOOSE_LEVEL: 
//				if(!levelSelector.isLoaded)
					levelSelector.loadResources(context); // choose level
				break;
			case CHOOSE_SUBLEVEL: 
				subLevelSelector.loadResources(context); // choose level
				break;
			case GAME_PLAY:
				LevelManager.load();
				break;
			case INTRODUCTION:
				introduction.loadResources(context);
				break;
			case FINISHGAME:
				finishGame.loadResources(context);
				break;
		}		
	}	

		public static Scene run() {
			unloadLastScene();
			switch(Menu_id){
			case SPLASHSCENE: return splash.run();					
			case MENUSCENE: return menuGame.run(); // MenuGame
			case CHOOSE_LEVEL: return levelSelector.run(); // chooce level	
			case CHOOSE_SUBLEVEL: return subLevelSelector.run(); // chooce sublevel
			case INTRODUCTION: return introduction.run();
			case FINISHGAME: return finishGame.run(); 
			case GAME_PLAY:
				return LevelManager.run();
			default: return null;
			}
			
		}
		
		
		public static void unload() {
			switch(Menu_id){
			case SPLASHSCENE: 
				splash.unloadResources(context);
				splash.isLoaded = false;
				break;
			case MENUSCENE: 
				menuGame.unloadResources(context);
				menuGame.isLoaded = false;
				break;
			case CHOOSE_LEVEL: 
				levelSelector.unloadResources(context); // chooce level
				levelSelector.isLoaded = false;
				break;
			case CHOOSE_SUBLEVEL: 
				subLevelSelector.unloadResources(context); // chooce level
				subLevelSelector.isLoaded = false;
				break;		
			case INTRODUCTION: 
				introduction.unloadResources(context); // chooce level
				introduction.isLoaded = false;
				break;
			case FINISHGAME: 
				finishGame.unloadResources(context); // chooce level
				finishGame.isLoaded = false;
				break;	
			case GAME_PLAY:			
				LevelManager.isLoaded = false;
				LevelManager.unload();
				break;		
			}
			System.gc();
		}
		
		public static void unloadLastScene() {
			switch(last_Menu_id){
			case SPLASHSCENE: 			
				splash.unloadResources(context);
				splash.isLoaded = false;
				break;
			case MENUSCENE: 
				menuGame.unloadResources(context);
				menuGame.isLoaded = false;
				break;
			case CHOOSE_LEVEL: 
				levelSelector.unloadResources(context); // chooce level
				levelSelector.isLoaded = false;
				break;
			case CHOOSE_SUBLEVEL: 
				subLevelSelector.unloadResources(context); // chooce level
				subLevelSelector.isLoaded = false;
				break;	
			case INTRODUCTION: 
				introduction.unloadResources(context); // chooce level
				introduction.isLoaded = false;
				break;
			case FINISHGAME: 
				finishGame.unloadResources(context); // chooce level
				finishGame.isLoaded = false;
				break;
			case GAME_PLAY:
				LevelManager.isLoaded = false;
				LevelManager.unload();
				break;	
			}
			System.gc();
		}
	
	
	public static Engine getEngine(){
		return context.getEngine();
	}
	public static BaseGameActivity getBaseGameActivity(){
		return context;
	}
	/**
	 * setScene() is the function we'll be using to switch from one
	 * screen to another.
	 */
	public static void setScene(Scene scene)
	{
		context.getEngine().setScene(scene);
	}
	public static Scene getScene()
	{
		return context.getEngine().getScene();
	}
	
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
	
	public static void loadSound(Sound sound){
		
	}
}