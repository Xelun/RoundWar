package Bars;

import Entities.MainCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HealthBar extends Bar {

	public HealthBar(MainCharacter mainpj) {
		super(Gdx.graphics.getWidth()*0.4f, Gdx.graphics.getHeight()*0.04f, mainpj.statHp, mainpj.getHealth());
		empty = new NinePatch(new TextureRegion(tbar, 16, 0, 16, 20), 7, 7, 0, 0);
		full = new NinePatch(new TextureRegion(tbar, 0, 0, 16, 20), 7, 7, 0, 0);
	}
	
	public void resize(int width, int height) {
		super.resize(width*0.4f, height*0.04f);
    }
}
