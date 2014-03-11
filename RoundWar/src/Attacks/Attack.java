package Attacks;

import screenControl.GameScreen;
import Entities.LivingEntity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Attack {
	protected static GameScreen game;
	protected float damage;
	protected LivingEntity entity;
	protected Vector2 actualPos;
	protected Vector2 finalPos;
	protected float speed;
	protected float seconds;
	
	public static void setScreen(GameScreen game) {
		Attack.game = game;
	}
	
	public Attack(LivingEntity entity, float posX, float posY) {
		this.entity = entity;
		actualPos = new Vector2();
		finalPos = new Vector2();
		actualPos.x = entity.getCenterX();
		actualPos.y = entity.getCenterY();
		//System.out.println(actualPos);
		//System.out.println(entity.getRectangle().x + " x " + entity.getRectangle().y);
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
		game.attacks.remove(this);
	}
	
}
