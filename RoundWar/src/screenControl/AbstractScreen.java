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

/**
 * @author sagoc dev
 * Clase base para la mayoría de las pantallas
 */
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
	
	/**
     * Constructor
     */
	public AbstractScreen() {
        stage = new Stage( 0, 0, true, batch );
    	Gdx.input.setInputProcessor(stage);
        batch = stage.getSpriteBatch();
        pause = false;
	}
	
	public static void setGame(RoundWar game) {
		AbstractScreen.game = game;
		AbstractScreen.load();
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public void setBackground(String path) {
		tbg = new Texture(Gdx.files.internal(path));
		tbg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        bg = new Image(new TextureRegionDrawable(new TextureRegion(tbg,512,512)), Scaling.stretch);
        bg.setFillParent(true);
        stage.addActor(bg);
	}
	
	/**
	 * Return the font
	 * @return
	 */
    public static BitmapFont getFont() { 
        return font; 
    }
 
    /**
     * Return the skin
     * @return
     */
    public static Skin getSkin() { 
        return skin; 
    }
 
    /**
     * Return the table
     * @return
     */
    protected Table getTable() { 
        if( table == null ) { 
            table = new Table( getSkin() ); 
            table.setFillParent( true ); 
            stage.addActor( table ); 
        } 
        return table; 
    }
    
    protected String getName() {
		return getClass().getSimpleName();
	}
	   
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
	 * 
	 */
	@Override
	public void render(float delta) {
		clear();
		drawStage(delta);
	}
	
	public void clear() {
		Gdx.gl.glClearColor( 0f, 0f, 0f, 1f ); 
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
	}
	
	public void drawStage(float delta){
		if(!pause) stage.act(delta);
		table.debug();
		stage.draw();
		Table.drawDebug(stage);
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}
	
	public boolean isPaused() {
	    	return pause;
	}
	
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
	
	public static void load() {
		FileHandle skinFile = Gdx.files.internal("skin/uiskin.json"); 
        skin = new Skin(skinFile);
        font = skin.getFont("default-font"); 
        font.setScale(Gdx.graphics.getWidth()/250); // Redimensión de la fuente. Mirar si se puede hacer de otra forma
	}
	
	public static void disposeStatic() {
		if (font != null)
            font.dispose();
		if (skin != null)
			skin.dispose();
	}
	
	@Override
	public void dispose() {
		stage.dispose();
		tbg.dispose();
	}
}
