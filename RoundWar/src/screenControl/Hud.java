package screenControl;

import roundwar.RoundWar;
import Bars.HealthBar;
import Bars.ManaBar;
import Entities.MainCharacter;
import ProfileSettings.Profile;

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

	/**
	 * Contructor.
	 * @param game
	 */
    public Hud(GameScreen game) {
    	h = Gdx.graphics.getHeight();
    	w = Gdx.graphics.getWidth();
    	
    	this.game = game;
    	this.stage = this.game.getStage();
    	this.mainpj = this.game.getCharacter();
    	this.skin = AbstractScreen.getSkin();
    	
    	initializeStage(game.getStage().getSpriteBatch());
    	initializeTable();
    }
    
    /**
     * Inicializa el stage del hud.
     * @param batch
     */
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
    	
    	// Initialize contoller, bars and attack buttons according to the option selected
    	if(Profile.isLeft()){
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
    
    /**
     * Inicializa la tabla, creando los botones.
     */
    private void initializeTable() {
    	// Inicialize buttons
		nearAttackButton = new TextButton("Near", skin, "menu");
		runAttackButton = new TextButton("Run", skin, "menu"); 
		farAttackButton = new TextButton("Far", skin, "menu"); 
		inAreaAttackButton = new TextButton("Area", skin, "menu"); 
		menuButton = new TextButton(String.valueOf(mainpj.getLevel()), skin, "go");
		nearAttackButton.setChecked(!mainpj.unlockAttack1());
		runAttackButton.setChecked(!mainpj.unlockAttack2());
		farAttackButton.setChecked(!mainpj.unlockAttack3());
		inAreaAttackButton.setChecked(!mainpj.unlockAttack4());
		
        // Add listeners to buttons
        nearAttackButton.addListener(new InputListener() {
		    @Override
			public boolean touchDown (InputEvent  event, float x, float y, int pointer, int button) {                   
		    	Gdx.app.log( RoundWar.LOG, "Pulsado botón near Attack" );
		    	
		    	if(!nearAttackButton.isChecked()) // Está disponible el ataque
		    		mainpj.doAttack1();
		    	
		    	nearAttackButton.setChecked(!nearAttackButton.isChecked());
		        return false;
		    } } ); 
		
        runAttackButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	Gdx.app.log( RoundWar.LOG, "Pulsado botón run Attack" ); 
		    	
		    	if(!runAttackButton.isChecked()) // Está disponible el ataque
		    		mainpj.doAttack2();
		    	
		    	runAttackButton.setChecked(!runAttackButton.isChecked());
		    	
		        return false;
		    } } ); 
		
        farAttackButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	Gdx.app.log( RoundWar.LOG, "Pulsado botón far Attack" );
		    	
		    	if(!farAttackButton.isChecked()) // Está disponible el ataque
		    		mainpj.doAttack3();
		    		
		    	farAttackButton.setChecked(!farAttackButton.isChecked());

		        return false;
		    } 
		} ); 
		
        inAreaAttackButton.addListener(new InputListener() { 
		    @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	Gdx.app.log( RoundWar.LOG, "Pulsado botón in area Attack" );
		    	
		    	if(!inAreaAttackButton.isChecked()) // Está disponible el ataque
		    		mainpj.doAttack4();
		    	
		    	inAreaAttackButton.setChecked(!inAreaAttackButton.isChecked());

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
    
    /**
     * Añade eventos de pulsación al hud.
     */
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
    
    /**
     * Alinea los botones de ataque a la derecha y coloca el controlador a la izquierda.
     */
    private void createLeft() {
    	// Controlador de dirección
    	control.setPosition(0, 0);
    	
    	// Barras de vida y MP
		healthBar.setPosition(w*0.03f, h-h*0.06f);
    	manaBar.setPosition(w*0.03f, h-h*0.1f);
    	
    	// Botones de ataque
    	table.right();
    }
    
    /**
     * Alinea los botones de ataque a la izquierda y coloca el controlador a la derecha.
     */
    private void createRight() {
    	// Controlador de dirección
    	control.setPosition(w*0.8f, 0);
    	
    	// Barras de vida y MP
    	healthBar.setPosition(w*0.13f, h-h*0.06f);
    	manaBar.setPosition(w*0.13f, h-h*0.1f);
    	
    	// Botones de ataque
		table.left();
    }
    
    /**
     * Redimensiona la pantalla.
     * @param width
     * @param height
     */
    public void resize(int width, int height) {
        this.h = height;
        this.w = width;
        hudStage.setViewport( width, height, true );
    }
    
    /**
     * Libera memoria.
     */
    public void dispose() {
    	hudStage.dispose();
    }
    
    /**
     * Actualiza la barra de vida añadiendole la cantidad indicada.
     * @param health
     */
    public static void updateHealthBar(float health){
    	healthBar.updateValue(health);
    }
    
    /**
     * Pone el valor máximo de la barra de vida.
     * @param value
     */
    public static void setMaxValueHp(float value) {
    	healthBar.setMaxValue(value);
    }
    
    /**
     * Actualiza la barra de maná añadiendole la cantidad indicada.
     * @param mana
     */
    public static void updateManaBar(float mana){
    	manaBar.updateValue(mana);
    }
    
    /**
     * Pone el valor máximo de la barra de maná.
     * @param value
     */
    public static void setMaxValueMp(float value) {
    	manaBar.setMaxValue(value);
    }
    
    /**
     * Actualiza el nivel del jugador añadiendole la cantidad indicada.
     * @param lvl
     */
    public static void updateLevel(int lvl) {
    	menuButton.setText(String.valueOf(lvl));
    }
    
    /**
     * Dibuja el stage del hud.
     * @param delta
     */
    public void drawStage(float delta) {
    	if(!game.isPaused()) hudStage.act(delta);
    	hudStage.draw();
    }
    
    /**
     * Devuelve el stage del hud.
     * @return
     */
    public Stage getStage(){
    	return hudStage;
    }
}

