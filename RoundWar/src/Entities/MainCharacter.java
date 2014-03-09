package Entities;

import screenControl.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MainCharacter extends LivingEntity {
	//private int score;
	public String name;
	private Stage stage;
	
	public MainCharacter(Type type, String name, GameScreen game){
		super(type, game);
    	this.name = name;
		//score = 0;
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		setPosition(w/2, h/2);
	}
	
	/*@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		rectangle.begin(ShapeType.Line);
		rectangle.box(bounds.bounds.x, bounds.bounds.y, 0, bounds.getWidth(), bounds.getHeight(), 0);
		rectangle.end();
		super.draw(batch, parentAlpha);
	}*/
	
	@Override
	public void setStage (Stage stage){
		this.stage = stage;
	}
	
	public void move (float deltaX, float deltaY){
		if((deltaX != 0 || deltaY != 0)) { 			//Si hay movimiento
			setRotation((float) Math.atan2(deltaY, deltaX)*57.3f); //Rota hacia donde apunte el controlador
			if(moveEntity(deltaX, deltaY)) { 		//Si se puede mover porque no hay colision
				moveCamera(deltaX, deltaY);			//Mueve la cámara si ha llegado al límite de vision
			}
		}
	}
	
	private void moveCamera(float deltaX, float deltaY) {
		setStatus(Status.WALK); //Pone la animación de andar
		float movX = deltaX*statVel;
		float movY = deltaY*statVel;
		Vector2 maxLimit = game.getMinLimit();
		Vector2 minLimit = game.getMaxLimit();
		//System.out.println("Bicho " + (int)bounds.x + " x " + (int)bounds.y);
		//System.out.println("Maximo: " + (int)maxLimit.x + " x " + (int)maxLimit.y);
		//System.out.println("Minimo: " + (int)minLimit.x + " x " + (int)minLimit.y);
		if(bounds.x < maxLimit.x) { 			// Supera el máximo en el eje x
			//setX(maxLimit.x);
			//setX(bounds.x - movX);
			//System.out.println((int)bounds.x + " > " + (int)maxLimit.x);
			//System.out.println("Supera el maximo en x");
			stage.getCamera().translate(movX, 0, 0);
		} else if (bounds.x > minLimit.x) { 	// Supera el mínimo en el eje x
			//setX(minLimit.x);
			//setX(bounds.x + movX);
			//System.out.println("Supera el minimo en x");
			stage.getCamera().translate(movX, 0, 0);
		}
		
		if (bounds.y < maxLimit.y) { 			// Supera el máximo en el eje y
			//setY(maxLimit.y);
			//setY(bounds.y - movY);
			//System.out.println("Supera el maximo en y");
			stage.getCamera().translate(0, movY, 0);
		} else if (bounds.y > minLimit.y) {	// Supera el mínimo en el eje y
			//setY(minLimit.y);
			//setY(bounds.y + movY);
			//System.out.println("Supera el minimo en y");
			stage.getCamera().translate(0, movY, 0);
		}
	}
}
