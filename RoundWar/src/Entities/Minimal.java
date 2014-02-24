package Entities;

import roundwar.HealthBar;
import roundwar.ManaBar;
import roundwar.RoundWar;

import com.badlogic.gdx.Gdx;

public class Minimal extends LivingEntity{
	public int lvl;
    public int score;
    private HealthBar healthBar;
    private ManaBar manaBar;
	
	public Minimal(Type type, String name) {
		super(type, name);
		lvl = 0;
    	score = 0;
    	this.move(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
	}

	public void setHealthBar (HealthBar healthBar){
		this.healthBar = healthBar;
	}
	
	public void setManaBar (ManaBar manaBar){
		this.manaBar = manaBar;
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
}
