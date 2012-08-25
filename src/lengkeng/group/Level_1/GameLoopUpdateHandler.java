package lengkeng.group.Level_1;

import lengkeng.group.LevelManager.LevelManager;
import lengkeng.group.LevelManager.RandomItem;
import lengkeng.group.Timer.Timer;
import lengkeng.group.mobileBlock.AI;

import org.anddev.andengine.engine.handler.IUpdateHandler;

public class GameLoopUpdateHandler implements IUpdateHandler{
	
	
	@Override
	public void onUpdate(float pSecondsElapsed) {
		// TODO Auto-generated method stub
		if(Level_1_Class_Scene.Touchable){
			RandomItem.checkCollidesWithItem(Level_1_Class_Scene.mStudent, Level_1_Class_Scene.score, Level_1_Class_Scene.timer);
			changeMusicBackgoundWhenTimeAlmostOut();	
			for(AI teacher : Level_1_Class_Scene.teacherPool.arrAI){
				if (teacher.checkCollidesWithStudent(Level_1_Class_Scene.mStudent)){
					teacher.studentCollidesWithMobileBlock(Level_1_Class_Scene.mStudent, Level_1_Class_Scene.dialog_Pool);
					Level_1_Class_Scene.soundTeacher.play();				
				}		
				teacher.move();
			}
			Level_1_Class_Scene.mStudent.move();
		
			Level_1_Class_Scene.score.updateScore();
			Level_1_Class_Scene.timer.updateTimer();
				
		
			if(Timer.isTimeOut()){
				Level_1_Class_Scene.Touchable = false;	
																
				LevelManager.finishLevel.setResult(Level_1_Class_Scene.score.getResult(), Level_1_Class_Scene.score.getTextScore(), Level_1_Class_Scene.score.iscompletely() );
				LevelManager.saveScore(Level_1_Class_Scene.score.getScore());
				LevelManager.finishLevel();
				LevelManager.getScene().clearUpdateHandlers();				
			}
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	public void changeMusicBackgoundWhenTimeAlmostOut(){
		 if (Level_1_Class_Scene.timer.getNumSec() == 10){
			 Level_1_Class_Scene.soundTickTack.play();
			 Level_1_Class_Scene.musicBackground.pause();			 
		 }
		 if (Level_1_Class_Scene.timer.getNumSec() > 10){
			 if (Level_1_Class_Scene.musicBackground != null)
				 if (!Level_1_Class_Scene.musicBackground.isPlaying())
					 Level_1_Class_Scene.musicBackground.resume();
		 }
			 
	}
	
}
