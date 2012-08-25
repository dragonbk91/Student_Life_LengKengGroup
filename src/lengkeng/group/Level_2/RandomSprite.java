package lengkeng.group.Level_2;

import java.util.Random;

import lengkeng.group.Grid.Grid;
import lengkeng.group.LevelManager.LevelManager;
import lengkeng.group.LevelManager.RandomItem;
import lengkeng.group.Timer.Timer;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;

public class RandomSprite {
	private enum mItem {

    	carUp,
    	carDown,
    	carLeft,
    	carRight
    }
	private RandomItem randomItem = new RandomItem();
	static Random rand = new Random();
	final int x_carRight = 0;
	final int y_carRight = 1;
	final int direction_carRight = 1;
	
	final int x_carLeft = 8;
	final int y_carLeft = 3;
	final int direction_carLeft = 3;
	
	final int x_carDown = 6;
	final int y_carDown = 0;
	final int direction_carDown = 2;
	
	final int x_carUp = 2;
	final int y_carUp = 4;
	final int direction_carUp = 0;
	
	public void createSpriteSpawnTimeHandler() { // 1s tao ra 1 target

		randomItem.createSpriteSpawnTimeHandler(Level_2_Market_Scene.timer);
	
		if (Level_2_Market_Scene.carDown) 
			addRandomItem("carDown");
		if (Level_2_Market_Scene.carUp) 
			addRandomItem("carUp");
		if (Level_2_Market_Scene.carRight) 
			addRandomItem("carRight");
		if (Level_2_Market_Scene.carLeft) 
			addRandomItem("carLeft");
	}
	
	private void addRandomItem(final String type){
		TimerHandler spriteTimerHandler;
		float mEffectSpawnDelay = 1.0f;	
		mItem currentSprite = mItem.valueOf(type);

		switch(currentSprite){

		case carDown:
			mEffectSpawnDelay =  (Level_2_Market_Scene.carDown_mEffectSpawnDelay);			
			break;
		case carLeft:
			mEffectSpawnDelay =  (Level_2_Market_Scene.carLeft_mEffectSpawnDelay);			
			break;
		case carRight:
			mEffectSpawnDelay =  (Level_2_Market_Scene.carRight_mEffectSpawnDelay);			
			break;
		case carUp:
			mEffectSpawnDelay =  (Level_2_Market_Scene.carUp_mEffectSpawnDelay);			
			break;
		}// end switch
		
		spriteTimerHandler = new TimerHandler(mEffectSpawnDelay, true,
				new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						addItem(type);
					}
				});
		LevelManager.getEngine().registerUpdateHandler(spriteTimerHandler);		
	}
	
	private void addItem(final String type){
		if( type == "carUp"){
			if(!Timer.isTimeOut() )//&& !Car.finishedCheckCollidesWithBlock)
				addBike(0, x_carUp, y_carUp, direction_carUp);				
		}
		if( type == "carLeft"){
			if(!Timer.isTimeOut() ){//&& !Car.finishedCheckCollidesWithBlock){
				addBike(3, x_carLeft, y_carLeft, direction_carLeft);									    	
			}		
		}
		if( type == "carDown"){
			if(!Timer.isTimeOut() )//&& !Car.finishedCheckCollidesWithBlock)
				addBike(2, x_carDown, y_carDown, direction_carDown);				
		}
		if( type == "carRight"){
			if(!Timer.isTimeOut() ){//&& !Car.finishedCheckCollidesWithBlock){
				addBike(1, x_carRight, y_carRight, direction_carRight);									    	
			}		
		}
		

		
	}
	

	private void addBike(final int choose, final int x_bike, final int y_bike, final int direction){

			Car car = null;
			switch(choose){
			case 0: // up
				car = Level_2_Market_Scene.CarUpPool.obtainPoolItem();
				car.setVelocity(Level_2_Market_Scene.carUp_velocity);
//							car.setPosition(Grid.COLUMN[x_carUp], Grid.ROW[y_carUp]);
				car.setPosition(Grid.COLUMN[x_carUp], Grid.HEIGHT + 95);
				car.setDirection(direction_carUp);						
				break;
			case 1: // right
				car = Level_2_Market_Scene.CarRightPool.obtainPoolItem();
				car.setVelocity(Level_2_Market_Scene.carRight_velocity);
//							car.setPosition(Grid.COLUMN[x_carRight], Grid.ROW[y_carRight]);
				car.setPosition(-80, Grid.ROW[y_carRight]);
				car.setDirection(direction_carRight);							
				break;
			case 2: // down
				car = Level_2_Market_Scene.CarDownPool.obtainPoolItem();
				car.setVelocity(Level_2_Market_Scene.carDown_velocity);
//							car.setPosition(Grid.COLUMN[x_carDown], Grid.ROW[y_carDown]);
				car.setPosition(Grid.COLUMN[x_carDown], - 95);
				car.setDirection(direction_carDown);							
				break;
			case 3: // left
				car = Level_2_Market_Scene.CarLeftPool.obtainPoolItem();
				car.setVelocity(Level_2_Market_Scene.carLeft_velocity);
//							car.setPosition(Grid.COLUMN[x_carLeft], Grid.ROW[y_carLeft]);
				car.setPosition(Grid.WIDTH + 95, Grid.ROW[y_carLeft]);
				car.setDirection(direction_carLeft);							
				break;
			}						
			if (!car.isAttachToScene()) {
				LevelManager.getScene().attachChild(car);
				car.setAttachToScene(true);
				}
			}
	
}