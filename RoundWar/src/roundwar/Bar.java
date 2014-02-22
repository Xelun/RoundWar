package roundwar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Bar extends Actor {

	protected SpriteBatch batch;
	protected Texture tbar;
	protected NinePatch full, empty;
	protected float maxWidthBar, widthBar, heightBar, maxValue, value; 
	protected float w, h;

	public Bar(float maxWidthBar, float heightBar, float maxValue, float value) {
		h = Gdx.graphics.getHeight();
    	w = Gdx.graphics.getWidth();
		this.maxWidthBar = maxWidthBar;
		this.widthBar = maxWidthBar;
		this.heightBar = heightBar;
		this.maxValue = maxValue;
		this.value = value;
		tbar = new Texture(Gdx.files.internal("skin/bar.png"));
		
		
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		empty.draw(batch, 0, 0, maxWidthBar, heightBar);
		if(widthBar > 0)
			full.draw(batch, 0, 0, widthBar, heightBar);
	}
	
	@Override
	public void act(float value){
		if(this.value != value){ //Ha cambiado el valor
			if (value > maxValue){
				widthBar = maxWidthBar;
				this.value = maxValue;
			} else if (value <= 0){
				widthBar = 0;
				this.value = 0;
			} else {
				this.value = value;
				widthBar = maxWidthBar*(value/maxValue);
			}
		}
	}
	
	public void dispose() {
		tbar.dispose();
	}
	
	public void resize(int width, int height) {
		this.h = height;
        this.w = width;
    }
}
