package ProfileSettings;

import Entities.LivingEntity;
import Entities.MainCharacter;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

public class CharacterProfile implements Serializable {
	private int id, experience, maxMp, lvl;
	private float statAtq, statDef, statHp, statVel, recoveryMp;
	private MainCharacter.Experience upExp;
	private LivingEntity.Type type;
	private String name;
	
	public CharacterProfile() {}
	
	public CharacterProfile(LivingEntity.Type type) {
		id = Profile.getNextId();
		lvl = 1;
		experience = 0;
		recoveryMp = 0.05f;
		this.type = type;
		switch(this.type) {
			case PIRKO:
				statAtq = 1;
		    	statDef = 1;
		    	statHp  = 30;
		    	statVel = 1;
		    	maxMp   = 100;
		    	upExp = MainCharacter.Experience.LOW;
		    	name = "Pirko";
				break;
			case GULLA:
				statAtq = 1;
		    	statDef = 1;
		    	statHp  = 30;
		    	statVel = 1;
		    	maxMp   = 100;
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

	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public float getStatAtq() {
		return statAtq;
	}

	public void setStatAtq(float statAtq) {
		this.statAtq = statAtq;
	}

	public float getStatDef() {
		return statDef;
	}

	public void setStatDef(float statDef) {
		this.statDef = statDef;
	}

	public float getStatHp() {
		return statHp;
	}

	public void setStatHp(float statHp) {
		this.statHp = statHp;
	}

	public float getStatVel() {
		return statVel;
	}

	public void setStatVel(float statVel) {
		this.statVel = statVel;
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

	@Override
	public void write(Json json) {
		//json.writeObjectStart(String.valueOf(id));
		json.writeValue( "characterId", id );
		json.writeValue( "experience", experience );
		json.writeValue( "maxMp", maxMp );
		json.writeValue( "characterLvl", lvl );
		json.writeValue( "statAtq", statAtq );
		json.writeValue( "statDef", statDef );
		json.writeValue( "statHp", statHp );
		json.writeValue( "statVel", statVel );
		json.writeValue( "recoveryMp", recoveryMp );
		json.writeValue( "upExp", upExp );
		json.writeValue( "type", type );
		json.writeValue( "characterName", name );
		//json.writeObjectEnd();
	}

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
		upExp = json.readValue( "upExp", MainCharacter.Experience.class, jsonData );
		type = json.readValue( "type", LivingEntity.Type.class, jsonData );
		name = json.readValue( "characterName", String.class, jsonData );
	}
}
