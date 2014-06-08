package Buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ImageBackgroundPopUp extends Actor {
	private NinePatch background;
	private int w, h;
	
	public ImageBackgroundPopUp(NinePatch bg) {
		background = bg;
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		background.draw(batch, w*0.2f, h*0.15f, w*0.6f, h*0.7f);
	}
}
