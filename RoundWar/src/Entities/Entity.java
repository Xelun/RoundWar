package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Entity extends Actor{
	public String name;
	
	//Atributos de imagen
	protected Texture entityTexture;
    protected Circle entityCircle;
    protected String path;
    protected int radius;
    protected Vector2 origin;
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
    	this.origin = new Vector2();
    	
    	entityCircle = new Circle(posX, posY, this.radius);
    	entityTexture = new Texture(Gdx.files.internal(path));
    	origin.x = entityCircle.x + radius;
    	origin.y = entityCircle.y + radius;
    }
    
    @Override
	public void setPosition(float posX, float posY){
    	origin.x = posX;
    	origin.y = posY;
    	entityCircle.x = origin.x - radius;
    	entityCircle.y = origin.y - radius;
    }
    
	public void dispose(){
		entityTexture.dispose();
	}
}
