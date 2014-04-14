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
	protected final RoundWar game;     
	protected BitmapFont font;
    protected Stage stage;
    protected SpriteBatch batch;
    protected Skin skin;
    protected Table table;
    private Texture tbg;
	private Image bg;
	protected boolean pause;
	
	/**
     * Constructor
     */
	public AbstractScreen( RoundWar game ) {
        this.game = game;
        stage = new Stage( 0, 0, true, batch );
    	Gdx.input.setInputProcessor(stage);
        batch = stage.getSpriteBatch();
        pause = false;
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
    public BitmapFont getFont() { 
        if( font == null ) { 
            font = new BitmapFont(); 
        } 
        return font; 
    }
 
    /**
     * Return the skin
     * @return
     */
    protected Skin getSkin() { 
        if( skin == null ) {
            FileHandle skinFile = Gdx.files.internal("skin/uiskin.json"); 
            skin = new Skin(skinFile); 
        } 
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

	public void setPause(boolean pause) {
		this.pause = pause;
	}
	
	public boolean isPaused() {
	    	return pause;
	}
	
	public void drawStage(float delta){
		if(!pause) stage.act(delta);
		stage.draw();
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

	@Override
	public void dispose() {
		stage.dispose();
		tbg.dispose();
        if (font != null)
                font.dispose();
	}

	public Stage getStage(){
		return stage;
	}
	
	protected String getName() {
		return getClass().getSimpleName();
	}
}
