package lengkeng.group.Level_2;

import lengkeng.group.GeneralClass.AnimatedItem;
import lengkeng.group.GeneralClass.AnimatedItemPool;
import lengkeng.group.GeneralClass.StaticItem;
import lengkeng.group.Grid.Grid;
import lengkeng.group.LevelManager.LevelManager;
import lengkeng.group.Student.ReuseMoveModifier;
import lengkeng.group.Student.Student;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.anddev.andengine.opengl.buffer.BufferObjectManager;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.ease.EaseLinear;

import android.annotation.SuppressLint;

public class Car extends StaticItem{
	
	private ReuseMoveModifier reuseMoveModifier;
	private float Velocity = 300;
	private int Direction = 0 ; 
	
	private int destinationX = 0;
	private int destinationY = 0;
	boolean finishedCheckCollidesWithBlock = false;
	private boolean finishedregister = false;
	private float TIME_STUN = 1f;
	private float TIME_RELEASE = 3f;
	private int loopCountanimationItem = 1;	
	
	protected IEntityModifierListener modifierListener = new IEntityModifierListener(){	
		@Override
		public void onModifierFinished(IModifier<IEntity> pModifier,
				IEntity pItem) {
			// TODO Auto-generated method stub	
		}
		@Override
		public void onModifierStarted(IModifier<IEntity> pModifier,
				IEntity pItem) {
			// TODO Auto-generated method stub
		}		
	};
	
	public Car(float pX, float pY, TextureRegion pTextureRegion){
		super(pX, pY, pTextureRegion);
		reuseMoveModifier = new ReuseMoveModifier(2, pX, pY, pX, pY, modifierListener, EaseLinear.getInstance());
	     this.registerEntityModifier(reuseMoveModifier);
	
	}
	public void registerListener(){
		this.registerEntityModifier(reuseMoveModifier);
	}
	public void unregisterListener(){
		this.unregisterEntityModifier(reuseMoveModifier);
		
	}
	public void setVelocity(float velocity){
		Velocity = velocity; 
	}
	public float getVelocity(){
		return Velocity;
	}
	public void setDirection(int direction){
		this.Direction = direction;
	}
	public int getDirection(){
		return this.Direction;
	}
	
	@SuppressLint("FloatMath")
	private float getDuration(final float x, final float y){
		float d = (float) Math.sqrt((this.getX() - x)*(this.getX() - x) + (this.getY() - y)*(this.getY() - y)) / this.Velocity;
		return (float) (d+0.001);
	}
	public void move(){ // di chuyen
		//if( (!finishedCheckCollidesWithBlock) && finishedStep){		
			caculateNextStep();
			if(!checkCollision()){						
				reuseMoveModifier.restart(getDuration(this.destinationX,this.destinationY), this.getX(), this.destinationX, this.getY(), this.destinationY);
			}
		//}
	}
	public void caculateNextStep(){ // tinh toan o tiep thep ma vat can di vao		
		switch(Direction){
		case 0: 
			this.destinationX = (int) this.getX();
			this.destinationY = -100;
			break;
		case 1: 
			this.destinationX = Grid.WIDTH + 100;
			this.destinationY = (int) this.getY(); 
			break;
		case 2: 
			this.destinationX = (int) this.getX();
			this.destinationY = Grid.HEIGHT + 100;
			break;
		case 3: 
			this.destinationX = -100;
			this.destinationY = (int) this.getY();
			break;
		default: break;
		}
	}
	public void removeMe(){		
		this.setIgnoreUpdate(true);
		this.setVisible(false);
		this.isAttachToScene = false;
		this.reset();
		this.detachSelf();
//		this.clearUpdateHandlers();
//		this.clearEntityModifiers();
		BufferObjectManager.getActiveInstance().unloadBufferObject(this.getVertexBuffer());				
	}
	public boolean checkCollision(){ 
		if( this.getX() <= -100) return true;
		if( this.getY() <= -100) return true;
		if( this.getX() >= Grid.WIDTH + 100) return true;
		if( this.getY() >= Grid.WIDTH + 100) return true;
		return false;
	}
	public float getTIME_STUN(){
		return TIME_STUN;
	}
	public void setTIME_STUN(float value){
		TIME_STUN = value;
	}
	
	public float getTIME_REALEASE(){
		return TIME_RELEASE;
	}
	public void setTIME_REALEASE(float value){
		TIME_RELEASE = value;
	}
	
	// kiem tra va cham voi student
	
	public boolean checkCollidesWithStudent(Student mStudent){
		float studentX = mStudent.getX();
		float studentY = mStudent.getY();
		float AIX = getX();
		float AIY = getY();

		if ( (this.collidesWith(mStudent)) && !finishedCheckCollidesWithBlock){
			if(studentX == AIX){
				if (this.Direction == 2 && ((studentY - AIY) > 30))
					return true;
				if (this.Direction == 0 && ((AIY - studentY) > 30))
					return true;
				return false;
			} if (studentY == AIY){
				if (this.Direction == 1 && ((studentX - AIX) > 30))
					return true;
				if (this.Direction == 3 && ((AIX - studentX) > 30))
					return true;
				return false;							
			} 
			return false;			
		}
		return false;
	}
	
	public void studentCollidesWithBike(final Student mStudent, final  AnimatedItemPool animationItemPool){		
		float AIX = getX();
		float AIY = getY();
		Level_2_Market_Scene.soundCarCollides.play();
		mStudent.unregisterListener();
		mStudent.recyclePath();
		mStudent.setTouchEnable(false);

		
		this.unregisterListener();		
		finishedCheckCollidesWithBlock = true; 
		changAnimateOfStudent(mStudent);		
		
		// can chinh toa do cho dung' ung' voi tung` nhan vat, tung hieu ung'
		addanimationItem(AIX, AIY-60, animationItemPool, mStudent);

		TimerHandler Timer2;
		
		float mEffectSpawnDelay2 = TIME_RELEASE;
		Timer2 = new TimerHandler(mEffectSpawnDelay2,false, new ITimerCallback(){
			@Override
			public void onTimePassed(TimerHandler pTimerHandler){
//				mStudent.setTouchEnable(true);
	            finishedCheckCollidesWithBlock = false;
	            finishedregister = false;
	            // nhung Timer chi dung 1 lan, roi lan sau lai tao new TimerHandler thi can unregister sau khi ket thuc
	            registerListener(); 
	            LevelManager.getEngine().unregisterUpdateHandler( pTimerHandler );	            
			}
		});
		LevelManager.getEngine().registerUpdateHandler(Timer2);
	}
	public void addanimationItem(final float x, final float y,final  AnimatedItemPool animationItemPool,final Student mStudent){ // them vao hieu ung trai tim khi nguoi va cham vao chuong ngai vat di dong
		final AnimatedItem animationItem = animationItemPool.obtainPoolItem();		
		animationItem.setPosition(x, y);
		Level_2_Market_Scene.soundDialog.play();
		if (animationItem.isAttachToScene() == false) {
			LevelManager.getScene().attachChild(animationItem);
			animationItem.setAttachToScene(true);
		}
		animationItem.animate((long) (TIME_STUN*1000/loopCountanimationItem/animationItem.getTextureRegion().getTileCount()), loopCountanimationItem, new IAnimationListener () {
		    @Override
		    public void onAnimationEnd(final AnimatedSprite pAnimatedSprite) {
				LevelManager.getEngine().runOnUpdateThread(new Runnable() {
					@Override
					public void run() {						
					if(!finishedregister){
							mStudent.registerListener();
				            mStudent.setTouchEnable(true);
				            finishedregister = true;
				            animationItemPool.recyclePoolItem(animationItem);				            
					}
					}
				});
			}
		});
	}
	
	private void changAnimateOfStudent(Student mStudent){
		switch(Direction){
		case 0: // AI's up 
			mStudent.stopAnimation(0);
			break;
		case 1: //  AI's right
			mStudent.stopAnimation(4);
			break;
		case 2:  // AI's down
			mStudent.stopAnimation(12);
			break;
		case 3: // AI's left
			mStudent.stopAnimation(8);
			break;
		default: break;
			
		}		
	}
}

