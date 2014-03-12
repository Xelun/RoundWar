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
	
    public BallAttack(LivingEntity entity, Vector2 pos) {
    	super(entity, pos);
    	attackTexture = new Texture(Gdx.files.internal("skin/touchKnob.png"));//sprite/ballattack.png"));
		currentFrame =  new TextureRegion(attackTexture, 0, 0, 32, 32);
    }
    
	public BallAttack(LivingEntity entity, float posX, float posY) {
		this(entity, new Vector2(posX, posY));
	}
	
	@Override
	public void act (float delta) {
		if(seconds < 0) {
			dispose();
			entity.updateMana(-10);
		} else {
			actualPos.x += delta*(finalPos.x - actualPos.x)/seconds;
			actualPos.y += delta*(finalPos.y - actualPos.y)/seconds;
			seconds -= delta;
		}
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		batch.draw(currentFrame, actualPos.x, actualPos.y);
	}
	
	public void dispose() {
		super.dispose();
		attackTexture.dispose();
	}
	
}
