/*******************************************************************************
 * Copyright (c) 2014
 *
 * @author Elisabet Romero Vaquero
 *******************************************************************************/
package screenControl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MenuScreen extends AbstractScreen {
	private TextButton startGameButton;
	private TextButton optionsButton;
	private TextButton scoresButton;
	private TextButton exitButton;

	/**
	 * Constructor.
	 */
    public MenuScreen() {       
            super();
            setBackground("background/startbg.png");
            createButtons();
            createTable();
    }
    
    /**
	 * Crea los botones de comienzo de juego, visualización de opciones, puntuaciones y escape.
	 */
    private void createButtons() {
    	// Inicialize buttons
        startGameButton = new TextButton("Start", getSkin());
        optionsButton = new TextButton("Options", getSkin()); 
        scoresButton = new TextButton("Scores", getSkin()); 
        exitButton = new TextButton("Exit", getSkin()); 
        
        // Add listeners
		startGameButton.addListener(new InputListener() {
		    @Override
			public boolean touchDown (InputEvent  event, float x, float y, int pointer, int button) {                   
		        //game.setScreen(new SelectGameScreenControl(game));
		    	game.setScreen(new GameSelectScreen());
		        return false;
		    } } ); 
		
		optionsButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		        game.setScreen(new OptionsScreen()); 
		        return false;
		    } } ); 
		
		scoresButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		        game.setScreen(new ScoreScreen());
		        return false;
		    } 
		} ); 
		
		exitButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		        Gdx.app.exit();
		        return false;
		    } 
		} );
    }
    
    /**
	 * Crea la tabla con los botones.
	 */
    private void createTable(){
    	table = super.getTable();
    	
        table.add().spaceBottom(h*0.4f);
        table.row();
        
		table.add(startGameButton).size(w*0.4f, h*0.2f).uniform().spaceBottom(h*0.05f).spaceRight(w*0.1f); 
		table.add(scoresButton).size(w*0.4f, h*0.2f).uniform().spaceBottom(h*0.05f).spaceLeft(w*0.1f);
		table.row(); 
		table.add(optionsButton).size(w*0.4f, h*0.2f).uniform().spaceRight(w*0.1f);
		table.add(exitButton).size(w*0.4f, h*0.2f).uniform().spaceLeft(w*0.1f);
    }
}
