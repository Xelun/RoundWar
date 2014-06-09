package PopUps;

import roundwar.RoundWar;
import screenControl.AbstractScreen;
import Buttons.ImageBackgroundPopUp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class PopUp {
	protected static RoundWar game;
	protected Stage popUpStage;
	protected Table table;
//	protected ImageBackgroundPopUp bg;
	protected ImageBackgroundPopUp bg;
	protected boolean visible;
	protected static int w, h;
	
	public PopUp(SpriteBatch batch) {
		visible = false;
		popUpStage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, batch);
		bg = new ImageBackgroundPopUp(AbstractScreen.getSkin().getPatch("bg-popup"));
		popUpStage.addActor(bg);
		initializeTable();
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
		if(visible) Gdx.input.setInputProcessor(popUpStage);
	}
	
	public void show() {
		Gdx.input.setInputProcessor(popUpStage);
		setVisible(true);
	}
	
	public void close() {
		setVisible(false);
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public Stage getStage() {
		return popUpStage;
	}
	
	public static void setGame(RoundWar game) {
		PopUp.game = game;
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
	}
	
	public void resize(int width, int height) {
		popUpStage.setViewport( width, height, true );
	}
	
	public void draw (float delta) {
		popUpStage.act(delta);
//		table.debug();
		popUpStage.draw();
//		Table.drawDebug(popUpStage);
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
