package roundwar;

import screenControl.GameScreenControl;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class RoundWar extends Game implements ApplicationListener  {	
	public static final String LOG = RoundWar.class.getSimpleName();
	
	@Override
	public void create() {		
		Gdx.app.log( RoundWar.LOG, "Creating game" ); 
    	//setScreen( new SplashScreenControl(this));
    	setScreen( new GameScreenControl(this));
    	
		/*float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(1, h/w);
		batch = new SpriteBatch();
		
		background = new Texture(Gdx.files.internal("StartScreen.png"));
		background.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		texture = new Texture(Gdx.files.internal("bicho1.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion region = new TextureRegion(background, 0, 0, 512, 512);
		
		sprite = new Sprite(region);
		sprite.setSize(1f, 1f);
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
		
		TextureRegion bicho = new TextureRegion(texture, 0, 0, 128, 128);
		
		sprite2 = new Sprite(bicho);
		sprite2.setSize(0.2f, 0.2f);
		sprite2.setOrigin(sprite2.getWidth()/2, sprite2.getHeight()/2);
		sprite2.setPosition(-sprite2.getWidth()/2, -sprite2.getHeight()/2);*/
	}

	@Override
	public void dispose() {
		super.dispose();
        Gdx.app.log( RoundWar.LOG, "Disposing game" );
	}

	@Override
	public void render() {	
		super.render();
		getScreen().render(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height) {
		super.resize( width, height );
        Gdx.app.log( RoundWar.LOG, "Resizing game to: " + width + " x " + height ); 
	}

	@Override
	public void pause() {
		super.pause();
        Gdx.app.log( RoundWar.LOG, "Pausing game" ); 
	}

	@Override
	public void resume() {
		super.resume();
        Gdx.app.log( RoundWar.LOG, "Resuming game" );
	}
	
	@Override 
	public void setScreen( Screen screen ) { 
        super.setScreen( screen ); 
        Gdx.app.log( RoundWar.LOG, "Setting screen: " + screen.getClass().getSimpleName() ); 
    }
}
