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

	public Bar(float maxWidthBar, float heightBar, float maxValue, float value) {
		this.maxWidthBar = maxWidthBar;
		this.maxValue = maxValue;
		this.value = value;
		this.widthBar = this.maxWidthBar*(this.value/this.maxValue);
		this.heightBar = heightBar;
		
		tbar = new Texture(Gdx.files.internal("skin/bar.png"));
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		empty.draw(batch, x, y, maxWidthBar, heightBar);
		if(widthBar > 0)
			full.draw(batch, x, y, widthBar, heightBar);
	}
	
	public void updateValue(float value){
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
	
	@Override
	public void setPosition(float x, float y){
		this.x = x;
		this.y = y;
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
