/*******************************************************************************
 * Copyright (c) 2014
 *
 * @author Elisabet Romero Vaquero
 *******************************************************************************/
package roundwar;

import java.util.LinkedList;
import java.util.List;

import screenControl.Background;
import screenControl.GameScreen;
import Entities.LivingEntity;
import Entities.ReturnIntEntity;
import PathFinders.PathFinder;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Scene {
	private static GameScreen game;
	
	private boolean lastWave;
	private String path, nameLevel;
	private List<Vector2> spawnPoints;
	private LinkedList<Wave> waves;
	private Wave currentWave;
	private Background bg;
	
	/**
	 * Constructor.
	 * @param id Id del escenario.
	 */
	public Scene(int id) {
		// Crea las oleadas e inicializa el escenario.
		switch(id) {
			case 1:
				setName("Prueba2");
				waves.add(new Wave(5,  3 + (int)(Math.random() * 5), 10, 12));
				waves.add(new Wave(10, 3 + (int)(Math.random() * 5), 12, 15));
				waves.add(new Wave(20, 3 + (int)(Math.random() * 5), 13, 17));
				waves.add(new Wave(30, 3 + (int)(Math.random() * 5), 14, 19));
				waves.add(new Wave(45, 3 + (int)(Math.random() * 5), 15, 20));
				waves.add(new Wave(60, 3 + (int)(Math.random() * 5), 16, 24));
				break;
			default: // Id 0 u otros
				setName("Test");
				waves.add(new Wave(5,  2 + (int)(Math.random() * 3), 1, 2));
				waves.add(new Wave(10, 2 + (int)(Math.random() * 3), 2, 4));
				waves.add(new Wave(15, 2 + (int)(Math.random() * 3), 3, 6));
				waves.add(new Wave(20, 2 + (int)(Math.random() * 3), 4, 8));
				waves.add(new Wave(30, 2 + (int)(Math.random() * 3), 5, 10));
				waves.add(new Wave(40, 2 + (int)(Math.random() * 3), 6, 12));
				break;
		}
		
		currentWave = waves.pop(); // Coge la primera oleada
	}
	
	private void setName(String name) {
		this.nameLevel = name;
		path = "background/map" + nameLevel + ".tmx"; 
		System.out.println(path);
		lastWave = false;
		
		bg = new Background(path); // Carga el mapa
		game.getStage().addActor(bg);
		spawnPoints = bg.loadObstacles();
		
		PathFinder.setLayer(bg.getLayerColission());
		Wave.setSpawns(spawnPoints);
		waves = new LinkedList<Wave>();
	}
	
	/**
	 * Actualiza el escenario y la oleada actual, en caso de haberla.
	 * @param delta
	 */
	public void update(float delta) {
		if(!(lastWave && currentWave.isSpawned()) && game.getTime() > currentWave.getTime()) {
			// Nueva oleada
			if(!currentWave.spawnEnemies(delta)){
				currentWave = waves.isEmpty() ? currentWave : waves.pop();
				if(waves.isEmpty())  // La oleada actual es la última
					lastWave = true;
			}
		}
	}
	
	/**
	 * Elimina una o varias entidades del escenario.
	 * @param enemiesDeads
	 */
	public void removeEnemy(int enemiesDeads) {
		if(lastWave && currentWave.isSpawned() && game.getLeftEnemies() == 0) { // Escenario terminado
			game.winGame();
		}
	}
	
	/**
	 * Devuelve los enemigos que quedan en el escenario.
	 */
	public int getLeftEnemies() {
		return game.getLeftEnemies();
	}
	
	/**
	 * Devuelve los puntos de spawneo de enemigos.
	 */
	public List<Vector2> getSpawnPoints() {
		return spawnPoints;
	}
	
	/**
	 * Guarda una instancia de la pantalla de juego.
	 * @param screen
	 */
	public static void setScreen(GameScreen screen) {
		Scene.game = screen;
		Background.setScreen(game);
		PathFinder.setScreen(game);
		Wave.setScreen(game);
	}
	
	/**
	 * Añade un punto de spawneo de enemigos.
	 * @param point
	 */
	public void addSpawn(Vector2 point) {
		spawnPoints.add(point);
	}
	
	/**
	 * Devuelve el fondo de pantalla (mapa).
	 */
	public Background getBackground() {
		return bg;
	}
	
	
	/**
	 * Devuelve la dirección del mapa.
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Devuelve si una entidad chocará si se mueve en deltaX y deltaY.
	 * @param entity
	 * @param deltaX
	 * @param deltaY
	 * @param cooldown
	 */
	public ReturnIntEntity isFree(LivingEntity entity, float deltaX, float deltaY, int cooldown) {
    	if(cooldown >= 0) {
    		return isFree(entity, deltaX, deltaY);
    	} else {
    		LivingEntity ent = game.collides(entity, deltaX, deltaY);
    		if(ent != null) {
    			return new ReturnIntEntity(3, ent);
    		}
    		return new ReturnIntEntity(0, null);
    	}
    }
    
	/**
	 * Devuelve la entidad y la dirección en la que se chocará la entidad pasada si se mueve delaX y deltaY, en caso de haberla.
	 * @param entity
	 * @param deltaX
	 * @param deltaY
	 */
    public ReturnIntEntity isFree(LivingEntity entity, float deltaX, float deltaY) {
    	Rectangle bounds = new Rectangle(entity.getBounds());
    	bounds.x += deltaX;
    	bounds.y += deltaY;
    	ReturnIntEntity result = new ReturnIntEntity(0, null);
    	LivingEntity ent = game.collides(entity, deltaX, deltaY);
    	
    	if(ent == null && bg.isFree(bounds)) { 			// Sin colision en ningún eje
    		return result;
    	} else {
    		result.setEntity(ent);
    		ent = game.collides(entity, deltaX, 0);
	    	bounds.y -= deltaY;
	    	if(ent == null && bg.isFree(bounds)) { 		// Sin colision en el eje x
	    		result.setInt(1);
	    	} else {
	    		result.setEntity(ent);
	    		ent = game.collides(entity, 0, deltaY);
	    		bounds.y += deltaY;
		    	bounds.x -= deltaX;
		    	if(ent == null && bg.isFree(bounds)) { 	// Sin colision en el eje y
		    		result.setInt(2);
		    	} else {								// Colisión en ambos ejes
		    		result.setInt(3);
		    	}
	    	}
    	}
    	return result;
    }
    
    /**
     * Si en una posición dada no hay obstáculos.
     * @param posX
     * @param posY
     */
    public boolean isFree(float posX, float posY) {
    	return bg.isFree(posX, posY);
    }
    
    /**
     * Libera memoria.
     */
    public void dispose() {
    	game.getStage().getRoot().removeActor(bg);
		bg.dispose();
		PathFinder.dispose();
    }
}
