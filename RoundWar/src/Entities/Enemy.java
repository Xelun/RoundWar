package Entities;

import roundwar.PathFinder;

import com.badlogic.gdx.math.Vector2;

public class Enemy extends LivingEntity{
	private static MainCharacter mainpj = game.getCharacter();
	private Vector2 nextStep;
	public int lvl;
	protected PathFinder pathFinder;
	
	public Enemy(Type type, float posX, float posY) {
		super(type);
		lvl = 0;
		setPosition(posX, posY);
    	pathFinder = new PathFinder();
    	nextStep = pathFinder.findNext(this, mainpj);
	}
	
	public static void setEnemy(LivingEntity enemy) {
		Enemy.mainpj = (MainCharacter) enemy;
	}

	@Override
	public void act (float delta){
		super.act(delta);
		float deltaX = 0, deltaY = 0;
		//System.out.println(nextStep.x + " x " + getCenterX());
		
		if(nextStep.x != getCenterX()) {
			if(nextStep.x - getCenterX() > 0)
				deltaX = statVel;
			else
				deltaX = -statVel;
		}
		if(nextStep.y != getCenterY()) {
			if(nextStep.y - getCenterY() > 0)
				deltaY = statVel;
			else
				deltaY = -statVel;
		}
		moveEntity(deltaX, deltaY, true);
		//System.out.println(deltaX + " x " + deltaY);
		
		nextStep = pathFinder.findNext(this, mainpj);
		if(nextStep == null) { //Ha tocado al target
			nextStep = new Vector2(getCenterX(), getCenterY());
		}
	}
	
	@Override
	public boolean moveEntity (float deltaX, float deltaY, boolean rotate){
		float rot = (getRotation() - (float) Math.atan2(deltaY, deltaX)*57.3f) % 180;
		if((rot >= 180) || (rot < 0 && rot > -180)) 		rotate(0.5f);
		else if ((rot > 0 && rot < 180) || (rot <= -180)) 	rotate(-0.5f);
		
		return super.moveEntity(deltaX, deltaY, true);
	}
	
	@Override
	public void dead() {
		game.removeEntity(this);
		super.dead();
	}
	
	public void dispose() {
		super.dispose();
	}
}
