package PathFinders;

import screenControl.GameScreen;
import Entities.LivingEntity;

import com.badlogic.gdx.math.Vector2;

public class TeleportPath extends PathFinder {

	public TeleportPath() {
		super();
	}
	
	@Override
	public Vector2 findNext(LivingEntity entity, LivingEntity entityTarget) {
		int distanceX = (int) Math.floor(Math.random()*4)-2;
		int distanceY = (int) Math.floor(Math.random()*4)-2; 
		if(distanceY == 0) {
			if(distanceX == 0) distanceX += 1;
		}
		if(distanceX == 0) {
			if(distanceY == 0) distanceY -= 1;
		}
		Vector2 node = getNode(entityTarget.getCenterX(), entityTarget.getCenterY(), distanceX, distanceY);
		if(GameScreen.getScene().isFree(node.x, node.y) && game.collidesWithEntity(entity, node.x, node.y) == null) {
			return node;
		} else {
			System.out.println("OOOCUPADO");
			return null;
		}
		//return (game.getScene().isFree(node.x, node.y) && game.collidesWithEntity(entity, node.x, node.y) == null) ? node : findNext(entity, entityTarget);
	}
}
