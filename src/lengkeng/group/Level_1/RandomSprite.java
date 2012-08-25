package lengkeng.group.Level_1;
import lengkeng.group.LevelManager.RandomItem;
public class RandomSprite {
	private RandomItem randomItem = new RandomItem();
	public void createSpriteSpawnTimeHandler() { // 1s tao ra 1 target
		randomItem.createSpriteSpawnTimeHandler(Level_1_Class_Scene.timer);
	}
	
}