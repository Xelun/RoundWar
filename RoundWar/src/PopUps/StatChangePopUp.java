/*******************************************************************************
 * Copyright (c) 2014
 *
 * @author Elisabet Romero Vaquero
 *******************************************************************************/
package PopUps;

import roundwar.RoundWar;
import screenControl.AbstractScreen;
import screenControl.GameScreen;
import screenControl.Hud;
import ProfileSettings.CharacterProfile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class StatChangePopUp extends PopUp {
	private Drawable up, down;
	public static Label leftPoints, atq, def, hp, mp, vel;
	private static CharacterProfile cprofile;
	private ImageButton[] upButtons;
	private TextButton resetButton, saveButton;
	
	/**
	 * Constructor.
	 * @param batch
	 */
	public StatChangePopUp(SpriteBatch batch) {
		super(batch);
		createButtons(cprofile);
		initializeStatTable();
	}
	
	/**
	 * Actualiza la tabla y la muestra.
	 */
	@Override
	public void show() {
		updateTable();
		super.show();
	}
	
	/**
	 * Actualiza los valores de la tabla.
	 */
	private void updateTable() {
		leftPoints.setText(String.valueOf(cprofile.getLeftPoints()));
		setUpButtons(cprofile.getLeftPoints() <= 0);
		atq.setText(String.valueOf(cprofile.getStatAtq()));
		def.setText(String.valueOf(cprofile.getStatDef()));
		vel.setText(String.valueOf(cprofile.getStatVel()));
		 hp.setText(String.valueOf(cprofile.getStatHp()));
		 mp.setText(String.valueOf(cprofile.getMaxMp()));
	}
	
	/**
	 * Le pasa el perfil de personaje de donde obtener la información.
	 * @param profile
	 */
	public static void setCharacterProfile(CharacterProfile profile) {
		cprofile = profile;
		leftPoints = new Label(String.valueOf(cprofile.getLeftPoints()), AbstractScreen.getSkin());
	}
	
	/**
	 * Crea los botones.
	 * @param cprofile
	 */
	private void createButtons(final CharacterProfile cprofile) {
		// Etiqueta con los puntos que quedan por gastar
		leftPoints.setText(String.valueOf(cprofile.getLeftPoints()));
		
		// Etiquetas de valor de las estadísticas
		atq = new Label(String.valueOf(cprofile.getStatAtq()), AbstractScreen.getSkin());
		def = new Label(String.valueOf(cprofile.getStatDef()), AbstractScreen.getSkin());
		vel = new Label(String.valueOf(cprofile.getStatVel()), AbstractScreen.getSkin());
		hp  = new Label(String.valueOf(cprofile.getStatHp()), AbstractScreen.getSkin());
		mp  = new Label(String.valueOf(cprofile.getMaxMp()), AbstractScreen.getSkin());
		
		// Botones para subida de las estadísticas
		upButtons  = new ImageButton[5];
    	up = AbstractScreen.getSkin().getDrawable("arrow");
    	down = AbstractScreen.getSkin().getDrawable("arrow-disabled");
		for(int i = 0; i < upButtons.length; i++) {
	    	upButtons[i] = new ImageButton(up, down, down);
	    	if(cprofile.getLeftPoints() <= 0) upButtons[i].setChecked(true);
		}
		
		// Botones de guardado y reseteo de estadísticas
		saveButton = new TextButton("Save", AbstractScreen.getSkin(), "go");
		resetButton = new TextButton("Reset stats", AbstractScreen.getSkin(), "danger");
		
		// Añadir eventos
		saveButton.addListener(new InputListener() { 
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
		    	close();
		        return false;
		    } 
		} );
		
		resetButton.addListener(new InputListener() { 
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { 
				// Pone los valores por defecto de las estadísticas de la raza del personaje
				cprofile.setDefaultStats();
				
				// Se actualiza los valores de la tabla de estadísticas
		    	updateTable();
		    	
		    	// Se reinician las barras de vida y mana
		    	Hud.setMaxValueHp(cprofile.getStatHp());
		    	Hud.setMaxValueMp(cprofile.getMaxMp());
		    	
		        return false;
		    } 
		} );
		
		// Sube la estadística de ataque
		upButtons[0].addListener(new InputListener() {
		    @Override
			public boolean touchDown (InputEvent  event, float x, float y, int pointer, int button) {     
		    	if(cprofile.getLeftPoints()>0) {
			    	cprofile.updateLeftPoints(-1);
			    	upButtons[0].setChecked(!upButtons[0].isChecked());
			    	if(cprofile.getLeftPoints()<=0) setUpButtons(true);
			    	cprofile.updateStatAtq();
			    	atq.setText(String.valueOf(cprofile.getStatAtq()));
			    	leftPoints.setText(String.valueOf(cprofile.getLeftPoints()));
		    	} else upButtons[0].setChecked(true);
		        return false;
		    } } );
		
		// Sube la estadística de defensa
		upButtons[1].addListener(new InputListener() {
		    @Override
			public boolean touchDown (InputEvent  event, float x, float y, int pointer, int button) {     
		    	if(cprofile.getLeftPoints()>0) {
			    	cprofile.updateLeftPoints(-1);
			    	upButtons[1].setChecked(!upButtons[1].isChecked());
			    	if(cprofile.getLeftPoints()<=0) setUpButtons(true);
			    	cprofile.updateStatDef();
			    	def.setText(String.valueOf(cprofile.getStatDef()));
			    	leftPoints.setText(String.valueOf(cprofile.getLeftPoints()));
		    	} else upButtons[1].setChecked(!upButtons[1].isChecked());
		        return false;
		    } } );
				
		// Sube la estadística de vida
		upButtons[2].addListener(new InputListener() {
		    @Override
			public boolean touchDown (InputEvent  event, float x, float y, int pointer, int button) {     
		    	if(cprofile.getLeftPoints()>0) {
			    	cprofile.updateLeftPoints(-1);
			    	upButtons[2].setChecked(!upButtons[2].isChecked());
			    	if(cprofile.getLeftPoints()<=0) setUpButtons(true);
			    	cprofile.updateStatVel();
			    	vel.setText(String.valueOf(cprofile.getStatVel()));
			    	leftPoints.setText(String.valueOf(cprofile.getLeftPoints()));
		    	} else upButtons[2].setChecked(!upButtons[2].isChecked());
		        return false;
		    } } );
				
		// Sube la estadística de maná
		upButtons[3].addListener(new InputListener() {
		    @Override
			public boolean touchDown (InputEvent  event, float x, float y, int pointer, int button) {     
		    	if(cprofile.getLeftPoints()>0) {
			    	cprofile.updateLeftPoints(-1);
			    	upButtons[3].setChecked(!upButtons[3].isChecked());
			    	if(cprofile.getLeftPoints()<=0) setUpButtons(true);
			    	cprofile.updateStatHp();
			    	Hud.setMaxValueHp(cprofile.getStatHp());
			    	hp.setText(String.valueOf(cprofile.getStatHp()));
			    	leftPoints.setText(String.valueOf(cprofile.getLeftPoints()));
		    	} else upButtons[3].setChecked(!upButtons[3].isChecked());
		        return false;
		    } } );
				
		// Sube la estadística de velocidad
		upButtons[4].addListener(new InputListener() {
		    @Override
			public boolean touchDown (InputEvent  event, float x, float y, int pointer, int button) {     
		    	if(cprofile.getLeftPoints()>0) {
			    	cprofile.updateLeftPoints(-1);
			    	upButtons[4].setChecked(!upButtons[4].isChecked());
			    	if(cprofile.getLeftPoints()<=0) setUpButtons(true);
			    	cprofile.updateMaxMp();
			    	Hud.setMaxValueMp(cprofile.getMaxMp());
			    	mp.setText(String.valueOf(cprofile.getMaxMp()));
			    	leftPoints.setText(String.valueOf(cprofile.getLeftPoints()));
		    	} else if(cprofile.getLeftPoints() == 0){
		    		upButtons[0].setChecked(true);
		    	} else upButtons[4].setChecked(!upButtons[4].isChecked());
		        return false;
		    } } );
	}
	
	/**
	 * Pone todos los botones de subida de estadísticas a checkeados o no según se le indique.
	 * @param checked
	 */
	private void setUpButtons(boolean checked) {
		for(int i = 0; i < 5; i++) 
			upButtons[i].setChecked(checked);
	}
	
	/**
	 * Inicializa la tabla.
	 */
	protected void initializeStatTable() {
		super.initializeTable();
		
		// Initialize table
	    table.add().width(w*0.02f);
		table.add("Left Points ").colspan(5).spaceBottom(h*0.02f); 	// Puntos restantes
		table.add(leftPoints).colspan(2).spaceBottom(h*0.02f);
		table.add().width(w*0.02f);
		table.row();
		
		table.add().size(w*0.02f,0);
		table.add("Atq ").spaceBottom(h*0.02f); 					// Ataque
		table.add(atq).spaceBottom(h*0.02f);
		table.add(upButtons[0]).size(w*0.05f).spaceBottom(h*0.02f); // Boton up
		table.add().width(w*0.05f);
		table.add("Def ").spaceBottom(h*0.02f); 					// Defensa
		table.add(def).spaceBottom(h*0.02f);
		table.add(upButtons[1]).size(w*0.05f).spaceBottom(h*0.02f); // Boton up
		table.add().size(w*0.02f,0);
		table.row();
		
		table.add().size(w*0.02f,0);
		table.add("Vel ").spaceBottom(h*0.02f); 					// Velocidad
		table.add(vel).spaceBottom(h*0.02f);
		table.add(upButtons[2]).size(w*0.05f).spaceBottom(h*0.02f); // Boton up
		table.row();
		
		table.add().size(w*0.02f,0);
		table.add("HP ").spaceBottom(h*0.03f); 						// Vida
		table.add(hp).spaceBottom(h*0.03f);
		table.add(upButtons[3]).size(w*0.05f).spaceBottom(h*0.03f); // Boton up
		table.add().width(w*0.05f);
		table.add("MP ").spaceBottom(h*0.03f); 						// Maná
		table.add(mp).spaceBottom(h*0.03f);
		table.add(upButtons[4]).size(w*0.05f).spaceBottom(h*0.03f); // Boton up
		table.add().size(w*0.02f,0);
		table.row();
		
		table.add(resetButton).size(w*0.24f, h*0.1f).colspan(4); 	// Reset
		table.add().width(w*0.05f);
		table.add(saveButton).size(w*0.24f, h*0.1f).colspan(4);		// Guardar
	}
	
	/**
	 * Guarda las estadísticas y esconde el popup.
	 */
	@Override
	public void close() {
		super.close();
		((GameScreen) game.getScreen()).getCharacter().save();
		RoundWar.save();
		GamePausePopUp.getScreen().setGamePause(true);
	}
}
