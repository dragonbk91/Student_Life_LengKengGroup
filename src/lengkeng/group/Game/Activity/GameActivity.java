package lengkeng.group.Game.Activity;



import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import lengkeng.group.LevelManager.LevelManager;
import lengkeng.group.PauseGame.PauseDialog;
import lengkeng.group.SceneManager.SceneManager;
import android.view.KeyEvent;
import lengkeng.group.Game.Activity.HighScore;

public class GameActivity extends BaseGameActivity {
	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;
	private PauseDialog dialogPause = null;
	private Camera mCamera; 
	public static HighScore mHighScore = new HighScore();
	
	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		mHighScore.creatScore(this);		
	}

	@Override
	public Engine onLoadEngine() {
		mCamera = new Camera(0,0,CAMERA_WIDTH,CAMERA_HEIGHT);
		// FillResolutionPolicy() is used here in an attempt to cater for
		// multiple screen resolutions
		EngineOptions eo = new EngineOptions(true, ScreenOrientation.LANDSCAPE, new FillResolutionPolicy(), this.mCamera);
		eo.getTouchOptions().setRunOnUpdateThread(true);
		eo.getRenderOptions().disableExtensionVertexBufferObjects();
		eo.setNeedsMusic(true).setNeedsSound(true);
		return  new Engine(eo);		
	}

	@Override
	public void onLoadResources() {
		SceneManager.init(this);
		// call the load method from our first screen,
		// therefore loading all the assets we're going to need
		// at this moment in time
		SceneManager.load();
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
        return SceneManager.run();// TODO Auto-generated method stub		
	}
	
	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent){
		if(pKeyCode == KeyEvent.KEYCODE_BACK && pEvent.getAction() == KeyEvent.ACTION_DOWN){
			if(SceneManager.Menu_id == SceneManager.GAME_PLAY && LevelManager.Level_id!=LevelManager.SPLASHSCENE && LevelManager.Level_id!=LevelManager.FINISH_LEVEL){
				if(this.mEngine.isRunning()){	
					mEngine.stop();				
				}	
					dialogPause = new PauseDialog(GameActivity.this, GameActivity.this,GameActivity.this.mEngine);
					dialogPause.show();
					return true;				
			}

			if(SceneManager.Menu_id == SceneManager.CHOOSE_LEVEL){				
				SceneManager.last_Menu_id = SceneManager.CHOOSE_LEVEL;
				SceneManager.Menu_id = SceneManager.MENUSCENE;				
				SceneManager.load();
				SceneManager.setScene(SceneManager.run());
				return true;
			}
			
			if(SceneManager.Menu_id == SceneManager.CHOOSE_SUBLEVEL){
				SceneManager.last_Menu_id = SceneManager.CHOOSE_SUBLEVEL;
				SceneManager.Menu_id = SceneManager.CHOOSE_LEVEL;				
				SceneManager.load();
				SceneManager.setScene(SceneManager.run());
				return true;
			}
			
			if(SceneManager.Menu_id == SceneManager.INTRODUCTION){
				SceneManager.last_Menu_id = SceneManager.INTRODUCTION;
				SceneManager.Menu_id = SceneManager.MENUSCENE;				
				SceneManager.load();
				SceneManager.setScene(SceneManager.run());
				return true;
			}
			
			if(SceneManager.Menu_id == SceneManager.FINISHGAME){
				SceneManager.last_Menu_id = SceneManager.FINISHGAME;
				SceneManager.Menu_id = SceneManager.MENUSCENE;				
				SceneManager.load();
				SceneManager.setScene(SceneManager.run());
				return true;
			}
			
			if(SceneManager.Menu_id == SceneManager.GAME_PLAY && (LevelManager.Level_id==LevelManager.SPLASHSCENE || LevelManager.Level_id==LevelManager.FINISH_LEVEL)){
				return false;
			}
			
			if(SceneManager.Menu_id == SceneManager.MENUSCENE || SceneManager.Menu_id == SceneManager.SPLASHSCENE){
				return false;
			}
		}
			return super.onKeyDown(pKeyCode, pEvent);		
	}
   
	@Override
	public void onWindowFocusChanged(final boolean pHasWindowFocus) {
		if(dialogPause!=null){
			final boolean dialogShowing = dialogPause.isShowing();
			super.onWindowFocusChanged(pHasWindowFocus || dialogShowing);
		}
		else super.onWindowFocusChanged(pHasWindowFocus);
	}
	
	@Override
	protected void onPause() {
	        if (mEngine.isRunning()) {
	            mEngine.stop();
	        }
	    super.onPause();
	}
	
	@Override
	public void onResumeGame() {	    
	    if (!mEngine.isRunning()) {
            mEngine.start();
        }
	    super.onResumeGame();
	}		
	
}