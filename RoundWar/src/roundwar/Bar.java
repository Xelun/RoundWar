package roundwar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Bar extends Actor {

	protected SpriteBatch batch;
	protected Texture tbar;
	protected NinePatch full, empty;
	protected float maxWidthBar, widthBar, heightBar, maxValue, value; 
	protected float x, y;

	public Bar(float maxWidthBar, float heightBar, float x, float y, float maxValue, float value) {
    	this.x = x;
    	this.y = y;
		this.maxWidthBar = maxWidthBar;
		this.widthBar = maxWidthBar;
		this.heightBar = heightBar;
		this.maxValue = maxValue;
		this.value = value;
		tbar = new Texture(Gdx.files.internal("skin/bar.png"));
		
		
	}

	public void draw(SpriteBatch batch) {
		empty.draw(batch, x, y, maxWidthBar, heightBar);
		if(widthBar > 0)
			full.draw(batch, x, y, widthBar, heightBar);
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
	
	public void resize(float width, float height) {
		maxWidthBar = width;
		widthBar = maxWidthBar*(value/maxValue);
		heightBar = height;
    }
	
	public void dispose() {
		tbar.dispose();
	}
}
