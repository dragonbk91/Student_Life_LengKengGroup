package lengkeng.group.Level_2;

import lengkeng.group.LevelManager.LevelManager;
import lengkeng.group.LevelManager.RandomItem;
import lengkeng.group.Level_2.Level_2_Market_Scene;
import lengkeng.group.Timer.Timer;

import org.anddev.andengine.engine.handler.IUpdateHandler;


public class GameLoopUpdateHandler implements IUpdateHandler{	

	
	@Override
	public void onUpdate(float pSecondsElapsed) {
		if(Level_2_Market_Scene.Touchable){			
			CarMove();
			RandomItem.checkCollidesWithItem(Level_2_Market_Scene.mStudent, Level_2_Market_Scene.score, Level_2_Market_Scene.timer);
			changeMusicBackgoundWhenTimeAlmostOut();
			
			CarUpCheckCollides();
			CarLeftCheckCollides();
			CarRightCheckCollides();
			CarDownCheckCollides();
			
			Level_2_Market_Scene.mStudent.move();		
			Level_2_Market_Scene.score.updateScore();
			Level_2_Market_Scene.timer.updateTimer();
		
			if(Timer.isTimeOut()){
				Level_2_Market_Scene.Touchable = false;								
				LevelManager.finishLevel.setResult(Level_2_Market_Scene.score.getResult(), Level_2_Market_Scene.score.getTextScore(), Level_2_Market_Scene.score.iscompletely() );												
				
				LevelManager.saveScore(Level_2_Market_Scene.score.getScore());
				LevelManager.finishLevel();
				LevelManager.getScene().clearUpdateHandlers();				
			}
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}		

	private void checkBikeCollidesWithGrid(final CarPool mBikePool){ // kiem tra nguoi di xe dap da cham den bien gioi grid chua. Neu cham den roi thi cho nguoi di xe dap bien mat
		for(Car mbike: mBikePool.arrBike){
			if (mbike.isAttachToScene)
				if (mbike.checkCollision()){
//					addExplosion(mbike.getX(), mbike.getY());
					mBikePool.recyclePoolItem(mbike);
				}
								
		}
	}

	// thuc hien di chuyen 2 xe dap va xoa bo chung khi het duong
	private void CarMove(){
		checkBikeCollidesWithGrid(Level_2_Market_Scene.CarDownPool);
		checkBikeCollidesWithGrid(Level_2_Market_Scene.CarUpPool);
		checkBikeCollidesWithGrid(Level_2_Market_Scene.CarLeftPool);
		checkBikeCollidesWithGrid(Level_2_Market_Scene.CarRightPool);
		
		for (Car mbike: Level_2_Market_Scene.CarDownPool.arrBike )
			if (mbike.isAttachToScene())
				mbike.move();
		for (Car mbike: Level_2_Market_Scene.CarUpPool.arrBike )
			if (mbike.isAttachToScene())
				mbike.move();		
		for (Car mbike: Level_2_Market_Scene.CarLeftPool.arrBike )
			if (mbike.isAttachToScene())
				mbike.move();
		for (Car mbike: Level_2_Market_Scene.CarRightPool.arrBike )
			if (mbike.isAttachToScene())
				mbike.move();	
	}
	// ham kiem tra va cham giua nguoi di xe dap va student
	
	private void CarUpCheckCollides(){
		for(Car mbike: Level_2_Market_Scene.CarUpPool.arrBike){
			if (mbike.isAttachToScene)
				if (mbike.checkCollidesWithStudent(Level_2_Market_Scene.mStudent)){
					mbike.studentCollidesWithBike(Level_2_Market_Scene.mStudent, Level_2_Market_Scene.dialog_Pool);

				}									
		}	

	}
	private void CarDownCheckCollides(){
		for(Car mbike: Level_2_Market_Scene.CarDownPool.arrBike){
			if (mbike.isAttachToScene)
				if (mbike.checkCollidesWithStudent(Level_2_Market_Scene.mStudent)){
					mbike.studentCollidesWithBike(Level_2_Market_Scene.mStudent, Level_2_Market_Scene.dialog_Pool);

				}									
		}	

	}
	private void CarLeftCheckCollides(){
		for(Car mbike: Level_2_Market_Scene.CarLeftPool.arrBike){
			if (mbike.isAttachToScene)
				if (mbike.checkCollidesWithStudent(Level_2_Market_Scene.mStudent)){
					mbike.studentCollidesWithBike(Level_2_Market_Scene.mStudent, Level_2_Market_Scene.dialog_Pool);

				}									
		}	

	}
	private void CarRightCheckCollides(){
		for(Car mbike: Level_2_Market_Scene.CarRightPool.arrBike){
			if (mbike.isAttachToScene)
				if (mbike.checkCollidesWithStudent(Level_2_Market_Scene.mStudent)){
					mbike.studentCollidesWithBike(Level_2_Market_Scene.mStudent, Level_2_Market_Scene.dialog_Pool);

				}									
		}	

	}
	

	public void changeMusicBackgoundWhenTimeAlmostOut(){
		 if (Level_2_Market_Scene.timer.getNumSec() == 10){
			 Level_2_Market_Scene.soundTickTack.play();
			 Level_2_Market_Scene.musicBackground.pause();			 
		 }
		 if (Level_2_Market_Scene.timer.getNumSec() > 10){
			 if (Level_2_Market_Scene.musicBackground != null)
				 if (!Level_2_Market_Scene.musicBackground.isPlaying())
					 Level_2_Market_Scene.musicBackground.resume();
		 }
	}
			 
	
}
