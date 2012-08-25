// date create 
// author Nguyen Thanh Linh
package lengkeng.group.mobileBlock;

import android.annotation.SuppressLint;
import java.util.Random;

import lengkeng.group.Grid.Grid;
import lengkeng.group.LevelManager.LevelManager;
import lengkeng.group.Student.ReuseMoveModifier;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.ease.EaseLinear;

public class MobileBlock extends AnimatedSprite{
	private boolean finishRegister = false;
	protected ReuseMoveModifier reuseMoveModifier;
	protected float Velocity = 300;
	protected int Direction = 2 ;	
	protected boolean finishStep = true;
	protected int nextCol =0;
	protected int nextRow =0;
	protected static final float TIME_CHANGE_DIRECTION_RANDOM = 3f;
	protected int[] qualityOfDirection = {0, 0, 0, 0};
	Random r = new Random();
	// qualityOfDirection luu tru kha nang di cua huong
	// neu huong i co va cham thi qualityOfDirection[i] = 0
	// neu huong i khong co va cham va la huong ngoc lai voi huong ban dau thi qualityOfDirection[i] = 1
	// neu huong i khong co va cham va khong la huong ngoc lai voi huong ban dau thi qualityOfDirection[i] = 2
	// neu huong i khong co va cham va chinh la huong ban dau thi qualityOfDirection[i] = 3
	// sau do khi ta tim huong di tiep theo thi huong di nao co quality cao nhat se dc chon
	
	
	protected IEntityModifierListener modifierListener = new IEntityModifierListener(){	
		@Override
		public void onModifierFinished(IModifier<IEntity> pModifier,
				IEntity pItem) {
			// TODO Auto-generated method stub			
			finishStep = true;	
		}

		@Override
		public void onModifierStarted(IModifier<IEntity> pModifier,
				IEntity pItem) {
			// TODO Auto-generated method stub
			switch(Direction){
			case 0:  // up
				animate(new long[]{100,100,100,100}, new int[]{12,13,15,14}, 0);
				break;
			case 1: // right 
				animate(new long[]{100,100,100,100}, new int[]{8,9,11,10}, 0);
				break;
			case 2: // down
				animate(new long[]{100,100,100,100}, new int[]{0,1,3,2}, 0);
				break;
			case 3: //  left
				animate(new long[]{100,100,100,100}, new int[]{4,5,7,6}, 0);
				break;
			}
			finishStep = false;			
		}		
	};
	public MobileBlock(float pX, float pY, TiledTextureRegion pTiledTextureRegion){
		super(pX, pY, pTiledTextureRegion);
		reuseMoveModifier = new ReuseMoveModifier(2, pX, pY, pX, pY, modifierListener, EaseLinear.getInstance());
//		reuseMoveModifier = new ReuseMoveModifier();
	      this.registerEntityModifier(reuseMoveModifier);
	      finishRegister = true;
	
	}
	/**
	 * register Entity Listener (reuseMoveModifier)
	 */
	public void registerListener(){
		if(!finishRegister){
			this.registerEntityModifier(reuseMoveModifier);
			finishRegister = true;
		}
			
		
	}
	/**
	 * unregister Entity Listener (reuseMoveModifier)
	 */
	public void unregisterListener(){
		if(finishRegister){
			this.unregisterEntityModifier(reuseMoveModifier);
			finishRegister = false;
		}
			
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
	public void move(int x, int y){ // di chuyển từ t�?a độ hiện tại đến t�?a độ x, y
		if(finishStep){				
			reuseMoveModifier.restart(getDuration(x,y), this.getX(), x, this.getY(), y);		
		}
	}
	public void move(){ // di chuyển
		if(finishStep){		
			this.caculateDirection();
			caculateNextStep(Direction);
			int x = Grid.getPosX(nextCol);
			int y = Grid.getPosY(nextRow);
			reuseMoveModifier.restart(getDuration(x,y), this.getX(), x, this.getY(), y);
		
		}
	}	
	public void changeDirection(){ // thay doi huong di tuan hoan
		switch (Direction){
		case 0: Direction = 1; break;
		case 1: Direction = 2; break;
		case 2: Direction = 3; break;
		case 3: Direction = 0; break;
		default: break;
		}		
	}
	public void caculateNextStep(int mDirection){ // tinh toan o tiep thep ma vat can di vao
		nextCol = Grid.getCol(this.getX());
		nextRow = Grid.getRow(this.getY());

		
		switch(mDirection){
		case 0: nextRow --; break;
		case 1: nextCol ++; break;
		case 2: nextRow ++; break;
		case 3: nextCol --; break;
		default: break;
		}
	}
	// kiem tra va cham voi chuong ngai vat co dinh va bien gioi 
	//nhung khong tinh  den Student
	public boolean checkCollision(int mDirection){ 
		caculateNextStep(mDirection);		
		if (nextCol == -1 || nextCol == Grid.NUM_COLUMN )
			return true;
		if (nextRow == -1 || nextRow == Grid.NUM_ROW)
			return true;
		if (Grid.checkBlock(nextRow, nextCol))
			return true;
		return false;
		
	}
	// tinh toan huong di cua chuong nagi vat di dong
	public void caculateDirection(){ 		
		//changeDirectionRanDom();		
		//while(this.checkCollision(Direction)){
		//	changeDirection();
		
		//}
		//collidesWithStudent = false;
		checkQualityOfDirection();
		Direction = findDirectionHasMaxQuality();
		
	}	
	public void checkQualityOfDirection(){
		for (int i = 0; i <4; i++){
			if (checkCollision(i))
				qualityOfDirection[i] = 0;
			else
				if (i == Direction)
					qualityOfDirection[i] = 3;
				else {
					if ((i % 2) == (Direction % 2))
						qualityOfDirection[i] = 1;
					else
						qualityOfDirection[i] = 2;	
				}	
				
		}
	}
	public int findDirectionHasMaxQuality(){
		for (int i =0; i<4; i++){
			if (qualityOfDirection[i] == 3){
				return i;
			}			
		}
		for (int i =0; i<4; i++){
			if (qualityOfDirection[i] == 2)
				return i;
		}
		for (int i =0; i<4; i++){
			if (qualityOfDirection[i] == 1)
				return i;
		}

		return 0; // truong hop khong co duong di chua giai quyet
		
			
			
		
	}
	public void changeDirectionRanDom(){ // thay doi huong di 1 cach ngau nhien
		TimerHandler Timer;
		float mEffectSpawnDelay = TIME_CHANGE_DIRECTION_RANDOM;

			Timer = new TimerHandler(mEffectSpawnDelay,true, new ITimerCallback(){
				@Override
				public void onTimePassed(TimerHandler pTimerHandler){
					
						int mDirection = Direction;
						while((mDirection % 2) == (Direction % 2)){							
							mDirection = r.nextInt(4);	
						}
						Direction = mDirection;					
					}	
				
			});
			LevelManager.getEngine().registerUpdateHandler(Timer);
			
		}
	/**
	 * set FinishRegisterListener of AI
	 */
	public void setFinishRegisterListener(boolean value){
		finishRegister = value;
	}
	/**
	 * 
	 * @return finishRegister of AI
	 */
	public boolean getFinishRegisterListener(){
		return finishRegister;
	}

}
