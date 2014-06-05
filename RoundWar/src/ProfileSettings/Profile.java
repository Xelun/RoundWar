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
	private static boolean music, sound, left;
	
	public Profile() {
		mainCharacters = new ArrayList<CharacterProfile>();
		nextId = 0;
		unlockGames = 8;
		unlockCharacters = 2;
		music = true;
		sound = true;
		left = true;
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

	public static boolean isMusic() {
		return music;
	}

	public static void setMusic(boolean music) {
		Profile.music = music;
	}

	public static boolean updateMusic() {
		Profile.music = Profile.isMusic()? false : true;
		return Profile.music;
	}
	
	public static boolean isSound() {
		return sound;
	}

	public static void setSound(boolean sound) {
		Profile.sound = sound;
	}

	public static boolean updateSound() {
		Profile.sound = Profile.isSound()? false : true;
		System.out.println("Profile: " + sound);
		return Profile.sound;
	}
	
	public static boolean isLeft() {
		return left;
	}

	public static void setLeft(boolean left) {
		Profile.left = left;
	}
	
	public static boolean updateLeft() {
		Profile.left = Profile.isLeft()? false : true;
		return Profile.left;
	}

	public static void updateUnlockCharacters() {
		unlockCharacters++;
	}
	
	@Override
	public void write(Json json) {
		json.writeValue( "nextId", nextId );
        json.writeValue( "unlockGames", unlockGames );
        json.writeValue( "unlockCharacters", unlockCharacters );
        json.writeValue( "music", music );
        json.writeValue( "sound", sound );
        json.writeValue( "left", left );
        json.writeArrayStart("Characters");
        for(CharacterProfile charprof : mainCharacters) {
        	json.writeValue(charprof);
        }
        json.writeArrayEnd();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void read(Json json, JsonValue jsonData) {
		nextId = json.readValue( "nextId", Integer.class, jsonData );
		unlockGames = json.readValue( "unlockGames", Integer.class, jsonData );
		unlockCharacters = json.readValue( "unlockCharacters", Integer.class, jsonData );
		music = json.readValue( "music", Boolean.class, jsonData );
		sound = json.readValue( "sound", Boolean.class, jsonData );
		left = json.readValue( "left", Boolean.class, jsonData );
        // libgdx handles the keys of JSON formatted HashMaps as Strings, but we
        // want it to be an integer instead (levelId)
		mainCharacters = json.readValue( "Characters", ArrayList.class, CharacterProfile.class, jsonData );
	}
}
