package ProfileSettings;

import Entities.LivingEntity;
import Entities.MainCharacter;
import PopUps.StatChangePopUp;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

public class CharacterProfile implements Serializable {
	private int id, experience, maxMp, lvl, leftPoints;
	private float statAtq, statDef, statHp, statVel, recoveryMp;
	private boolean atq1, atq2, atq3, atq4;
	private MainCharacter.Experience upExp;
	private LivingEntity.Type type;
	private String name;
	
	public CharacterProfile(){}
	
	/**
	 * Crea una nueva entidad según su tipo.
	 * @param type
	 */
	public CharacterProfile(LivingEntity.Type type) {
		id = Profile.getNextId();
		lvl = 1;
		experience = 0;
		recoveryMp = 0.05f;
		this.type = type;
		leftPoints = 0;
		leftPoints = setDefaultStats();
		atq1 = true;
		atq2 = atq3 = atq4 = false;
	}
	
	/**
	 * Pone las estadísticas al nivel base de cada tipo de entidad.
	 * @return devuelve los puntos que tiene el jugador para aumentar sus estadísticas.
	 */
	public int setDefaultStats() {
		if(lvl > 1) leftPoints = leftPoints + (int) (statAtq + statDef + statHp/10 + statVel + maxMp/10);
		switch(this.type) {
			case PIRKO:
				statAtq = 1;
		    	statDef = 1;
		    	statHp  = 50;
		    	statVel = 1;
		    	maxMp   = 100;
		    	upExp = MainCharacter.Experience.LOW;
		    	name = "Pirko";
				break;
			case GULLA:
				statAtq = 1;
		    	statDef = 1;
		    	statHp  = 100;
		    	statVel = 1;
		    	maxMp   = 50;
		    	upExp = MainCharacter.Experience.MEDIUM;
		    	name = "Gulla";
			default:
				statAtq = 1;
		    	statDef = 1;
		    	statHp  = 30;
		    	statVel = 1;
		    	maxMp   = 100;
		    	upExp = MainCharacter.Experience.MEDIUM;
		    	name = "Cosa";
		}
		if(lvl > 1) leftPoints = leftPoints - (int) (statAtq + statDef + statHp/10 + statVel + maxMp/10);
		return leftPoints;
	}
	
	// Methods get / set
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setExperience(int experience) {
		this.experience = experience;
	}
	
	public int getExperience() {
		return experience;
	}
	
	public void setRecoveryMp(float recoveryMp) {
		this.recoveryMp = recoveryMp;
	}
	
	public float getRecoveryMp() {
		return recoveryMp;
	}

	public int getMaxMp() {
		return maxMp;
	}

	public void setMaxMp(int maxMp) {
		this.maxMp = maxMp;
	}
	
	public void updateMaxMp() {
		this.maxMp += 10;
	}

	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public void updateLvl() {
		this.lvl ++;
	}
	
	public float getStatAtq() {
		return statAtq;
	}

	public void setStatAtq(float statAtq) {
		this.statAtq = statAtq;
	}

	public void updateStatAtq() {
		this.statAtq ++;
	}

	public float getStatDef() {
		return statDef;
	}

	public void setStatDef(float statDef) {
		this.statDef = statDef;
	}

	public void updateStatDef() {
		this.statDef ++;
	}
	
	public float getStatHp() {
		return statHp;
	}

	public void setStatHp(float statHp) {
		this.statHp = statHp;
	}

	public void updateStatHp() {
		this.statHp += 10;
	}
	
	public float getStatVel() {
		return statVel;
	}

	public void setStatVel(float statVel) {
		this.statVel = statVel;
	}

	public void updateStatVel() {
		this.statVel ++;
	}
	
	public MainCharacter.Experience getUpExp() {
		return upExp;
	}

	public void setUpExp(MainCharacter.Experience upExp) {
		this.upExp = upExp;
	}

	public LivingEntity.Type getType() {
		return type;
	}

	public void setType(LivingEntity.Type type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLeftPoints() {
		return leftPoints;
	}

	public void setLeftPoints(int leftPoints) {
		this.leftPoints = leftPoints;
		StatChangePopUp.leftPoints.setText(String.valueOf(leftPoints));
	}
	
	public void updateLeftPoints(int leftPoints) {
		this.leftPoints += leftPoints;
		if(this.leftPoints < 0) this.leftPoints = 0;
	}

	public boolean isAtq1() {
		return atq1;
	}

	public void setAtq1(boolean atq1) {
		this.atq1 = atq1;
	}

	public boolean isAtq2() {
		return atq2;
	}

	public void setAtq2(boolean atq2) {
		this.atq2 = atq2;
	}

	public boolean isAtq3() {
		return atq3;
	}

	public void setAtq3(boolean atq3) {
		this.atq3 = atq3;
	}

	public boolean isAtq4() {
		return atq4;
	}

	public void setAtq4(boolean atq4) {
		this.atq4 = atq4;
	}

	/**
	 * Guarda la entidad en fichero.
	 */
	@Override
	public void write(Json json) {
		json.writeValue( "characterId", id );
		json.writeValue( "experience", experience );
		json.writeValue( "maxMp", maxMp );
		json.writeValue( "characterLvl", lvl );
		json.writeValue( "statAtq", statAtq );
		json.writeValue( "statDef", statDef );
		json.writeValue( "statHp", statHp );
		json.writeValue( "statVel", statVel );
		json.writeValue( "recoveryMp", recoveryMp );
		json.writeValue( "leftPoints", leftPoints);
		json.writeValue( "upExp", upExp );
		json.writeValue( "type", type );
		json.writeValue( "characterName", name );
		json.writeValue( "atq1", atq1 );
		json.writeValue( "atq2", atq2 );
		json.writeValue( "atq3", atq3 );
		json.writeValue( "atq4", atq4 );
	}

	/**
	 * Lee la entidad de fichero.
	 */
	@Override
	public void read(Json json, JsonValue jsonData) {
		id = json.readValue( "characterId", Integer.class, jsonData );
		experience = json.readValue( "experience", Integer.class, jsonData );
		maxMp = json.readValue( "maxMp", Integer.class, jsonData );
		lvl = json.readValue( "characterLvl", Integer.class, jsonData );
		statAtq = json.readValue( "statAtq", Integer.class, jsonData );
		statDef = json.readValue( "statDef", Integer.class, jsonData );
		statHp = json.readValue( "statHp", Integer.class, jsonData );
		statVel = json.readValue( "statVel", Integer.class, jsonData );
		recoveryMp = json.readValue( "recoveryMp", Integer.class, jsonData );
		leftPoints = json.readValue( "leftPoints", Integer.class, jsonData );
		upExp = json.readValue( "upExp", MainCharacter.Experience.class, jsonData );
		type = json.readValue( "type", LivingEntity.Type.class, jsonData );
		name = json.readValue( "characterName", String.class, jsonData );
		atq1 = json.readValue( "atq1", Boolean.class, jsonData );
		atq2 = json.readValue( "atq1", Boolean.class, jsonData );
		atq3 = json.readValue( "atq1", Boolean.class, jsonData );
		atq4 = json.readValue( "atq1", Boolean.class, jsonData );
	}
}
