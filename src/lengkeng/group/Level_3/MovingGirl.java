package lengkeng.group.Level_3;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import lengkeng.group.GeneralClass.AnimatedItem;
import lengkeng.group.LevelManager.LevelManager;
import lengkeng.group.mobileBlock.AI;

public class MovingGirl extends AI{

	public MovingGirl(float pX, float pY, TiledTextureRegion pTiledTextureRegion) {
		super(pX, pY, pTiledTextureRegion);
		// TODO Auto-generated constructor stub
	}
	
	public void studentCollidesWithMobileBlock(){			
		Level_3_Park_Scene.mStudent.unregisterListener();
		Level_3_Park_Scene.mStudent.recyclePath();
		Level_3_Park_Scene.mStudent.setTouchEnable(false);
		
		unregisterListener();
		finishedCheckCollidesWithBlock = true; 
		changAnimateOfStudent(Level_3_Park_Scene.mStudent);
		changeDirectionWhenMeetStudent();
		
		// can chinh toa do cho dung' ung' voi tung` nhan vat, tung hieu ung'
		addThunder(Level_3_Park_Scene.mStudent.getX(), Level_3_Park_Scene.mStudent.getY());

		TimerHandler Timer2;
		
		float mEffectSpawnDelay2 = TIME_RELEASE;
		Timer2 = new TimerHandler(mEffectSpawnDelay2,false, new ITimerCallback(){
			@Override
			public void onTimePassed(TimerHandler pTimerHandler){
				Level_3_Park_Scene.mStudent.setTouchEnable(true);
	            finishedCheckCollidesWithBlock = false;
	            // nhung Timer chi dung 1 lan, roi lan sau lai tao new TimerHandler thi can unregister sau khi ket thuc
	            LevelManager.getEngine().unregisterUpdateHandler( pTimerHandler );

			}
		});
		LevelManager.getEngine().registerUpdateHandler(Timer2);
	}
	
	public void addThunder(final float x, final float y){ // them vao hieu ung trai tim khi nguoi va cham vao chuong ngai vat di dong
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
	
	public void addHeart(final float x, final float y){ // them vao hieu ung trai tim khi nguoi va cham vao chuong ngai vat di dong
		final AnimatedItem heart = Level_3_Park_Scene.HeartPool.obtainPoolItem();
		heart.setPosition(x, y);
		Level_3_Park_Scene.soundKiss.play();
		
		if (!heart.isAttachToScene()) {
			LevelManager.getScene().attachChild(heart);
			heart.setAttachToScene(true);
		}
		
		heart.animate((long) (1*1000/1/heart.getTextureRegion().getTileCount()), false, new IAnimationListener () {
		    @Override
		    public void onAnimationEnd(final AnimatedSprite pAnimatedSprite) {
				LevelManager.getEngine().runOnUpdateThread(new Runnable() {
					@Override
					public void run() {
						Level_3_Park_Scene.HeartPool.recyclePoolItem(heart);
						Level_3_Park_Scene.mStudent.registerListener();
						Level_3_Park_Scene.mStudent.setTouchEnable(true);
			            registerListener(); 
					}
				});
			}
		});
	}
	
}
