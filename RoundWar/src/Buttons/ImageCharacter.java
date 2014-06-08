package Buttons;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ImageCharacter extends Actor {
	private TextureRegion character;
	private NinePatch bg;
	private float w, h;
	
	public ImageCharacter(NinePatch bg, TextureRegion character) {
		this.bg = bg;
		this.character = character;
	}
	
	public void setSize(float width, float height) {
		w = width;
		h = height;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		bg.draw(batch, getX(), getY(), w, h);
		batch.draw(character, getX()+w*0.1f, getY()+h*0.1f, w*0.8f, h*0.8f);
	}
}
