package Entities;

import roundwar.PathFinder;

import com.badlogic.gdx.math.Vector2;

public class Enemy extends LivingEntity{
	private static MainCharacter mainpj = game.getCharacter();
	private Vector2 nextStep;
	public int lvl;
	protected PathFinder pathFinder;
	protected int countDown;
	
	public Enemy(Type type, Vector2 position) {
		this(type, position.x, position.y);
	}
	
	public Enemy(Type type, float posX, float posY) {
		super(type);
		lvl = 0;
		setPosition(posX, posY);
		countDown = -1;
    	pathFinder = new PathFinder();
    	int rand = (int) Math.floor(Math.random()*4);
    	System.out.println(rand);
    	nextStep = game.calculeAdyacentCellCenter(getCenterX(), getCenterY(), rand);
	}
	
	public static void setEnemy(LivingEntity enemy) {
		Enemy.mainpj = (MainCharacter) enemy;
	}

	@Override
	public void act (float delta){
		super.act(delta);
		float deltaX = 0, deltaY = 0;

		if(countDown == 0) { // Comprueba de nuevo su trayectoria cada 20 ticks
			calculateNewStep();
			countDown = 20;
		} else countDown --;
		
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
		} else if(nextStep.x == getCenterX()) { //Si estas en el punto del siguiente paso, calcula otro
			countDown = 0;
		}
		moveEntity(deltaX, deltaY, true);
	}
	
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
	
	@Override
	public boolean moveEntity (float deltaX, float deltaY, boolean rotate){
		int rot = (int) ((getRotation() - (Math.atan2(deltaY, deltaX)*57.3f)) % 180);
		//System.out.println(rot);
		if   (rot > 180 || rot < 0 )	rotate( 1f);
		else 							rotate(-1f);
		
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
