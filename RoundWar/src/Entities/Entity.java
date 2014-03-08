package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Entity extends Actor{
	//Atributos de imagen
	protected Texture entityTexture;
    protected TextureRegion currentFrame;
    protected Rectangle bounds;
    
    float stateTime;
    
    public Entity() { }
	
    protected void initializeEntity(float size, float scale, String path, float rotation, float posX, float posY){
    	stateTime = 0f;
    	setScale(scale);
    	setRotation(rotation);
    	setBounds(posX, posY, size*scale, size*scale);
    	bounds = new Rectangle(posX, posY, size*scale, size*scale);
    	
    	entityTexture = new Texture(Gdx.files.internal(path));
    	System.out.println("Tamaño: " + getHeight() + " x " + getWidth());
    }
    
    public float getCenterX() {
    	return getX() + getWidth()/2;
    }
    
    public float getCenterY() {
    	return getY() + getHeight()/2;
    }
    
	public void dispose(){
		entityTexture.dispose();
	}
	
	public Rectangle getRectangle() {
		return bounds;
	}
	
	@Override
	public void setX(float x){
		super.setX(x);
		bounds.setX(x);
	}
	
	@Override
	public void setPosition(float posX, float posY) {
		super.setPosition(posX, posY);
		bounds.setPosition(posX, posY);
	}
	
	@Override
	public void setY(float y){
		super.setY(y);
		bounds.setY(y);
	}
}
