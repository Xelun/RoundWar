package screenControl;

import roundwar.HealthBar;
import roundwar.ManaBar;
import roundwar.RoundWar;
import Entities.MainCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class HudControl {
	private GameScreenControl screen;
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
	private TouchControl control;
	
	private MainCharacter mainpj;
	private HealthBar healthBar;
	private ManaBar manaBar;

    public HudControl(GameScreenControl screen, boolean left, MainCharacter mainpj, SpriteBatch batch) {
    	h = Gdx.graphics.getHeight();
    	w = Gdx.graphics.getWidth();
    	
    	hudStage = new Stage( 0, 0, true, batch );
    	Gdx.input.setInputProcessor(this.hudStage);
    	control = new TouchControl(mainpj);//, hudStage);
    	
    	this.mainpj = mainpj;
    	this.screen = screen;
    	this.table = this.screen.getTable();
    	healthBar = new HealthBar(mainpj);
    	manaBar = new ManaBar(mainpj);
    	hudStage.addActor(table);
    	hudStage.addActor(healthBar);
    	hudStage.addActor(manaBar);
    	hudStage.addActor(control);
    	this.skin = this.screen.getSkin();
    	this.left = left;
    	if(left){
    		createLeft();
    	} else {
    		createRight();
    	}
    }
    
    private void createCommon() {
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
    	control.setPosition(0, 0);
    	//Barras de vida y MP
    	healthBar.setPosition(w*0.03f, h-h*0.06f);
    	manaBar.setPosition(w*0.03f, h-h*0.1f);
    	
    	//Tabla de botones de ataque
    	Gdx.app.log( RoundWar.LOG, "Creando barra" ); 
        table.right();
        createTable();
    	
    }
    
    private void createRight() {
    	createCommon();
    	
    	control.setPosition(w*0.05f, h*0.95f);
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
    	healthBar.updateValue(health);
    }
    
    public void actManaBar(float mana){
    	manaBar.updateValue(mana);
    }
    
    public void draw(SpriteBatch batch) {
    	//System.out.println("Actores del hudStage:" + hudStage.getActors().toString(", "));
    	//healthBar.draw(batch);
    	//manaBar.draw(batch);
    	//table.draw(batch, 1f);
    	//control.act();
    }
    
    public Stage getStage(){
    	return hudStage;
    }
}

