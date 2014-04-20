package Buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class NewCharacterButton extends Actor {
	private static Texture texture;
	private static TextureRegion texBg, texLvl;
	private static BitmapFont font;
	private TextureRegion texCharacter;
	boolean lvlEnable;
	int lvl;
	
	public NewCharacterButton(TextureRegion texCharacter) {
		if(texture==null) {
			texture = new Texture(Gdx.files.internal("images/prueba.png"));
			texBg = new TextureRegion(texture,0,0,85,85);
			texLvl = new TextureRegion(texture,96,0,50,50);
		}
		this.texCharacter = texCharacter;
		lvlEnable = false;
	}
	
	public NewCharacterButton(TextureRegion texCharacter, int lvl) {
		if(texture==null) {
			texture = new Texture(Gdx.files.internal("images/prueba.png"));
			texBg = new TextureRegion(texture,0,0,85,85);
			texLvl = new TextureRegion(texture,96,0,50,50);
		}
		this.texCharacter = texCharacter;
		lvlEnable = true;
		this.lvl = lvl;
	}
	
	public static void setFont(BitmapFont font) {
		NewCharacterButton.font = font;
		//NewCharacterButton.font.setColor(Color.GRAY);
		NewCharacterButton.font.setScale(1.6f);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.draw(texBg, getX(), getY()+11);
		batch.draw(texCharacter, getX()+11, getY()+22);
		if(lvlEnable) {
			batch.draw(texLvl, getX()+46, getY());
			font.drawWrapped(batch, String.valueOf(lvl), getX()+45, getY()+37, 50, BitmapFont.HAlignment.CENTER);
		}
	}
	
	public static void dispose() {
		if(texture != null)
			texture.dispose();
		texture = null;
	}
}
