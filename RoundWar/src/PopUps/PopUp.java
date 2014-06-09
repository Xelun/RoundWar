/*******************************************************************************
 * Copyright (c) 2014
 *
 * @author Elisabet Romero Vaquero
 *******************************************************************************/
package PopUps;

import roundwar.RoundWar;
import screenControl.AbstractScreen;
import Buttons.ImageBackgroundPopUp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class PopUp {
	protected static RoundWar game;
	protected Stage popUpStage;
	protected Table table;
	protected ImageBackgroundPopUp bg;
	protected boolean visible;
	protected static int w, h;
	
	/**
	 * Constructor.
	 * @param batch
	 */
	public PopUp(SpriteBatch batch) {
		visible = false;
		popUpStage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, batch);
		bg = new ImageBackgroundPopUp(AbstractScreen.getSkin().getPatch("bg-popup"));
		popUpStage.addActor(bg);
		initializeTable();
	}
	
	/**
	 * Establece la visibilidad del popup.
	 * @param visible
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
		if(visible) Gdx.input.setInputProcessor(popUpStage);
	}
	
	/**
	 * Muestra el popup.
	 */
	public void show() {
		Gdx.input.setInputProcessor(popUpStage);
		setVisible(true);
	}
	
	/**
	 * Esconde el popup.
	 */
	public void close() {
		setVisible(false);
	}
	
	/**
	 * Devuelve si el popup es visible o no.
	 * @return
	 */
	public boolean isVisible() {
		return visible;
	}
	
	/**
	 * Devuelve el Stage del popup.
	 * @return
	 */
	public Stage getStage() {
		return popUpStage;
	}
	
	public static void setGame(RoundWar game) {
		PopUp.game = game;
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
	}
	
	/**
	 * Redimensiona el popup.
	 * @param width
	 * @param height
	 */
	public void resize(int width, int height) {
		popUpStage.setViewport( width, height, true );
	}
	
	/**
	 * Dibuja el popup.
	 * @param delta
	 */
	public void draw (float delta) {
		popUpStage.act(delta);
//		table.debug(); 					// Solo para depuración
		popUpStage.draw();
//		Table.drawDebug(popUpStage); 	// Solo para depuración
	}
	
	/**
	 * Inicializa la tabla.
	 */
	protected void initializeTable() {
		table = new Table( AbstractScreen.getSkin() ); 
        table.setFillParent( true ); 
        popUpStage.addActor( table );
	}
	
	/**
	 * Libera memoria.
	 */
	public void dispose() {
		popUpStage.dispose();
	}
}
