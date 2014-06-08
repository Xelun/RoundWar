package ProfileSettings;

import java.util.ArrayList;

import roundwar.RoundWar;
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
	
	/**
	 * Añade un personaje.
	 * @param type
	 */
	public static void addCharacter(LivingEntity.Type type) {
		updateNextId();
		mainCharacters.add(new CharacterProfile(type));
	}
	
	/**
	 * Añade un personaje.
	 * @param charProfile
	 */
	public static void addCharacter(CharacterProfile charProfile) {
		updateNextId();
		mainCharacters.add(charProfile);
	}
	
	/**
	 * Devuelve el personaje con id dado, en caso de haberlo.
	 * @param id
	 * @return
	 */
	public static CharacterProfile getCharacter(int id) {
		return mainCharacters.get(id);
	}
	
	/**
	 * Devuelve los juegos desbloqueados.
	 */
	public static int getUnlockGames() {
		return unlockGames;
	}
	
	/**
	 * Guarda los juegos desbloqueados.
	 * @param games
	 */
	public static void setUnlockGames(int games) {
		if(games > 0 && games <= MAXGAMES)
			unlockGames = games;
	}
	
	/**
	 * Suma uno a los juegos desbloqueados.
	 */
	public static void updateUnlockGames() {
		if(unlockGames < MAXGAMES)
			unlockGames++;
	}
	
	/**
	 * Devuelve el siguiente id.
	 * @return
	 */
	public static int getNextId() {
		return nextId;
	}

	/**
	 * Actualiza el siguiente id.
	 */
	public static void updateNextId() {
		nextId++;
	}

	/**
	 * Devuelve el número de personajes desbloqueados.
	 * @return
	 */
	public static int getUnlockCharacters() {
		return unlockCharacters;
	}

	/**
	 * Devuelve si la música está activa.
	 */
	public static boolean isMusic() {
		return music;
	}

	public static void setMusic(boolean music) {
		Profile.music = music;
		if(music) RoundWar.bgMusic.play();
		else RoundWar.bgMusic.pause();
	}

	public static boolean updateMusic() {
		Profile.music = Profile.isMusic()? false : true;
		if(music) RoundWar.bgMusic.play();
		else RoundWar.bgMusic.pause();
		return Profile.music;
	}
	
	/**
	 * Devuelve si el sonido está activo.
	 * @return
	 */
	public static boolean isSound() {
		return sound;
	}

	public static void setSound(boolean sound) {
		Profile.sound = sound;
	}

	public static boolean updateSound() {
		Profile.sound = Profile.isSound()? false : true;
		return Profile.sound;
	}
	
	/**
	 * Devuelve si está activado el modo para zurdos (true) o para diestros (false).
	 * @return
	 */
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
	
	/**
	 * Guarda en fichero el perfil actual, creando uno nuevo si no existe.
	 */
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

	/**
	 * Lee de fichero el perfil.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void read(Json json, JsonValue jsonData) {
		nextId = json.readValue( "nextId", Integer.class, jsonData );
		unlockGames = json.readValue( "unlockGames", Integer.class, jsonData );
		unlockCharacters = json.readValue( "unlockCharacters", Integer.class, jsonData );
		music = json.readValue( "music", Boolean.class, jsonData );
		sound = json.readValue( "sound", Boolean.class, jsonData );
		left = json.readValue( "left", Boolean.class, jsonData );
		
		mainCharacters = json.readValue( "Characters", ArrayList.class, CharacterProfile.class, jsonData );
	}
}
