package Buttons;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ImageCharacter extends Actor {
	private TextureRegion character;
	private NinePatch bg;
	private float w, h;
	
	/**
	 * Constructor.
	 * @param bg
	 * @param character
	 */
	public ImageCharacter(NinePatch bg, TextureRegion character) {
		this.bg = bg;
		this.character = character;
	}
	
	/**
	 * Establece el tamaño de la imagen.
	 */
	@Override
	public void setSize(float width, float height) {
		w = width;
		h = height;
	}
	
	/**
	 * Dibuja el fondo de la imagen y la imagen del personaje.
	 */
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		bg.draw(batch, getX(), getY(), w, h);
		batch.draw(character, getX()+w*0.1f, getY()+h*0.1f, w*0.8f, h*0.8f);
	}
}
