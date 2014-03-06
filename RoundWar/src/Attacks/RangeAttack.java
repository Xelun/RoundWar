package Attacks;

import screenControl.Hud;
import Entities.MainCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class RangeAttack extends Actor {
	private float radius;
	private Vector2 position;
	private boolean visible;
	private MainCharacter mainpj;
	private ShapeRenderer circle;
	private Circle formCircle;
	
	public RangeAttack(MainCharacter mainpj) {
		visible = false;
		this.mainpj = mainpj;
		radius = 0;
		position = new Vector2();
		circle = new ShapeRenderer();
		circle.setColor(0.8f, 0.93f, 0.96f, 0.8f);
		formCircle = new Circle();
	}
	
	public void setRadius(Hud.Attack type) {
		switch(type) {
			case NEAR:
				radius = 10;
				break;
			case FAR:
				radius = 60;
				break;
			case RUN:
				radius = 40;
				break;
			case INAREA:
				radius = 80;
				break;
			default:
				radius = 0;
				break;
		}
	}
	
	public boolean inRange(float x, float y) {
		formCircle.setPosition(mainpj.getCenterX(), mainpj.getCenterY());
		formCircle.setRadius(radius);
		if(formCircle.contains(x, y)) { //Esta dentro del rango
			return true;
		} else { //No está dentro del rango
			return false;
		}
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible (boolean visible) {
		this.visible = visible;
	}
	
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		this.position.x = x;
		this.position.y = y;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if (visible) {
			batch.end();
			Gdx.gl.glEnable(GL10.GL_LINE_SMOOTH);
			Gdx.gl.glEnable(GL10.GL_BLEND);
			    Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
				circle.begin(ShapeType.Filled);
				circle.setColor(0.8f, 0.93f, 0.96f, 0.5f);
			    circle.circle(position.x, position.y, radius, 40);
			    circle.end();
			    circle.begin(ShapeType.Line);
			    circle.setColor(0.4f, 0.6f, 1f, 0.9f);
			    circle.circle(position.x, position.y, radius, 40);
			    circle.end();
		    Gdx.gl.glDisable(GL10.GL_BLEND);
		    batch.begin();
		}
	}
    
    @Override
    public void act(float delta) {
    	if (visible) {
    		setPosition(mainpj.getCenterX(), mainpj.getCenterY());
    	}
    }
    
    public void dispose() {
    	circle.dispose();
    }
}
