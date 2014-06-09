/*******************************************************************************
 * Copyright (c) 2014
 *
 * @author Elisabet Romero Vaquero
 *******************************************************************************/
package Entities;

import PathFinders.FollowPath;
import com.badlogic.gdx.math.Vector2;

public class EnemyFollower extends Enemy {

	/**
	 * Constructor.
	 * @param type Tipo de enemigo
	 * @param position Posicion donde se crea
	 * @param lvl Nivel del enemigo
	 */
	public EnemyFollower(Type type, Vector2 position, int lvl) {
		this(type, position.x, position.y, lvl);
	}
	
	/**
	 * Constructor
	 * @param type
	 * @param posX
	 * @param posY
	 * @param lvl
	 */
	public EnemyFollower(Type type, float posX, float posY, int lvl) {
		super(type, posX, posY, lvl);
		setCenterPosition(posX, posY);
		pathFinder = new FollowPath();
    	nextStep = game.calculeAdyacentCellCenter(getCenterX(), getCenterY(), (int) Math.floor(Math.random()*4));
    	setRotation(nextStep.angle());
	}
	
	/**
	 * Calcula por el algoritmo A* el centro de la siguiente celda del mapa a la que debe ir
	 * para perseguir al personaje principal.
	 */
	private void calculateNewStep() {
		float heuristic = (float) (Math.sqrt(Math.pow(mainpj.getCenterX()-getCenterX(), 2) + Math.pow(mainpj.getCenterY()-getCenterY(), 2)));
		//float heuristic = 50;
		if(heuristic < 100) {
			nextStep.x = mainpj.getCenterX();
			nextStep.y = mainpj.getCenterY();
		} else {
			nextStep = pathFinder.findNext(this, mainpj);
			if(nextStep == null) { //Ha tocado al target
				nextStep = new Vector2(mainpj.getCenterX(), mainpj.getCenterY());
			}
		}
	}
	
	/**
	 * Mueve al enemigo.
	 */
	@Override
	public boolean moveEntity (float deltaX, float deltaY, boolean rotate){
		int rot = (int) ((getRotation() - (Math.atan2(deltaY, deltaX)*57.3f)) % 180);
		if (rot < 0)	rotate( 1f);
		else 			rotate(-1f);
		return super.moveEntity(deltaX, deltaY, true);
	}
	
	/**
	 * Actualiza al enemigo.
	 */
	@Override
	public void act (float delta){
		super.act(delta);
		float deltaX = 0, deltaY = 0;
		boolean center = false;
		
		if(nextStep.x != getCenterX()) {
			if(nextStep.x - getCenterX() > 1)
				deltaX = statVel;
			else if (nextStep.x - getCenterX() < -1)
				deltaX = -statVel;
			else center = true;
		} else center = true;
		if(nextStep.y != getCenterY()) {
			if(nextStep.y - getCenterY() > 1)
				deltaY = statVel;
			else if(nextStep.y - getCenterY() < -1)
				deltaY = -statVel;
			else if(center)
				countDown = 0;
		} else if(center)
			countDown=0;
		
		if(countDown == 0) { // Comprueba de nuevo su trayectoria cada 20 ticks
			calculateNewStep();
			countDown = 20;
		} else countDown --;
		
		moveEntity(deltaX, deltaY, true);
	}
}
