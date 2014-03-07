package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Entity extends Actor{
	public String name;
	
	//Atributos de imagen
	protected Texture entityTexture;
    protected Rectangle entityCircle;
    protected String path;
    protected float scale;
    protected float rotation;
    protected TextureRegion currentFrame;
    
    float stateTime;
    
    public Entity() { }
	
    protected void inicialiceEntity(String name, float size, float scale, String path, float rotation, float posX, float posY){
    	this.name = name;
    	this.setSize(size*scale, size*scale);
    	this.scale = scale;
    	this.stateTime = 0f;
    	this.path = path; 
    	this.rotation = rotation;
    	
    	entityCircle = new Rectangle(posX, posY, getWidth(), getHeight());
    	entityTexture = new Texture(Gdx.files.internal(path));
    	System.out.println("Tamaño: " + this.getHeight() + " x " + this.getWidth());
    }
    
    public float getCenterX() {
    	return entityCircle.x + getWidth()/2;
    }
    
    public float getCenterY() {
    	return entityCircle.y + getHeight()/2;
    }
    
	public void dispose(){
		entityTexture.dispose();
	}
}
