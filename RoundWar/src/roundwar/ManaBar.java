package roundwar;

import Entities.Minimal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ManaBar extends Bar {

	public ManaBar (Minimal mainpj, float x, float y) {
		super(Gdx.graphics.getWidth()*0.4f, Gdx.graphics.getHeight()*0.03f, x, y, mainpj.maxMp, mainpj.getMp());
		empty = new NinePatch(new TextureRegion(tbar, 16, 20, 16, 12), 7, 7, 0, 0);
		full = new NinePatch(new TextureRegion(tbar, 0, 20, 16, 12), 7, 7, 5, 5);
		Gdx.app.log( RoundWar.LOG, "Mana = " + value); 
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		maxWidthBar = w * 0.4f;
		widthBar = maxWidthBar*(value/maxValue);
		heightBar = h * 0.03f;
    }
}
