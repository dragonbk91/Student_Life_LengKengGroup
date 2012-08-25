// date create 
// author Nguyen Thanh Linh
package lengkeng.group.LevelManager;

import java.util.Random;

import lengkeng.group.GeneralClass.AnimatedItem;
import lengkeng.group.Grid.Grid;
import lengkeng.group.SceneManager.MenuGame;
import lengkeng.group.Score.Score;
import lengkeng.group.Student.Student;
import lengkeng.group.Timer.Timer;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener;

public class RandomItem {
	public static int NUM_BOOK;
	public static int NUM_CLOCK;
	public static int NUM_SHOES;
	public static int NUM_MONEY;
	public static int NUM_GIFT;
	
	public static int BOOK_DELAY;
	public static int CLOCK_DELAY;
	public static int MONEY_DELAY;
	public static int SHOES_DELAY;
	public static int GIFT_DELAY;
	
	public static String TAG_BOOK ="book";
	public static String TAG_CLOCK = "clock";
	public static String TAG_SHOES = "shoes";
	public static String TAG_GIFT = "gift";
	public static String TAG_MONEY = "money";
		
	private enum mItem {
    	book,
    	clock,
    	shoes,    	
    	money,
    	gift
    }
	
	static Random rand = new Random();

	public void createSpriteSpawnTimeHandler(Timer mTimer) { // 1s tao ra 1 target
//		addRandomItem("book", mTimer);
//		addRandomItem("book", mTimer);
//		addRandomItem("book", mTimer);
//		addRandomItem("book", mTimer);
//		addRandomItem("book", mTimer);
//		addRandomItem("book", mTimer);
//		addRandomItem("money", mTimer);
//		addRandomItem("gift", mTimer);
//		addRandomItem("clock", mTimer);
//		addRandomItem("shoes", mTimer);
		for(int i = 0; i< NUM_BOOK; i++)
			addRandomItem("book", mTimer);
		for(int i = 0; i< NUM_CLOCK; i++)
			addRandomItem("clock", mTimer);
		for(int i = 0; i< NUM_GIFT; i++)
			addRandomItem("gift", mTimer);
		for(int i = 0; i< NUM_MONEY; i++)
			addRandomItem("money", mTimer);
		for(int i = 0; i< NUM_SHOES; i++)
			addRandomItem("shoes", mTimer);
	}
	
	private void addRandomItem(final String type, final Timer mTimer){
		TimerHandler spriteTimerHandler;
		float mEffectSpawnDelay = 1.0f;	
		mItem currentSprite = mItem.valueOf(type);

		switch(currentSprite){
		case book:
//			mEffectSpawnDelay = 1.0f;		
			mEffectSpawnDelay = BOOK_DELAY;
			break;
		case clock:
//			mEffectSpawnDelay = 30.0f;		
			mEffectSpawnDelay = CLOCK_DELAY;
			break;
		case shoes:
//			mEffectSpawnDelay = 25.0f;
			mEffectSpawnDelay = SHOES_DELAY;
			break;
		case money:
//			mEffectSpawnDelay = 3.0f;
			mEffectSpawnDelay = MONEY_DELAY;
			break;
		case gift:
//			mEffectSpawnDelay = 5.0f;
			mEffectSpawnDelay = GIFT_DELAY;
			break;
		}// end switch
		
		spriteTimerHandler = new TimerHandler(mEffectSpawnDelay, true,
				new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						addItem(type, mTimer);
					}
				});
		LevelManager.getEngine().registerUpdateHandler(spriteTimerHandler);		
	}
	
	private void addItem(final String type, final Timer mTimer){
		
		final mItem currentSprite = mItem.valueOf(type);
		final int x;
		final int y;
		// x : cot ----------------------
		// y : hang ---------------------
		x = rand.nextInt(Grid.NUM_COLUMN);
		y = rand.nextInt(Grid.NUM_ROW);
		
		if (!Grid.checkItem(y, x) && !Grid.checkBlock(y, x) && LevelManager.explosion_1Pool.inScene) {
			Grid.setItem(y, x, true);
			
			final AnimatedItem explosion = LevelManager.explosion_1Pool.obtainPoolItem();
			explosion.setPosition(Grid.getPosX(x), Grid.getPosY(y));				
			
			if (!explosion.isAttachToScene()) {
				LevelManager.getScene().attachChild(explosion);
				explosion.setAttachToScene(true);
			}
			
			explosion.animate(25, false, new IAnimationListener () {
			    @Override
			    public void onAnimationEnd(final AnimatedSprite pAnimatedSprite) {
					LevelManager.getEngine().runOnUpdateThread(new Runnable() {
						@Override
						public void run() {
							if(LevelManager.explosion_1Pool.inScene)
								LevelManager.explosion_1Pool.recyclePoolItem(explosion);
							
							// add item															
								AnimatedItem item = null;
								float duration = 1.0f;								
								switch(currentSprite){
								case book:			
									duration = (float) (((float)mTimer.getNumSec()) / 60 + 0.6);
									if(LevelManager.bookPool.inScene){
										item = LevelManager.bookPool.obtainPoolItem();
										if (!item.isAttachToScene()) {
											LevelManager.getScene().attachChild(item);
											item.setAttachToScene(true);
										}
									}
									break;
								case clock:
									duration = (float) (((float)mTimer.getNumSec())/ 60 + 0.8);
									if(LevelManager.clockPool.inScene){
										item = LevelManager.clockPool.obtainPoolItem();
										if (!item.isAttachToScene()) {
											LevelManager.getScene().attachChild(item);
											item.setAttachToScene(true);
										}
									}
									break;
								case shoes:
									duration = (float) (((float)mTimer.getNumSec()) / 60 + 1);
									if(LevelManager.shoesPool.inScene){
										item = LevelManager.shoesPool.obtainPoolItem();
										if (!item.isAttachToScene()) {
											LevelManager.getScene().attachChild(item);
											item.setAttachToScene(true);
										}
									}
									break;
								case money:
									duration = (float) (((float)mTimer.getNumSec()) / 60 + 0.6);
									if(LevelManager.moneyPool.inScene){
										item = LevelManager.moneyPool.obtainPoolItem();
										if (!item.isAttachToScene()) {
											LevelManager.getScene().attachChild(item);
											item.setAttachToScene(true);
										}
									}
									break;
								case gift:
									if(LevelManager.giftPool.inScene){
										item = LevelManager.giftPool.obtainPoolItem();
										if (!item.isAttachToScene()) {
											LevelManager.getScene().attachChild(item);
											item.setAttachToScene(true);
										}
									}
									break;
								}// end switch
								
								if(item != null ){
									item.setPosition(Grid.COLUMN[x] + 5, Grid.ROW[y] + 5);
									if (!item.isAttachToScene()) {
										LevelManager.getScene().attachChild(item);
										item.setAttachToScene(true);
									}
									animate(item, duration, type);	
								}																
						}
					});
				}
			});
		}
		
	}
	
	private void animate(final AnimatedItem item, final float duration, final String type){
		item.animate((long) (1000*duration/item.getTextureRegion().getTileCount()), 1, new IAnimationListener() {
			@Override
			public void onAnimationEnd(final AnimatedSprite pAnimatedSprite) {
				LevelManager.getEngine().runOnUpdateThread(new Runnable() {
					@Override
					public void run() {
						Grid.setItem(Grid.getRow(item.getY()),Grid.getCol(item.getX()), false);
						switch(mItem.valueOf(type)){
						case book:
							addExplosion_2(item.getX(), item.getY());
							if(LevelManager.bookPool.inScene)
								LevelManager.bookPool.recyclePoolItem(item);
							break;
						case clock:
							addExplosion_2(item.getX(), item.getY());
							if(LevelManager.clockPool.inScene)
								LevelManager.clockPool.recyclePoolItem(item);
							break;
						case shoes:
							addExplosion_2(item.getX(), item.getY());
							if(LevelManager.shoesPool.inScene)
								LevelManager.shoesPool.recyclePoolItem(item);
							break;
						case money:
							addExplosion_2(item.getX(), item.getY());
							if(LevelManager.moneyPool.inScene)
								LevelManager.moneyPool.recyclePoolItem(item);
							break;
						case gift:
							addExplosion_2(item.getX(), item.getY());
							if(LevelManager.giftPool.inScene)
								LevelManager.giftPool.recyclePoolItem(item);
							break;
						}// end switch								
					}
				});
			}
		});	
	}
	/**
	 * them hinh vu no khi item bien mat ma khong bi an
	 * @param x
	 * @param y
	 */
	public void addExplosion_2(final float x, final float y){
		if(LevelManager.explosion_2Pool.inScene){
			final AnimatedItem explosion = LevelManager.explosion_2Pool.obtainPoolItem();
			explosion.setPosition(x, y);		
			
			// x : cot ----------------------
			// y : hang ---------------------
	//		if(Grid.checkInScene(x, y))
				Grid.setItem(Grid.getRow(y), Grid.getCol(x), false);
			
			if (!explosion.isAttachToScene()) {
				LevelManager.getScene().attachChild(explosion);
				explosion.setAttachToScene(true);
			}
			
			explosion.animate(50, false, new IAnimationListener () {
			    @Override
			    public void onAnimationEnd(final AnimatedSprite pAnimatedSprite) {
					LevelManager.getEngine().runOnUpdateThread(new Runnable() {
						@Override
						public void run() {
							if(LevelManager.explosion_2Pool.inScene)
								LevelManager.explosion_2Pool.recyclePoolItem(explosion);
						}
					});
				}
			});
		}
	}
	/**
	 * them hinh vu no khi ma item bi an mat
	 * @param x
	 * @param y
	 */
	public static void addExplosion_3(final float x, final float y){
		final AnimatedItem explosion = LevelManager.explosion_3Pool.obtainPoolItem();
		explosion.setPosition(x, y);		
		MenuGame.soundEatItem.play();
		
		// x : cot ----------------------
		// y : hang ---------------------
		Grid.setItem(Grid.getRow(y), Grid.getCol(x), false);
		
		if (!explosion.isAttachToScene()) {
			LevelManager.getScene().attachChild(explosion);
			explosion.setAttachToScene(true);
		}
		
		explosion.animate(20, false, new IAnimationListener () {
		    @Override
		    public void onAnimationEnd(final AnimatedSprite pAnimatedSprite) {
				LevelManager.getEngine().runOnUpdateThread(new Runnable() {
					@Override
					public void run() {
						LevelManager.explosion_3Pool.recyclePoolItem(explosion);
					}
				});
			}
		});
	}
	public static void checkCollidesWithItem(Student mStudent, Score mScore, Timer mTimer){
		checkCollidesWithBook(mStudent, mScore);
		checkCollidesWithClock(mStudent, mTimer);
		checkCollidesWithGift(mStudent, mScore);
		checkCollidesWithMoney(mStudent, mScore);
		checkCollidesWithShoes(mStudent);		
	}
	
	public static void checkCollidesWithBook(Student mStudent, Score mScore){
		for (AnimatedItem book : LevelManager.bookPool.arrAniamatedItem) {
			if ( (book.collidesWith(mStudent)&& (book.isAttachToScene))){
				addExplosion_3(book.getX(), book.getY());		
				LevelManager.bookPool.recyclePoolItem(book);		
				mScore.incScore(1); // tang diem								
			}
		}
	}
	public static void checkCollidesWithMoney(Student mStudent, Score mScore){
		for (AnimatedItem money : LevelManager.moneyPool.arrAniamatedItem) {
			if ( (money.collidesWith(mStudent)&& (money.isAttachToScene))){
				addExplosion_3(money.getX(), money.getY());		
				LevelManager.moneyPool.recyclePoolItem(money);		
				mScore.incScore(5); // tang diem								
			}
		}
	}
	public static void checkCollidesWithGift(Student mStudent, Score mScore){
		for (AnimatedItem gift : LevelManager.giftPool.arrAniamatedItem) {
			if ( (gift.collidesWith(mStudent)&& (gift.isAttachToScene))){
				addExplosion_3(gift.getX(), gift.getY());		
				LevelManager.giftPool.recyclePoolItem(gift);		
				mScore.incScore(10); // tang diem								
			}
		}
	}
	
	public static void checkCollidesWithClock(Student mStudent, Timer mTimer){
		for (AnimatedItem clock : LevelManager.clockPool.arrAniamatedItem) {
			if ( (clock.collidesWith(mStudent)&& (clock.isAttachToScene))){
				addExplosion_3(clock.getX(), clock.getY());	
				LevelManager.clockPool.recyclePoolItem(clock);		
				mTimer.incSecond(10); // tang diem
			}
		}
	}
	
	public static void checkCollidesWithShoes(Student mStudent){
		for (AnimatedItem shoes : LevelManager.shoesPool.arrAniamatedItem) {
			if ( (shoes.collidesWith(mStudent)&& (shoes.isAttachToScene))){
				addExplosion_3(shoes.getX(), shoes.getY());	
				LevelManager.shoesPool.recyclePoolItem(shoes);				
				mStudent.setVelocity(mStudent.getVelocity() + 150); // tang toc do
			}
		}
	}
	
	public static void loadParam(final String type, final int num, final int time_delay){		
		if(type.equals(TAG_BOOK)){
			NUM_BOOK = num;
			BOOK_DELAY = time_delay;
		} else if(type.equals(TAG_GIFT)){
			NUM_GIFT = num;
			GIFT_DELAY = time_delay;
		}else if(type.equals(TAG_MONEY)){
			NUM_MONEY = num;
			MONEY_DELAY = time_delay;
		}else if(type.equals(TAG_SHOES)){
			NUM_SHOES = num;
			SHOES_DELAY = time_delay;
		}else if(type.equals(TAG_CLOCK)){
			NUM_CLOCK = num;
			CLOCK_DELAY = time_delay;
		}
	}
}