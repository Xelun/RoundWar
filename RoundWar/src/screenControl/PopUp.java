package screenControl;

import roundwar.RoundWar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class PopUp {
	
	protected static Stage popUpStage;
	protected static Table table;
	protected static RoundWar game;
	
	public PopUp(SpriteBatch batch) {
		popUpStage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, batch);
		initializeTable();
	}
	
	public static Stage getStage() {
		return popUpStage;
	}
	
	public static void setGame(RoundWar game) {
		PopUp.game = game;
	}
	
	public void resize(int width, int height) {
		popUpStage.setViewport( width, height, true );
	}
	
	public void draw (float delta) {
		popUpStage.act(delta);
		popUpStage.draw();
	}
	
	protected void initializeTable() {
		table = new Table( AbstractScreen.getSkin() ); 
        table.setFillParent( true ); 
        popUpStage.addActor( table );
	}
	
	public void dispose() {
		popUpStage.dispose();
	}
}
