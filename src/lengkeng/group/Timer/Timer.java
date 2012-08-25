package lengkeng.group.Timer;

import lengkeng.group.LevelManager.LevelManager;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.opengl.buffer.BufferObjectManager;
import org.anddev.andengine.opengl.font.Font;

public class Timer extends ChangeableText{	

	public static int min = 0;
	public static int sec = 0;	
	public String time;
	
	public Timer(float pX, float pY, Font pFont, String pText, int r) {
		super(pX, pY, pFont, pText, r);
		// TODO Auto-generated constructor stub
	}
	
	public void setTime(final int pMin, final int pSec){
		min = pMin;
		sec = pSec;
		if(pSec<10) time = min + ":0" + sec;
		else time = min + ":" + sec;
	}
	
	public int getNumSec(){
		return min*60 + sec;
	}
	// increase pSec seconds
	public void incSecond(final int pSec){
		sec += pSec;
		if (sec >= 60 ){
			min++;
			sec -= 60;
		}
	}

	// decrease 1 second
	public void decSecond(){
		if(sec <= 0){
			if(min>0) min --;
			sec = 59;
		}
		else{
			sec--;
		}
	}
	
	public String getTime(){
		if(sec<10) time = min + ":0" + sec;
		else time = min + ":" + sec;
		return time;
	}
	
	public int getMin(){
		return min;
	}
	
	public int getSec(){
		return sec;
	}
	
	public static boolean isTimeOut(){
		return ( (min==sec) && (sec ==0));
	}
	
	public void UpdateHandler(){ // 1s tao ra 1 target
		TimerHandler Timer;
		float mEffectSpawnDelay = 1.0f;
		
		Timer = new TimerHandler(mEffectSpawnDelay,true, new ITimerCallback(){
			@Override
			public void onTimePassed(TimerHandler pTimerHandler){
				Timer.this.decSecond();			
			}
		});
		LevelManager.getEngine().registerUpdateHandler(Timer);
	}
	
	public void updateTimer(){
		this.setText(getTime());
	}
	
	public void unload(){
		this.setIgnoreUpdate(true);
		this.setVisible(false);
		this.reset();
		this.detachSelf();
		BufferObjectManager.getActiveInstance().unloadBufferObject(this.getVertexBuffer());				
	}
}
