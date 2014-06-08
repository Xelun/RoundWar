package screenControl;

import java.util.ArrayList;

import Buttons.NewCharacterButton;
import Entities.LivingEntity;
import PopUps.CharacterInfoPopUp;
import ProfileSettings.Profile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class CharacterSelectScreen extends AbstractScreen {
	private Texture tcharacters;
	private TextureRegion character;
	private LivingEntity.Type type;
	private CharacterInfoPopUp popUp;
	private ArrayList<NewCharacterButton> buttons;
	private TextButton backButton;
	
	/**
	 * Constructor.
	 */
	public CharacterSelectScreen() {
		super();
		popUp = new CharacterInfoPopUp(stage.getSpriteBatch());
		setBackground("background/startbg.png");
        createButtons();
        createTable();
	}

	/**
	 * Crea todos los botones de selección de personaje, añadiendole la imagen de cada uno
	 * o una por defecto en caso de no haber desbloqueado a ese personaje.
	 */
	private void createButtons() {
		// Botón para volver a la pantalla anterior
		backButton = new TextButton("Back", getSkin()); 
		
		backButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		        game.setScreen(new GameSelectScreen());
		        return false;
		    } 
		} );
		
		// Botones de selección de nuevo personaje
		tcharacters = new Texture(Gdx.files.internal("images/front.png"));
		TextureRegion[][] tmp = TextureRegion.split(tcharacters, 64, 64);
		buttons = new ArrayList<NewCharacterButton>();
		
		for(int i = 0; i < 12; i++) {
			if(i < Profile.getUnlockCharacters()) { // Personajes desbloqueados
				character = tmp[i/4+1][i%4];
		    	buttons.add(new NewCharacterButton(character));
		    	
		    	switch(i) {
					case 0:
						type = LivingEntity.Type.PIRKO;
						break;
					case 1:
						type = LivingEntity.Type.GULLA;
						break;
		    	}
		    	// Añade el evento de pulsar el botón
		    	buttons.get(i).addListener(new InputListener() {
		    		LivingEntity.Type type2 = type;
		    		TextureRegion character2 = character;
				    @Override
					public boolean touchDown (InputEvent  event, float x, float y, int pointer, int button) {                   
				    	popUp.show(type2, character2); // Muestra la información del personaje
				        return false;
				    } } ); 
			} else { // Imagen por defecto de personaje desconocido
				buttons.add(new NewCharacterButton(tmp[0][2]));
			}
		}
	}
	
	/**
	 * Crea la tabla con todos los botones.
	 */
	private void createTable(){
		table = super.getTable();
		
//		NewCharacterButton aux;
        for (int x = 0; x < 3; x++) {
        	for(int j = 0; j < 4; j++) {
//        		aux = buttons.get(x*4+j);
        		//aux.setBounds(0, 0, w*0.15f, w*0.15f);
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
	 * Renderiza la pantalla y los popUps en caso de haber alguno visible.
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
		tcharacters.dispose();
		popUp.dispose();
		super.dispose();
	}
}
