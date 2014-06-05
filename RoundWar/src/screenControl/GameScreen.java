package screenControl;

import java.util.LinkedList;

import roundwar.RoundWar;
import roundwar.Scene;
import Attacks.Attack;
import Entities.Entity;
import Entities.LivingEntity;
import Entities.MainCharacter;
import PopUps.GamePausePopUp;
import PopUps.WinLosePopUp;
import ProfileSettings.CharacterProfile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameScreen extends AbstractScreen {
	private MainCharacter mainpj;
	private Hud hud;
	private GamePausePopUp pauseMenu;
	private WinLosePopUp winLosePopUp;
	private Scene scene;
	private float time;
	private final Vector2 minLimit, maxLimit;
	private LinkedList<LivingEntity> entities;
	public LinkedList<Attack> attacks;
	
	public static final float tileSize = 32f;

    public GameScreen(CharacterProfile characterProfile) {     
            super();
            
            int h = Gdx.graphics.getHeight();
            int w = Gdx.graphics.getWidth();
            time = 0;
            
            // Inicialización del monstruo del jugador
            this.mainpj = new MainCharacter(characterProfile);
            
            // Inicialización de vectores
            attacks  = new LinkedList<Attack>();
            entities = new LinkedList<LivingEntity>();
            minLimit = new Vector2(w*0.15f, h*0.85f);
    		maxLimit = new Vector2(w*0.75f - mainpj.getWidth(), h*0.15f + mainpj.getHeight());
    		
            // Inicializaciones de clase
            Attack.setScreen(this);
            Entity.setScreen(this);
            Scene.setScreen(this);
            GamePausePopUp.setScreen(this);
            
            // Inicialización de Hud, nivel y cámaras
            scene = new Scene("Test");
            hud = new Hud(this);
            pauseMenu = new GamePausePopUp(stage.getSpriteBatch());
            winLosePopUp = new WinLosePopUp(stage.getSpriteBatch());
            batch.setProjectionMatrix(stage.getCamera().combined);
            
            // Inicialización de entidades   
            entities.add(mainpj);
            stage.addActor(mainpj);
    }
    
    public Scene getScene() {
    	return scene;
    }
    
    public int getLeftEnemies() {
    	return entities.size()-1;
    }
    
    public void winGame() {
    	winLosePopUp.show(true);
    	setPause(true);
    	RoundWar.save();
    }
    
    public void loseGame() {
    	winLosePopUp.show(false);
    	setPause(true);
    	RoundWar.save();
    }
    
    public void setGamePause(boolean pause) {
    	setPause(pause);
    	if(pause) pauseMenu.show();
    	else pauseMenu.close();
    }
    
    @Override
    public void setPause(boolean pause) {
    	super.setPause(pause);
    	if(!pause) Gdx.input.setInputProcessor(hud.getStage());
    }
    
    public float getTime() {
    	return time;
    }
    
    public void addEntity(LivingEntity entity) {
    	entities.add(entity);
    	stage.addActor(entity);
    	System.out.println(entities.size());
    }
    
    @Override
    public void render(float delta) {
    	if(!pause) gameRender(delta);
    	else if(pauseMenu.isVisible()) pauseMenu.draw(delta);
    	else if(winLosePopUp.isVisible()) winLosePopUp.draw(delta);
    }
    
    private void gameRender(float delta) {
    	if(time == 0) {
    		stage.getCamera().position.set(mainpj.getCenterX(), mainpj.getCenterY(), 0);
    	}
    	time += delta;
    	super.render(delta);
    	scene.update(delta);
    	hud.drawStage(delta);
    	stage.getSpriteBatch().begin();
    	getFont().draw(batch, "FPS:   " + Gdx.graphics.getFramesPerSecond(), 20, 30);
    	//getFont().draw(batch, String.format("Max:   %.1f", (float)(Runtime.getRuntime().maxMemory()   / 1048576f)), 20, 70);
    	//getFont().draw(batch, String.format("Free:  %.1f", (float)(Runtime.getRuntime().freeMemory()  / 1048576f)), 20, 50);
    	//getFont().draw(batch, String.format("Total: %.1f", (float)(Runtime.getRuntime().totalMemory() / 1048576f)), 20, 30);
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
    
    public Vector2 calculeAdyacentCellCenter(float posX, float posY, int direction) {
    	return scene.getBackground().calculeAdyacentCellCenter(posX, posY, direction);
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
    
    public LivingEntity collidesWithEntity (LivingEntity entity, float posX, float posY) {
    	Rectangle bounds = new Rectangle(posX-entity.getWidth()/2, posY-entity.getHeight()/2, entity.getWidth(), entity.getHeight());
    	for (LivingEntity ent : entities) {
    		if(!entity.equals(ent) && ent.getBounds().overlaps(bounds))
    			return ent;
    	}
    	return null;
    }
    
    public LivingEntity attackCollides (LivingEntity entity, float posX, float posY) {
    	for (LivingEntity ent : entities) {
    		if(!entity.equals(ent) && ent.getBounds().contains(posX, posY))
    			return ent;
    	}
    	return null;
    }
    
    public LivingEntity enemyAttackCollides (LivingEntity entity, float posX, float posY) {
    	for (LivingEntity ent : entities) {
    		if(ent instanceof MainCharacter && ent.getBounds().contains(posX, posY))
    			return ent;
    	}
    	return null;
    }
    
    public void removeEntity(LivingEntity entity) {
    	getStage().getRoot().removeActor(entity);
    	entities.remove(entity);
    	scene.removeEnemy(1);
    }
    
    public void removeTemporallyEntity(LivingEntity entity) {
    	entity.setVisible(false);
    	//entities.remove(entity);
    }
    
    public void addTemporallyEntity(LivingEntity entity) {
    	entity.setVisible(true);
    	//entities.add(entity);
    }
    
    public void removeAttack(Attack attack) {
    	getStage().getRoot().removeActor(attack);
    	attacks.remove(attack);
    	attack.dispose();
    }
    
    @Override
	public void resize(int width, int height) {
    	super.resize(width, height);
    	hud.resize(width, height);
    	pauseMenu.resize(width, height);
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
