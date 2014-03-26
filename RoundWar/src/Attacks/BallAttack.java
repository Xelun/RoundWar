package Attacks;

import Entities.LivingEntity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class BallAttack extends Attack {
	protected Texture attackTexture;
    protected TextureRegion currentFrame;
    private int size, center;
	
    public BallAttack(LivingEntity entity, Vector2 pos) {
    	super(entity, pos);
    	size = 170;  // Distancia que recorrerá antes de desaparecer ?
    	center = 32; // Tamaño de la textura
    	double radians = Math.toRadians(entity.getRotation());
    	finalPos.x = entity.getCenterX() + (float) (size*Math.cos(radians));
    	finalPos.y = entity.getCenterY() + (float) (size*Math.sin(radians));
    	attackTexture = new Texture(Gdx.files.internal("skin/touchKnob.png"));//sprite/ballattack.png"));
		currentFrame =  new TextureRegion(attackTexture, 0, 0, center, center);
		actualPos.x -= center/2;
		actualPos.y -= center/2;
		entity.addMp(-10);
    }
    
	public BallAttack(LivingEntity entity, float posX, float posY) {
		this(entity, new Vector2(posX, posY));
	}
	
	private void collides() {
		if(!game.getScene().isFree(actualPos.x + center/2, actualPos.y + center/2)) { // Si choca con un obstaculo
			dispose();
		} else {
			LivingEntity diana = game.attackCollides(entity, actualPos.x + center/2, actualPos.y + center/2);
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
		batch.draw(currentFrame, actualPos.x, actualPos.y);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		attackTexture.dispose();
	}
	
}
