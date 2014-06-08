package screenControl;

import java.util.ArrayList;

import ProfileSettings.CharacterProfile;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class SceneSelectScreen extends AbstractScreen {
	private ArrayList<TextButton> lvl;
	private int numLevels = 4;
//	private int size = (int) ( (w*0.85f - (numLevels/2)*0.1f*w) / Math.round(numLevels/2f) );
	private int size;
	private TextButton backButton;
	private CharacterProfile charprofile;

	/**
	 * Constructor.
	 * @param charprofile
	 */
    public SceneSelectScreen(CharacterProfile charprofile) {       
        super();
        setBackground("background/startbg.png");
        this.charprofile = charprofile;
        int haux, waux;
        haux = (int) (h*0.25f);
        waux = (int) (w*0.9f/numLevels);
        size = (haux <= waux)? haux : waux;
        createButtons();
        createTable();
    }
    
    /**
     * Crea los botones para cada escenario.
     */
    private void createButtons() {
    	// Inicialize buttons
    	lvl = new ArrayList<TextButton>();
    	TextButton aux;
    	String text;
    	for(int i = 0; i < numLevels; i++) {
    		// Initialize button
    		text = String.valueOf(i) + "-" + String.valueOf((i+1)*10);
    		aux = new TextButton(text, getSkin());
    		lvl.add(aux);
    		
    		final int id = i;
    		// Add listeners
    		lvl.get(i).addListener(new InputListener() {
    		    @Override
    			public boolean touchDown (InputEvent  event, float x, float y, int pointer, int button) {                   
    		    	game.setScreen(new GameScreen(charprofile, id));
    		        return false;
    		    } } );
    	}
    	
        // Back button
    	backButton = new TextButton("Back", getSkin());
    	
    	backButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	game.setScreen(new GameSelectScreen());
		        return false;
		    } 
		} );
    }
    
    /**
     * Crea la tabla para colocar los botones de escenario y el de volver a la pantalla anterior.
     */
    private void createTable(){
    	System.out.println(size);
    	table = super.getTable();
    	
    	for(int i = 0; i < (numLevels/2); i++) {
    		table.add(lvl.get(i)).width(size).height(size);
    		table.add().width(w*0.05f);
    	}
    	table.row();
    	
    	table.add().size(h*0.1f);
    	table.row();
    	
    	for(int i = (numLevels/2); i < numLevels; i++) {
    		table.add().width(w*0.05f);
    		table.add(lvl.get(i)).width(size).height(size);
    	}
    	table.row();
    	
        table.add().size(h*0.15f);
        table.row();
        
        table.add(backButton).size(w*0.4f, h*0.1f).colspan(numLevels+2);
		table.row();
		
    	table.add().size(0,h*0.05f);
		table.bottom();
    }
}
