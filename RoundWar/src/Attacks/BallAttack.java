package Attacks;

import screenControl.AbstractScreen;
import screenControl.GameScreen;
import Entities.LivingEntity;
import Entities.MainCharacter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class BallAttack extends Attack {
    protected TextureRegion currentFrame;
    private int centerX, centerY, size;
    private float rotation;
	
    public BallAttack(LivingEntity entity, float posX, float posY, Type type) {
		this(entity, new Vector2(posX, posY), type);
	}
    
    public BallAttack(LivingEntity entity, Vector2 pos, Type type) {
    	super(entity, pos);
    	inicialiceBallAttack(type);
		actualPos.x -= centerX/2;
		actualPos.y -= centerY/2;
		if(entity instanceof MainCharacter) {
			rotation = entity.getRotation();
			double radians = Math.toRadians(rotation);
			finalPos.x = entity.getCenterX() + (float) (size*Math.cos(radians));
        	finalPos.y = entity.getCenterY() + (float) (size*Math.sin(radians));
		}
    }
    
    private void inicialiceBallAttack(Type type) {
    	switch (type) {
	    	case ARROW:
	    		centerX = 64;
	    		centerY = 56;
	    		seconds = (2-(entity.statVel*0.01f)<0.2f)? 0.2f : 2-(entity.statVel*0.01f);
	    		size = 300;
	    		currentFrame = new TextureRegion(texAttack, 0, 0, centerX, centerY);
	    		damage = 40*entity.statAtq*0.1f;
	    		break;
	    	default:
	    		centerX = centerY = 32;
	    		seconds = (1-(entity.statVel*0.01f)<0.2f)? 0.2f : 1-(entity.statVel*0.01f);
	    		size = 150;
	    		currentFrame = AbstractScreen.getSkin().getRegion("touch-knob");//new TextureRegion(attackTexture, 0, 0, centerX, centerY);
	    		damage = 20*entity.statAtq*0.1f;
	    	}
    }
    
	private void collides() {
		if(!GameScreen.getScene().isFree(actualPos.x + centerX/2, actualPos.y + centerY/2)) { // Si choca con un obstaculo
			game.removeAttack(this);
		} else {
			LivingEntity diana = entity instanceof MainCharacter ? game.attackCollides(entity, actualPos.x + centerX/2, actualPos.y + centerY/2)
					: game.enemyAttackCollides(entity, actualPos.x + centerX/2, actualPos.y + centerY/2);
			if(diana != null) {
				//System.out.println("Daño = " + damage);
				diana.receiveDamage(entity, damage);
				game.removeAttack(this);
			}
		}
	}
	
	@Override
	public void act (float delta) {
		if(seconds < 0) {
			game.removeAttack(this);
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
}
