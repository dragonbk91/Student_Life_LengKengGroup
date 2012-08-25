package lengkeng.group.SceneManager;

import lengkeng.group.LevelManager.LevelManager;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.buffer.BufferObjectManager;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.HorizontalAlign;

import android.graphics.Color;

public class LevelSelector extends ManageableScene implements IManageableScene{
	private BitmapTextureAtlas bg_bitmapTextureAtlas;
	private TextureRegion bg_textureRegion; // luu khi load anh
	private Sprite bg_sprite; // sprite lam anh nen			
	
	private static BitmapTextureAtlas mFontTexture; // font vao bbo 			
	public static Font mfont; // luu lai font
	
	private Text[] subLevel = new Text[LevelManager.NUM_LEVEL+1];
	
	private boolean isTouch = false;
	
	public LevelSelector(BaseGameActivity context) {
		super();
		// TODO Auto-generated constructor stub
	}	

	@Override
	public void loadResources(BaseGameActivity context) {	

		this.isLoaded = true;		
		this.isTouch = false;
		mScene.setTouchAreaBindingEnabled(true);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game script/menu game/");
		// background

		bg_bitmapTextureAtlas = new BitmapTextureAtlas(1024,512,TextureOptions.DEFAULT); // luu anh vao bo nho
		bg_textureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bg_bitmapTextureAtlas,context,"menu_background.png",0,0);
		
		SceneManager.loadTexture( bg_bitmapTextureAtlas );				
		
		bg_sprite = new Sprite(0,0,bg_textureRegion);
		mScene.setBackground(new SpriteBackground(bg_sprite));
		MenuGame.musicBackground.play();
	}
	
	private void loadTexture(){
		mFontTexture = new BitmapTextureAtlas(256,256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);		
		FontFactory.setAssetBasePath("font/");        
        mfont = FontFactory.createFromAsset(mFontTexture, SceneManager.getBaseGameActivity(), "BRADHITC.TTF", 60, true, Color.rgb(85, 91, 87));
    	//load
    	SceneManager.loadTexture(mFontTexture);    	
    	SceneManager.loadFont(mfont);
    	
    	for(int i = 1; i<= LevelManager.NUM_SUBLEVEL; i++){
			final int sublevel = i;
			subLevel[i] = new Text(LevelManager.getCamera().getWidth()/2 + 10,50 + (i-1)*100, mfont, "Level "+  i,HorizontalAlign.CENTER){				    	
				    	// chuyen sang chon level
				    	@Override
				    	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX,final float pTouchAreaLocalY){
				    			if(pSceneTouchEvent.getAction()== TouchEvent.ACTION_DOWN && !isTouch){
				    				isTouch = true;
				    				addExplosion(subLevel[sublevel].getX(), subLevel[sublevel].getY(), sublevel);
				    			}
				    			return true;
				    		}
				    	};
			subLevel[i].setPosition(LevelManager.getCamera().getWidth()/2 - subLevel[i].getWidth()/2 + 50, 100 + (i-1)*100);
			mScene.attachChild(subLevel[i]);
			mScene.registerTouchArea(subLevel[i]);
		}	   
	}
	
	@Override
	public Scene run() {
		loadTexture();
		return this.mScene;
	}

	@Override
	public void unloadResources(BaseGameActivity context) {
		// TODO Auto-generated method stub

		for(int i=1; i<= LevelManager.NUM_SUBLEVEL; i++)
			removeButton(subLevel[i]);
		
		mScene.detachChildren();
		mScene.detachSelf();		
		mScene.clearUpdateHandlers();
		mScene.clearEntityModifiers();
		mScene.clearTouchAreas();
		mScene.clearUpdateHandlers();
		SceneManager.unloadTexture(bg_bitmapTextureAtlas);
		SceneManager.unloadTexture(mFontTexture);
		mScene.reset();	
	}
	
	private void addExplosion(final float x, final float y, final int level){
		mScene.attachChild(MenuGame.explosion);
		MenuGame.explosion.setPosition(x + 50, y - 40);
		MenuGame.soundPushButton.play();
		MenuGame.explosion.animate(50, false, new IAnimationListener() {
			
			@Override
			public void onAnimationEnd(AnimatedSprite pAnimatedSprite) {
				SceneManager.last_Menu_id = SceneManager.CHOOSE_LEVEL;
				SceneManager.Menu_id = SceneManager.CHOOSE_SUBLEVEL;
				LevelManager.Level = level;

				SceneManager.load();
				SceneManager.setScene(SceneManager.run());
				SceneManager.getScene().detachChild(MenuGame.explosion);
			}
		});		
	}	
	
	public void removeButton(Text button){
		BufferObjectManager.getActiveInstance().unloadBufferObject(button.getVertexBuffer());
		button.setIgnoreUpdate(true);
		button.setVisible(false);
		button.reset();
		button.detachSelf();				
	}
}


