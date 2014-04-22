package Buttons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ImageCharacter extends Actor {
	private TextureRegion character, bg;
	
	public ImageCharacter(TextureRegion bg, TextureRegion character) {
		this.bg = bg;
		this.character = character;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.draw(bg, getX(),  getY());
		batch.draw(character,  getX(), getY());
	}
}
