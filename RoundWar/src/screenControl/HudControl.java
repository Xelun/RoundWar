package screenControl;

import roundwar.HealthBar;
import roundwar.ManaBar;
import roundwar.RoundWar;
import Entities.Minimal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class HudControl {
	private GameScreenControl screen;
	private Table table;
	private Skin skin;
	private boolean left;
	private Vector2 controllerOrigin;
	private int h, w;
	
	private TextButton nearAttackButton;
	private TextButton runAttackButton;
	private TextButton farAttackButton;
	private TextButton inAreaAttackButton;
	private TextButton menuButton;
	private TouchControl control;
	private SpriteBatch batch;
	private Minimal mainpj;
	private HealthBar healthBar;
	private ManaBar manaBar;

    public HudControl(GameScreenControl screen, boolean left, Minimal mainpj, SpriteBatch batch) {
    	h = Gdx.graphics.getHeight();
    	w = Gdx.graphics.getWidth();
    	controllerOrigin = new Vector2();
    	control = new TouchControl(mainpj, batch);
    	this.batch = batch;
    	
    	this.mainpj = mainpj;
    	this.screen = screen;
    	this.table = this.screen.getTable();
    	this.skin = this.screen.getSkin();
    	this.left = left;
    	if(left){
    		createLeft();
    	} else {
    		createRight();
    	}
    }
    
    private void createCommon() {
    	controllerOrigin.y = w*0.175f;
    	if (w*0.25f > 150){ // El tamaño máximo del controlador serán 150px
    		controllerOrigin.y = 75+w*0.05f;
    	}
    	// Inicialize buttons
		nearAttackButton = new TextButton("N", skin);
		runAttackButton = new TextButton("R", skin); 
		farAttackButton = new TextButton("F", skin); 
		inAreaAttackButton = new TextButton("I", skin); 
		menuButton = new TextButton("M", skin); 
		
        // Add listeners
        nearAttackButton.addListener(new InputListener() {
		    @Override
			public boolean touchDown (InputEvent  event, float x, float y, int pointer, int button) {                   
		    	Gdx.app.log( RoundWar.LOG, "Pulsado botón near Attack" );
		        return false;
		    } } ); 
		
        runAttackButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	Gdx.app.log( RoundWar.LOG, "Pulsado botón run Attack" ); 
		        return false;
		    } } ); 
		
        farAttackButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	Gdx.app.log( RoundWar.LOG, "Pulsado botón far Attack" );
		        return false;
		    } 
		} ); 
		
        inAreaAttackButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	Gdx.app.log( RoundWar.LOG, "Pulsado botón in area Attack" );
		        return false;
		    } 
		} );
        
        menuButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	Gdx.app.log( RoundWar.LOG, "Pulsado botón menu" );
		        return false;
		    } 
		} );
    }
    
    private void createLeft() {
    	createCommon();
    	
    	//Controlador de dirección
    	controllerOrigin.x = w*0.175f;
    	if(w*0.25f > 150){ // El tamaño máximo del controlador serán 150px
    		controllerOrigin.x = 75 + w*0.05f;
    	}
    	
    	//Barras de vida y MP
    	healthBar = new HealthBar(screen.getCharacter(), w*0.03f, h-h*0.06f);
    	screen.getCharacter().setHealthBar(healthBar);
    	manaBar = new ManaBar(screen.getCharacter(), w*0.03f, h-h*0.1f);
    	screen.getCharacter().setManaBar(manaBar);
    	
    	//Tabla de botones de ataque
    	Gdx.app.log( RoundWar.LOG, "Creando barra" ); 
        table.right();
        createTable();
    	
    }
    
    private void createRight() {
    	createCommon();
    	
    	controllerOrigin.x = w - w*0.175f;
    	if(w*0.25f > 150){ // El tamaño máximo del controlador serán 150px
    		controllerOrigin.x = w - 75 + w*0.05f;
    	}
    }
    
    private void createTable(){
    	table.add(nearAttackButton).size(w*0.1f, h*0.2f);
    	table.row();
    	table.add(runAttackButton).size(w*0.1f, h*0.2f);
    	table.row();
    	table.add(farAttackButton).size(w*0.1f, h*0.2f);
    	table.row();
    	table.add(inAreaAttackButton).size(w*0.1f, h*0.2f);
    	table.row();
    	table.add(menuButton).size(w*0.1f, h*0.2f);
    }
    
    public void resize(int width, int height) {
        this.h = height;
        this.w = width;
        this.healthBar.resize(width, height);
    	if(left){
    		createLeft();
    	} else {
    		createRight();
    	}
    }
    
    public void dispose() {
    	healthBar.dispose();
    	manaBar.dispose();
    	control.dispose();
    }
    
    public Table getTable(){
    	return table;
    }
    
    public boolean getLeft(){
    	return left;
    }
    
    public void setLeft(boolean left){
    	this.left = left;
    }
    
    public void actHealthBar(float health){
    	healthBar.act(health);
    }
    
    public void actManaBar(float mana){
    	manaBar.act(mana);
    }
    
    public void draw() {
    	healthBar.draw(batch);
    	manaBar.draw(batch);
    	table.draw(batch, 1f);
    	control.draw(batch);
    }
    
    public void stageDraw(){
    	control.stageDraw();
    }
}

