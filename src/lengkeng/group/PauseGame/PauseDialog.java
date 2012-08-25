package lengkeng.group.PauseGame;

import org.anddev.andengine.engine.Engine;

import lengkeng.group.Game.Activity.R;
import lengkeng.group.LevelManager.LevelManager;

import lengkeng.group.SceneManager.SceneManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.Button;

public class PauseDialog extends Dialog{
	public final static int FINISH_LEVEL= -1;	
	public final static int SPLASHSCENE= 0;	
	public final static int LEVEL_1_CLASS = 1;
    public final static int LEVEL_2_MARKET = 2;
    public final static int LEVEL_3_PARK = 3;
    public final static int LEVEL_4_KITCHEN = 4;
    private boolean alreadyPress = false;

	
	public int bool_thoat = 2;

	public PauseDialog(Context context,final Activity activity,final Engine mEngine){//, final Music backgroundMusic) {		
		super(context);		
		this.setTitle(Html.fromHtml("<b>PAUSE</b>"));
		this.setContentView(R.layout.pause);
		LevelManager.pause();
		Button exit = (Button)findViewById(R.id.button_exit);
		exit.setOnClickListener(new View.OnClickListener() {
			
			
			@Override
			public void onClick(View arg0) {
				if (!alreadyPress){
					bool_thoat = 1;				
					if(activity != null){
						SceneManager.unload();
						LevelManager.unloadItem();
						activity.finish();			
						android.os.Process.killProcess(android.os.Process.myPid());
					}
					PauseDialog.this.dismiss();
					alreadyPress = true;					
				}				
			}
		});
		
		Button resum = (Button)findViewById(R.id.button_resume);
		resum.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!alreadyPress){
					LevelManager.resumPause();
					
					//mEngine.getMusicManager().setMasterVolume(1.0f);
					//mEngine.getSoundManager().setMasterVolume(1.0f);
					bool_thoat = 0;									
//						if(!backgroundMusic.isPlaying())
//							backgroundMusic.play();						
						if(!mEngine.isRunning())
							mEngine.start();
					//SceneManager.setScene(SceneManager.run());
					PauseDialog.this.dismiss();	
					alreadyPress = true;
				}
				
			}
		});
		

	}	
}
