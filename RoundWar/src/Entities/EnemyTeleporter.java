package Entities;

import PathFinders.TeleportPath;
import com.badlogic.gdx.math.Vector2;

public class EnemyTeleporter extends Enemy {
	int delayAttack, delayAppear, delayDisappear, actualDelay, mode;
	
	
	public EnemyTeleporter(Type type, Vector2 position, int lvl) {
		this(type, position.x, position.y, lvl);
	}
	
	public EnemyTeleporter(Type type, float posX, float posY, int lvl) {
		super(type, posX, posY, lvl);
		pathFinder = new TeleportPath();
		calculateNewStep();
		setPosition(nextStep);
	}
	
	@Override
	public void setStats(float incrementAtq, float incrementDef, float incrementHp, float incrementVel,
			int baseExperience) {
		super.setStats(incrementAtq, incrementDef, incrementHp, incrementVel, baseExperience);
		// ToDo: Cambiar delays según estadísticas
		this.delayAppear = 2;
		this.delayAttack = 2;
		this.delayDisappear = 2;
		this.actualDelay = this.delayAttack;
		this.mode = 0;
	}
	
	
	private void calculateNewStep() {
		nextStep = pathFinder.findNext(this, mainpj);
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
		if(mode == 0) {
			if(delayAttack > 0) actualDelay --;
			else {
				actualDelay = delayDisappear;
				
			}
		}
	}

}
