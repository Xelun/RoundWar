package Entities;

import screenControl.Hud;
import Attacks.Attack;
import Attacks.BallAttack;
import ProfileSettings.CharacterProfile;

import com.badlogic.gdx.math.Vector2;

public class MainCharacter extends LivingEntity {
	public enum Experience {LOW, MEDIUM, FAST}
	//private RangeAttack range; // Solo para ataques con área de ataque
	private int totalExp, nextLevelExp;
	private Experience upExp;
	private CharacterProfile profile;
	
	/** 
	 * Contructor.
	 * @param profile
	 */
	public MainCharacter(CharacterProfile profile){
		super(profile.getType(), profile.getLvl());
		this.profile = profile;
    	setName(profile.getName());
    	totalExp = profile.getExperience();
    	this.upExp = profile.getUpExp();

    	// Iguala sus estadísticas a las del perfil de personaje.
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
	
//	public void setRangeRadius(Hud.AttackType type) {
//		//range.setRadius(type);
//	}
//	
//	public void setRangeVisible(boolean visible) {
//		//range.setVisible(visible);
//	}
	
	/**
	 * Devuelve si está o no desbloqueado el ataque especial 1.
	 * @return
	 */
	public boolean unlockAttack1() {
		return profile.isAtq1();
	}
	
	/**
	 * Devuelve si está o no desbloqueado el ataque especial 2.
	 * @return
	 */
	public boolean unlockAttack2() {
		return profile.isAtq2();
	}
	
	/**
	 * Devuelve si está o no desbloqueado el ataque especial 3.
	 * @return
	 */
	public boolean unlockAttack3() {
		return profile.isAtq3();
	}
	
	/**
	 * Devuelve si está o no desbloqueado el ataque especial 4.
	 * @return
	 */
	public boolean unlockAttack4() {
		return profile.isAtq4();
	}
	
	/**
	 * Cambia la vida del personaje y actualiza la barra.
	 */
	@Override
	public void addHealth(float update) {
		super.addHealth(update);
		Hud.updateHealthBar(health);
	}
	
	/**
	 * Cambia el maná del personaje y actualiza la barra.
	 */
	@Override
	public void addMp(float update) {
		super.addMp(update);
		Hud.updateManaBar(mp);
	}
	
	/**
	 * Cambia la experiencia del personaje y lo sube de nivel si es el caso.
	 */
	public void updateExperience(int experience) {
		this.totalExp += experience;
		//System.out.println("Exp recibida: " + experience + " Exp total: " + totalExp + " Exp next lvl: " + nextLevelExp);
		if(totalExp >= nextLevelExp) {
			levelUp();
		}
	}
	
	/**
	 * Sube de nivel, calcula la experiencia necesaria para el siguiente,
	 * actualiza el número en la UI y aumenta el número de puntos disponibles
	 * para mejorar estadísticas.
	 */
	private void levelUp() {
		profile.updateLvl();
		lvl++;
		calculateExperienceNextLevel();
		Hud.updateLevel(lvl);
		profile.updateLeftPoints(1);
	}
	
	/**
	 * Calcula la experiencia necesaria para llegar al siguiente nivel según
	 * el tipo de crecimiento de la especie del personaje.
	 */
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
	
	/**
	 * Mueve o para al personaje según la rotación del touchpad.
	 */
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
	
	/**
	 * Mueve la camara si el personaje se sale del rango de visión de la cámara.
	 * @param deltaX
	 * @param deltaY
	 */
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
	
	/**
	 * Hace el ataque básico hacia la posición dada.
	 * @param finalPos
	 */
	public void doBasicAttack(Vector2 finalPos) {
		if(mp >= Attack.Type.BASIC.getCost()) {
			game.attacks.add(new BallAttack(this, finalPos, Attack.Type.BASIC));
			addMp(Attack.Type.BASIC.getCost());
		}
	}
	
	/**
	 * Hace el ataque especial 1.
	 * @param finalPos
	 */
	public void doAttack1() {
		if(mp >= Attack.Type.ARROW.getCost()) {
			game.attacks.add(new BallAttack(this, 500, 200, Attack.Type.ARROW));
			addMp(-Attack.Type.ARROW.getCost());
		}
	}
	
	/**
	 * Hace el ataque especial 2.
	 * @param finalPos
	 */
	public void doAttack2() {
		
	}
	
	/**
	 * Hace el ataque especial 3.
	 * @param finalPos
	 */
	public void doAttack3() {
		
	}
	
	/**
	 * Hace el ataque especial 4.
	 * @param finalPos
	 */
	public void doAttack4() {
		
	}
	
	/**
	 * Guarda los datos en el perfil.
	 */
	public void save() {
		profile.setExperience(totalExp);
		profile.setLvl(lvl);
//		profile.setMaxMp(maxMp);
//		profile.setRecoveryMp(recoveryMp);
//		profile.setStatAtq(statAtq);
//		profile.setStatDef(statDef);
//		profile.setStatHp(statHp);
//		profile.setStatVel(statVel);
		profile.setUpExp(upExp);
	}

	/**
	 * Muere el personaje y pierde la partida.
	 */
	@Override
	public void dead(LivingEntity killer) {
		game.loseGame();
		super.dead(killer);
	}
	
	/**
	 * Guarda los datos.
	 */
	@Override
	public void dispose() {
		save();
		//range.dispose();
		super.dispose();
	}
}
