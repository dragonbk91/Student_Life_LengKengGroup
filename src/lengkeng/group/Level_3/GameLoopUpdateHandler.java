package lengkeng.group.Level_3;

import java.util.Random;

import lengkeng.group.GeneralClass.AnimatedItem;
import lengkeng.group.Grid.Grid;
import lengkeng.group.LevelManager.LevelManager;
import lengkeng.group.LevelManager.RandomItem;
import lengkeng.group.Timer.Timer;

import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener;

public class GameLoopUpdateHandler implements IUpdateHandler{	
	private static boolean collidesGirl = false;
	
	@Override
	public void onUpdate(float pSecondsElapsed) {
		if(Level_3_Park_Scene.Touchable){
			RandomItem.checkCollidesWithItem(Level_3_Park_Scene.mStudent, Level_3_Park_Scene.score, Level_3_Park_Scene.timer);
			checkCollidesWithGirl();
			changeMusicBackgoundWhenTimeAlmostOut();
			
			for(MovingGirl movingGirl : Level_3_Park_Scene.movingGirlpool.arrMovingGirl){
				if (movingGirl.checkCollidesWithStudent(Level_3_Park_Scene.mStudent) && !collidesGirl){
					movingGirl.studentCollidesWithMobileBlock();
				}					
				movingGirl.move();
			}
			
			Level_3_Park_Scene.mStudent.move();		
			Level_3_Park_Scene.score.updateScore();
			Level_3_Park_Scene.timer.updateTimer();
		
			if(Timer.isTimeOut()){
				Level_3_Park_Scene.Touchable = false;								
				LevelManager.finishLevel.setResult(Level_3_Park_Scene.score.getResult(), Level_3_Park_Scene.score.getTextScore(), Level_3_Park_Scene.score.iscompletely() );												
				
				LevelManager.saveScore(Level_3_Park_Scene.score.getScore());
				LevelManager.finishLevel();
				LevelManager.getScene().clearUpdateHandlers();				
			}
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	

	
	private static void checkCollidesWithGirl(){
		int isCollides = 3;
		for(MovingGirl movingGirl : Level_3_Park_Scene.movingGirlpool.arrMovingGirl){
			if(!collidesGirl && !movingGirl.checkCollidesWithStudent(Level_3_Park_Scene.mStudent)){
				int col = Grid.getCol(Level_3_Park_Scene.mStudent.getX());
				int row = Grid.getRow(Level_3_Park_Scene.mStudent.getY());
				
				if((Level_3_Park_Scene.mStudent.getY() == Grid.ROW[Level_3_Park_Scene.Thunder_Row[0]])
						&& (Level_3_Park_Scene.mStudent.getX()< Grid.COLUMN[Level_3_Park_Scene.Thunder_Col[0]]+ 5)
						&& (col == Level_3_Park_Scene.Thunder_Col[0]) && (row == Level_3_Park_Scene.Thunder_Row[0])){
							isCollides = 0;										
				} else
					if((Level_3_Park_Scene.mStudent.getY() == Grid.ROW[Level_3_Park_Scene.Thunder_Row[2]])
							&& (Level_3_Park_Scene.mStudent.getX()< Grid.COLUMN[Level_3_Park_Scene.Thunder_Col[2]]+ 5)
							&& (col == Level_3_Park_Scene.Thunder_Col[2]) && (row == Level_3_Park_Scene.Thunder_Row[2])){
								isCollides = 2;										
					} else
						if((Level_3_Park_Scene.mStudent.getX() == Grid.COLUMN[Level_3_Park_Scene.Thunder_Col[1]])
								&& (Level_3_Park_Scene.mStudent.getY()< Grid.ROW[Level_3_Park_Scene.Thunder_Row[1]]+ 5)
								&& (col == Level_3_Park_Scene.Thunder_Col[1]) && (row == Level_3_Park_Scene.Thunder_Row[1])){
									isCollides = 1;										
						}
				
				if(isCollides != 3){
					Random ran = new Random();
					int collides1 = ran.nextInt(10);
					if(collides1 == 2){
						collidesGirl = true;
						switch (isCollides){
						case 0: 
							Level_3_Park_Scene.mStudent.stopAnimation(12);
							break;
						case 1:
							Level_3_Park_Scene.mStudent.stopAnimation(4);
							break;
						case 2:
							Level_3_Park_Scene.mStudent.stopAnimation(0);
							break;
						}
						Level_3_Park_Scene.mStudent.recyclePath();
						Level_3_Park_Scene.mStudent.setTouchEnable(false);
						Level_3_Park_Scene.mStudent.unregisterListener();
						addThunder(Level_3_Park_Scene.mStudent.getX(), Level_3_Park_Scene.mStudent.getY());
					}
				}
			}
		}
	}
	
	
	public static void addThunder(final float x, final float y){ // them vao hieu ung trai tim khi nguoi va cham vao chuong ngai vat di dong
		final AnimatedItem thunder = Level_3_Park_Scene.ThunderPool.obtainPoolItem();
		thunder.setPosition(x-20, y-30);		
		Level_3_Park_Scene.soundThunder.play();	
		
		if (!thunder.isAttachToScene()) {
			LevelManager.getScene().attachChild(thunder);
			thunder.setAttachToScene(true);
		}
		
		thunder.animate(100, false, new IAnimationListener () {
		    @Override
		    public void onAnimationEnd(final AnimatedSprite pAnimatedSprite) {
				LevelManager.getEngine().runOnUpdateThread(new Runnable() {
					@Override
					public void run() {
						Level_3_Park_Scene.ThunderPool.recyclePoolItem(thunder);
						addHeart(x, y);
					}
				});
			}
		});
	}
	
	public static void addHeart(final float x, final float y){ // them vao hieu ung trai tim khi nguoi va cham vao chuong ngai vat di dong
		final AnimatedItem heart = Level_3_Park_Scene.HeartPool.obtainPoolItem();
		heart.setPosition(x, y);		
		Level_3_Park_Scene.soundKiss.play();
		
		if (!heart.isAttachToScene()) {
			LevelManager.getScene().attachChild(heart);
			heart.setAttachToScene(true);
		}
		
		heart.animate(100, false, new IAnimationListener () {
		    @Override
		    public void onAnimationEnd(final AnimatedSprite pAnimatedSprite) {
				LevelManager.getEngine().runOnUpdateThread(new Runnable() {
					@Override
					public void run() {
						Level_3_Park_Scene.mStudent.setTouchEnable(true);
						Level_3_Park_Scene.mStudent.registerListener();
						collidesGirl = false;
						Level_3_Park_Scene.HeartPool.recyclePoolItem(heart);						
					}
				});
			}
		});
	}
	/**
	 * create sound tick tac
	 */
	public void changeMusicBackgoundWhenTimeAlmostOut(){
		 if (Level_3_Park_Scene.timer.getNumSec() == 10){
			 Level_3_Park_Scene.soundTickTack.play();
			 Level_3_Park_Scene.musicBackground.pause();			 
		 }
		 if (Level_3_Park_Scene.timer.getNumSec() > 10){
			 if(Level_3_Park_Scene.musicBackground != null)
				 if (!Level_3_Park_Scene.musicBackground.isPlaying())
					 Level_3_Park_Scene.musicBackground.resume();
		 }
			 
	}

}
