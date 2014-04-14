package roundwar;

import screenControl.GameScreen;
import screenControl.MenuScreen;

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
    	//setScreen( new GameScreen(this));
    	setScreen( new MenuScreen(this));
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
