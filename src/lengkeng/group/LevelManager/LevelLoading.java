package lengkeng.group.LevelManager;

import java.util.Random;

import lengkeng.group.SceneManager.SubLevelSelector;

import org.anddev.andengine.level.LevelLoader;
import org.anddev.andengine.level.LevelLoader.IEntityLoader;
import org.anddev.andengine.level.util.constants.LevelConstants;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.SAXUtils;
import org.xml.sax.Attributes;

public class LevelLoading {
		
	// xml tag
	final static String TAG_ENTITY = "entity";
    final static String TAG_ENTITY_ATTRIBUTE_X = "x";
    final static String TAG_ENTITY_ATTRIBUTE_Y = "y";
    final static String TAG_ENTITY_ATTRIBUTE_TYPE = "type";
    final static String TAG_ENTITY_VELOCITY = "velocity";
    final static String TAG_SCOREREQUIREMENTS =  "ScoreRequirements";        
        
    // xml type
    final static int MAX_LEVEL = 5;
	
    
    static Random rand = new Random();
    
	public static LevelLoader mLevelLoader ;	
	
	// load level
	public static void loadLevel(BaseGameActivity context) {		
		if (LevelManager.Level>MAX_LEVEL) return;
								
		context.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				try {
					startLoadingLevel2();
					mLevelLoader.loadLevelFromAsset(LevelManager.getBaseGameActivity(), "level/level_" + LevelManager.Level + "_" + LevelManager.subLevel+ ".xml" );
				} catch (Exception e) {
					Debug.e(e);
				}
			}
		});
    }
	
	
	
	// load entity
	public static void startLoadingLevel2() {
		mLevelLoader = new LevelLoader();
		mLevelLoader.registerEntityLoader(LevelConstants.TAG_LEVEL, new IEntityLoader() {
			@Override
			public void onLoadEntity(final String pEntityName, final Attributes pAttributes) {
			}
		}); 
	
		mLevelLoader.registerEntityLoader(TAG_SCOREREQUIREMENTS, new IEntityLoader() {
			@Override
			public void onLoadEntity(final String pEntityName, final Attributes pAttributes) {
                final int score = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_X);                               
                final String type = SAXUtils.getAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_TYPE);

                addEntity(score, 0, type, 0);
            }
		});		
		
		mLevelLoader.registerEntityLoader(TAG_ENTITY, new IEntityLoader() {
			@Override
			public void onLoadEntity(final String pEntityName, final Attributes pAttributes) {
                final int x = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_X);
                final int y = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_Y);                
                final String type = SAXUtils.getAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_TYPE);
                final int velocity = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_VELOCITY);
                addEntity(x, y, type, velocity);
            }
		});							
	}

	public static void addEntity(final int pX, final int pY, final String pType, final int velocity) {
		LevelManager.loadEntity(pType, pX, pY, velocity);				
    }
				
	public static void loadAllScoreRequirements(BaseGameActivity context){		
			for(int j = 1; j<=LevelManager.NUM_SUBLEVEL; j++)
				loadScoreRequirement(context, j);
	}				
	
	public static void loadScoreRequirement(BaseGameActivity context, final int y){
				try {
					startLoadingLevel(y);
					mLevelLoader.loadLevelFromAsset(context, "level/level_" + LevelManager.Level +"_" + y + ".xml" );
				} catch (Exception e) {
					Debug.e(e);
				}
	}
	
	public static void startLoadingLevel(final int y) {
		mLevelLoader = new LevelLoader();
		mLevelLoader.registerEntityLoader(LevelConstants.TAG_LEVEL, new IEntityLoader() {
			@Override
			public void onLoadEntity(final String pEntityName, final Attributes pAttributes) {
			}
		}); 
		mLevelLoader.registerEntityLoader(TAG_SCOREREQUIREMENTS, new IEntityLoader() {
			@Override
			public void onLoadEntity(final String pEntityName, final Attributes pAttributes) {
                final int score = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_X);                               
                addScoreLevel(y,score);
            }
		});		
	}			
	
	protected static void addScoreLevel(int y, int scoreRequirement) {
		// TODO Auto-generated method stub
		SubLevelSelector.ScoreRequirement[y] = scoreRequirement;
	}
}

