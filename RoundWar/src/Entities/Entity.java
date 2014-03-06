package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Entity extends Actor{
	public String name;
	
	//Atributos de imagen
	protected Texture entityTexture;
    protected Rectangle entityCircle;
    protected String path;
    protected int radius;
    protected Vector2 center;
    protected float rotation;
    protected TextureRegion currentFrame;
    
    float stateTime;
    
    public Entity() { }
	
    protected void inicialiceEntity(String name, int radius, String path, float rotation, float posX, float posY){
    	this.name = name;
    	this.radius = radius;
    	this.stateTime = 0f;
    	this.path = path; 
    	this.rotation = rotation;
    	this.center = new Vector2();
    	
    	entityCircle = new Rectangle(posX, posY, this.radius*2, this.radius*2);
    	entityTexture = new Texture(Gdx.files.internal(path));
    }
    
    @Override
	public void setPosition(float posX, float posY){
    	entityCircle.x = posX - radius;
    	entityCircle.y = posY - radius;
    }
    
    public float getCenterX() {
    	return entityCircle.x + radius;
    }
    
    public float getCenterY() {
    	return entityCircle.y + radius;
    }
    
	public void dispose(){
		entityTexture.dispose();
	}
}
