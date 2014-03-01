package screenControl;

import Entities.LivingEntity.Status;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

public class Background extends Actor {
	private boolean game;
	private Texture tbg;
	private Image bg;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer; 
	OrthographicCamera cam;
	
	public Background (GameScreenControl screen) {
		game = true;
		cam =  (OrthographicCamera)((GameScreenControl)screen).getStage().getCamera();
		map = new TmxMapLoader().load("background/mapa1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);//, screen.getStage().getSpriteBatch());
	}
	
	public Background(String path){
		game = false;
		// Inicialize background
		tbg = new Texture(Gdx.files.internal(path));
		tbg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        bg = new Image(new TextureRegionDrawable(new TextureRegion(tbg,512,512)), Scaling.stretch);
        bg.setFillParent(true);
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha){
		if(game) {
			super.draw(batch, parentAlpha);
			//batch.begin();
            renderer.render();
            //batch.end();
		} else {
			bg.draw(batch, parentAlpha);
		}
	}
	
	@Override
    public void act (float delta) {
    	super.act(delta);
    	if(game){
    		renderer.setView(cam);
    	}/* else {
    		
    	}*/
    }
	
	public void dispose(){
		if(game) {
			map.dispose();
			renderer.dispose();
		} else {
			tbg.dispose();
		}
		
	}
	
}
