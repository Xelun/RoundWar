/*******************************************************************************
 * Copyright (c) 2014
 *
 * @author Elisabet Romero Vaquero
 *******************************************************************************/
package Entities;

import Attacks.Attack;
import Attacks.BallAttack;
import PathFinders.TeleportPath;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class EnemyTeleporter extends Enemy {
	float delayAttack, delayAppear, delayDisappear, actualDelay;
	int mode;
	Vector2 attackDirection;
	boolean draw;
	
	/**
	 * Constructor.
	 * @param type
	 * @param position
	 * @param lvl
	 */
	public EnemyTeleporter(Type type, Vector2 position, int lvl) {
		this(type, position.x, position.y, lvl);
	}
	
	/**
	 * Constructor.
	 * @param type
	 * @param posX
	 * @param posY
	 * @param lvl
	 */
	public EnemyTeleporter(Type type, float posX, float posY, int lvl) {
		super(type, posX, posY, lvl);
		pathFinder = new TeleportPath();
		if(calculateNewStep()) {
			setCenterPosition(nextStep);
		}
		attackDirection = new Vector2(mainpj.getCenterX(), mainpj.getCenterY());
		draw = true;
	}
	
	/**
	 * Establece las estadísticas usando las bases de su raza, el nivel y los incrementos pasados.
	 */
	@Override
	public void setStats(float incrementAtq, float incrementDef, float incrementHp, float incrementVel,
			int baseExperience) {
		super.setStats(incrementAtq, incrementDef, incrementHp, incrementVel, baseExperience);
		// ToDo: Cambiar delays según estadísticas
		this.delayAppear = 2 + (int)(Math.random()*6);
		this.delayAttack = 1 + (int)(Math.random()*4);
		this.delayDisappear = 2 + (int)(Math.random()*6);
		this.actualDelay = this.delayAttack;
		this.mode = 0;
	}
	
	/**
	 * Calcula el siguiente paso que debe hacer.
	 * @return
	 */
	private boolean calculateNewStep() {
		nextStep = pathFinder.findNext(this, mainpj);
		return nextStep == null ? false : true;
	}
	
	/**
	 * Actualiza al enemigo.
	 */
	@Override
	public void act (float delta){
		super.act(delta);
		switch(mode) {
			case 0: // Ha aparecido y espera para atacar. Ataca
				if(actualDelay > 0) actualDelay -= delta;
				else {
					actualDelay = delayDisappear;
					mode = 1;
					game.attacks.add(new BallAttack(this, attackDirection.x, attackDirection.y, Attack.Type.BASIC));
				}
				break;
			case 1: // Ha atacado y espera para desaparecer. Desaparece
				if(actualDelay > 0) actualDelay -= delta;
				else {
					actualDelay = delayAppear;
					mode = 2;
					draw = false;
					game.removeTemporallyEntity(this);
				}
				break;
			case 2: // Ha desaparecido y espera para aparecer. Aparece
				if(actualDelay > 0) actualDelay -= delta;
				else if(calculateNewStep()) { // Si se puede mover
					actualDelay = delayAttack;
					mode = 0;
					draw = true;
					game.addTemporallyEntity(this);
					setCenterPosition(nextStep);
					attackDirection.x = mainpj.getCenterX();
					attackDirection.y = mainpj.getCenterY();
					setRotation(new Vector2(getCenterX() - attackDirection.x, getCenterY() - attackDirection.y).angle() + 180);
				}
				break;
		}
	}
	
	/**
	 * Dibuja al enemigo si está en escena.
	 */
	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		if(draw) super.draw(batch, parentAlpha);
	}
	

}
