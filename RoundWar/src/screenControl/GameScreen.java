package screenControl;

import java.util.LinkedList;

import roundwar.PathFinder;
import roundwar.RoundWar;
import Attacks.Attack;
import Entities.Enemy;
import Entities.Entity;
import Entities.LivingEntity;
import Entities.MainCharacter;
import Entities.ReturnIntEntity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameScreen extends AbstractScreen {
	private MainCharacter mainpj;
	
	private LinkedList<LivingEntity> entities;
	private Hud hud;
	private final Vector2 minLimit, maxLimit;
	private int maxEnemies, totalEnemies;
	public LinkedList<Attack> attacks;
	
	public static final float tileSize = 32f;

    public GameScreen(RoundWar game) {     
            super(game);
            
            int h = Gdx.graphics.getHeight();
            int w = Gdx.graphics.getWidth();
            
            // Inicialización del monstruo del jugador
            mainpj = new MainCharacter(LivingEntity.Type.PIRKO, "Pirko");
            
            // Inicialización de vectores
            attacks  = new LinkedList<Attack>();
            entities = new LinkedList<LivingEntity>();
            minLimit = new Vector2(w*0.15f, h*0.85f);
    		maxLimit = new Vector2(w*0.75f - mainpj.getWidth(), h*0.15f + mainpj.getHeight());
    		
            // Inicializaciones de clase
            Attack.setScreen(this);
            PathFinder.setScreen(this);
            Entity.setScreen(this);
            
            //Inicialización de Hud, fondo y cámaras
            setBackground(this);
            hud = new Hud(this, true);
            batch.setProjectionMatrix(stage.getCamera().combined);
            
            // Inicialización de entidades
            maxEnemies = 5;
            
            entities.add(mainpj);
            entities.add(new Enemy(LivingEntity.Type.ENEMY1, calculateRandomSpawn()));
            //entities.add(new Enemy(LivingEntity.Type.ENEMY1, 800, 100));
            //entities.add(new Enemy(LivingEntity.Type.ENEMY1, 200, 500));
            //entities.add(new Enemy(LivingEntity.Type.ENEMY1, 100, 450));
            totalEnemies = 1;
            
            for (LivingEntity entity : entities) {
            	stage.addActor(entity);
            }
    }
    
    @Override
    public void render(float delta) {
    	super.render(delta);
    	drawStage(delta);
    	hud.drawStage(delta);
    	stage.getSpriteBatch().begin();
    	getFont().draw(batch, "FPS:   " + Gdx.graphics.getFramesPerSecond(), 20, 90);
    	getFont().draw(batch, String.format("Max:   %.1f", (float)(Runtime.getRuntime().maxMemory()   / 1048576f)), 20, 70);
    	getFont().draw(batch, String.format("Free:  %.1f", (float)(Runtime.getRuntime().freeMemory()  / 1048576f)), 20, 50);
    	getFont().draw(batch, String.format("Total: %.1f", (float)(Runtime.getRuntime().totalMemory() / 1048576f)), 20, 30);
    	stage.getSpriteBatch().end();
    }
    
    public Vector2 getMaxLimit(){
    	return stage.screenToStageCoordinates(maxLimit.cpy());
    }
    
    public Vector2 getMinLimit(){
    	return stage.screenToStageCoordinates(minLimit.cpy());
    }
    
    public MainCharacter getCharacter(){
    	return mainpj;
    }
    
    private Vector2 calculateRandomSpawn() {
    	return bg.calculateRandomSpawn();
    }
    
    public Vector2 calculeAdyacentCellCenter(float posX, float posY, int direction) {
    	return bg.calculeAdyacentCellCenter(posX, posY, direction);
    }
    
    public TiledMapTileLayer getLayerCollision() {
    	return bg.getLayerColission();
    }
    
    public LivingEntity collides(LivingEntity entity, float deltaX, float deltaY) {
    	Rectangle bounds = new Rectangle(entity.getBounds());
    	bounds.x += deltaX;
    	bounds.y += deltaY;
    	for (LivingEntity ent : entities) {
    		if(!entity.equals(ent) && bounds.overlaps(ent.getBounds())) {
    			return ent;
    		}
    	}
    	return null;
    }
    
    public ReturnIntEntity isFree(LivingEntity entity, float deltaX, float deltaY, int cooldown) {
    	if(cooldown >= 0) {
    		return isFree(entity, deltaX, deltaY);
    	} else {
    		LivingEntity ent = collides(entity, deltaX, deltaY);
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
    	LivingEntity ent = collides(entity, deltaX, deltaY);
    	
    	if(ent == null && bg.isFree(bounds)) { 			// Sin colision en ningún eje
    		return result;
    	} else {
    		result.setEntity(ent);
    		ent = collides(entity, deltaX, 0);
	    	bounds.y -= deltaY;
	    	if(ent == null && bg.isFree(bounds)) { 		// Sin colision en el eje x
	    		result.setInt(1);
	    	} else {
	    		result.setEntity(ent);
	    		ent = collides(entity, 0, deltaY);
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
    
    public LivingEntity attackCollides (LivingEntity entity, float posX, float posY) {
    	for (LivingEntity ent : entities) {
    		if(!entity.equals(ent) && ent.collides(posX, posY))
    			return ent;
    	}
    	return null;
    }
    
    public void removeEntity(LivingEntity entity) {
    	getStage().getRoot().removeActor(entity);
    	entities.remove(entity);
    }
    
    public void removeAttack(Attack attack) {
    	getStage().getRoot().removeActor(attack);
    	attacks.remove(attack);
    }
    
    @Override
	public void resize(int width, int height) {
    	super.resize(width, height);
    	hud.resize(width, height);
	}
    
    @Override
	public void dispose() {
        hud.dispose();
        for (LivingEntity entity : entities) {
        	entity.dispose();
        }
        for(Attack attack : attacks) {
    		attack.dispose();
    	}
	}
}
