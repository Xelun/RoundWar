package PathFinders;

import Entities.LivingEntity;

import com.badlogic.gdx.math.Vector2;

public class TeleportPath extends PathFinder {

	public TeleportPath() {
		super();
	}
	
	@Override
	public Vector2 findNext(LivingEntity entity, LivingEntity entityTarget) {
		return null;
	}
}
