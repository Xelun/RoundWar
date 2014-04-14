package screenControl;

import roundwar.HealthBar;
import roundwar.RoundWar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GamePauseMenu {
	private GameScreen game;
	private Stage pauseStage;
	private TextButton resumeButton;
	protected Table table;
	//private HealthBar hb;
	
	public GamePauseMenu(GameScreen game) {
		this.game = game;
		pauseStage = new Stage(0, 0, true, game.getStage().getSpriteBatch());
		pauseStage.getSpriteBatch().setColor(0.5f, 0.5f, 0.5f, 0.5f);
		
		initializeTable();
	}
	private void initializeTable() {
		table = new Table( game.getSkin() ); 
        table.setFillParent( true ); 
        pauseStage.addActor( table );
        
		resumeButton = new TextButton("Resume", game.getSkin());
		resumeButton.addListener(new InputListener() { 
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	Gdx.app.log( RoundWar.LOG, "Pulsado botón menu" );
		    	game.setPause(false);
		        return false;
		    } 
		} );
		
		table.add(resumeButton).size(Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight()*0.2f);
	}
	
	public Stage getStage() {
		return pauseStage;
	}
	
	public void resize(int width, int height) {
        pauseStage.setViewport( width, height, true );
	}
	
	public void drawStage (float delta) {
		pauseStage.act(delta);
    	pauseStage.draw();
	}
}
