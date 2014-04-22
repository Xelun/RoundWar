package PopUps;

import roundwar.RoundWar;
import screenControl.AbstractScreen;
import Buttons.ImageBackgroundPopUp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class PopUp {
	protected static RoundWar game;
	protected Texture popupTexture;
	protected Stage popUpStage;
	protected Table table;
	protected ImageBackgroundPopUp bg;
	protected boolean visible;
	
	public PopUp(SpriteBatch batch) {
		visible = false;
		popupTexture = new Texture(Gdx.files.internal("images/popup.png"));
		popUpStage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, batch);
		bg = new ImageBackgroundPopUp(new NinePatch(new TextureRegion(popupTexture, 0, 0, 32, 32), 14, 14, 14, 14)); 
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
	}
	
	public void resize(int width, int height) {
		popUpStage.setViewport( width, height, true );
	}
	
	public void draw (float delta) {
		//System.out.println(popUpStage.getActors());
		popUpStage.act(delta);
		popUpStage.draw();
	}
	
	protected void initializeTable() {
		table = new Table( AbstractScreen.getSkin() ); 
        table.setFillParent( true ); 
        popUpStage.addActor( table );
	}
	
	public void dispose() {
		popupTexture.dispose();
		popUpStage.dispose();
	}
}
