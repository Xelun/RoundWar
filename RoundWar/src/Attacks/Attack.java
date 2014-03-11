package Attacks;

import Entities.LivingEntity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Attack {
	protected float damage;
	protected LivingEntity entity;
	protected Vector2 actualPos;
	protected Vector2 finalPos;
	protected float speed;
	protected float seconds;
	
	public Attack(LivingEntity entity, float posX, float posY) {
		this.entity = entity;
		actualPos.x = entity.getCenterX();
		actualPos.y = entity.getCenterY();
		finalPos.x = posX;
		finalPos.y = posY;
		speed = 1f;
		damage = 1*entity.statAtq;
		seconds = 3;
	}
	
	public void act (float delta) {
		
	}
	
	public void draw (SpriteBatch batch) {
		
	}
	
	public void dispose() {
		
	}
	
}
