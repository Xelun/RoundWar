package Entities;

import roundwar.HealthBar;
import roundwar.RoundWar;

import com.badlogic.gdx.Gdx;

public class MainCharacter extends Entity{
	public int lvl;
    public int score;
    private HealthBar healthBar;
	
	public MainCharacter(Type type, String name) {
		super(type, name);
		lvl = 0;
    	score = 0;
    	this.move(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
	}

	public void setHealthBar (HealthBar healthBar){
		this.healthBar = healthBar;
	}
	
	public void setPosition(int h, int w){
    	//Coloca a tu bicho donde toques con la pantalla
    	if(Gdx.input.isTouched()){
    		cirEntity.x = Gdx.input.getX() - cirEntity.radius;
    		cirEntity.y = h - Gdx.input.getY() - cirEntity.radius;
    		Gdx.app.log( RoundWar.LOG, "Vida = " + health ); 
			//Gdx.app.log( RoundWar.LOG, "Moving pirko to: " + cirEntity.x + " x " + cirEntity.y ); 
        }
    	
        // Evita que se salga de los límites de la pantalla
        if(cirEntity.x < 0){
        	cirEntity.x = 0;
        } else if(cirEntity.x + cirEntity.radius > w){
        	cirEntity.x = w - cirEntity.radius;
        } else if(cirEntity.y < 0){
        	cirEntity.y = 0;
        } else if(cirEntity.y + cirEntity.radius > h){
        	cirEntity.y = h - cirEntity.radius;
        }
    }
}
