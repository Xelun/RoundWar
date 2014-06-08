package roundwar;

import java.util.List;

import screenControl.GameScreen;
import Entities.Enemy;
import Entities.EnemyFollower;
import Entities.EnemyTeleporter;
import Entities.LivingEntity;

import com.badlogic.gdx.math.Vector2;

public class Wave {
	private static GameScreen game;
	private static List<Vector2> spawnPoints;
	private int startTime;
	private int maxEnemies, spawnedEnemies;
	private int maxLevel, minLevel;
	private float delay;
	
	/**
	 * Constructor.
	 * @param startTime Cuando comenzará la oleada.
	 * @param maxEnemies Numero de enemigos que spawnearán en la oleada.
	 * @param minLevel Nivel mínimo de los enemigos.
	 * @param maxLevel Nivel máximo de los enemigos.
	 */
	Wave(int startTime, int maxEnemies, int minLevel, int maxLevel) {
		this.startTime = startTime;
		this.maxEnemies = maxEnemies;
		this.maxLevel = maxLevel;
		this.minLevel = minLevel;
		this.delay = 0;
		spawnedEnemies = 0;
	}
	
	/**
	 * Devuelve el tiempo en el que comienza la oleada.
	 */
	public int getTime() {
		return startTime;
	}
	
	/**
	 * Guarda la instancia del escenario en el que se crea la oleada.
	 * @param screen
	 */
	public static void setScreen(GameScreen screen) {
		Wave.game = screen;
	}
	
	/**
	 * Se guardan los puntos donde pueden spawnear enemigos.
	 * @param spawns
	 */
	public static void setSpawns(List<Vector2> spawns) {
		Wave.spawnPoints = spawns;
	}
	
	/**
	 * Spawnea enemigos.
	 * @param delta
	 */
	public boolean spawnEnemies(float delta) {
		if(delay < 0) {
			//rand = 0;
			Enemy enemy;
			Enemy.Ia iarand;
			Vector2 spawn = spawnPoints.get((int)(Math.random() * spawnPoints.size()));
			// Generamos un enemigo aleatorio
			iarand = Enemy.randomEnemy(maxLevel);
			switch(iarand) {
				case TELEPORTER:
					enemy = new EnemyTeleporter(LivingEntity.Type.ENEMY1, spawn, minLevel + 4 + (int)(Math.random() * (maxLevel + 4)));
					break;
				default: // Follower
					enemy = new EnemyFollower(LivingEntity.Type.ENEMY1, spawn, minLevel + 1 + (int)(Math.random() * (maxLevel + 1)));
					break;
			}
			
			if(game.collidesWithEntity(enemy, spawn.x, spawn.y) == null) {
				game.addEntity(enemy);
				spawnedEnemies++;
			} else enemy.dispose();
			delay = 1;
			
			return spawnedEnemies < maxEnemies ? true : false;
		} else {
			delay -= delta;
			return true;
		}
	}
	
	public boolean isSpawned() {
		return spawnedEnemies == maxEnemies;
	}
}
