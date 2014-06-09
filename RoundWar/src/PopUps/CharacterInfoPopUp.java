package PopUps;

import screenControl.AbstractScreen;
import screenControl.SceneSelectScreen;
import Buttons.ImageCharacter;
import Entities.LivingEntity;
import ProfileSettings.CharacterProfile;
import ProfileSettings.Profile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class CharacterInfoPopUp extends PopUp {
	private TextButton goButton;
	private Image backButton;
	
	private ImageCharacter imageCharacter;
	private CharacterProfile cprofile;
	private int w, h;
	
	/**
	 * Constructor.
	 * @param batch
	 */
	public CharacterInfoPopUp(SpriteBatch batch) {
		super(batch);
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
	}
	
	/**
	 * Inicializa la tabla.
	 * @param create
	 */
	protected void initializeTable(final boolean create) {
		if(cprofile == null) return;
		super.initializeTable();
		goButton = new TextButton("Go", AbstractScreen.getSkin(), "go");
		goButton.addListener(new InputListener() { 
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	if(create) { // Se ha creado un nuevo personaje, se añade al perfil de usuario
		    		Profile.addCharacter(cprofile);
		    	}
		    	game.setScreen(new SceneSelectScreen(cprofile));
		    	dispose();
		        return false;
		    } 
		} );
		
		backButton = new Image(AbstractScreen.getSkin().getDrawable("exit"));
		backButton.addListener(new InputListener() { 
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
				close();
		        return false;
		    }
		} );
		imageCharacter.setSize(h*0.2f, h*0.2f);
	    table.add();
		table.add();
		table.add(backButton).size(h*0.1f).spaceLeft(w*0.3f).spaceBottom(h*0.05f).align(1);
		table.row();
		table.add().width(w*0.1f);
		table.add(imageCharacter).size(h*0.2f).align(1).spaceRight(20).spaceBottom(h*0.02f);
		table.add(cprofile.getName() + "\n" + String.valueOf(cprofile.getLvl())).spaceLeft(w*0.1f).spaceRight(w*0.2f);
		table.add();
		table.row();
		table.add().width(w*0.1f);
		table.add("ATQ: " + String.valueOf(cprofile.getStatAtq())).align(4);
		table.add("DEF: " + String.valueOf(cprofile.getStatDef()));
		table.add();
		table.row();
		table.add().width(w*0.1f);
		table.add("HP:  " + String.valueOf(cprofile.getStatHp())).align(4);
		table.add("VEL: " + String.valueOf(cprofile.getStatVel()));
		table.add();
		table.row();
		table.add().height(h*0.02f).colspan(4);
		table.row();
		table.add(goButton).size(w*0.2f, h*0.1f).colspan(4);
		table.row();
		table.add().height(h*0.22f).colspan(4);
		table.bottom();
	}
	
	/**
	 * Muestra la información del perfil pasado.
	 * @param cprofile
	 * @param characterTexture
	 */
	public void show(CharacterProfile cprofile, TextureRegion characterTexture) {
		create(cprofile, characterTexture, false);
	}
	
	/**
	 * Esconde el popUp.
	 */
	@Override
	public void close() {
		super.close();
		Gdx.input.setInputProcessor(((AbstractScreen)game.getScreen()).getStage());
	}
	
	/**
	 * Crea la tabla y las imagenes de perfil.
	 * @param cprofile
	 * @param characterTexture
	 * @param table
	 */
	private void create(CharacterProfile cprofile, TextureRegion characterTexture, boolean table) {
		imageCharacter = new ImageCharacter(AbstractScreen.getSkin().getPatch("bg-info"), characterTexture);
		popUpStage.addActor(imageCharacter);
		this.cprofile = cprofile;
		initializeTable(table);
		super.show();
	}
	
	/**
	 * Muestra la información genérica de un tipo de entidad.
	 * @param type
	 * @param characterTexture
	 */
	public void show(LivingEntity.Type type, TextureRegion characterTexture) {
		cprofile = new CharacterProfile(type);
		create(cprofile, characterTexture, true);
	}
}
