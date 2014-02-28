package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MainCharacter extends Minimal {
	//private int score;
	private Stage stage;
	private Vector2 minLimit, maxLimit;
	
	public MainCharacter(Type type, String name){
		super(type, name);
		//score = 0;
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		minLimit = new Vector2();
		maxLimit = new Vector2();
		minLimit.x = w*0.15f;
		minLimit.y = h*0.15f;
		maxLimit.x = w*0.75f - entityCircle.radius*2;
		maxLimit.y = h*0.85f - entityCircle.radius*2;
	}
	
	@Override
	public void setStage (Stage stage){
		this.stage = stage;
	}
	
	@Override
	public void move (float x, float y){
		if(x != 0 && y != 0) { //Si hay movimiento
			setStatus(Status.WALK);
			entityCircle.x = entityCircle.x + x*speed;
			entityCircle.y = entityCircle.y + y*speed;
			this.rotation = (float) Math.atan2(y*speed, x*speed)*57.3f;
			
			if(entityCircle.x > maxLimit.x) { 			// Supera el máximo en el eje x
				entityCircle.x = maxLimit.x;
				stage.getCamera().translate(x*speed, 0, 0);
			} else if (entityCircle.x < minLimit.x) { 	// Supera el mínimo en el eje x
				entityCircle.x = minLimit.x;
				stage.getCamera().translate(x*speed, 0, 0);
			}
			
			if (entityCircle.y > maxLimit.y) { 			// Supera el máximo en el eje y
				entityCircle.y = maxLimit.y;
				stage.getCamera().translate(0, y*speed, 0);
			} else if (entityCircle.y < minLimit.y) {	// Supera el mínimo en el eje y
				entityCircle.y = minLimit.y;
				stage.getCamera().translate(0, y*speed, 0);
			}
			/*if(posX <= maxLimit.x && posY <= maxLimit.y){ // No supera el máximo
				if (posX <= minLimit.x ){ // Supera el mínimo en el eje x
					// Poner x como minLimit.x
					entityCircle.x = minLimit.x;
					if (posY <= minLimit.y){ // En la esquina inferior
						// Poner y como minLimit.y. 
						entityCircle.y = minLimit.y;
						//Mover la cámara en x e y
						stage.getCamera().translate(x*speed, y*speed, 0);
					} else {
						// Mover en el eje y. 
						entityCircle.y = posY;
						//Mover la cámara en x
						stage.getCamera().translate(x*speed, 0, 0);
					}
				} else if (posY <= minLimit.y){ // Supera el mínimo en el eje y
					// Poner y como minLimit y mover en el eje x. 
					entityCircle.y = minLimit.y;
					entityCircle.x = posX;
					//Mover la cámara en y
					stage.getCamera().translate(0, y*speed, 0);
				} else { // Está dentro del cuadrado
					// Mover el eje x e y
					entityCircle.y = posY;
					entityCircle.x = posX;
				}
			} else { // Supera el máximo
				if(posX >= maxLimit.x){ // Supera el máximo en el eje x
					// Poner x como maxLimit
					entityCircle.x = maxLimit.x;
					if(posY >= maxLimit.y){ // En la esquina superior
						// Poner y como maxLimit. 
						entityCircle.y = maxLimit.y;
						//Mover la cámara en x e y
						stage.getCamera().translate(x*speed, y*speed, 0);
					} else {
						// Mover en el eje y
						entityCircle.y = posY;
						// Mover la cámara en x
						stage.getCamera().translate(x*speed, 0, 0);
					}
				} else if(posY >= maxLimit.y){ // Supera el máximo en el eje y
					// Poner y como maxLimit y mover en el eje x
					entityCircle.y = maxLimit.y;
					entityCircle.x = posX;
					//Mover la cámara en y
					stage.getCamera().translate(0, y*speed, 0);
				}
			}*/
		}
	}
}
