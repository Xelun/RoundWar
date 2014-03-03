package screenControl;

import roundwar.RoundWar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

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
    
    protected Background bg;
	
	/**
     * Constructor
     */
	public AbstractScreen( RoundWar game ) {
        this.game = game;
        stage = new Stage( 0, 0, true, batch );
    	Gdx.input.setInputProcessor(stage);
        batch = stage.getSpriteBatch();
	}
	
	public void setBackground(String path) {
		bg = new Background(path);
        stage.addActor(bg);
	}
	
	public void setBackground(GameScreenControl screen) {
		bg = new Background(screen);
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
            table.debug(); 
            
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
		Gdx.gl.glClearColor( 0f, 0f, 0f, 1f ); 
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
		
		drawStage(delta);
	}
	
	public void drawStage(float delta){
        stage.act(delta);
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
        bg.dispose();
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
