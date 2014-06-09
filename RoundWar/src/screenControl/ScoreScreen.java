/*******************************************************************************
 * Copyright (c) 2014
 *
 * @author Elisabet Romero Vaquero
 *******************************************************************************/
package screenControl;

import java.util.Arrays;

import roundwar.Scroll;
import roundwar.Scroll.ScrollItem;
import ProfileSettings.CharacterProfile;
import ProfileSettings.Profile;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


public class ScoreScreen extends AbstractScreen {
	private Scroll scroll;
	private TextButton backButton;
    /**
     * Constructor.
     */
    public ScoreScreen() {       
            super();
            setBackground("background/startbg.png");
            ScrollItem.initialize();
            scroll = new Scroll();
            ScrollItem[] items = new ScrollItem[Profile.getNextId()];
            CharacterProfile character;
            if(Profile.getNextId() == 0) { // No hay ninguna partida creada
            	scroll.setItems(null);
            } else {
	            for(int i = Profile.getNextId()-1; i >= 0; i--) {
	            	character = Profile.getCharacter(i);
	            	items[i] = new ScrollItem(character.getType(), character.getLvl(), character.getExperience() );
	            }
	            Arrays.sort(items);
	            scroll.setItems(items);
            }
            
            scroll.setEnabled(false);
            
            
         // Botón para volver a la pantalla anterior
        	backButton = new TextButton("Back", getSkin());
        	
        	backButton.addListener(new InputListener() { 
    		    @Override
    			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
    		        game.setScreen(new MenuScreen());
    		        return false;
    		    } 
    		} );
        	
        	table = super.getTable();
        	table.bottom();
        	table.add(scroll).size(w*0.95f, h*0.8f);
        	table.row();
        	table.add(backButton).size(w*0.4f, h*0.1f);
        	table.row();
        	table.add().size(0,h*0.05f);
    }
}
