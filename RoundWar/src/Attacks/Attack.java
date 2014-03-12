package Attacks;

import screenControl.GameScreen;
import Entities.LivingEntity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Attack extends Actor{
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
	
	public Attack(LivingEntity entity, Vector2 pos) {
		this.entity = entity;
		game.getStage().addActor(this);
		actualPos = new Vector2();
		finalPos = pos;
		actualPos.x = entity.getCenterX();
		actualPos.y = entity.getCenterY();
		//System.out.println(entity.getRectangle().x + " x " + entity.getRectangle().y);
		speed = 1f;
		damage = 1*entity.statAtq;
		seconds = 3;
	}
	
	public Attack(LivingEntity entity, float posX, float posY) {
		this(entity, new Vector2(posX, posY));
	}
	
	@Override
	public void act (float delta) {
		
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		
	}
	
	public void dispose() {
		game.attacks.remove(this);
		game.getStage().getRoot().removeActor(this);
	}
	
}
