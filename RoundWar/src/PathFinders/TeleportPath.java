package PathFinders;

import Entities.LivingEntity;

import com.badlogic.gdx.math.Vector2;

public class TeleportPath extends PathFinder {

	public TeleportPath() {
		super();
	}
	
	@Override
	public Vector2 findNext(LivingEntity entity, LivingEntity entityTarget) {
		int distance = (int) Math.floor(Math.random()*4)-2;
		Vector2 node = getNode(entityTarget.getCenterX(), entityTarget.getCenterY(), distance, distance);
		return (game.getScene().isFree(node.x, node.y) && game.collidesWithEntity(entity, node.x, node.y) == null) ? node : findNext(entity, entityTarget);
	}
}
