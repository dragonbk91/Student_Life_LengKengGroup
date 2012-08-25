package lengkeng.group.LevelManager;

import java.util.ArrayList;

import lengkeng.group.GeneralClass.AnimatedItem;
import lengkeng.group.GeneralClass.StaticItem;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
public class ManageableLevel {
	protected Scene mScene;
	public boolean isLoaded;
	protected int ScoreRequirements;
	
	protected BitmapTextureAtlas bg_bitmapTextureAtlas;
	protected TextureRegion bg_textureRegion; // luu khi load anh
	protected Sprite bg_sprite; // sprite lam anh nen	
	
	protected BitmapTextureAtlas sheetBitmapTextureAtlas; // luu anh vao bo nho
	
	
	protected BitmapTextureAtlas mFontTexture; // font vao bbo nho
	protected Font mfont; // luu lai font
	
	// Block array
	protected ArrayList<StaticItem> arrRemoveableBlock = new ArrayList<StaticItem>();
	protected ArrayList<AnimatedItem> arrRemoveableAnimatedBlock = new ArrayList<AnimatedItem>();
				
	public ManageableLevel (){
		mScene = new Scene();
		isLoaded = false;
	}	
	
	public int getScoreRequirement(){
		return this.ScoreRequirements;
	}
}
