package Entities;

import PathFinders.PathFinder;

import com.badlogic.gdx.math.Vector2;

public class Enemy extends LivingEntity {
	protected static MainCharacter mainpj;
	protected Vector2 nextStep;
	protected PathFinder pathFinder;
	protected int countDown;
	protected int experience; // Experiencia que da al morir (entre 1 y 255 * el nivel)
	
	public Enemy(Type type, Vector2 position, int lvl) {
		this(type, position.x, position.y, lvl);
	}
	
	public Enemy(Type type, float posX, float posY, int lvl) {
		super(type, lvl);
		countDown = -1;
		setPosition(posX, posY);
	}
	
	public static void setEnemy(LivingEntity enemy) {
		Enemy.mainpj = (MainCharacter) enemy;
	}

	public void setStats(float incrementAtq, float incrementDef, float incrementHp, float incrementVel,
			int baseExperience) {
		this.statAtq += lvl * incrementAtq;
		this.statDef += lvl * incrementDef;
		this.statHp  += lvl * incrementHp;
		this.statVel += lvl * incrementVel;
		this.experience = baseExperience * lvl;
	}
	
	@Override
	public void dead(LivingEntity killer) {
		game.removeEntity(this);
		super.dead(killer);
		System.out.println("Has matado un enemigo de nivel " + lvl);
		((MainCharacter)killer).updateExperience(experience);
	}
}
