package screenControl;

import java.util.HashMap;
import java.util.Map;

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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
	private Map<Vector2, Rectangle> obstacles;
	private float tileSize;
	
	public Background (GameScreen screen, String path) {
		game = true;
		cam =  (OrthographicCamera)screen.getStage().getCamera();
		map = new TmxMapLoader().load(path);
        renderer = new OrthogonalTiledMapRenderer(map, screen.getStage().getSpriteBatch());
        collision = (TiledMapTileLayer)map.getLayers().get("collision");
        tileSize = collision.getTileHeight();
        loadBackGround();
	}
	
	public Background(String path){
		game = false;
		tbg = new Texture(Gdx.files.internal(path));
		tbg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        bg = new Image(new TextureRegionDrawable(new TextureRegion(tbg,512,512)), Scaling.stretch);
        bg.setFillParent(true);
	}
	
	public void loadBackGround() {
		obstacles = new HashMap<Vector2, Rectangle>();
		for (int i = 0; i < collision.getWidth(); i++) {
			for (int j = 0; j < collision.getHeight(); j++) {
				if(collision.getCell(i, j) != null) {
					obstacles.put(new Vector2(i, j),
							new Rectangle(i*tileSize, j*tileSize, tileSize, tileSize));
				}
			}
		}
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
	
	public boolean isFree(Rectangle bounds, Vector2 pos) {
		if(obstacles.containsKey(pos)) {
			Rectangle rec = obstacles.get(pos);
			if( rec != null && rec.overlaps(bounds)) return false;
		}
		return true;
	}
	
	public boolean isFree(float posX, float posY) {
		return (obstacles.get(new Vector2((int)(posX/tileSize), (int)(posY/tileSize))) == null);
	}
	
	public boolean isFree(Rectangle bounds) {
		boolean free = true;
		int posX1, posY1, posX2, posY2;
		posX1 = (int)  (bounds.x / tileSize);
		posY1 = (int)  (bounds.y / tileSize);
		posX2 = (int) ((bounds.x + bounds.width ) / tileSize);
		posY2 = (int) ((bounds.y + bounds.height) / tileSize);
		if		(!isFree(bounds, new Vector2(posX1, posY1))) free = false;
		else if (!isFree(bounds, new Vector2(posX1, posY2))) free = false;
		else if (!isFree(bounds, new Vector2(posX2, posY1))) free = false;
		else if (!isFree(bounds, new Vector2(posX2, posY2))) free = false;
		
		return free;
	}
	
	public TiledMapTileLayer getLayerColission() {
		return collision;
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
