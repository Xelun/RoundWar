/*******************************************************************************
 * Copyright (c) 2014
 *
 * @author Elisabet Romero Vaquero
 *******************************************************************************/
package screenControl;

import roundwar.RoundWar;
import ProfileSettings.Profile;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class OptionsScreen extends AbstractScreen {
//	private Texture texImageButtons;
	private Drawable on, off;
	private ImageButton musicButton, soundButton;
	private TextButton clearButton;
	private TextButton lefthanderButton;
	private TextButton backButton;
	
	/**
	 * Constructor.
	 */
    public OptionsScreen() {       
            super();
            setBackground("background/startbg.png");
            createButtons();
            createTable();
    }
    
    /**
     * Crea los botones con las distintas opciones.
     */
    private void createButtons() {
    	// Texturas  	
    	on = AbstractScreen.getSkin().getDrawable("on");
    	off = AbstractScreen.getSkin().getDrawable("off");
    	
    	// Si está activada o no la música en el juego
    	musicButton = new ImageButton(on, off, off);
    	musicButton.setChecked(!Profile.isMusic());
    	
    	// Si está activada o no el sonido en el juego
    	soundButton = new ImageButton(on, off, off);
    	soundButton.setChecked(!Profile.isSound());
    	
    	// Si está en modo zurdo o diestro
    	lefthanderButton = new TextButton(Profile.isLeft()? "Left" : "Right", getSkin());
    	
    	// Botón para eliminar el historial
    	clearButton = new TextButton("Borrar datos", getSkin(), "danger"); 
    	
    	// Botón para volver a la pantalla anterior
    	backButton = new TextButton("Back", getSkin());
        
        // Eventos de pulsación de botones
    	musicButton.addListener(new InputListener() {
		    @Override
			public boolean touchDown (InputEvent  event, float x, float y, int pointer, int button) {     
		    	musicButton.setChecked(Profile.updateMusic());
		        return false;
		    } } ); 
    	
    	soundButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	soundButton.setChecked(Profile.updateSound());
		        return false;
		    } } ); 
		
    	lefthanderButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	lefthanderButton.setText(Profile.updateLeft()? "Left" : "Right"); 
		        return false;
		    } 
		} ); 
		
		clearButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		        RoundWar.clearProfile();
		        return false;
		    } 
		} );
		
		backButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		        game.setScreen(new MenuScreen());
		        return false;
		    } 
		} );
    }
    
    /**
	 * Crea la tabla con los botones.
	 */
    private void createTable(){
    	table = super.getTable();
       
		table.add("Music").spaceLeft(w*0.1f).spaceBottom(h*0.05f);
		table.add(musicButton).size(w*0.14f, w*0.07f).spaceBottom(h*0.05f).spaceRight(w*0.1f).right(); 
		table.row();
		table.add("Sound").spaceLeft(w*0.1f).spaceBottom(h*0.05f);
		table.add(soundButton).size(w*0.14f, w*0.07f).spaceBottom(h*0.05f).spaceRight(w*0.1f).right();
		table.row();
		table.add("Controller").spaceLeft(w*0.1f).spaceBottom(h*0.05f);
		table.add(lefthanderButton).size(w*0.3f, h*0.1f).spaceBottom(h*0.05f).spaceLeft(w*0.1f).right();
		table.row();
		table.add(clearButton).size(w*0.5f, h*0.1f).spaceBottom(h*0.15f).colspan(2);
		table.row();
		table.add(backButton).size(w*0.4f, h*0.1f).colspan(2);
		table.row();
    	table.add().size(0,h*0.05f);
		table.bottom();
    }
    
    /**
	 * Libera memoria para eliminar la pantalla.
	 */
    @Override
    public void dispose() {
//    	texImageButtons.dispose();
    	super.dispose();
    }
}
