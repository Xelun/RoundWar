package screenControl;

import roundwar.RoundWar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MenuScreenControl extends AbstractScreen {
	private TextButton startGameButton;
	private TextButton optionsButton;
	private TextButton scoresButton;
	private TextButton exitButton;

    public MenuScreenControl(RoundWar game) {       
            super(game);
    }
    
    @Override 
    public void show() {
        super.show();
        
		int h = Gdx.graphics.getHeight();
		int w = Gdx.graphics.getWidth();
        
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
		    	game.setScreen(new GameScreenControl(game));
		        return false;
		    } } ); 
		
		optionsButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		        game.setScreen(new OptionsScreenControl(game)); 
		        return false;
		    } } ); 
		
		scoresButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		        game.setScreen(new ScoreScreenControl(game));
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
		
		// Create background
		setBackground("background/startScreen.png");
		
		// Create table
		Table table = super.getTable();
        table.add().spaceBottom(h*0.4f);
        table.row();
        
		table.add(startGameButton).size(w*0.4f, h*0.2f).uniform().spaceBottom(h*0.05f).spaceRight(w*0.1f); 
		table.add(scoresButton).size(w*0.4f, h*0.2f).uniform().spaceBottom(h*0.05f).spaceLeft(w*0.1f);
		table.row(); 
		table.add(optionsButton).size(w*0.4f, h*0.2f).uniform().spaceRight(w*0.1f);
		table.add(exitButton).size(w*0.4f, h*0.2f).uniform().spaceLeft(w*0.1f);
	}
}
