package screenControl;

import roundwar.HealthBar;
import roundwar.ManaBar;
import roundwar.RoundWar;
import Entities.MainCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Hud {
	public enum AttackType {NONE, NEAR, RUN, FAR, INAREA, NORMAL, COLLISION};
	private GameScreen game;
	private Stage hudStage, stage;
	private Table table;
	private Skin skin;
	private boolean left;
	private int h, w;
	
	private TextButton nearAttackButton;
	private TextButton runAttackButton;
	private TextButton farAttackButton;
	private TextButton inAreaAttackButton;
	private static TextButton menuButton;
	private Controller control;
	
	private MainCharacter mainpj;
	private static HealthBar healthBar;
	private static ManaBar manaBar;

    public Hud(GameScreen game, boolean left) {
    	h = Gdx.graphics.getHeight();
    	w = Gdx.graphics.getWidth();
    	
    	this.game = game;
    	this.left = left;
    	this.stage = this.game.getStage();
    	this.mainpj = this.game.getCharacter();
    	this.skin = AbstractScreen.getSkin();
    	
    	initializeStage(game.getStage().getSpriteBatch());
    	initializeTable();
    }
    
    private void initializeStage(SpriteBatch batch) {
    	// Inicialize stage
    	hudStage = new Stage(0, 0, true, batch);
    	Gdx.input.setInputProcessor(this.hudStage); // Sobreescribe el inputprocesor, dejando de escuchar al otro stage
    	
    	addListeners();
    	
    	// Inicialize actors
    	table = game.getTable();
    	control = new Controller(mainpj);
    	healthBar = new HealthBar(mainpj);
    	manaBar = new ManaBar(mainpj);
    	
    	if(left){
    		createLeft();
    	} else {
    		createRight();
    	}
    	
    	// Add actors to the stage
    	hudStage.addActor(mainpj);
    	hudStage.addActor(table);
    	hudStage.addActor(control);
    	hudStage.addActor(healthBar);
    	hudStage.addActor(manaBar);
    }
    
    private void initializeTable() {
    	// Inicialize buttons
		nearAttackButton = new TextButton("N", skin);
		runAttackButton = new TextButton("R", skin); 
		farAttackButton = new TextButton("F", skin); 
		inAreaAttackButton = new TextButton("I", skin); 
		menuButton = new TextButton(String.valueOf(mainpj.getLevel()), skin);
    	
        // Add listeners to buttons
        nearAttackButton.addListener(new InputListener() {
		    @Override
			public boolean touchDown (InputEvent  event, float x, float y, int pointer, int button) {                   
		    	Gdx.app.log( RoundWar.LOG, "Pulsado botón near Attack" );
		    	mainpj.doAttack1();
		    	/*if(attack != AttackType.NEAR) {
		    		attack = AttackType.NEAR;
		    		mainpj.setRangeRadius(AttackType.NEAR);
		    		mainpj.setRangeVisible(true);
		    	} else {
		    		attack = AttackType.NONE;
		    		mainpj.setRangeVisible(false);
		    	}*/
		        return false;
		    } } ); 
		
        runAttackButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	Gdx.app.log( RoundWar.LOG, "Pulsado botón run Attack" ); 
		    	/*if(attack != AttackType.RUN) {
		    		attack = AttackType.RUN;
		    		mainpj.setRangeRadius(AttackType.RUN);
		    		mainpj.setRangeVisible(true);
		    	} else {
		    		attack = AttackType.NONE;
		    		mainpj.setRangeVisible(false);
		    	}*/
		        return false;
		    } } ); 
		
        farAttackButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	Gdx.app.log( RoundWar.LOG, "Pulsado botón far Attack" );
		    	/*if(attack != AttackType.FAR) {
		    		attack = AttackType.FAR;
		    		mainpj.setRangeRadius(AttackType.FAR);
		    		mainpj.setRangeVisible(true);
		    	} else {
		    		attack = AttackType.NONE;
		    		mainpj.setRangeVisible(false);
		    	}*/
		        return false;
		    } 
		} ); 
		
        inAreaAttackButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	Gdx.app.log( RoundWar.LOG, "Pulsado botón in area Attack" );
		    	/*if(attack != AttackType.INAREA) {
		    		attack = AttackType.INAREA;
		    		mainpj.setRangeRadius(AttackType.INAREA);
		    		mainpj.setRangeVisible(true);
		    	} else {
		    		attack = AttackType.NONE;
		    		mainpj.setRangeVisible(false);
		    	}*/
		        return false;
		    } 
		} );
        
        menuButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	Gdx.app.log( RoundWar.LOG, "Pulsado botón menu" );
		    	game.setGamePause(true);
		        return false;
		    } 
		} );
        
        //Add buttons to the table
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
    
    private void addListeners() {
    	hudStage.addListener(new InputListener() {
		    @Override
			public boolean touchDown (InputEvent  event, float x, float y, int pointer, int button) {                   
		    	if(hudStage.hit(x, y, true) == null) { // Si no pulso algún actor del hud
		    		Vector2 aux = stage.screenToStageCoordinates(new Vector2(x,h-y));
		    		mainpj.doBasicAttack(aux);
		    	}
		        return true;
		    } } );
    }
    
    private void createLeft() {
    	//Controlador de dirección
    	control.setPosition(0, 0);
    	//Barras de vida y MP
    	healthBar.setPosition(w*0.03f, h-h*0.06f);
    	manaBar.setPosition(w*0.03f, h-h*0.1f);
    	
    	System.out.println(w*0.03f + " x " + (h-h*0.1f));
    	
        table.right();
    }
    
    private void createRight() {
    	control.setPosition(w*0.05f, h*0.95f);
    }
    
    public void resize(int width, int height) {
        this.h = height;
        this.w = width;
        hudStage.setViewport( width, height, true );
        //this.healthBar.resize(width, height);
    	/*if(left){
    		createLeft();
    	} else {
    		createRight();
    	}*/
    }
    
    public void dispose() {
    	healthBar.dispose();
    	manaBar.dispose();
    	control.dispose();
    	hudStage.dispose();
    	//pauseMenu.dispose();
    }
    
    public boolean getLeft(){
    	return left;
    }
    
    public void setLeft(boolean left){
    	this.left = left;
    }
    
    public static void updateHealthBar(float health){
    	healthBar.updateValue(health);
    }
    
    public static void updateManaBar(float mana){
    	manaBar.updateValue(mana);
    }
    
    public static void updateLevel(int lvl) {
    	menuButton.setText(String.valueOf(lvl));
    }
    
    public void drawStage(float delta) {
    	if(!game.isPaused()) hudStage.act(delta);
    	hudStage.draw();
    }
    
    public Stage getStage(){
    	return hudStage;
    }
}

