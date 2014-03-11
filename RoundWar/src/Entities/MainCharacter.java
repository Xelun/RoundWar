package Entities;

import screenControl.GameScreen;
import screenControl.Hud;
import Attacks.RangeAttack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MainCharacter extends LivingEntity {
	//private int score;
	public String name;
	private Stage stage;
	private RangeAttack range;
	
	public MainCharacter(Type type, String name, GameScreen game){
		super(type, game);
    	this.name = name;
		//score = 0;
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		setPosition(w/2, h/2);
		range = new RangeAttack(this);
	}
	
	public void setRangeRadius(Hud.AttackType type) {
		range.setRadius(type);
	}
	
	public void setRangeVisible(boolean visible) {
		range.setVisible(visible);
	}
	
	@Override
	public void setStage (Stage stage){
		this.stage = stage;
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		range.draw(batch, parentAlpha);
		super.draw(batch, parentAlpha);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		range.act(delta);
	}
	
	@Override
	public void dispose() {
		range.dispose();
		super.dispose();
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
		if(bounds.x < maxLimit.x) { 			// Supera el máximo en el eje x
			stage.getCamera().translate(movX, 0, 0);
		} else if (bounds.x > minLimit.x) { 	// Supera el mínimo en el eje x
			stage.getCamera().translate(movX, 0, 0);
		}
		
		if (bounds.y < maxLimit.y) { 			// Supera el máximo en el eje y
			stage.getCamera().translate(0, movY, 0);
		} else if (bounds.y > minLimit.y) {	// Supera el mínimo en el eje y
			stage.getCamera().translate(0, movY, 0);
		}
	}
}
