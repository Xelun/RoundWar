package screenControl;

import roundwar.RoundWar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GamePausePopUp extends PopUp {
	private TextButton resumeButton, statsButton;
	protected static GameScreen game;
	
	public GamePausePopUp(SpriteBatch batch) {
		super(batch);
	}
	
	public GamePausePopUp() {
		super(game.getStage().getSpriteBatch());
	}

	public static void setScreen(GameScreen game) {
		GamePausePopUp.game = game;
	}
	
	@Override
	protected void initializeTable() {
		resumeButton = new TextButton("Resume", game.getSkin());
		resumeButton.addListener(new InputListener() { 
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	Gdx.app.log( RoundWar.LOG, "Pulsado botón menu" );
		    	game.setPause(false);
		        return false;
		    } 
		} );
		
		statsButton = new TextButton("Stats", game.getSkin());
		statsButton.addListener(new InputListener() { 
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	Gdx.app.log( RoundWar.LOG, "Pulsado botón menu" );
		    	System.out.println("Pulsado boton para cambiar los stats");
		        return false;
		    } 
		} );
		
		table.add(resumeButton).size(Gdx.graphics.getWidth()*0.3f, Gdx.graphics.getHeight()*0.2f);
		table.row();
		table.add(statsButton).size(Gdx.graphics.getWidth()*0.3f, Gdx.graphics.getHeight()*0.2f);
	}
}
