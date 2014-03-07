package screenControl;

import Entities.LivingEntity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

public class Background extends Actor {
	public enum Collision {UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT, NONE}
	private boolean game;
	private Texture tbg;
	private Image bg;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer; 
	OrthographicCamera cam;
	private TiledMapTileLayer collision;
	private float tileSize;
	
	public Background (GameScreen screen) {
		game = true;
		cam =  (OrthographicCamera)((GameScreen)screen).getStage().getCamera();
		map = new TmxMapLoader().load("background/mapa1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, screen.getStage().getSpriteBatch());
        collision = (TiledMapTileLayer)map.getLayers().get("collision");
        tileSize = collision.getTileHeight();
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
	
	public boolean isFree(float posX, float posY) {
		posY = Gdx.graphics.getHeight()-posY;
		Vector2 aux = getStage().screenToStageCoordinates(new Vector2(posX, posY));
		int x = (int) (aux.x/tileSize);
		int y = (int) (aux.y/tileSize);
		if(collision.getCell(x, y) != null){
			//System.out.println("Choca en: (" + x + ", " + y + ")");
			return false;
		}
		return true;
	}
	
	public Collision isCollision(LivingEntity entity, Vector2 delta){
		float size = entity.getWidth();
		
		float x1 = delta.x/collision.getTileWidth() + 1;
		float y1 = (delta.y + size)/collision.getTileHeight() + 1;
		float x2 = ((delta.x + size)/collision.getTileWidth()) + 1;
		float y2 = ((delta.y)/collision.getTileHeight()) + 1;
		
		//System.out.println("Posicion: (" + (int)x1 + ", " + (int)(y1) + ")");
		
		Cell cell = collision.getCell((int)x1, (int)y1);
		if(cell != null) { System.out.println("Choca en: (" + (int)x1 + ", " + (int)(y1) + ")"); return Collision.UPLEFT; }
		
		cell = collision.getCell((int)(x2), (int)(y2));
		if(cell != null)  { System.out.println("Choca en: (" + (int)x2 + ", " + (int)(y2) + ")"); return Collision.DOWNRIGHT; }
		
		cell = collision.getCell((int)(x2), (int)(y1));
		if(cell != null)  { System.out.println("Choca en: (" + (int)x2 + ", " + (int)(y1) + ")"); return Collision.UPRIGHT; }
		
		cell = collision.getCell((int)(x1), (int)(y2));
		if(cell != null)  { System.out.println("Choca en: (" + (int)x1 + ", " + (int)(y2) + ")"); return Collision.DOWNLEFT; }
		
		return Collision.NONE;
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
