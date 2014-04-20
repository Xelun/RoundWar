package screenControl;

import Buttons.ImageCharacter;
import Entities.LivingEntity;
import ProfileSettings.CharacterProfile;
import ProfileSettings.Profile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class CharacterInfoPopUp extends PopUp {
	private Image goButton, backButton;
	private Texture popupTexture;
	private ImageCharacter imageCharacter;
	private CharacterProfile cprofile;
	private int w, h;
	private boolean visible;
	
	public CharacterInfoPopUp(SpriteBatch batch) {
		super(batch);
		popupTexture = new Texture(Gdx.files.internal("images/popup.png"));
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		visible = false;
	}
	
	protected void initializeTable(final boolean create) {//String name, int lvl, float statatq, float statdef, float stathp, float statvel) {
		if(cprofile == null) return;
		super.initializeTable();
		goButton = new Image(new TextureRegion(popupTexture,64,0,96,32));
		goButton.addListener(new InputListener() { 
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	if(create) { // Se ha creado un nuevo personaje, se añade al perfil de usuario
		    		Profile.addCharacter(cprofile);
		    	}
		    	game.setScreen(new GameScreen(game,cprofile));
		        return false;
		    } 
		} );
		
		backButton = new Image(new TextureRegion(popupTexture,32,0,32,32));
		backButton.addListener(new InputListener() { 
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
				dispose();
		        return false;
		    }
		} );
		table.add().spaceBottom(h*0.1f);
		table.row();
		table.add().spaceRight(w*0.2f+74f);
		table.add(cprofile.getName());
		table.row();
		table.add(String.valueOf(cprofile.getLvl()));
		table.row();
		table.add(goButton).size(96, 32);
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void show(CharacterProfile cprofile, TextureRegion characterTexture) {
		create(cprofile, characterTexture, false);
	}
	
	private void create(CharacterProfile cprofile, TextureRegion characterTexture, boolean table) {
		imageCharacter = new ImageCharacter( new NinePatch(new TextureRegion(popupTexture, 0, 0, 32, 32), 14, 14, 14, 14),
				new TextureRegion(popupTexture,0,32,64,64), characterTexture, w, h);
		popUpStage.addActor(imageCharacter);
		this.cprofile = cprofile;
		initializeTable(table);
		visible = true;
		Gdx.input.setInputProcessor(popUpStage);
	}
	
	public void show(LivingEntity.Type type, TextureRegion characterTexture) {
		cprofile = new CharacterProfile(type);
		create(cprofile, characterTexture, true);
	}
	
	@Override
	public void dispose() {
		//Gdx.input.setInputProcessor(((AbstractScreen)game.getScreen()).getStage());
		popupTexture.dispose();
		super.dispose();
	}
}
