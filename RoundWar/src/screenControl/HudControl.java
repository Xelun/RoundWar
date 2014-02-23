package screenControl;

import roundwar.HealthBar;
import roundwar.ManaBar;
import roundwar.RoundWar;
import Entities.MainCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
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
	private Circle controller, position;
	private MainCharacter character;
	private HealthBar healthBar;
	private ManaBar manaBar;

    public HudControl(GameScreenControl screen, boolean left, MainCharacter character) {
    	h = Gdx.graphics.getHeight();
    	w = Gdx.graphics.getWidth();
    	controllerOrigin = new Vector2();
    	this.character = character;
    	this.screen = screen;
    	this.table = this.screen.getTable();
    	this.skin = this.screen.getSkin();
    	this.left = left;
    	controller = new Circle();
    	position = new Circle();
    	position.radius = 20;
    	if(left){
    		createLeft();
    	} else {
    		createRight();
    	}
    }
    
    private void createCommon() {
    	controller.radius = w*0.175f;
    	controllerOrigin.y = w*0.175f;
    	if (w*0.25f > 150){ // El tamaño máximo del controlador serán 150px
    		controllerOrigin.y = 75+w*0.05f;
    	}
    }
    
    private void createLeft() {
    	createCommon();
    	
    	controllerOrigin.x = w*0.175f;
    	if(w*0.25f > 150){ // El tamaño máximo del controlador serán 150px
    		controllerOrigin.x = 75 + w*0.05f;
    	}
    	healthBar = new HealthBar(screen.getCharacter(), w*0.03f, h-h*0.06f);
    	screen.getCharacter().setHealthBar(healthBar);
    	manaBar = new ManaBar(screen.getCharacter(), w*0.03f, h-h*0.1f);
    	screen.getCharacter().setManaBar(manaBar);
    	//table.add(healthBar).spaceBottom(h/2);//.spaceLeft(w*0.05f).spaceRight(h*0.05f).spaceBottom(h-h*0.1f);
    	Gdx.app.log( RoundWar.LOG, "Creando barra" ); 
        
		//table.add(startGameButton).size(w*0.4f, h*0.2f).uniform().spaceBottom(h*0.05f).spaceRight(w*0.1f); 
    }
    
    private void createRight() {
    	createCommon();
    	
    	controllerOrigin.x = w - w*0.175f;
    	if(w*0.25f > 150){ // El tamaño máximo del controlador serán 150px
    		controllerOrigin.x = w - 75 + w*0.05f;
    	}
    	
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
    
    /*public void show() {
    	
		int h = Gdx.graphics.getHeight();
		int w = Gdx.graphics.getWidth();
        
        // Inicialize buttons
		nearAttackButton = new TextButton("Start", skin);
		runAttackButton = new TextButton("Options", skin); 
		farAttackButton = new TextButton("Scores", skin); 
		inAreaAttackButton = new TextButton("Exit", skin); 
		menuButton = new TextButton("Exit", skin); 
		
        // Add listeners
        nearAttackButton.addListener(new InputListener() {
		    @Override
			public boolean touchDown (InputEvent  event, float x, float y, int pointer, int button) {                   
		        //game.setScreen(new SelectGameScreenControl(game));
		    	//game.setScreen(new GameScreenControl(game));
		        return false;
		    } } ); 
		
        runAttackButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		        //game.setScreen(new OptionsScreenControl(game)); 
		        return false;
		    } } ); 
		
        farAttackButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		        //game.setScreen(new ScoreScreenControl(game));
		        return false;
		    } 
		} ); 
		
        inAreaAttackButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		        Gdx.app.exit();
		        return false;
		    } 
		} ); 
		
        
		// Create table
		 */
        /*table.add().spaceRight(w*0.9f);
        
		table.add(startGameButton).size(w*0.4f, h*0.2f).uniform().spaceBottom(h*0.05f).spaceRight(w*0.1f); 
		table.add(scoresButton).size(w*0.4f, h*0.2f).uniform().spaceBottom(h*0.05f).spaceLeft(w*0.1f);
		table.row(); 
		table.add(optionsButton).size(w*0.4f, h*0.2f).uniform().spaceRight(w*0.1f);
		table.add(exitButton).size(w*0.4f, h*0.2f).uniform().spaceLeft(w*0.1f);
	}*/
    
    public void draw(SpriteBatch batch) {
    	healthBar.draw(batch);
    	manaBar.draw(batch);
    }
}

