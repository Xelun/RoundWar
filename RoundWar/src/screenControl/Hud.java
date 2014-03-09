package screenControl;

import roundwar.HealthBar;
import roundwar.ManaBar;
import roundwar.RoundWar;
import Attacks.RangeAttack;
import Entities.MainCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Hud {
	public enum Attack {NONE, NEAR, RUN, FAR, INAREA, NORMAL, COLLISION};
	
	private GameScreen screen;
	private Stage hudStage;
	private Table table;
	private Skin skin;
	private boolean left;
	private int h, w;
	
	private TextButton nearAttackButton;
	private TextButton runAttackButton;
	private TextButton farAttackButton;
	private TextButton inAreaAttackButton;
	private TextButton menuButton;
	private Controller control;
	
	private Attack attack;
	
	private MainCharacter mainpj;
	private HealthBar healthBar;
	private ManaBar manaBar;
	private RangeAttack range;

    public Hud(GameScreen screen, boolean left, MainCharacter mainpj) {
    	h = Gdx.graphics.getHeight();
    	w = Gdx.graphics.getWidth();
    	this.mainpj = mainpj;
    	
    	this.screen = screen;
    	this.left = left;
    	this.attack = Attack.NONE;
    	this.skin = this.screen.getSkin();

    	initializeStage(screen.getStage().getSpriteBatch());
    	initializeTable();
    	
    	if(left){
    		createLeft();
    	} else {
    		createRight();
    	}
    }
    
    private void initializeStage(SpriteBatch batch) {
    	// Inicialize stage
    	hudStage = new Stage(0, 0, true, batch);
    	Gdx.input.setInputProcessor(this.hudStage);
    	
    	//Inicialize actors
    	table = screen.getTable();
    	control = new Controller(mainpj);
    	healthBar = new HealthBar(mainpj);
    	manaBar = new ManaBar(mainpj);
    	range = new RangeAttack(mainpj);
    	
    	//Add actors to the stage
    	hudStage.addActor(range);
    	hudStage.addActor(mainpj);
    	hudStage.addActor(table);
    	hudStage.addActor(control);
    	hudStage.addActor(healthBar);
    	hudStage.addActor(manaBar);
    	
    	System.out.println("Hud: " + hudStage.getActors());
    }
    
    private void initializeTable() {
    	// Inicialize buttons
		nearAttackButton = new TextButton("N", skin);
		runAttackButton = new TextButton("R", skin); 
		farAttackButton = new TextButton("F", skin); 
		inAreaAttackButton = new TextButton("I", skin); 
		menuButton = new TextButton("M", skin);
    	
        // Add listeners to buttons
        nearAttackButton.addListener(new InputListener() {
		    @Override
			public boolean touchDown (InputEvent  event, float x, float y, int pointer, int button) {                   
		    	Gdx.app.log( RoundWar.LOG, "Pulsado botón near Attack" );
		    	if(attack != Attack.NEAR) {
		    		attack = Attack.NEAR;
		    		range.setRadius(Attack.NEAR);
		    		range.setVisible(true);
		    	} else {
		    		attack = Attack.NONE;
		    		range.setVisible(false);
		    	}
		        return false;
		    } } ); 
		
        runAttackButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	Gdx.app.log( RoundWar.LOG, "Pulsado botón run Attack" ); 
		    	if(attack != Attack.RUN) {
		    		attack = Attack.RUN;
		    		range.setRadius(Attack.RUN);
		    		range.setVisible(true);
		    	} else {
		    		attack = Attack.NONE;
		    		range.setVisible(false);
		    	}
		        return false;
		    } } ); 
		
        farAttackButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	Gdx.app.log( RoundWar.LOG, "Pulsado botón far Attack" );
		    	if(attack != Attack.FAR) {
		    		attack = Attack.FAR;
		    		range.setRadius(Attack.FAR);
		    		range.setVisible(true);
		    	} else {
		    		attack = Attack.NONE;
		    		range.setVisible(false);
		    	}
		        return false;
		    } 
		} ); 
		
        inAreaAttackButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	Gdx.app.log( RoundWar.LOG, "Pulsado botón in area Attack" );
		    	if(attack != Attack.INAREA) {
		    		attack = Attack.INAREA;
		    		range.setRadius(Attack.INAREA);
		    		range.setVisible(true);
		    	} else {
		    		attack = Attack.NONE;
		    		range.setVisible(false);
		    	}
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
    
    private void createLeft() {
    	//Controlador de dirección
    	control.setPosition(0, 0);
    	//Barras de vida y MP
    	healthBar.setPosition(w*0.03f, h-h*0.06f);
    	manaBar.setPosition(w*0.03f, h-h*0.1f);
    	
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
    	range.dispose();
    	hudStage.dispose();
    }
    
    public boolean getLeft(){
    	return left;
    }
    
    public void setLeft(boolean left){
    	this.left = left;
    }
    
    public void actHealthBar(float health){
    	healthBar.updateValue(health);
    }
    
    public void actManaBar(float mana){
    	manaBar.updateValue(mana);
    }
    
    public void drawStage(float delta) {
    	hudStage.act(delta);
    	hudStage.draw();
    }
    
    public Stage getStage(){
    	return hudStage;
    }
}

