package Entities;

import screenControl.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Entity extends Actor{
	//Atributos de imagen
	protected Texture entityTexture;
    protected TextureRegion currentFrame;
    protected Rectangle bounds;
    protected static GameScreen game;
	
    /**
     * Inicializa la entidad.
     * @param size Tamaño de la entidad
     * @param scale Escala a la que se dibuja
     * @param path Dirección de su sprite
     * @param rotation Rotación con la que se va a dibujar
     * @param posX Posicion x donde se crea
     * @param posY Posicion y donde se crea
     */
    protected void initializeEntity(float size, float scale, String path, float rotation, float posX, float posY){
    	setScale(scale);
    	setRotation(rotation);
    	bounds = new Rectangle(posX, posY, size*scale, size*scale);
    	
    	entityTexture = new Texture(Gdx.files.internal(path));
    }
    
    /**
     * Devuelve el centro en el eje X de la entidad.
     * @return
     */
    public float getCenterX() {
    	return bounds.getX() + bounds.getWidth()/2f;
    }
    
    /**
     * Devuelve el centro en el eje Y de la entidad.
     * @return
     */
    public float getCenterY() {
    	return bounds.getY() + bounds.getHeight()/2f;
    }
    
    /**
     * Pone el centro en la posición pasada.
     * @param posX
     */
    public void setCenterX(float posX) {
    	bounds.x = posX - bounds.getWidth()/2f;
    }
    
    /**
     * Pone el centro en la posición pasada.
     * @param posX
     */
    public void setCenterY(float posY) {
    	bounds.y = posY - bounds.getHeight()/2f;
    }
    
    /**
     * Pone el centro en la posición pasada.
     * @param posX
     */
    public void setCenterPosition(Vector2 position) {
    	setCenterPosition(position.x, position.y);
    }
    
    /**
     * Pone el centro en la posición pasada.
     * @param posX
     */
    public void setCenterPosition(float posX, float posY) {
    	bounds.x = posX - bounds.getWidth()/2f;
    	bounds.y = posY - bounds.getHeight()/2f;
    }
    
    /**
     * Libera memoria.
     */
	public void dispose(){
		entityTexture.dispose();
	}
	
	/**
	 * Devuelve el rectángulo de colisión.
	 * @return
	 */
	public Rectangle getBounds() {
		return bounds;
	}
	
	
	/**
	 * Establece la pantalla de juego donde se creará la entidad.
	 * @param screen
	 */
	public static void setScreen(GameScreen screen) {
		Entity.game = screen;
		Enemy.setEnemy(game.getCharacter());
	}

	@Override
	public void setPosition(float posX, float posY) {
		bounds.setPosition(posX, posY);
	}
	
	public void setPosition(Vector2 position) {
		bounds.setPosition(position);
	}
	
	@Override
	public void setX(float x){
		bounds.setX(x);
	}
	
	@Override
	public void setY(float y){
		bounds.setY(y);
	}
	
	@Override
	public float getWidth() {
		return bounds.getWidth();
	}
	
	@Override
	public float getHeight() {
		return bounds.getHeight();
	}
}
