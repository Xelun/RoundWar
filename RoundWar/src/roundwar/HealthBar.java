package roundwar;

import Entities.MainCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HealthBar extends Bar {

	public HealthBar(MainCharacter mainpj, float x, float y) {
		super(Gdx.graphics.getWidth()*0.4f, Gdx.graphics.getHeight()*0.04f, x, y, mainpj.maxHealth, mainpj.getHealth());
		empty = new NinePatch(new TextureRegion(tbar, 16, 0, 16, 20), 7, 7, 0, 0);
		full = new NinePatch(new TextureRegion(tbar, 0, 0, 16, 20), 7, 7, 0, 0);
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		maxWidthBar = w * 0.4f;
		widthBar = maxWidthBar*(value/maxValue);
		heightBar = h * 0.04f;
    }
}
