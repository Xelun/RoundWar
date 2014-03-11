package Attacks;

import screenControl.GameScreen;
import Entities.LivingEntity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

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
	
	public Attack(LivingEntity entity, float posX, float posY) {
		this.entity = entity;
		game.getStage().addActor(this);
		actualPos = new Vector2();
		finalPos = new Vector2();
		actualPos.x = entity.getCenterX();
		actualPos.y = entity.getCenterY();
		System.out.println(entity.getRectangle());
		System.out.println(actualPos);
		//System.out.println(entity.getRectangle().x + " x " + entity.getRectangle().y);
		finalPos.x = posX;
		finalPos.y = posY;
		speed = 1f;
		damage = 1*entity.statAtq;
		seconds = 3;
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
		//game.getStage().unfocus(this);
	}
	
}
