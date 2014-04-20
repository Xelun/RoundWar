package Entities;

import screenControl.Hud;
import ProfileSettings.CharacterProfile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MainCharacter extends LivingEntity {
	public enum Experience {LOW, MEDIUM, FAST}
	//private RangeAttack range;
	private int totalExp, nextLevelExp;
	private Experience upExp;
	private CharacterProfile profile;
	
	public MainCharacter(CharacterProfile profile){
		super(profile.getType(), profile.getLvl());
		this.profile = profile;
    	setName(profile.getName());
    	totalExp = profile.getExperience();
    	this.upExp = profile.getUpExp();

    	statAtq = profile.getStatAtq();
    	statDef = profile.getStatDef();
    	statHp  = profile.getStatHp();
    	statVel = profile.getStatVel();
    	health  = profile.getStatHp();
    	maxMp   = profile.getMaxMp();
    	mp 		= profile.getMaxMp();
    	recoveryMp = profile.getRecoveryMp();
    	
		//range = new RangeAttack(this);
	}
	
	public void setRangeRadius(Hud.AttackType type) {
		//range.setRadius(type);
	}
	
	public void setRangeVisible(boolean visible) {
		//range.setVisible(visible);
	}
	
	@Override
	public void addHealth(float update) {
		super.addHealth(update);
		Hud.updateHealthBar(health);
	}
	
	@Override
	public void addMp(float update) {
		super.addMp(update);
		Hud.updateManaBar(mp);
	}
	
	public void updateExperience(int experience) {
		this.totalExp += experience;
		System.out.println("Exp recibida: " + experience + " Exp total: " + totalExp + " Exp next lvl: " + nextLevelExp);
		if(totalExp >= nextLevelExp) {
			lvl ++;
			calculateExperienceNextLevel();
			Hud.updateLevel(lvl);
			System.out.println("Estás a nivel " + lvl);
		}
	}
	
	private void calculateExperienceNextLevel() {
		switch(upExp) {
			case LOW:
				nextLevelExp = (int) (0.8*Math.pow(lvl,3));
				break;
			case MEDIUM:
				nextLevelExp = (int) (Math.pow(lvl,3));
				break;
			case FAST:
				nextLevelExp = (int) (1.25*Math.pow(lvl,3));
				break;
		}
	}
	
	@Override
	public boolean moveEntity (float deltaX, float deltaY, boolean rotate){
		if((deltaX != 0 || deltaY != 0)) { 			//Si hay movimiento
			if(rotate) setRotation((float) Math.atan2(deltaY, deltaX)*57.3f); //Rota hacia donde apunte el controlador
			if(super.moveEntity(deltaX, deltaY, rotate)) { 	//Si se puede mover porque no hay colision
				moveCamera(deltaX, deltaY);			//Mueve la cámara si ha llegado al límite de vision
				return true;
			}
		} else {
			setStatus(Status.ILDE);
		}
		return false;
	}
	
	private void moveCamera(float deltaX, float deltaY) {
		//setStatus(Status.WALK); //Pone la animación de andar
		float movX = deltaX*statVel;
		float movY = deltaY*statVel;
		Vector2 maxLimit = game.getMinLimit();
		Vector2 minLimit = game.getMaxLimit();
		
		if(bounds.x < maxLimit.x) { 			// Supera el máximo en el eje x
			getStage().getCamera().translate(movX, 0, 0);
		} else if (bounds.x > minLimit.x) { 	// Supera el mínimo en el eje x
			getStage().getCamera().translate(movX, 0, 0);
		}
		
		if (bounds.y < maxLimit.y) { 			// Supera el máximo en el eje y
			getStage().getCamera().translate(0, movY, 0);
		} else if (bounds.y > minLimit.y) {		// Supera el mínimo en el eje y
			getStage().getCamera().translate(0, movY, 0);
		}
	}
	
	private void save() {
		profile.setExperience(totalExp);
		profile.setLvl(lvl);
		profile.setMaxMp(maxMp);
		profile.setRecoveryMp(recoveryMp);
		profile.setStatAtq(statAtq);
		profile.setStatDef(statDef);
		profile.setStatHp(statHp);
		profile.setStatVel(statVel);
		profile.setUpExp(upExp);
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		//range.draw(batch, parentAlpha);
		super.draw(batch, parentAlpha);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		//range.act(delta);
	}
	
	@Override
	public void dispose() {
		save();
		//range.dispose();
		super.dispose();
	}
}
