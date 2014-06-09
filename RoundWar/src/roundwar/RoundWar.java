package roundwar;

import screenControl.AbstractScreen;
import screenControl.LoadingScreen;
import PopUps.PopUp;
import ProfileSettings.Profile;
import ProfileSettings.ProfileSerializer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

/**
 * Clase principal del videojuego 2d de batalla de monstruos. 2014
 * @author Elisabet Romero Vaquero
 */
public class RoundWar extends Game implements ApplicationListener  {	
	public static final String LOG = RoundWar.class.getSimpleName();
	public static Profile profile;
	public static Music bgMusic;
	public AssetManager manager = new AssetManager();

	/**
	 * Crea la aplicación e indica cual será la primera pantalla a mostrar.
	 */
	@Override
	public void create() {		
		Gdx.app.log( RoundWar.LOG, "Creating game" ); 
		load();
		PopUp.setGame(this);
		AbstractScreen.setGame(this);
//		setScreen( new SplashScreen());
    	setScreen( new LoadingScreen());
	}
	
	/**
	 * Comienza la música de fondo.
	 */
	public static void startMusic() {
		bgMusic = LoadingScreen.takeMusic();
		if(Profile.isMusic())  {
			bgMusic.setLooping(true);
			bgMusic.play();
		}
	}

	/**
	 * Guarda el perfil de jugador y opciones.
	 */
	public static void save() {
		ProfileSerializer.write(profile);
	}
	
	/**
	 * Carga, en caso de haberlo, un perfil ya creado anteriormente.
	 */
	public static void load() {
		profile = ProfileSerializer.read();
	}
	
	/**
	 * Crea un nuevo perfil en blanco y lo guarda, sobreescribiendo si había uno anterior guardado.
	 */
	public static void clearProfile() {
		profile = new Profile();
		ProfileSerializer.write(profile);
	}
	
	/**
	 * Libera la memoria y guarda el perfil del jugador para cerrar la aplicación.
	 */
	@Override
	public void dispose() {
		save();
		if(bgMusic != null) bgMusic.dispose();
		AbstractScreen.disposeStatic();
		super.dispose();
        Gdx.app.log( RoundWar.LOG, "Disposing game" );
	}

	/**
	 * Dibuja en pantalla.
	 */
	@Override
	public void render() {	
		super.render();
		getScreen().render(Gdx.graphics.getDeltaTime());
	}

	/**
	 * Redimensiona el tamaño de la pantalla.
	 */
	@Override
	public void resize(int width, int height) {
		super.resize( width, height );
        Gdx.app.log( RoundWar.LOG, "Resizing game to: " + width + " x " + height ); 
	}

	/**
	 * Pausa la aplicación, parando todos sus procesos.
	 */
	@Override
	public void pause() {
		super.pause();
		//save();
        Gdx.app.log( RoundWar.LOG, "Pausing game" ); 
	}

	/**
	 * Recarga la aplicación y los recursos tras haberla pausado.
	 */
	@Override
	public void resume() {
		super.resume();
		AbstractScreen.load();
        Gdx.app.log( RoundWar.LOG, "Resuming game" );
	}
	
	/**
	 * Indica cual es la pantalla que se mostrará.
	 */
	@Override 
	public void setScreen( Screen screen ) { 
        super.setScreen( screen ); 
        Gdx.app.log( RoundWar.LOG, "Setting screen: " + screen.getClass().getSimpleName() ); 
    }
}
