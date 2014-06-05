package screenControl;

import roundwar.RoundWar;
import ProfileSettings.Profile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class OptionsScreen extends AbstractScreen {
	private Texture texImageButtons;
	private TextureRegionDrawable on, off;
	private ImageButton musicButton, soundButton;
	private TextButton clearButton;
	private TextButton lefthanderButton;
	private TextButton backButton;
	
	
    public OptionsScreen() {       
            super();
            setBackground("background/startbg.png");
            createButtons();
            createTable();
    }
    
    private void createButtons() {
    	texImageButtons = new Texture(Gdx.files.internal("skin/bars.png"));
    	on = new TextureRegionDrawable(new TextureRegion(texImageButtons,0,0,64,32));
    	off = new TextureRegionDrawable(new TextureRegion(texImageButtons,0,32,64,32));
    	// Inicialize buttons
    	TextureRegionDrawable aux, noaux;
    	if(Profile.isMusic()) {
    		aux = on;
    		noaux = off;
    	} else {
    		aux = off;
    		noaux = on;
    	}
    	musicButton = new ImageButton(aux, aux, noaux);
    	musicButton.setChecked(false);
    	if(Profile.isSound()) {
    		aux = on;
    		noaux = off;
    	} else {
    		aux = off;
    		noaux = on;
    	}
    	System.out.println(Profile.isSound());
    	soundButton = new ImageButton(aux, aux, noaux);
    	soundButton.setChecked(Profile.isSound());
    	lefthanderButton = new TextButton(Profile.isLeft()? "Left" : "Right", getSkin());
    	clearButton = new TextButton("Borrar datos", getSkin()); 
    	backButton = new TextButton("Back", getSkin());
        
        // Add listeners
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
		    	System.out.println(Profile.isSound());
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
    
    private void createTable(){
    	table = super.getTable();
    	int h = Gdx.graphics.getHeight();
		int w = Gdx.graphics.getWidth();
    	
        table.add().spaceBottom(h*0.1f);
        table.row();
        
		table.add("Music").spaceLeft(w*0.1f).spaceBottom(h*0.05f);
		table.add(musicButton).spaceBottom(h*0.05f).spaceRight(w*0.1f).right(); 
		table.row();
		table.add("Sound").spaceLeft(w*0.1f).spaceBottom(h*0.05f);
		table.add(soundButton).spaceBottom(h*0.05f).spaceRight(w*0.1f).right();
		table.row();
		table.add("Controller").spaceLeft(w*0.1f).spaceBottom(h*0.05f);
		table.add(lefthanderButton).size(w*0.3f, h*0.1f).spaceBottom(h*0.05f).spaceLeft(w*0.1f).right();
		table.row();
		table.add(clearButton).size(w*0.5f, h*0.1f).spaceBottom(h*0.15f).colspan(2);
		table.row();
		table.add(backButton).size(w*0.4f, h*0.15f).colspan(2);
    }
    
    @Override
    public void dispose() {
    	texImageButtons.dispose();
    	super.dispose();
    }
}
