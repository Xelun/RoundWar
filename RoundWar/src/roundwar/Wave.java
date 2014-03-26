package roundwar;

import java.util.List;

import screenControl.GameScreen;
import Entities.Enemy;
import Entities.LivingEntity;

import com.badlogic.gdx.math.Vector2;

public class Wave {
	private static GameScreen game;
	private static List<Vector2> spawnPoints;
	private int startTime;
	private int maxEnemies, spawnedEnemies;
	private int maxLevel, minLevel;
	private float delay;
	
	Wave(int startTime, int maxEnemies, int minLevel, int maxLevel) {
		this.startTime = startTime;
		this.maxEnemies = maxEnemies;
		this.maxLevel = maxLevel;
		this.minLevel = minLevel;
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
			//System.out.println("Creando enemigos");
			for(Vector2 spawn : spawnPoints) {
				Enemy enemy = new Enemy(LivingEntity.Type.ENEMY1, spawn, minLevel + (int)(Math.random() * ((maxLevel - minLevel) + 1)));
				if(game.collidesWithEntity(enemy, spawn.x, spawn.y) == null) {
					game.addEntity(enemy);
					spawnedEnemies ++;
					System.out.println("Enemigo lvl: " + enemy.getLevel() + " vel: " + enemy.statVel + " hp: " + enemy.statHp
							+ " atq: " + enemy.statAtq + " def: " + enemy.statDef);
				} else enemy.dispose();
					
			}
			System.out.println("Enemigos spawneados: " + spawnedEnemies);
			delay = 4;
			
			return spawnedEnemies < maxEnemies ? true : false;
		} else {
			delay -= delta;
			return true;
		}
	}
}
