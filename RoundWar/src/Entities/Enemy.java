package Entities;

import PathFinders.PathFinder;

import com.badlogic.gdx.math.Vector2;

public class Enemy extends LivingEntity {
	public static enum Ia {FOLLOWER, TELEPORTER}
	protected static MainCharacter mainpj;
	protected Vector2 nextStep;
	protected PathFinder pathFinder;
	protected int countDown;
	protected int experience; // Experiencia que da al morir (entre 1 y 255 * el nivel)
	
	/**
	 * Contructor.
	 * @param type Tipo de enemigo (Aspecto externo).
	 * @param position Donde aparece en el mapa.
	 * @param lvl Nivel del enemigo.
	 */
	public Enemy(Type type, Vector2 position, int lvl) {
		this(type, position.x, position.y, lvl);
	}
	
	/**
	 * Constructor.
	 * @param type Tipo de enemigo (Aspecto externo).
	 * @param posX Posicion en el eje x donde aparece en el mapa.
	 * @param posY Posicion en el eje y donde aparece en el mapa.
	 * @param lvl Nivel del enemigo.
	 * @param type
	 
	 * @param lvl
	 */
	public Enemy(Type type, float posX, float posY, int lvl) {
		super(type, lvl);
		countDown = -1;
		setPosition(posX, posY);
	}
	
	/**
	 * Pone como enemigo de todos a la entidad pasada.
	 * @param enemy Enemigo de todas las instancias de esta clase.
	 */
	public static void setEnemy(LivingEntity enemy) {
		Enemy.mainpj = (MainCharacter) enemy;
	}
	
	/**
	 * Genera un tipo de IA aleatorio según el nivel pasado.
	 * @param lvl
	 */
	public static Ia randomEnemy(int lvl) {
		if(lvl < 6) return Ia.FOLLOWER;
		int rand = (int)(Math.random()*5); // Número aleatorio entre 0 y 4
		return (rand < 2)? Ia.FOLLOWER : Ia.TELEPORTER;
	}

	/**
	 * Establece las estadísticas según el nivel y los incrementos por nivel pasados.
	 * @param incrementAtq
	 * @param incrementDef
	 * @param incrementHp
	 * @param incrementVel
	 * @param baseExperience
	 */
	public void setStats(float incrementAtq, float incrementDef, float incrementHp, float incrementVel,
			int baseExperience) {
		this.statAtq += lvl * incrementAtq;
		this.statDef += lvl * incrementDef;
		this.statHp  += lvl * incrementHp;
		this.statVel += lvl * incrementVel;
		this.experience = baseExperience * lvl;
	}
	
	/**
	 * Elimina al enemigo del juego y le da la experiencia al jugador principal.
	 */
	@Override
	public void dead(LivingEntity killer) {
		super.dead(killer);
		game.removeEntity(this);
		((MainCharacter)killer).updateExperience(experience);
	}
}
