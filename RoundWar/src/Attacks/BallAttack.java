package Attacks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import Entities.LivingEntity;

public class BallAttack extends Attack {
	protected Texture attackTexture;
    protected TextureRegion currentFrame;
	
	public BallAttack(LivingEntity entity, float posX, float posY) {
		super(entity, posX, posY);
		attackTexture = new Texture(Gdx.files.internal("skin/touchKnob.png"));//sprite/ballattack.png"));
		currentFrame =  new TextureRegion(attackTexture, 0, 0, 32, 32); 
	}
	
	public void act (float delta) {
		if(seconds < delta) {
			dispose();
		} else {
			actualPos.x = delta*(finalPos.x - actualPos.x)/seconds;
			actualPos.y = delta*(finalPos.y - actualPos.y)/seconds;
			seconds -= delta;
		}
	}
	
	public void draw (SpriteBatch batch) {
		batch.draw(currentFrame, actualPos.x, actualPos.y);
	}
	
	public void dispose() {
		super.dispose();
		attackTexture.dispose();
	}
	
}
