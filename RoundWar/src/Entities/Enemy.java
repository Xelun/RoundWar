package Entities;

import PathFinders.PathFinder;

import com.badlogic.gdx.math.Vector2;

public class Enemy extends LivingEntity {
	private static MainCharacter mainpj = game.getCharacter();
	private Vector2 nextStep;
	protected PathFinder pathFinder;
	protected int countDown;
	private int experience; // Experiencia que da al morir (entre 20 y 390 por el nivel)
	
	public Enemy(Type type, Vector2 position, int lvl) {
		this(type, position.x, position.y, lvl);
	}
	
	public Enemy(Type type, float posX, float posY, int lvl) {
		super(type, lvl);
		setCenterPosition(posX, posY);
		countDown = -1;
    	int rand = (int) Math.floor(Math.random()*4);
    	System.out.println(rand);
    	nextStep = game.calculeAdyacentCellCenter(getCenterX(), getCenterY(), rand);
    	setRotation(nextStep.angle());
	}
	
	public void setPathFinder(PathFinder pathfinder) {
		this.pathFinder = pathfinder;
	}
	
	public static void setEnemy(LivingEntity enemy) {
		Enemy.mainpj = (MainCharacter) enemy;
	}

	public void setStats(float incrementAtq, float incrementDef, float incrementHp, float incrementVel,
			float baseExperience) {
		this.statAtq += lvl * incrementAtq;
		this.statDef += lvl * incrementDef;
		this.statHp  += lvl * incrementHp;
		this.statVel += lvl * incrementVel;
		this.experience = (int) (baseExperience * lvl);
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
		if (rot < 0)	rotate( 1f);
		else 			rotate(-1f);
		return super.moveEntity(deltaX, deltaY, true);
	}
	
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
			//System.out.println("Entra");
			calculateNewStep();
			countDown = 20;
		} else countDown --;
		
		moveEntity(deltaX, deltaY, true);
	}
	
	@Override
	public void dead(LivingEntity killer) {
		game.removeEntity(this);
		super.dead(killer);
		System.out.println("Has matado un enemigo de nivel " + lvl + " y tu estas a nivel " + killer.getLevel());
		((MainCharacter)killer).updateExperience((experience));//*(Math.pow((2 * lvl + 10)/(lvl+killer.getLevel()+5),2.5))));
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
}
