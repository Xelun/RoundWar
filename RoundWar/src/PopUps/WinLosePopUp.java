/*******************************************************************************
 * Copyright (c) 2014
 *
 * @author Elisabet Romero Vaquero
 *******************************************************************************/
package PopUps;

import screenControl.AbstractScreen;
import screenControl.MenuScreen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class WinLosePopUp extends PopUp {
	private TextButton backButton;
	
	/**
	 * Constructor.
	 * @param batch
	 */
	public WinLosePopUp(SpriteBatch batch) {
		super(batch);
	}
	
	/**
	 * Muestra el popup de pardida ganada o perdida según se le indique.
	 * @param win
	 */
	public void show(boolean win) {
		if(win) initializeWinTable();
		else	initializeLoseTable();
		initializeCommonTable();
		super.show();
	}
	
	/**
	 * Inicializa la tabla de mensaje ganador.
	 */
	protected void initializeWinTable() {
		table.add("HAS GANADO").expandX().center();
	}
	
	/**
	 * Inicializa la tabla de mensaje perdedor.
	 */
	protected void initializeLoseTable() {
		table.add("HAS PERDIDO").expandX().center();
	}
	
	/**
	 * Inicializa la tabla en común.
	 */
	private void initializeCommonTable() {
		backButton = new TextButton("Back to MainMenu", AbstractScreen.getSkin(), "go");
		backButton.addListener(new InputListener() { 
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	game.setScreen(new MenuScreen());
		        return false;
		    } 
		} );
		table.row();
    	table.add().size(0,h*0.15f);
		table.row();
		table.add(backButton).size(w*0.5f, h*0.1f).colspan(2);
		table.row();
    	table.add().size(0,h*0.3f);
		table.bottom();
	}
}
