package screenControl;

import java.util.LinkedList;

import roundwar.PathFinder;
import roundwar.RoundWar;
import roundwar.Scene;
import Attacks.Attack;
import Entities.Entity;
import Entities.LivingEntity;
import Entities.MainCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameScreen extends AbstractScreen {
	private MainCharacter mainpj;
	private Hud hud;
	private Scene scene;
	private float time;
	private final Vector2 minLimit, maxLimit;
	private LinkedList<LivingEntity> entities;
	public LinkedList<Attack> attacks;
	
	public static final float tileSize = 32f;

    public GameScreen(RoundWar game) {     
            super(game);
            
            int h = Gdx.graphics.getHeight();
            int w = Gdx.graphics.getWidth();
            time = 0;
            
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
            Scene.setScreen(this);
            
            //Inicialización de Hud, nivel y cámaras
            scene = new Scene("Prueba");
            hud = new Hud(this, true);
            batch.setProjectionMatrix(stage.getCamera().combined);
            
         // Inicialización de entidades   
            entities.add(mainpj);
            stage.addActor(mainpj);
            //entities.add(new Enemy(LivingEntity.Type.ENEMY1, calculateRandomSpawn()));
            //entities.add(new Enemy(LivingEntity.Type.ENEMY1, 800, 100));
            //entities.add(new Enemy(LivingEntity.Type.ENEMY1, 200, 500));
            //entities.add(new Enemy(LivingEntity.Type.ENEMY1, 100, 450));
            
            /*for (LivingEntity entity : entities) {
            	stage.addActor(entity);
            }*/
    }
    
    public Scene getScene() {
    	return scene;
    }
    
    public float getTime() {
    	return time;
    }
    
    public void addEntity(LivingEntity entity) {
    	entities.add(entity);
    	stage.addActor(entity);
    }
    
    @Override
    public void render(float delta) {
    	time += delta;
    	super.render(delta);
    	drawStage(delta);
    	hud.drawStage(delta);
    	scene.update(delta);
    	/*stage.getSpriteBatch().begin();
    	getFont().draw(batch, "FPS:   " + Gdx.graphics.getFramesPerSecond(), 20, 90);
    	getFont().draw(batch, String.format("Max:   %.1f", (float)(Runtime.getRuntime().maxMemory()   / 1048576f)), 20, 70);
    	getFont().draw(batch, String.format("Free:  %.1f", (float)(Runtime.getRuntime().freeMemory()  / 1048576f)), 20, 50);
    	getFont().draw(batch, String.format("Total: %.1f", (float)(Runtime.getRuntime().totalMemory() / 1048576f)), 20, 30);
    	stage.getSpriteBatch().end();*/
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
    
    /*private Vector2 calculateRandomSpawn() {
    	return scene.calculateRandomSpawn();
    }*/
    
    public Vector2 calculeAdyacentCellCenter(float posX, float posY, int direction) {
    	return scene.getBackground().calculeAdyacentCellCenter(posX, posY, direction);
    }
    
    public TiledMapTileLayer getLayerCollision() {
    	return scene.getBackground().getLayerColission();
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
    
    public LivingEntity attackCollides (LivingEntity entity, float posX, float posY) {
    	for (LivingEntity ent : entities) {
    		if(!entity.equals(ent) && ent.collides(posX, posY))
    			return ent;
    	}
    	return null;
    }
    
    public void removeEntity(LivingEntity entity) {
    	getStage().getRoot().removeActor(entity);
    	scene.updateNumEnemies(-1);
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
        scene.dispose();
        for (LivingEntity entity : entities) {
        	entity.dispose();
        }
        for(Attack attack : attacks) {
    		attack.dispose();
    	}
	}
}
