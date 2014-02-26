package Entities;

import roundwar.RoundWar;

import com.badlogic.gdx.Gdx;

public class Minimal extends LivingEntity{
	public int lvl;
    
    private float speed;
	
	public Minimal(Type type, String name) {
		super(type, name);
		lvl = 0;
    	speed = 2f;
    	setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
	}
	
	public void setPosition(int h, int w){
    	//Coloca a tu bicho donde toques con la pantalla
    	if(Gdx.input.isTouched()){
    		entityCircle.x = Gdx.input.getX() - entityCircle.radius;
    		entityCircle.y = h - Gdx.input.getY() - entityCircle.radius;
    		Gdx.app.log( RoundWar.LOG, "Vida = " + health ); 
			//Gdx.app.log( RoundWar.LOG, "Moving pirko to: " + entityCircle.x + " x " + entityCircle.y ); 
        }
    	
        // Evita que se salga de los límites de la pantalla
        if(entityCircle.x < 0){
        	entityCircle.x = 0;
        } else if(entityCircle.x + entityCircle.radius > w){
        	entityCircle.x = w - entityCircle.radius;
        } else if(entityCircle.y < 0){
        	entityCircle.y = 0;
        } else if(entityCircle.y + entityCircle.radius > h){
        	entityCircle.y = h - entityCircle.radius;
        }
    }
	
	public void move (float x, float y, float rotation){
		entityCircle.x = entityCircle.x + x*speed;
		entityCircle.y = entityCircle.y + y*speed;
		this.rotation = rotation;
	}
	
	public void move (float x, float y){
		if(x != 0 && y != 0) {
			float x1 = x*speed;
			float y1 = y*speed;
			entityCircle.x = entityCircle.x + x1;
			entityCircle.y = entityCircle.y + y1;
			this.rotation = (float) Math.atan2(y1, x1)*57.3f;
		}
	}
}
