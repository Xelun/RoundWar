package screenControl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
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
	private TiledMapTileLayer collision;
	
	public Background (GameScreen screen) {
		game = true;
		cam =  (OrthographicCamera)((GameScreen)screen).getStage().getCamera();
		map = new TmxMapLoader().load("background/mapa1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, screen.getStage().getSpriteBatch());
        collision = (TiledMapTileLayer)map.getLayers().get("collision");
	}
	
	public Background(String path){
		game = false;
		tbg = new Texture(Gdx.files.internal(path));
		tbg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        bg = new Image(new TextureRegionDrawable(new TextureRegion(tbg,512,512)), Scaling.stretch);
        bg.setFillParent(true);
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha){
		if(game) {
			super.draw(batch, parentAlpha);
			batch.end();
            renderer.render();
            batch.begin();
		} else {
			bg.draw(batch, parentAlpha);
		}
	}
	
	@Override
    public void act (float delta) {
    	super.act(delta);
    	if(game){
    		renderer.setView(cam);
    	}
    }
	
	public boolean isCollision(float posX, float posY) {
		System.out.println("Posicion: (" + (int)(posX/collision.getTileWidth()) + ", " 
				+ (int)(posY/collision.getTileHeight()) + ")");
		if (collision.getCell((int)(posX/collision.getTileWidth()), (int)(posY/collision.getTileHeight())) == null) {
			//System.out.println("No colisiona");
			return false;
		} else {
			//System.out.println("COLISION");
			return true;
		}
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
