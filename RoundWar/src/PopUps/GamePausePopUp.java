package PopUps;

import roundwar.RoundWar;
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

	public static void setScreen(GameScreen gameScreen) {
		GamePausePopUp.gameScreen = gameScreen;
	}
	
	@Override
	protected void initializeTable() {
		super.initializeTable();
		resumeButton = new TextButton("Resume", AbstractScreen.getSkin());
		resumeButton.addListener(new InputListener() { 
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	Gdx.app.log( RoundWar.LOG, "Pulsado botón menu" );
		    	gameScreen.setGamePause(false);
		        return false;
		    } 
		} );
		
		statsButton = new TextButton("Stats", AbstractScreen.getSkin());
		statsButton.addListener(new InputListener() { 
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	Gdx.app.log( RoundWar.LOG, "Pulsado botón menu" );
		    	System.out.println("Pulsado boton para cambiar los stats");
		        return false;
		    } 
		} );
		
		exitButton = new TextButton("Exit", AbstractScreen.getSkin());
		exitButton.addListener(new InputListener() { 
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	Gdx.app.log( RoundWar.LOG, "Pulsado botón exit" );
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
