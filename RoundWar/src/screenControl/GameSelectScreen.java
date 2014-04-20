package screenControl;

import java.util.ArrayList;

import roundwar.RoundWar;
import Buttons.NewCharacterButton;
import ProfileSettings.CharacterProfile;
import ProfileSettings.Profile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class GameSelectScreen  extends AbstractScreen {
	private ArrayList<NewCharacterButton> buttons;
	private Texture texture;
	private int i, COLS, ROWS;
	CharacterProfile charprofile;
	TextureRegion character;
	private CharacterInfoPopUp popUp;
	
	public GameSelectScreen(RoundWar game) {
		super(game);
		PopUp.setGame(game);
		popUp = new CharacterInfoPopUp(stage.getSpriteBatch());
		setBackground("background/startScreen.png");
		COLS = 4;
		ROWS = (Profile.getUnlockCharacters()/4) + 2;
        createButtons();
        createTable();
	}

	private void createButtons() {
		NewCharacterButton.setFont(getFont());
		texture = new Texture(Gdx.files.internal("images/front.png"));
		TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / COLS, texture.getHeight() / ROWS);
		buttons = new ArrayList<NewCharacterButton>();
		for(i = 0; i < 12; i++) {
			// Si el botón es una partida ya creada
			if(i < Profile.getNextId()) {
				charprofile = Profile.getCharacter(i);
				character = tmp[charprofile.getType().getId()/4+1][charprofile.getType().getId()%4];
				buttons.add(new NewCharacterButton(character, charprofile.getLvl()));
				
		    	buttons.get(i).addListener(new InputListener() {
		    		CharacterProfile charprofile2 = charprofile;
		    		TextureRegion character2 = character;
				    @Override
					public boolean touchDown (InputEvent  event, float x, float y, int pointer, int button) {                   
				        popUp.show(charprofile2, character2);
				        return false;
				    } } ); 
			} else if(i < Profile.getUnlockGames()){ // Si no es una partida pero puedes crearla
				buttons.add(new NewCharacterButton(tmp[0][1]));
				buttons.get(i).addListener(new InputListener() {
				    @Override
					public boolean touchDown (InputEvent  event, float x, float y, int pointer, int button) {                   
				        game.setScreen(new CharacterSelectScreen(game));
				        return false;
				    } } ); 
			} else { // Si no puedes crear aún la partida
				buttons.add(new NewCharacterButton(tmp[0][0]));
			}
		}
	}
	
	private void createTable(){
		table = super.getTable();
    	int h = Gdx.graphics.getHeight();
		int w = Gdx.graphics.getWidth();
    	
        table.add().spaceBottom(h*0.1f);
        table.row();
        for (int x = 0; x < 3; x++) {
        	//table.add().spaceRight(w*0.1f);
        	for(int j = 0; j < 4; j++) {
        		table.add(buttons.get(x*4+j)).size(w*0.15f, w*0.15f).uniform().spaceBottom(h*0.05f).spaceRight(w*0.05f);
        	}
        	table.row();
        }
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		if(popUp.isVisible()) popUp.draw(delta);
	}
	
	@Override
	public void dispose() {
		//NewCharacterButton.dispose();
		texture.dispose();
		super.dispose();
	}
}
