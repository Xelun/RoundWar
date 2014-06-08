package PopUps;

import screenControl.AbstractScreen;
import screenControl.MenuScreen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class WinLosePopUp extends PopUp {
	private TextButton backButton;
	
	public WinLosePopUp(SpriteBatch batch) {
		super(batch);
	}
	
	public void show(boolean win) {
		if(win) initializeWinTable();
		else	initializeLoseTable();
		initializeCommonTable();
		super.show();
	}
	
	@Override
	public void close() {
		super.close();
	}
	
	protected void initializeWinTable() {
		table.add("HAS GANADO").expandX().center();
	}
	
	protected void initializeLoseTable() {
		table.add("HAS PERDIDO").expandX().center();
	}
	
	private void initializeCommonTable() {
		backButton = new TextButton("Back to MainMenu", AbstractScreen.getSkin(), "go");
		backButton.addListener(new InputListener() { 
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	game.setScreen(new MenuScreen());
		        return false;
		    } 
		} );
		table.row();
    	table.add().size(0,h*0.15f);
		table.row();
		table.add(backButton).size(w*0.5f, h*0.1f).colspan(2);
		table.row();
    	table.add().size(0,h*0.3f);
		table.bottom();
	}
	
}
