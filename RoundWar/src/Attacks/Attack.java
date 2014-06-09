/*******************************************************************************
 * Copyright (c) 2014
 *
 * @author Elisabet Romero Vaquero
 *******************************************************************************/
package Attacks;

import screenControl.GameScreen;
import Entities.LivingEntity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Attack extends Actor{
	// Tipo de ataque con su coste en maná
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
	protected static Texture texAttack;
	protected float damage;
	protected LivingEntity entity;
	protected Vector2 actualPos;
	protected Vector2 finalPos;
	protected float seconds;
	
	/**
	 * Indica la pantalla de juego en donde se crearán los ataques.
	 * @param game
	 */
	public static void setScreen(GameScreen game) {
		Attack.game = game;
		texAttack = new Texture(Gdx.files.internal("images/attacks.png"));
	}
	
	/**
	 * Constructor.
	 * @param entity
	 * @param pos
	 */
	public Attack(LivingEntity entity, Vector2 pos) {
		this.entity = entity;
		game.getStage().addActor(this);
		actualPos = new Vector2(entity.getCenterX(), entity.getCenterY());
		finalPos = pos;
	}
	
	/**
	 * Constructor.
	 * @param entity
	 * @param posX
	 * @param posY
	 */
	public Attack(LivingEntity entity, float posX, float posY) {
		this(entity, new Vector2(posX, posY));
	}
	
	/**
	 * Libera memoria.
	 */
	public static void dispose() {
		texAttack.dispose();
	}
	
}
