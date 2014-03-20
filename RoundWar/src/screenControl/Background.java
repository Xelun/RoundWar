package screenControl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Background extends Actor {
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer; 
	OrthographicCamera cam;
	private TiledMapTileLayer collision;
	private Map<Vector2, Rectangle> obstacles;
	private List<Vector2> spawns; //Guarda el punto justo donde se tienen que spawnear los enemigos
	private float tileSize;
	
	public Background (GameScreen screen, String path) {
		cam =  (OrthographicCamera)screen.getStage().getCamera();
		map = new TmxMapLoader().load(path);
        renderer = new OrthogonalTiledMapRenderer(map, screen.getStage().getSpriteBatch());
        collision = (TiledMapTileLayer)map.getLayers().get("collision");
        tileSize = collision.getTileHeight();
        loadBackGround();
	}
	
	public void loadBackGround() {
		obstacles = new HashMap<Vector2, Rectangle>();
		spawns = new ArrayList<Vector2>();
		Cell cell;
		for (int i = 0; i < collision.getWidth(); i++) {
			for (int j = 0; j < collision.getHeight(); j++) {
				cell = collision.getCell(i, j);
				if(cell != null) {
					if(cell.getTile().getProperties().get("spawn") != null) {
						spawns.add(new Vector2(i*tileSize + tileSize/2, j*tileSize - tileSize/2));
						System.out.println("Añadido nuevo spawn");
					}
					obstacles.put(new Vector2(i, j),
							new Rectangle(i*tileSize, j*tileSize, tileSize, tileSize));
				}
			}
		}
	}
	
	public Vector2 calculateRandomSpawn() {
    	int random = (int)(Math.random()%spawns.size());
    	return spawns.get(random);
    }
	
	public Vector2 calculeAdyacentCellCenter(float posX, float posY, int direction) {
		int x = (int)(posX/tileSize);
		int y = (int)(posY/tileSize);
		switch(direction) {
			case 0:		// Arriba
				y++;
				break;
			case 1:		// Derecha
				x++;
				break;
			case 2:		// Abajo
				y--;
				break;
			default:	// Izquierda
				x--;
				break;
		}
		if(!obstacles.containsKey(new Vector2(x,y))) {	// No es un obtáculo
			return new Vector2(x*tileSize + tileSize/2, y*tileSize + tileSize/2);
		} else {
			return calculeAdyacentCellCenter(posX, posY, (direction + 1)%4); // Intenta moverse en otra dirección
		}
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha){
		super.draw(batch, parentAlpha);
		batch.end();
        renderer.render();
        batch.begin();
	}
	
	@Override
    public void act (float delta) {
    	super.act(delta);
    	renderer.setView(cam);
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
		map.dispose();
		renderer.dispose();
	}
	
}
