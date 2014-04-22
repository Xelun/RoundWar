package Attacks;

import screenControl.GameScreen;
import Entities.LivingEntity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Attack extends Actor{
	public enum Type { 
		BASIC(0), ARROW(10);
    	private int cost;
    	
    	private Type(int cost){
    		this.cost = cost;
    	}
		
    	public int getCost() {
    		return cost;
    	}
	}
	protected static GameScreen game;
	protected float damage;
	protected LivingEntity entity;
	protected Vector2 actualPos;
	protected Vector2 finalPos;
	protected float seconds;
	
	public static void setScreen(GameScreen game) {
		Attack.game = game;
	}
	
	public Attack(LivingEntity entity, Vector2 pos) {
		//if(entity instanceof MainCharacter && ((MainCharacter)entity).getMp()<=0) return;
		this.entity = entity;
		game.getStage().addActor(this);
		actualPos = new Vector2(entity.getCenterX(), entity.getCenterY());
		finalPos = pos;
		//seconds = 2;
	}
	
	public Attack(LivingEntity entity, float posX, float posY) {
		this(entity, new Vector2(posX, posY));
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		
	}
	
	public void dispose() {
		//game.removeAttack(this);
	}
	
}
