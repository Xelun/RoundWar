package Buttons;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ImageCharacter extends Actor {
	private TextureRegion character, bg;
	private NinePatch background;
	private int w, h;
	
	public ImageCharacter(NinePatch background, TextureRegion bg, TextureRegion character, int w, int h) {
		this.background = background;
		this.bg = bg;
		this.character = character;
		this.w = w;
		this.h = h;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		background.draw(batch, w*0.2f, h*0.2f, w*0.6f, h*0.6f);
		batch.draw(bg, w*0.2f+20,  h*0.8f-20-bg.getRegionHeight());
		batch.draw(character,  w*0.2f+20, h*0.8f-20-character.getRegionHeight());
	}
}
