/*******************************************************************************
 * Copyright (c) 2014
 *
 * @author Elisabet Romero Vaquero
 *******************************************************************************/
package PopUps;

import screenControl.AbstractScreen;
import screenControl.GameScreen;
import screenControl.MenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GamePausePopUp extends PopUp {
	private TextButton resumeButton, statsButton, exitButton;
	protected static GameScreen gameScreen;
	
	public GamePausePopUp(SpriteBatch batch) {
		super(batch);
	}

	/**
	 * Establece la pantalla de juego donde se mostrará el popup.
	 * @param gameScreen
	 */
	public static void setScreen(GameScreen gameScreen) {
		GamePausePopUp.gameScreen = gameScreen;
	}
	
	/**
	 * Devuelve la pantalla de juego donde se muestra el popup.
	 * @return
	 */
	public static GameScreen getScreen() {
		return GamePausePopUp.gameScreen;
	}
	
	/**
	 * Inicializa la tabla.
	 */
	@Override
	protected void initializeTable() {
		super.initializeTable();
		resumeButton = new TextButton("Resume", AbstractScreen.getSkin());
		resumeButton.addListener(new InputListener() { 
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	gameScreen.setGamePause(false);
		        return false;
		    } 
		} );
		
		statsButton = new TextButton("Change stats", AbstractScreen.getSkin(), "go");
		statsButton.addListener(new InputListener() { 
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
				close();
				gameScreen.showStats();
				
				System.out.println("ENTRA");
		        return false;
		    } 
		} );
		
		exitButton = new TextButton("Exit", AbstractScreen.getSkin(), "go");
		exitButton.addListener(new InputListener() { 
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	game.setScreen(new MenuScreen());
		        return false;
		    } 
		} );
		
		table.add(resumeButton).size(Gdx.graphics.getWidth()*0.3f, Gdx.graphics.getHeight()*0.15f);
		table.row();
		table.add(statsButton).size(Gdx.graphics.getWidth()*0.3f, Gdx.graphics.getHeight()*0.15f);
		table.row();
		table.add(exitButton).size(Gdx.graphics.getWidth()*0.3f, Gdx.graphics.getHeight()*0.15f);
	}
}
