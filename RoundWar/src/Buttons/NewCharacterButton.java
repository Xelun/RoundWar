package Buttons;

import screenControl.AbstractScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class NewCharacterButton extends Actor {
//	private static Texture texture;
	private static NinePatch texBg, texLvl;
	private static BitmapFont font;
	private TextureRegion texCharacter;
	private int lvl;
	private static int size;
	
	public NewCharacterButton(TextureRegion texCharacter) {
		this(texCharacter, -1);
	}
	
	public NewCharacterButton(TextureRegion texCharacter, int lvl) {
		if(texBg==null) {
			texBg = AbstractScreen.getSkin().getPatch("bg-select");
			texLvl = AbstractScreen.getSkin().getPatch("bg-lvl");
		}
		this.texCharacter = texCharacter;
		this.texCharacter.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		this.lvl = lvl;
		size = (int) ((Gdx.graphics.getWidth()*0.15f < Gdx.graphics.getHeight()*0.2f)? Gdx.graphics.getWidth()*0.15f : Gdx.graphics.getHeight()*0.2f);
	}
	
	public static int getSize() {
		return size;
	}
	
	public static void setFont(BitmapFont font) {
		NewCharacterButton.font = font;
//		NewCharacterButton.font.setScale(1.6f);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if(lvl>0) { // Se muestra el nivel
			texBg.draw(batch, getX(), getY(), size, size);
			batch.draw(texCharacter, getX()+11, getY()+22, size*0.7f, size*0.7f);
			
			texLvl.draw(batch, getX()+size/2, getY(), size/2, size/2);
			font.drawWrapped(batch, String.valueOf(lvl), getX()+size/2, getY()+size/2-font.getCapHeight()/2, size/2, BitmapFont.HAlignment.CENTER);
		} else { // No tiene nivel
			texBg.draw(batch, getX(), getY()-11, size+11, size+11);
			batch.draw(texCharacter, getX()+size*0.1f, getY()+size*0.1f, size*0.8f, size*0.8f);
		}
	}
}
