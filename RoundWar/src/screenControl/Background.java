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
	private Screen screen;
	private boolean game;
	private Texture tbg;
	private Image bg;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer; 
	OrthographicCamera cam;
	
	public Background (GameScreenControl screen) {
		game = true;
		this.screen = screen;
		cam =  (OrthographicCamera)((GameScreenControl)screen).getStage().getCamera();
		map = new TmxMapLoader().load("background/mapa1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / 32f);//, screen.getStage().getSpriteBatch());
        renderer.setView(cam);
		/*map = new TmxMapLoader().load("background/mapa1.tmx");

    	renderer = new OrthogonalTiledMapRenderer(map, 1 / 32, screen.getStage().getSpriteBatch());
    	renderer.setView((OrthographicCamera)screen.getStage().getCamera());*/
    	//renderer.getSpriteBatch().disableBlending(); 
	}
	
	public Background(Screen screen, String path){
		game = false;
		this.screen = screen;
		// Inicialize background
		tbg = new Texture(Gdx.files.internal(path));
		tbg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        bg = new Image(new TextureRegionDrawable(new TextureRegion(tbg,512,512)), Scaling.stretch);
        bg.setFillParent(true);
        
        tbg.dispose();
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha){
		if(game) {
			super.draw(batch, parentAlpha);
			//OrthographicCamera cam = (OrthographicCamera)((GameScreenControl)screen).getStage().getCamera();
			//cam.update();
			//renderer.setView(cam);
			cam.update();
			renderer.setView(cam);
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
    		
    	}/* else {
    		
    	}*/
    }
	
	public void dispose(){
		if(game) {
			map.dispose();
			renderer.dispose();
		} /*else {
			tbg.dispose();
		}*/
		
	}
	
}
