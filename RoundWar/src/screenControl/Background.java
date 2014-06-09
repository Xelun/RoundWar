/*******************************************************************************
 * Copyright (c) 2014
 *
 * @author Elisabet Romero Vaquero
 *******************************************************************************/
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
	private static GameScreen game;
	private static TiledMap map;
	private static OrthogonalTiledMapRenderer renderer; 
	private static OrthographicCamera cam;
	private TiledMapTileLayer collision;
	private Map<Vector2, Rectangle> obstacles;
	//private List<Vector2> spawns; //Guarda el punto justo donde se tienen que spawnear los enemigos
	private float tileSize;
	
	/**
	 * Constructor
	 * @param path Dirección donde se encuentra el mapa a cargar (Solo para GameScreen).
	 */
	public Background (String path) {
		map = new TmxMapLoader().load(path);
        collision = (TiledMapTileLayer)map.getLayers().get("collision");
        tileSize = collision.getTileHeight();
        cam =  (OrthographicCamera)game.getStage().getCamera();
		renderer = new OrthogonalTiledMapRenderer(map, game.getStage().getSpriteBatch());
	}
	
	/**
	 * Guarda la instancia de la pantalla de juego.
	 * @param screen
	 */
	public static void setScreen(GameScreen screen) {
		Background.game = screen;
	}
	
	/**
	 * Analiza todas las celdas del mapa para ver cuales serán obstáculos.
	 * Determina la posición inicial del jugador según la celda spawn.
	 * Guarda los puntos de spawneo de enemigos.
	 * @return Devuelve los puntos de spawneo de enemigos.
	 */
	public List<Vector2> loadObstacles() {
		List<Vector2> spawnPoints = new ArrayList<Vector2>();
		obstacles = new HashMap<Vector2, Rectangle>();
		Cell cell;
		for (int i = 0; i < collision.getWidth(); i++) {
			for (int j = 0; j < collision.getHeight(); j++) {
				cell = collision.getCell(i, j);
				if(cell != null) { // Existe esa celda en la capa de colisión
					if(cell.getTile().getProperties().get("init") != null) { // Posición de inicio del jugador
						game.getCharacter().setCenterPosition(i*tileSize + tileSize/2, j*tileSize + tileSize/2);
					} else { // Es un obstáculo
						if(cell.getTile().getProperties().get("spawn") != null) { // Es un punto de spawn de enemigos
							spawnPoints.add(new Vector2(i*tileSize + tileSize/2, j*tileSize + tileSize/2));
						}
						obstacles.put(new Vector2(i, j), new Rectangle(i*tileSize, j*tileSize, tileSize, tileSize));
					}
				}
			}
		}
		return spawnPoints;
	}
	
	/**
	 * Devuelve la celda adyacente a la ocupada por la posición dada en la dirección indicada.
	 * @param posX Valor en el eje x de la celda
	 * @param posY Valor en el eje y de la celda
	 * @param direction Dirección de la celda adyacente
	 * @return Devuelve la posición central de la celda adyacente
	 */
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
	
	/**
	 * Dibuja el mapa.
	 */
	@Override
	public void draw (SpriteBatch batch, float parentAlpha){
		super.draw(batch, parentAlpha);
		batch.end();
        renderer.render();
        batch.begin();
	}
	
	/**
	 * Actualiza el mapa con respecto a la cámara.
	 */
	@Override
    public void act (float delta) {
    	super.act(delta);
    	renderer.setView(cam);
    }
	
	/**
	 * Devuelve si una posición está ocupada por un obstáculo con respecto a un rectángulo de colisión.
	 * @param bounds Rectángulo de colisión con el que se comparará.
	 * @param pos Posición que se quiere comprobar.
	 */
	public boolean isFree(Rectangle bounds, Vector2 pos) {
		if(obstacles.containsKey(pos)) {
			Rectangle rec = obstacles.get(pos);
			if( rec != null && rec.overlaps(bounds)) return false;
		}
		return true;
	}
	
	/**
	 * Devuelve si una posición está vacía (true) o tiene algún obstáculo en ella (false).
	 * @param posX
	 * @param posY
	 */
	public boolean isFree(float posX, float posY) {
		return (obstacles.get(new Vector2((int)(posX/tileSize), (int)(posY/tileSize))) == null);
	}
	
	/**
	 * Devuelve si un rectángulo de colisión choca con algún obstácuclo.
	 */
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
	
	/**
	 * Devuelve el número de celda de una posición
	 * @param posX
	 * @param posY
	 */
	public Vector2 getVectorCell(float posX, float posY) {
		return new Vector2(posX/tileSize, posY/tileSize);
	}
	
	/**
	 * Devuelve la capa de colisión del mapa.
	 */
	public TiledMapTileLayer getLayerColission() {
		return collision;
	}
	
	/**
	 * Libera memoria.
	 */
	public void dispose(){
		map.dispose();
		renderer.dispose();
	}
	
}
