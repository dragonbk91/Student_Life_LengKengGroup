package lengkeng.group.Score;

import lengkeng.group.GeneralClass.AnimatedItem;
import lengkeng.group.GeneralClass.AnimatedItemPool;
import lengkeng.group.LevelManager.LevelManager;
import lengkeng.group.SceneManager.SceneManager;

import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.opengl.buffer.BufferObjectManager;
import org.anddev.andengine.opengl.font.Font;

public class Score extends ChangeableText{
	private int mScore = 0;
	private int ScoreRequirements = 0;
	private int lastScore = 0; // luu so diem cua lan tinh combo truoc
	private int comboScore = 0;
	public Score(float pX, float pY, Font mFont, String pText,int r){
		super(pX, pY, mFont, pText,r);
	}
	
	public void incScore(final int _score){
		this.mScore += _score;		
	}
	public void decScore(final int _score){
		this.mScore -= _score;		
	}
	
	public void setScoreRequirements(int _score){
		this.ScoreRequirements = _score;
	}
	public int getScoreRequirements(){
		return this.ScoreRequirements;
	}
	
	public int getScore(){
		return this.mScore;
	}
	
	public String getTextScore(){
		String s = String.valueOf(this.mScore) + "/" + this.ScoreRequirements;
		return s;
	}

	public void updateScore(){
		this.setText(getTextScore());
	}
	
	public boolean iscompletely(){
		return (this.mScore >= this.ScoreRequirements);
	}
	
	public String getResult(){
		if(!iscompletely())
			return "Fail!";
		if(this.mScore > this.ScoreRequirements*2)
			return "Excellent!";
		if(this.mScore > this.ScoreRequirements*1.5)
			return "Great!";
		if(this.mScore > this.ScoreRequirements*1.2)
			return "Good!";
		if( (this.mScore >= this.ScoreRequirements))
			return "Passable!";						
		return "";
	}
	
	public void unload(){
		this.setIgnoreUpdate(true);
		this.setVisible(false);
		this.reset();
		this.detachSelf();
		BufferObjectManager.getActiveInstance().unloadBufferObject(this.getVertexBuffer());		
	}
	public int getLastScore(){
		return lastScore;
	}
	public void setLastScore(int value){
		lastScore = value;
	}
	public void createComboScore(final Score pScore, final AnimatedItemPool effectPool, final Sound pSoundGood){
		TimerHandler spriteTimerHandler;
		float mEffectSpawnDelay = 10.0f;
		spriteTimerHandler = new TimerHandler(mEffectSpawnDelay, true,
				new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						pScore.comboScore = pScore.getScore() - pScore.getLastScore();
						if (pScore.comboScore >= 20)
							addText(pSoundGood, pScore, 10,40, effectPool);
					}
				});
		LevelManager.getEngine().registerUpdateHandler(spriteTimerHandler);		
	}
	private void addText(final Sound pSound, final Score pScore, final float x, final float y, final AnimatedItemPool effectPool){
		final AnimatedItem animationItem = effectPool.obtainPoolItem();
		
		animationItem.setPosition(x,y-20);
		// x : cot ----------------------
		// y : hang ---------------------
		if (animationItem.isAttachToScene() == false) {
			LevelManager.getScene().attachChild(animationItem);
			animationItem.setAttachToScene(true);
		}
		pSound.play();
		final ChangeableText mComboText = new ChangeableText(x, y, SceneManager.mfontCombo,"+ " +(pScore.comboScore-10),  10);
		mComboText.setColor(244, 33, 33);
		LevelManager.getScene().attachChild(mComboText);
		animationItem.animate((long)100, false, new IAnimationListener () {
		    @Override
		    public void onAnimationEnd(final AnimatedSprite pAnimatedSprite) {
				LevelManager.getEngine().runOnUpdateThread(new Runnable() {
					@Override
					public void run() {
						effectPool.recyclePoolItem(animationItem);			
						pScore.incScore(pScore.comboScore-10);								
						pScore.setLastScore(pScore.getScore());
						pScore.comboScore = 0;
						LevelManager.getScene().detachChild(mComboText);
						}
				});
			}
		});
	}

	





	
}
