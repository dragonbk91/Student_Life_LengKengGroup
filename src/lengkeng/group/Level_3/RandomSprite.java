//package lengkeng.group.Level_3;
//
//import java.util.Random;
//
//import lengkeng.group.GeneralClass.AnimatedItem;
//import lengkeng.group.Grid.Grid;
//import lengkeng.group.LevelManager.LevelManager;
//
//import org.anddev.andengine.engine.handler.timer.ITimerCallback;
//import org.anddev.andengine.engine.handler.timer.TimerHandler;
//import org.anddev.andengine.entity.sprite.AnimatedSprite;
//import org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
//
//public class RandomSprite {
//	private enum mItem {
//    	clock,
//    	shoes,
//    	item,
//    }
//	
//	static Random rand = new Random();
//
//	public void createSpriteSpawnTimeHandler() { // 1s tao ra 1 target
//		addRandomItem("item");
//		addRandomItem("item");
//		addRandomItem("item");
//		addRandomItem("clock");
//		addRandomItem("shoes");		
//	}
//	
//	private void addRandomItem(final String type){
//		TimerHandler spriteTimerHandler;
//		float mEffectSpawnDelay = 1.0f;	
//		mItem currentSprite = mItem.valueOf(type);
//
//		switch(currentSprite){
//		case item:
//			mEffectSpawnDelay = 1.0f;			
//			break;
//		case clock:
//			mEffectSpawnDelay = 30.0f;			
//			break;
//		case shoes:
//			mEffectSpawnDelay = 25.0f;			
//			break;
//		}// end switch
//		
//		spriteTimerHandler = new TimerHandler(mEffectSpawnDelay, true,
//				new ITimerCallback() {
//					@Override
//					public void onTimePassed(TimerHandler pTimerHandler) {
//						addItem(type);
//					}
//				});
//		LevelManager.getEngine().registerUpdateHandler(spriteTimerHandler);		
//	}
//	
//	private void addItem(final String type){
//		
//		final mItem currentSprite = mItem.valueOf(type);
//		final int x;
//		final int y;
//		// x : cot ----------------------
//		// y : hang ---------------------
//		x = rand.nextInt(Grid.NUM_COLUMN);
//		y = rand.nextInt(Grid.NUM_ROW);
//		
//		if (!Grid.checkItem(y, x) && !Grid.checkBlock(y, x)) {
//			Grid.setItem(y, x, true);
//			final AnimatedItem explosion = Level_3_Park_Scene.explosion_1Pool.obtainPoolItem();
//			explosion.setPosition(Grid.getPosX(x), Grid.getPosY(y));				
//			
//			if (!explosion.isAttachToScene()) {
//				LevelManager.getScene().attachChild(explosion);
//				explosion.setAttachToScene(true);
//			}			
//			explosion.animate(25, false, new IAnimationListener () {
//			    @Override
//			    public void onAnimationEnd(final AnimatedSprite pAnimatedSprite) {
//					LevelManager.getEngine().runOnUpdateThread(new Runnable() {
//						@Override
//						public void run() {
//							Level_3_Park_Scene.explosion_1Pool.recyclePoolItem(explosion);
//							
//							// add item							
//								
//								AnimatedItem item = null;// = new AnimatedItem(-10, -10, null);
//								float duration = 1.0f;
//								
//								switch(currentSprite){
//								case item:			
////									duration = 1.0f;
//									duration = (float) (((float)Level_3_Park_Scene.timer.getNumSec()) / 60 + 0.6);
//									item = Level_3_Park_Scene.ItemPool.obtainPoolItem();
//									if (!item.isAttachToScene()) {
//										LevelManager.getScene().attachChild(item);
//										item.setAttachToScene(true);
//									}
//									break;
//								case clock:
////									duration = 1.5f;
//									duration = (float) (((float)Level_3_Park_Scene.timer.getNumSec())/ 60 + 0.8);
//									item = Level_3_Park_Scene.ClockPool.obtainPoolItem();
//									if (!item.isAttachToScene()) {
//										LevelManager.getScene().attachChild(item);
//										item.setAttachToScene(true);
//									}
//									break;
//								case shoes:
////									duration = 2.0f;
//									duration = (float) (((float)Level_3_Park_Scene.timer.getNumSec()) / 60 + 1);
//									item = Level_3_Park_Scene.ShoesPool.obtainPoolItem();
//									if (!item.isAttachToScene()) {
//										LevelManager.getScene().attachChild(item);
//										item.setAttachToScene(true);
//									}
//									break;
//								}// end switch
//								
//								if(item != null ){
//									item.setPosition(Grid.COLUMN[x] + 5, Grid.ROW[y] + 5);
//									if (!item.isAttachToScene()) {
//										LevelManager.getScene().attachChild(item);
//										item.setAttachToScene(true);
//									}
//									animate(item, duration, type);	
//								}																
//						}
//					});
//				}
//			});
//		}
//		
//	}
//	
//	private void animate(final AnimatedItem item, final float duration, final String type){
//		item.animate((long) (1000*duration/item.getTextureRegion().getTileCount()), 1, new IAnimationListener() {
//			@Override
//			public void onAnimationEnd(final AnimatedSprite pAnimatedSprite) {
//				LevelManager.getEngine().runOnUpdateThread(new Runnable() {
//					@Override
//					public void run() {
//						Grid.setItem(Grid.getRow(item.getY()),Grid.getCol(item.getX()), false);
//						switch(mItem.valueOf(type)){
//						case item:
//							addExplosion(item.getX(), item.getY());
//							Level_3_Park_Scene.ItemPool.recyclePoolItem(item);
//							break;
//						case clock:
//							addExplosion(item.getX(), item.getY());
//							Level_3_Park_Scene.ClockPool.recyclePoolItem(item);
//							break;
//						case shoes:
//							addExplosion(item.getX(), item.getY());
//							Level_3_Park_Scene.ShoesPool.recyclePoolItem(item);
//							break;
//						}// end switch								
//					}
//				});
//			}
//		});	
//	}
//	
//	private void addExplosion(final float x, final float y){
//		final AnimatedItem explosion = Level_3_Park_Scene.explosion_2Pool.obtainPoolItem();
//		explosion.setPosition(x, y);		
//		
//		// x : cot ----------------------
//		// y : hang ---------------------
//		if(Grid.checkInScene(x, y))
//			Grid.setItem(Grid.getRow(y), Grid.getCol(x), false);
//		
//		if (!explosion.isAttachToScene()) {
//			LevelManager.getScene().attachChild(explosion);
//			explosion.setAttachToScene(true);
//		}
//		
//		explosion.animate(50, false, new IAnimationListener () {
//		    @Override
//		    public void onAnimationEnd(final AnimatedSprite pAnimatedSprite) {
//				LevelManager.getEngine().runOnUpdateThread(new Runnable() {
//					@Override
//					public void run() {
//						Level_3_Park_Scene.explosion_2Pool.recyclePoolItem(explosion);
//					}
//				});
//			}
//		});
//	}
//}
package lengkeng.group.Level_3;


import lengkeng.group.LevelManager.RandomItem;
public class RandomSprite {

	private RandomItem randomItem = new RandomItem();

	public void createSpriteSpawnTimeHandler() { // 1s tao ra 1 target
		randomItem.createSpriteSpawnTimeHandler(Level_3_Park_Scene.timer);
	}
}