package roundwar;

import Entities.MainCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HealthBar extends Bar {

	public HealthBar(MainCharacter mainpj) {
		super(Gdx.graphics.getWidth()*0.4f, Gdx.graphics.getHeight()*0.08f, mainpj.maxHealth, mainpj.getHealth());
		empty = new NinePatch(new TextureRegion(tbar, 24, 24, 24, 24), 7, 7, 8, 8);
		full = new NinePatch(new TextureRegion(tbar, 24, 24), 7, 7, 8, 8);
		
		Gdx.app.log( RoundWar.LOG, "Max health = " + maxValue); 
		Gdx.app.log( RoundWar.LOG, "Health = " + value); 
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		maxWidthBar = w * 0.4f;
		widthBar = maxWidthBar*(value/maxValue);
		heightBar = h * 0.08f;
    }
}
