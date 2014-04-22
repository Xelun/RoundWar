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
	//private int rand;
	//private Scene scene;
	
	Wave(int startTime, int maxEnemies, int minLevel, int maxLevel, Scene scene) {
		this.startTime = startTime;
		this.maxEnemies = maxEnemies;
		this.maxLevel = maxLevel;
		this.minLevel = minLevel;
		//this.scene = scene;
		this.delay = 0;
		spawnedEnemies = 0;
	}
	
	public int getTime() {
		return startTime;
	}
	
	public static void setScreen(GameScreen screen) {
		Wave.game = screen;
	}
	
	public static void setSpawns(List<Vector2> spawns) {
		Wave.spawnPoints = spawns;
	}
	
	public boolean spawnEnemies(float delta) {
		if(delay < 0) {
			//rand = 0;
			Enemy enemy;
			for(Vector2 spawn : spawnPoints) {
				if(maxLevel > 6) {
//					rand = ((int)Math.random() * 2);
					/*if(rand > 0)*/ enemy = new EnemyTeleporter(LivingEntity.Type.ENEMY1, spawn, minLevel + (int)(Math.random() * ((maxLevel - minLevel) + 1)));
//					else enemy = new EnemyFollower(LivingEntity.Type.ENEMY1, spawn, minLevel + 2 + (int)(Math.random() * ((maxLevel - minLevel) + 1)));
				} else {
					enemy = new EnemyFollower(LivingEntity.Type.ENEMY1, spawn, minLevel + 2 + (int)(Math.random() * ((maxLevel - minLevel) + 1)));
				}
				
				if(game.collidesWithEntity(enemy, spawn.x, spawn.y) == null) {
					game.addEntity(enemy);
					spawnedEnemies++;
					System.out.println("Enemigo lvl: " + enemy.getLevel() + " vel: " + enemy.statVel + " hp: " + enemy.statHp
							+ " atq: " + enemy.statAtq + " def: " + enemy.statDef);
					//scene.addEnemy(1); // Añades un enemigo al nivel
				} else enemy.dispose();
				if(spawnedEnemies == maxEnemies)  {
					System.out.println("BREAKKKKKKKKKKK");
					break;
				}
			}
			//System.out.println("Enemigos spawneados: " + spawnedEnemies);
			delay = 3;
			
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
