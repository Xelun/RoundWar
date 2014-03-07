package Entities;

import screenControl.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MainCharacter extends LivingEntity {
	//private int score;
	private Stage stage;
	private Vector2 minLimit, maxLimit;
	private boolean upleft, upright, downleft, downright;
	
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
	
	public void move (float deltaX, float deltaY){
		moveFromCollision(deltaX, deltaY);
		
		moveCamera(deltaX, deltaY);
	}
	
	private void moveFromCollision(float deltaX, float deltaY) {
		float xL = entityCircle.x + deltaX*statVel;
		float xR = entityCircle.x + deltaX*statVel + getWidth();
		float yU = entityCircle.y + deltaY*statVel;
		float yD = entityCircle.y + deltaY*statVel + getWidth();
		
		upleft=game.isFree(xL,yU);
		downleft=game.isFree(xL,yD);
		upright=game.isFree(xR,yU);
		downright=game.isFree(xR,yD);
		
		if(upleft && upright && downleft && downright){ //Sin colision, se mueve normal
			entityCircle.y = yD;
			entityCircle.x = xL;
		} /*else {
			if(deltaY != 0 && ((upleft && upright) || (downleft && downright))) {
				entityCircle.x = xL;
			}
			
			if(deltaX != 0 && ((downleft && upleft) || (downright && upright))) {
				entityCircle.y = yD;
			}
		}*/
	}
	
	private void moveCamera(float deltaX, float deltaY) {
		if((deltaX != 0 || deltaY != 0)) { //Si hay movimiento
			setStatus(Status.WALK); //Pone la animación de andar
			
			this.rotation = (float) Math.atan2(deltaY, deltaX)*57.3f; //Rota hacia donde apunte el controlador
			if(entityCircle.x > maxLimit.x) { 			// Supera el máximo en el eje x
				entityCircle.x = maxLimit.x;
				stage.getCamera().translate(deltaX*statVel, 0, 0);
			} else if (entityCircle.x < minLimit.x) { 	// Supera el mínimo en el eje x
				entityCircle.x = minLimit.x;
				stage.getCamera().translate(deltaX*statVel, 0, 0);
			}
			
			if (entityCircle.y > maxLimit.y) { 			// Supera el máximo en el eje y
				entityCircle.y = maxLimit.y;
				stage.getCamera().translate(0, deltaY*statVel, 0);
			} else if (entityCircle.y < minLimit.y) {	// Supera el mínimo en el eje y
				entityCircle.y = minLimit.y;
				stage.getCamera().translate(0, deltaY*statVel, 0);
			}
		}
	}
}
