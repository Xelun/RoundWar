package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

public abstract class Entity {
	public String name;
	
	//Atributos de imagen
	protected Texture entityTexture;
    protected Circle entityCircle;
    protected String path;
    protected int size;
    protected TextureRegion currentFrame;
    
    float stateTime;
    
    public Entity(String name) {
    	this.name = name;
        stateTime = 0f;
    }
	
	public void dispose(){
		//entityTexture.dispose();
	}
}
