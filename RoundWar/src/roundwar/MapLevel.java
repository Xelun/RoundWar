package roundwar;

import java.util.LinkedList;
import java.util.List;

import Entities.LivingEntity;


public class MapLevel {
	// Calcular el nivel maximo y minimo de los bichos que aparecen
	// Spawneo de bichos al comienzo del mapa
	// Spawneo dinámico de bichos durante el mapa
	// Tipos de bichos que pueden aparecer en este mapa
	// Bichos que hay spawneados?
	// Cantidad de bichos que has matado?
	// Numero de oleada en la que estas?
	
	private int level;
	private String path, nameLevel;
	private List<LivingEntity.Type> monstersSpawn;
	
	
	MapLevel(int level, String nameLevel) {
		this.level = level;
		this.nameLevel = nameLevel;
		this.path = "background/map" + nameLevel;
		monstersSpawn = new LinkedList<>();
		
	}
	
	public void initializeMonsters(){
		if(nameLevel == "Prueba") {
			
		}
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public String getPath() {
		return path;
	}
	
}
