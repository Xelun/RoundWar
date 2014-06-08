package Bars;

import screenControl.AbstractScreen;
import Entities.MainCharacter;

import com.badlogic.gdx.Gdx;

public class HealthBar extends Bar {

	public HealthBar(MainCharacter mainpj) {
		super(Gdx.graphics.getWidth()*0.4f, Gdx.graphics.getHeight()*0.04f, mainpj.statHp, mainpj.getHealth());
		empty = AbstractScreen.getSkin().getPatch("bg-bar-large");
		full = AbstractScreen.getSkin().getPatch("bar-hp");
	}
	
	public void resize(int width, int height) {
		super.resize(width*0.4f, height*0.04f);
    }
}
