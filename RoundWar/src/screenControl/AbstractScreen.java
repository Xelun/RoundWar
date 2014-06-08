package screenControl;

import roundwar.RoundWar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

public abstract class AbstractScreen implements Screen {
	protected static RoundWar game;     
	protected static BitmapFont font;
    protected Stage stage;
    protected SpriteBatch batch;
    protected static Skin skin;
    protected Table table;
    private Texture tbg;
	private Image bg;
	protected boolean pause;
	protected static int h = Gdx.graphics.getHeight();
	protected static int w = Gdx.graphics.getWidth();
	
	/**
     * Constructor
     */
	public AbstractScreen() {
        stage = new Stage( 0, 0, true, batch );
    	Gdx.input.setInputProcessor(stage);
        batch = stage.getSpriteBatch();
        pause = false;
	}
	
	/**
	 * Guarda la instancia de la aplicación
	 * @param game
	 */
	public static void setGame(RoundWar game) {
		AbstractScreen.game = game;
		AbstractScreen.load();
	}
	
	/**
	 * Devuelve el stage de la pantalla.
	 */
	public Stage getStage() {
		return stage;
	}
	
	/**
	 * Dada la ruta de la imagen, la añade a la pantalla como fondo.
	 */
	public void setBackground(String path) {
		tbg = new Texture(Gdx.files.internal(path));
		tbg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        bg = new Image(new TextureRegionDrawable(new TextureRegion(tbg,512,512)), Scaling.stretch);
        bg.setFillParent(true);
        stage.addActor(bg);
	}
	
	/**
	 * Devuelve la fuente.
	 */
    public static BitmapFont getFont() { 
        return font; 
    }
 
    /**
     * Devuelve el skin.
     */
    public static Skin getSkin() { 
        return skin; 
    }
 
    /**
     * Devuelve la tabla (Creandola si no existe).
     */
    protected Table getTable() { 
        if( table == null ) { 
            table = new Table( getSkin() ); 
            table.setFillParent( true ); 
            stage.addActor( table ); 
        } 
        return table; 
    }
    
    /**
     * Devuelve el nombre de la pantalla.
     */
    protected String getName() {
		return getClass().getSimpleName();
	}
	   
    /**
     * 
     */
    @Override
    public void show() { }

	/**
	 * 
	 */
	@Override
	public void resize(int width, int height) {
		stage.setViewport( width, height, true );
	}

	/**
	 * Borra todo lo que hay en pantalla y dibuja los actores que haya en el stage.
	 */
	@Override
	public void render(float delta) {
		clear();
		drawStage(delta);
	}
	
	/**
	 * Pone la pantalla a negro.
	 */
	public void clear() {
		Gdx.gl.glClearColor( 0f, 0f, 0f, 1f ); 
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
	}
	
	/**
	 * Dibuja el stage. En caso de estar en pausa no lo actualizará.
	 * @param delta Intervalo de tiempo desde la última vez que se llamó a la función.
	 */
	public void drawStage(float delta){
		if(!pause) stage.act(delta);
		table.debug();
		stage.draw();
		Table.drawDebug(stage);
	}

	/**
	 * Pone el juego en pausa (Solo útil en GameScreen).
	 * @param pause Indica si se pausa o no.
	 */
	public void setPause(boolean pause) {
		this.pause = pause;
	}
	
	/**
	 * Devuelve si está el juego pausado o no.
	 */
	public boolean isPaused() {
	    	return pause;
	}
	
	/**
	 * Cierra la aplicación.
	 */
	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
	}

	@Override 
	public void resume() {
	}
	
	/**
	 * Carga la fuente y el skin y los redimensiona.
	 */
	public static void load() {
		FileHandle skinFile = Gdx.files.internal("skin/skin.json"); 
        skin = new Skin(skinFile);
        font = skin.getFont("default-font"); 
        font.setScale(Gdx.graphics.getWidth()/500f); // Redimensión de la fuente. Mirar si se puede hacer de otra forma
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
	
	/**
	 * Elimina la fuente y el skin en caso de existir.
	 */
	public static void disposeStatic() {
		if (font != null)
            font.dispose();
		if (skin != null)
			skin.dispose();
	}
	
	/**
	 * Libera memoria para eliminar la pantalla.
	 */
	@Override
	public void dispose() {
		stage.dispose();
		tbg.dispose();
	}
}
