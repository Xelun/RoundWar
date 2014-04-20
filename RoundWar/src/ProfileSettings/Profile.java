package ProfileSettings;

import java.util.ArrayList;

import Entities.LivingEntity;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

public class Profile implements Serializable {
	private static int MAXGAMES = 12;
	private static int nextId, unlockGames, unlockCharacters;
	private static ArrayList<CharacterProfile> mainCharacters;
	
	public Profile() {
		mainCharacters = new ArrayList<CharacterProfile>();
		nextId = 0;
		unlockGames = 3;
		unlockCharacters = 2;
	}
	
	public static void addCharacter(LivingEntity.Type type) {
		updateNextId();
		mainCharacters.add(new CharacterProfile(type));
	}
	
	public static void addCharacter(CharacterProfile charProfile) {
		updateNextId();
		mainCharacters.add(charProfile);
	}
	
	public static CharacterProfile getCharacter(int id) {
		return mainCharacters.get(id);
	}
	
	public static int getUnlockGames() {
		return unlockGames;
	}
	
	public static void setUnlockGames(int games) {
		if(games > 0 && games <= MAXGAMES)
			unlockGames = games;
	}
	
	public static void updateUnlockGames() {
		if(unlockGames < MAXGAMES)
			unlockGames++;
	}
	
	public static int getNextId() {
		return nextId;
	}

	public static void updateNextId() {
		nextId++;
	}

	public static int getUnlockCharacters() {
		return unlockCharacters;
	}

	public static void updateUnlockCharacters() {
		unlockCharacters++;
	}

	@Override
	public void write(Json json) {
		json.writeValue( "nextId", nextId );
        json.writeValue( "unlockGames", unlockGames );
        json.writeValue( "unlockCharacters", unlockCharacters );
        json.writeArrayStart("Characters");
        for(CharacterProfile charprof : mainCharacters) {
        	json.writeValue(charprof);
        }
        json.writeArrayEnd();
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		nextId = json.readValue( "nextId", Integer.class, jsonData );
		unlockGames = json.readValue( "unlockGames", Integer.class, jsonData );
		unlockCharacters = json.readValue( "unlockCharacters", Integer.class, jsonData );

        // libgdx handles the keys of JSON formatted HashMaps as Strings, but we
        // want it to be an integer instead (levelId)
		mainCharacters = json.readValue( "Characters", ArrayList.class, CharacterProfile.class, jsonData );
	}
}
