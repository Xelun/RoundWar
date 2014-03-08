package Entities;

import screenControl.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MainCharacter extends LivingEntity {
	//private int score;
	public String name;
	private Stage stage;
	private Vector2 minLimit, maxLimit;
	
	public MainCharacter(Type type, String name, GameScreen game){
		super(type, game);
    	this.name = name;
		//score = 0;
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		setPosition(w/2, h/2);
		minLimit = new Vector2(w*0.15f, h*0.15f);
		maxLimit = new Vector2(w*0.75f - getWidth(), h*0.85f - getHeight());
	}
	
	/*@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		rectangle.begin(ShapeType.Line);
		rectangle.box(bounds.getX(), bounds.getY(), 0, bounds.getWidth(), bounds.getHeight(), 0);
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
		
		if(getX() > maxLimit.x) { 			// Supera el máximo en el eje x
			setX(maxLimit.x);
			stage.getCamera().translate(deltaX*statVel, 0, 0);
		} else if (getX() < minLimit.x) { 	// Supera el mínimo en el eje x
			setX(minLimit.x);
			stage.getCamera().translate(deltaX*statVel, 0, 0);
		}
		
		if (getY() > maxLimit.y) { 			// Supera el máximo en el eje y
			setY(maxLimit.y);
			stage.getCamera().translate(0, deltaY*statVel, 0);
		} else if (getY() < minLimit.y) {	// Supera el mínimo en el eje y
			setY(minLimit.y);
			stage.getCamera().translate(0, deltaY*statVel, 0);
		}
	}
}
