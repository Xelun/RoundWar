package Entities;

import screenControl.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MainCharacter extends LivingEntity {
	//private int score;
	private Stage stage;
	private Vector2 minLimit, maxLimit;
	
	public MainCharacter(Type type, String name, GameScreen game){
		super(type, name, game);
		//score = 0;
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		entityCircle.x = w/2;
		entityCircle.y = h/2;
		minLimit = new Vector2(w*0.15f, h*0.15f);
		maxLimit = new Vector2(w*0.75f - entityCircle.width, h*0.85f - entityCircle.height);
	}
	
	@Override
	public void setStage (Stage stage){
		this.stage = stage;
	}
	
	public void move (float x, float y){
		
		if(x != 0 && y != 0 && canMove(entityCircle.x + x*statVel, entityCircle.y + y*statVel)) { //Si hay movimiento y no hay colision
			setStatus(Status.WALK); //Pone la animación de andar
			entityCircle.x = entityCircle.x + x*statVel;
			entityCircle.y = entityCircle.y + y*statVel;
			this.rotation = (float) Math.atan2(y, x)*57.3f; //Rota hacia donde apunte el controlador
			
			
			
			if(entityCircle.x > maxLimit.x) { 			// Supera el máximo en el eje x
				entityCircle.x = maxLimit.x;
				stage.getCamera().translate(x*statVel, 0, 0);
			} else if (entityCircle.x < minLimit.x) { 	// Supera el mínimo en el eje x
				entityCircle.x = minLimit.x;
				stage.getCamera().translate(x*statVel, 0, 0);
			}
			
			if (entityCircle.y > maxLimit.y) { 			// Supera el máximo en el eje y
				entityCircle.y = maxLimit.y;
				stage.getCamera().translate(0, y*statVel, 0);
			} else if (entityCircle.y < minLimit.y) {	// Supera el mínimo en el eje y
				entityCircle.y = minLimit.y;
				stage.getCamera().translate(0, y*statVel, 0);
			}
		}
	}
	
	public boolean collision() {
		
		return false;
	}
}
