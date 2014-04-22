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

public class CharacterSelectScreen extends AbstractScreen {
	private Texture tcharacters;
	private TextureRegion character;
	private LivingEntity.Type type;
	private CharacterInfoPopUp popUp;
	private ArrayList<NewCharacterButton> buttons;
	
	public CharacterSelectScreen() {
		super();
		popUp = new CharacterInfoPopUp(stage.getSpriteBatch());
		setBackground("background/startScreen.png");
        createButtons();
        createTable();
	}

	private void createButtons() {
		tcharacters = new Texture(Gdx.files.internal("images/front.png"));
		TextureRegion[][] tmp = TextureRegion.split(tcharacters, 64, 64);
		buttons = new ArrayList<NewCharacterButton>();
		for(int i = 0; i < 12; i++) {
			if(i < Profile.getUnlockCharacters()) {
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
		    	buttons.get(i).addListener(new InputListener() {
		    		LivingEntity.Type type2 = type;
		    		TextureRegion character2 = character;
				    @Override
					public boolean touchDown (InputEvent  event, float x, float y, int pointer, int button) {                   
				    	popUp.show(type2, character2);
				        return false;
				    } } ); 
			} else {
				buttons.add(new NewCharacterButton(tmp[0][2]));
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
		NewCharacterButton.dispose();
		tcharacters.dispose();
		popUp.dispose();
		super.dispose();
	}
}
