/*******************************************************************************
 * Copyright (c) 2014
 *
 * @author Elisabet Romero Vaquero
 *******************************************************************************/
package screenControl;

import java.util.LinkedList;

import roundwar.RoundWar;
import roundwar.Scene;
import Attacks.Attack;
import Entities.Entity;
import Entities.LivingEntity;
import Entities.MainCharacter;
import PopUps.GamePausePopUp;
import PopUps.StatChangePopUp;
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
	private StatChangePopUp statPopUp;
	private static Scene scene;
	private float time;
	private final Vector2 minLimit, maxLimit;
	private LinkedList<LivingEntity> entities;
	public LinkedList<Attack> attacks;
	
	public static final float tileSize = 32f;

    /**
	 * Constructor con id del escenario.
	 * @param characterProfile
	 * @param id Id del escenario a crear
	 */
    public GameScreen(CharacterProfile characterProfile, int id) {     
            super();
            
            time = 0; // Contador de tiempo jugado (en segundos)
            
            // Inicialización del personaje del jugador
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
            StatChangePopUp.setCharacterProfile(characterProfile);
            
            // Inicialización de Hud y cámaras
            scene = new Scene(id);
            hud = new Hud(this);
            statPopUp = new StatChangePopUp(stage.getSpriteBatch());
            pauseMenu = new GamePausePopUp(stage.getSpriteBatch());
            winLosePopUp = new WinLosePopUp(stage.getSpriteBatch());
            
            batch.setProjectionMatrix(stage.getCamera().combined);
            
            // Inicialización de entidades   
            entities.add(mainpj);
            stage.addActor(mainpj);
    }
    
    /**
     * Devuelve el escenario en el que se está jugando.
     */
    public static Scene getScene() {
    	return scene;
    }
    
    /**
     * Devuelve el escenario en el que se está jugando.
     */
    public static void setScene(Scene scene) {
    	GameScreen.scene = scene;
    }
    
    /**
     * Devuelve los enemigos que hay vivos en el escenario.
     */
    public int getLeftEnemies() {
    	return entities.size()-1;
    }
    
    /**
     * Muestra el mensaje de escenario superado y guarda los datos.
     */
    public void winGame() {
    	winLosePopUp.show(true);
    	setPause(true);
    	mainpj.save();
    	RoundWar.save();
    }
    
    /**
     * Muestra el mensaje de derrota y guarda los datos.
     */
    public void loseGame() {
    	winLosePopUp.show(false);
    	setPause(true);
    	mainpj.save();
    	RoundWar.save();
    }
    
    
    /**
     * Muestra el pop up de estadísticas de jugador.
     */
    public void showStats() {
    	pauseMenu.close();
    	statPopUp.show();
    	setPause(true);
    }
    
    /**
     * Pone el juego en pausa y muestra el menú de pausa.
     */
    public void setGamePause(boolean pause) {
    	setPause(pause);
    	if(pause) pauseMenu.show();
    	else pauseMenu.close();
    }
    
    /**
     * Pausa el juego.
     */
    @Override
    public void setPause(boolean pause) {
    	super.setPause(pause);
    	if(!pause) Gdx.input.setInputProcessor(hud.getStage());
    }
    
    /**
     * Devuelve el tiempo de juego (en segundos).
     */
    public float getTime() {
    	return time;
    }
    
    /**
     * Añade una entidad al juego
     */
    public void addEntity(LivingEntity entity) {
    	entities.add(entity);
    	stage.addActor(entity);
    }
    
    /**
     * Dibuja la pantalla y los popUps en caso de estar visibles.
     */
    @Override
    public void render(float delta) {
    	if(!pause) gameRender(delta);
    	else if(pauseMenu.isVisible()) pauseMenu.draw(delta);
    	else if(winLosePopUp.isVisible()) winLosePopUp.draw(delta);
    	else if(statPopUp.isVisible()) statPopUp.draw(delta);
    }
    
    /**
     * Dibuja el juego no pausado.
     */
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
    
    /**
     * Punto que indica la esquina máxima hasta donde se podrá mover el personaje principal en la cámara.
     */
    public Vector2 getMaxLimit(){
    	return stage.screenToStageCoordinates(maxLimit.cpy());
    }
    
    /**
     * Punto que indica la esquina mínima hasta donde se podrá mover el personaje principal en la cámara.
     */
    public Vector2 getMinLimit(){
    	return stage.screenToStageCoordinates(minLimit.cpy());
    }
    
    /**
     * Devuelve el personaje principal.
     */
    public MainCharacter getCharacter(){
    	return mainpj;
    }
    
    /**
     * Calcula la celda adyacente a una posición dada en la dirección que se indique.
     */
    public Vector2 calculeAdyacentCellCenter(float posX, float posY, int direction) {
    	return scene.getBackground().calculeAdyacentCellCenter(posX, posY, direction);
    }
    
    /**
     * Devuelve la entidad, en caso de haberla, con la que colisiona una entidad avanzando deltaX y deltaY.
     * @param entity Entidad con la que se quiere comprobar si hay alguna colisión.
     * @param deltaX Valor en el eje x hacia donde se moverá la entidad.
     * @param deltaY Valor en el eje y hacia donde se moverá la entidad.
     */
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
    
    /**
     * Devuelve la entidad, en caso de haberla, con la que colisiona una entidad en la posicion dada.
     * @param entity Entidad con la que se quiere comprobar si hay alguna colisión.
     * @param deltaX Valor en el eje x donde está la entidad.
     * @param deltaY Valor en el eje y donde está la entidad.
     */
    public LivingEntity collidesWithEntity (LivingEntity entity, float posX, float posY) {
    	Rectangle bounds = new Rectangle(posX-entity.getWidth()/2, posY-entity.getHeight()/2, entity.getWidth(), entity.getHeight());
    	for (LivingEntity ent : entities) {
    		if(!entity.equals(ent) && ent.getBounds().overlaps(bounds))
    			return ent;
    	}
    	return null;
    }
    
    /**
     * Devuelve la entidad, en caso de haberla con la que colisiona un ataque.
     * @param entity Entidad con la que se quiere comprobar si hay alguna colisión.
     * @param deltaX Valor en el eje x donde está el ataque.
     * @param deltaY Valor en el eje y donde está el ataque.
     */
    public LivingEntity attackCollides (LivingEntity entity, float posX, float posY) {
    	for (LivingEntity ent : entities) {
    		if(!entity.equals(ent) && ent.getBounds().contains(posX, posY))
    			return ent;
    	}
    	return null;
    }
    
    /**
     * Devuelve al jugador principal en caso de que un enemigo choque contra él.
     */
    public LivingEntity enemyAttackCollides (LivingEntity entity, float posX, float posY) {
    	for (LivingEntity ent : entities) {
    		if(ent instanceof MainCharacter && ent.getBounds().contains(posX, posY))
    			return ent;
    	}
    	return null;
    }
    
    /**
     * Elimina una entidad del escenario.
     */
    public void removeEntity(LivingEntity entity) {
    	getStage().getRoot().removeActor(entity);
    	entities.remove(entity);
    	scene.removeEnemy(1);
    }
    
    /**
     * Oculta temporalmente una entidad del escenario
     */
    public void removeTemporallyEntity(LivingEntity entity) {
    	entity.setVisible(false);
    }
    
    /**
     * Muestra temporalmente una entidad del escenario
     */
    public void addTemporallyEntity(LivingEntity entity) {
    	entity.setVisible(true);
    }
    
    /**
     * Elimina un ataque del escenario.
     * @param attack
     */
    public void removeAttack(Attack attack) {
    	getStage().getRoot().removeActor(attack);
    	attacks.remove(attack);
    }
    
    /**
     * Redimensiona la pantalla.
     */
    @Override
	public void resize(int width, int height) {
    	super.resize(width, height);
    	hud.resize(width, height);
    	pauseMenu.resize(width, height);
	}
    
    /**
     * Liberala memoria para eliminar la pantalla.
     */
    @Override
	public void dispose() {
        hud.dispose();
        scene.dispose();
        for (LivingEntity entity : entities) {
        	entity.dispose();
        }
        Attack.dispose();
//        for(Attack attack : attacks) {
//    		attack.dispose();
//    	}
        winLosePopUp.dispose();
        statPopUp.dispose();
        pauseMenu.dispose();
	}
}
