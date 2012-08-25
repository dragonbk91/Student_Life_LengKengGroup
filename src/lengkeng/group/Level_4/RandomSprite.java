// date create 
// author Nguyen Thanh Linh
package lengkeng.group.Level_4;

import java.util.Random;
import lengkeng.group.LevelManager.RandomItem;

public class RandomSprite {
	
	static Random rand = new Random();
	private RandomItem randomItem = new RandomItem();

	public void createSpriteSpawnTimeHandler() { // 1s tao ra 1 target
		randomItem.createSpriteSpawnTimeHandler(Level_4_Kitchen_Scene.timer);
	}	
}
