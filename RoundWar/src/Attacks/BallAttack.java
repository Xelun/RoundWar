package Attacks;

import Entities.LivingEntity;
import Entities.MainCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class BallAttack extends Attack {
	public enum TypeBallAttack { ARROW, BASIC }
	protected Texture attackTexture;
    protected TextureRegion currentFrame;
    private int centerX, centerY, size, rotation;
	
    public BallAttack(LivingEntity entity, float posX, float posY, TypeBallAttack type) {
		this(entity, new Vector2(posX, posY), type);
	}
    
    public BallAttack(LivingEntity entity, Vector2 pos, TypeBallAttack type) {
    	super(entity, pos);
    	inicialiceBallAttack(type);
    	//double radians = Math.toRadians(entity.getRotation());
    	//finalPos.x = entity.getCenterX() + (float) (size*Math.cos(radians));
    	//finalPos.y = entity.getCenterY() + (float) (size*Math.sin(radians));
		actualPos.x -= centerX/2;
		actualPos.y -= centerY/2;
		rotation = getRotation(type);
		
    }
    
    private void inicialiceBallAttack(TypeBallAttack type) {
    	switch (type) {
    	case BASIC:
    		centerX = centerY = 32;
    		seconds = 2;
    		size = 100;
    		attackTexture = new Texture(Gdx.files.internal("skin/touchKnob.png"));//sprite/ballattack.png"));
    		currentFrame =  new TextureRegion(attackTexture, 0, 0, centerX, centerY);
    		double radians = Math.toRadians(entity.getRotation());
    		finalPos.x = entity.getCenterX() + (float) (size*Math.cos(radians));
        	finalPos.y = entity.getCenterY() + (float) (size*Math.sin(radians));
    		//entity.addMp(-10);
    		break;
    	default:
    		centerX = 64;
    		centerY = 56;
    		seconds = 3;
    		size = 300;
    		attackTexture = new Texture(Gdx.files.internal("sprite/ballAttackArrow.png"));//sprite/ballattack.png"));
    		currentFrame =  new TextureRegion(attackTexture, 0, 0, centerX, centerY);
    		entity.addMp(-10);
    	}
    }
    
    private int getRotation(TypeBallAttack type) {
    	switch (type) {
    	case BASIC:
    		return 0;
    	default:
    		return (int) new Vector2(finalPos.x - actualPos.x, finalPos.y - actualPos.y).angle();
    	}
    }
    
	private void collides() {
		if(!game.getScene().isFree(actualPos.x + centerX/2, actualPos.y + centerY/2)) { // Si choca con un obstaculo
			dispose();
		} else {
			LivingEntity diana = entity instanceof MainCharacter ? game.attackCollides(entity, actualPos.x + centerX/2, actualPos.y + centerY/2)
					: game.enemyAttackCollides(entity, actualPos.x + centerX/2, actualPos.y + centerY/2);
			if(diana != null) {
				System.out.println("Daño = " + damage);
				diana.receiveDamage(entity, damage);
				dispose();
			}
		}
	}
	
	@Override
	public void act (float delta) {
		if(seconds < 0) {
			dispose();
		} else {
			actualPos.x += delta*(finalPos.x - actualPos.x)/seconds;
			actualPos.y += delta*(finalPos.y - actualPos.y)/seconds;
			seconds -= delta;
			collides();
		}
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		batch.draw(currentFrame, actualPos.x, actualPos.y, centerX/2, centerY/2, centerX, centerY, 1, 1, rotation);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		attackTexture.dispose();
	}
	
}
