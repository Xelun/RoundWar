package screenControl;

import java.util.ArrayList;

import Buttons.NewCharacterButton;
import PopUps.CharacterInfoPopUp;
import ProfileSettings.CharacterProfile;
import ProfileSettings.Profile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameSelectScreen  extends AbstractScreen {
	private ArrayList<NewCharacterButton> buttons;
	private Texture texture;
	private int i, COLS, ROWS;
	CharacterProfile charprofile;
	TextureRegion character;
	private CharacterInfoPopUp popUp;
	private TextButton backButton;
	
	/**
	 * Constructor.
	 */
	public GameSelectScreen() {
		super();
		popUp = new CharacterInfoPopUp(stage.getSpriteBatch());
		setBackground("background/startbg.png");
		COLS = 4;
		ROWS = (Profile.getUnlockCharacters()/4) + 2;
        createButtons();
        createTable();
	}
	
	/**
	 * Crea los botones con las partidas guardadas, partidas disponibles o partidas bloqueadas.
	 */
	private void createButtons() {
		// Botón para volver a la pantalla anterior
		backButton = new TextButton("Back", getSkin()); 
		
		backButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		        game.setScreen(new MenuScreen());
		        return false;
		    } 
		} );
				
		// Botones de selección de partida
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
				        game.setScreen(new CharacterSelectScreen());
				        return false;
				    } } ); 
			} else { // Si no puedes crear aún la partida
				buttons.add(new NewCharacterButton(tmp[0][0]));
			}
		}
	}
	
	/**
	 * Crea la tabla con los botones.
	 */
	private void createTable(){
		table = super.getTable();
    	
        for (int x = 0; x < 3; x++) {
        	for(int j = 0; j < 4; j++) {
        		table.add(buttons.get(x*4+j)).size(NewCharacterButton.getSize()).spaceBottom(h*0.05f).spaceRight(w*0.05f);
        	}
        	table.row();
        }
        table.add(backButton).size(w*0.4f, h*0.1f).colspan(4);
		table.row();
    	table.add().size(0,h*0.05f);
		table.bottom();
	}
	
	/**
	 * Renderiza la pantalla.
	 */
	@Override
	public void render(float delta) {
		super.render(delta);
		if(popUp.isVisible()) popUp.draw(delta);
	}
	
	/**
	 * Libera memoria para eliminar la pantalla.
	 */
	@Override
	public void dispose() {
		texture.dispose();
		super.dispose();
	}
}
