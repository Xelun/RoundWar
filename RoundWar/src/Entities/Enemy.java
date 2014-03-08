package Entities;

import screenControl.GameScreen;

public class Enemy extends LivingEntity{
	public int lvl;
	
	public Enemy(Type type, GameScreen game) {
		super(type, game);
		lvl = 0;
		setPosition(300, 80);
	}

	/*public void setRandomInitialPosition(){
		entity
        entityCircle.x = entityCircle.x - 0.5f;
    }*/
}
