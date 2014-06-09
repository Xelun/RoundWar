/*******************************************************************************
 * Copyright (c) 2014
 *
 * @author Elisabet Romero Vaquero
 *******************************************************************************/
package PathFinders;

import screenControl.GameScreen;

import Entities.LivingEntity;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class PathFinder {
	protected static GameScreen game;
	/** The layer being searched */
	protected static TiledMapTileLayer layer;
	protected static int tilesize;
	/** The complete set of nodes across the map */
	protected static Node[][] nodes;
	
	public PathFinder() { }
	
	/**
	 * Coge las celdas del mapa y las pone como nodos de valor -1 o 1 según si son obstáculos o no.
	 */
	private static void setNodes() {
		if(nodes == null) {
			nodes = new Node[getLayer().getWidth()][getLayer().getHeight()];
			for (int i=0; i<getLayer().getWidth(); i++) {
				for (int j=0; j<getLayer().getHeight(); j++) {
					nodes[i][j] = new Node(i,j);
					if(getLayer().getCell(i, j) == null) {
						nodes[i][j].cost = 1;
					} else {
						nodes[i][j].cost = -1;
					}
				}
			}
		}
	}
	
	public Vector2 findNext(LivingEntity entity, LivingEntity entityTarget) {
		return null;
	}
	
	/**
	 * Get the heuristic cost for the given location. This determines in which 
	 * order the locations are processed.
	 * 
	 * @param entity The entifinalY that is being moved
	 * @param x The x coordinate of the tile whose cost is being determined
	 * @param y The y coordiante of the tile whose cost is being determined
	 * @param finalX The x coordinate of the target location
	 * @param finalY The y coordinate of the target location
	 * @return The heuristic cost assigned to the tile
	 */
	public float getHeuristicCost(int x, int y, int finalX, int finalY) {
		return (float) Math.sqrt(Math.pow(finalX-x, 2) + Math.pow(finalY-y, 2));
	}
	
	protected Vector2 nodeToVector(Node node) {
		if(node != null)
			return new Vector2((node.x*tilesize) + tilesize/2, 
				(node.y*tilesize) + tilesize/2);
		return null;
	}
	
	public static void dispose() {
		nodes = null;
	}
	
	public static void setScreen(GameScreen screen) {
		PathFinder.game = screen;
	}
	
	public static void setLayer(TiledMapTileLayer layer) {
		PathFinder.layer = layer;
		PathFinder.tilesize = (int) layer.getTileWidth();
		PathFinder.setNodes();
	}
	
	public static TiledMapTileLayer getLayer() {
		return layer;
	}

	public Vector2 getNode(float posX, float posY, int addX, int addY) {
		Vector2 node = new Vector2((int)(posX/tilesize) + addX, (int)(posY/tilesize) + addY);
		if (node.x < 0) node.x = 0;
		else if (node.x > getLayer().getWidth()) node.x = getLayer().getWidth();
		if (node.y < 0) node.y = 0;
		else if (node.y > getLayer().getHeight()) node.y = getLayer().getHeight();
		node.x = node.x * tilesize + tilesize/2; 
		node.y = node.y * tilesize + tilesize/2;
		return node;
	}
}
