package screenControl;

import roundwar.RoundWar;
import ProfileSettings.CharacterProfile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class SceneSelectScreen extends AbstractScreen {
	private TextButton lvl00_10, lvl10_20, lvl20_30, lvl30_40, lvl40_50;
	private TextButton back;
	private CharacterProfile charprofile;

    public SceneSelectScreen(RoundWar game, CharacterProfile charprofile) {       
        super(game);
        setBackground("background/startScreen.png");
        this.charprofile = charprofile;
        createButtons();
        createTable();
    }
    
    private void createButtons() {
    	// Inicialize buttons
    	lvl00_10 = new TextButton("1-10", getSkin());
    	lvl10_20 = new TextButton("10-20", getSkin()); 
    	lvl20_30 = new TextButton("20-30", getSkin()); 
    	lvl30_40 = new TextButton("30-40", getSkin());
    	lvl40_50 = new TextButton("40-50", getSkin());
    	back     = new TextButton("40-50", getSkin());
        
        // Add listeners
    	lvl00_10.addListener(new InputListener() {
		    @Override
			public boolean touchDown (InputEvent  event, float x, float y, int pointer, int button) {                   
		        //game.setScreen(new SelectGameScreenControl(game));
		    	game.setScreen(new GameScreen(game, charprofile));
		        return false;
		    } } ); 
		
		lvl10_20.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		        game.setScreen(new OptionsScreen(game)); 
		        return false;
		    } } ); 
		
		lvl20_30.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		        game.setScreen(new ScoreScreen(game));
		        return false;
		    } 
		} ); 
		
		lvl30_40.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		        Gdx.app.exit();
		        return false;
		    } 
		} );
		
		lvl40_50.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		        Gdx.app.exit();
		        return false;
		    } 
		} );
		
		back.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		        Gdx.app.exit();
		        return false;
		    } 
		} );
    }
    
    private void createTable(){
    	table = super.getTable();
    	int h = Gdx.graphics.getHeight();
		int w = Gdx.graphics.getWidth();
    	
        table.add().spaceBottom(h*0.4f);
        table.row();
        
		/*table.add(startGameButton).size(w*0.4f, h*0.2f).uniform().spaceBottom(h*0.05f).spaceRight(w*0.1f); 
		table.add(scoresButton).size(w*0.4f, h*0.2f).uniform().spaceBottom(h*0.05f).spaceLeft(w*0.1f);
		table.row(); 
		table.add(optionsButton).size(w*0.4f, h*0.2f).uniform().spaceRight(w*0.1f);
		table.add(exitButton).size(w*0.4f, h*0.2f).uniform().spaceLeft(w*0.1f);*/
    }
}
