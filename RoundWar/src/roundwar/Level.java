package roundwar;

import Entities.LivingEntity;
import Entities.ReturnIntEntity;

import com.badlogic.gdx.math.Rectangle;

import screenControl.Background;
import screenControl.GameScreen;

public class Level {
	// Calcular el nivel maximo y minimo de los bichos que aparecen
	// Spawneo de bichos al comienzo del mapa
	// Spawneo dinámico de bichos durante el mapa
	// Tipos de bichos que pueden aparecer en este mapa
	// Bichos que hay spawneados?
	// Cantidad de bichos que has matado?
	// Numero de oleada en la que estas?
	private static GameScreen game;
	
	private int minLevel, maxLevel;
	private int maxEnemies, totalEnemies;
	private String path, nameLevel;
	private Background bg;
	//private List<LivingEntity.Type> monstersSpawn;
	
	
	public Level(String nameLevel) {
		this.nameLevel = nameLevel;
		if(this.nameLevel == "Prueba") {
			initializeLevel(1, 3, 5);
		} else if(this.nameLevel == "2") {
			initializeLevel(1, 2, 5);
		}
		bg = new Background(game, path);
		game.getStage().addActor(bg);
		
		//monstersSpawn = new LinkedList<LivingEntity.Type>();
		
	}
	
	public static void setScreen(GameScreen screen) {
		Level.game = screen;
	}
	
	public void setMaxLevel(int level) {
		this.maxLevel = level;
	}
	
	public Background getBackground() {
		return bg;
	}
	
	public void setMinLevel(int level) {
		this.minLevel = level;
	}
	
	public String getPath() {
		return path;
	}
	
	private void initializeLevel(int minLevel, int maxLevel, int maxEnemies) {
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		this.maxEnemies = maxEnemies;
		this.totalEnemies = 0;
		this.path = "background/map" + nameLevel + ".tmx";
	}
	
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
    
    public boolean isFree(float posX, float posY) {
    	return bg.isFree(posX, posY);
    }
    
    public void dispose() {
    	game.getStage().getRoot().removeActor(bg);
		bg.dispose();
    }
}
