package Entities;

import screenControl.GameScreen;

public class Enemy extends LivingEntity{
	public int lvl;
	
	public Enemy(Type type, String name, GameScreen game) {
		super(type, name, game);
		lvl = 0;
		entityCircle.x = 300;
		entityCircle.y = 100;
	}

	/*public void setRandomInitialPosition(){
		entity
        entityCircle.x = entityCircle.x - 0.5f;
    }*/
}
