// date create 
// author Nguyen Thanh Linh
package lengkeng.group.Level_4;

import lengkeng.group.GeneralClass.AnimatedItem;
import lengkeng.group.Grid.Grid;
import lengkeng.group.LevelManager.LevelManager;
import lengkeng.group.LevelManager.RandomItem;
import lengkeng.group.Timer.Timer;
import lengkeng.group.mobileBlock.AI;

import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener;

public class GameLoopUpdateHandler implements IUpdateHandler{
	@Override
	public void onUpdate(float pSecondsElapsed) {
		// TODO Auto-generated method stub
		if(Level_4_Kitchen_Scene.Touchable){

			RandomItem.checkCollidesWithItem(Level_4_Kitchen_Scene.mStudent, Level_4_Kitchen_Scene.score, Level_4_Kitchen_Scene.timer);
			
			for(AI killer : Level_4_Kitchen_Scene.killerPool.arrAI){
				if (killer.checkCollidesWithStudent(Level_4_Kitchen_Scene.mStudent)){
					Level_4_Kitchen_Scene.soundDialog.play();
					killer.studentCollidesWithMobileBlock(Level_4_Kitchen_Scene.mStudent, Level_4_Kitchen_Scene.dialog_Pool);
				}
			
				killer.move();
				CheckKillerCollidesWithItem(killer);
			}
			changeMusicBackgoundWhenTimeAlmostOut();
			Level_4_Kitchen_Scene.mStudent.move();		
			Level_4_Kitchen_Scene.score.updateScore();
			Level_4_Kitchen_Scene.timer.updateTimer();
		
			if(Timer.isTimeOut()){
				Level_4_Kitchen_Scene.Touchable = false;								
				LevelManager.finishLevel.setResult(Level_4_Kitchen_Scene.score.getResult(), Level_4_Kitchen_Scene.score.getTextScore(), Level_4_Kitchen_Scene.score.iscompletely() );											
				LevelManager.saveScore(Level_4_Kitchen_Scene.score.getScore());
				LevelManager.finishLevel();
				LevelManager.getScene().clearUpdateHandlers();				
			}
		}
	}
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	
	private void addExplosion(final float x, final float y){
		final AnimatedItem explosion = LevelManager.explosion_3Pool.obtainPoolItem();
		explosion.setPosition(x, y);	
		Level_4_Kitchen_Scene.soundEatItem.play();
		
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
	private void CheckKillerCollidesWithItem(AI mkiller){
		for (AnimatedItem clock : LevelManager.clockPool.arrAniamatedItem) {
			if ( (clock.collidesWith(mkiller)&& (clock.isAttachToScene))){
				addExplosion(clock.getX(), clock.getY());
				LevelManager.clockPool.recyclePoolItem(clock);
			}
		}
		for (AnimatedItem shoes : LevelManager.shoesPool.arrAniamatedItem) {
			if ( (shoes.collidesWith(mkiller)&& (shoes.isAttachToScene))){
				addExplosion(shoes.getX(), shoes.getY());
				LevelManager.shoesPool.recyclePoolItem(shoes);
			}
		}
		for (AnimatedItem item : LevelManager.bookPool.arrAniamatedItem) {
			if ( (item.collidesWith(mkiller)&& (item.isAttachToScene))){
				addExplosion(item.getX(), item.getY());		
				LevelManager.bookPool.recyclePoolItem(item);
							
			}
		}
		for (AnimatedItem item : LevelManager.giftPool.arrAniamatedItem) {
			if ( (item.collidesWith(mkiller)&& (item.isAttachToScene))){
				addExplosion(item.getX(), item.getY());		
				LevelManager.giftPool.recyclePoolItem(item);
							
			}
		}
		for (AnimatedItem item : LevelManager.moneyPool.arrAniamatedItem) {
			if ( (item.collidesWith(mkiller)&& (item.isAttachToScene))){
				addExplosion(item.getX(), item.getY());		
				LevelManager.moneyPool.recyclePoolItem(item);
							
			}
		}
		
		
	}

	public static void adddialog(final float x, final float y){ // them vao hieu ung trai tim khi nguoi va cham vao chuong ngai vat di dong
		final AnimatedItem dialog = Level_4_Kitchen_Scene.dialog_Pool.obtainPoolItem();
		dialog.setPosition(x, y);
		//Level_4_Kitchen_Scene.soundDialog.play();
		// x : cot ----------------------
		// y : hang ---------------------
		if (dialog.isAttachToScene() == false) {
			LevelManager.getScene().attachChild(dialog);
			dialog.setAttachToScene(true);
		}
		dialog.animate(100, 4, new IAnimationListener () {
		    @Override
		    public void onAnimationEnd(final AnimatedSprite pAnimatedSprite) {
				LevelManager.getEngine().runOnUpdateThread(new Runnable() {
					@Override
					public void run() {
						Level_4_Kitchen_Scene.dialog_Pool.recyclePoolItem(dialog);
					}
				});
			}
		});
	}
	/**
	 * create sound tick tac
	 */
	public void changeMusicBackgoundWhenTimeAlmostOut(){
		 if (Level_4_Kitchen_Scene.timer.getNumSec() == 10){
			 Level_4_Kitchen_Scene.soundTickTack.play();
			 Level_4_Kitchen_Scene.musicBackground.pause();			 
		 }
		 if (Level_4_Kitchen_Scene.timer.getNumSec() > 10){
			 if (!Level_4_Kitchen_Scene.musicBackground.isPlaying())
				 Level_4_Kitchen_Scene.musicBackground.resume();
		 }			 
	}
	
}

	



