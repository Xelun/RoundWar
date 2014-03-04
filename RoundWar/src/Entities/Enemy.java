package Entities;

public class Enemy extends LivingEntity{
	public int lvl;
	
	public Enemy(Type type, String name) {
		super(type, name);
		lvl = 0;
		entityCircle.x = 300;
		entityCircle.y = 100;
	}

	/*public void setRandomInitialPosition(){
		entity
        entityCircle.x = entityCircle.x - 0.5f;
    }*/
}
