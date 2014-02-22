package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

public abstract class Entity {
	//Atributos de imagen
	protected Texture tentity;
    protected TextureRegion entity;
    protected Circle cirEntity;
    protected String path;
    protected int size;
    
    //Atributos comunes a todos los tipos
    public String name;
    public float maxMp;
    protected float mp;
    public float recoverymp;
    
    //Atributos únicos según el tipo
    public int statAtq, statHp, statVel, statDef;
    public float maxHealth;
    protected float health;
    
    //Tipos
    public enum Type {PIRKO, ENEMY1, ENEMY2}
    
    public Entity(Type type, String name) {
    	maxMp = 100;
    	mp = 100;
    	recoverymp = 0.07f;
    	switch (type){
    		case PIRKO:
    			inicialize("Pirko", "sprite/pirko.png", 128, 10, 10, 10, 10, 100);
    			break;
    		case ENEMY1:
    			inicialize("Enemy1", "sprite/enemy.png", 128, 10, 10, 10, 10, 100);
    			break;
    		default:
    			inicialize("Enemy2", "sprite/enemy.png", 128, 10, 10, 10, 10, 100);
    			break;
    	}
    	tentity = new Texture(Gdx.files.internal(path));
    	entity = new TextureRegion(tentity, size, size);
    	cirEntity = new Circle();
    	cirEntity.setRadius(size/2);
    }
	
    private void inicialize(String name, String path, int size, int statAtq, int statHp, int statVel, int statDef, 
    		float health) {
    	this.name = name;
    	this.path = path;
    	this.size = size;
    	this.statAtq = statAtq;
    	this.statDef = statDef;
    	this.statHp = statHp;
    	this.statVel = statVel;
    	this.maxHealth = health;
    	this.health = health;
    	
    }
    
    public void dispose(){
    	tentity.dispose();
    }
	
    public void draw(SpriteBatch batch){
        batch.draw(entity, cirEntity.x, cirEntity.y);
    }
    
    public void move(float x, float y) {
    	cirEntity.x = x - cirEntity.radius;
    	cirEntity.y = y - cirEntity.radius;
    }
	
	public boolean isCollision(Entity entity){
		return this.cirEntity.overlaps(entity.cirEntity);
	}
    
	public void actHealth(float act){
		health += act;
		if(health < 0) health = 0;
		else if (health > maxHealth) health = maxHealth;
	}
	
    // Métodos get y set
    public float getHealth(){
    	return health;
    }
    
    public void setHealth(float health) {
    	if(health >= this.maxHealth){
    		this.health = maxHealth;
    	} else {
    		this.health = health;
    	}
    }
    
    public float getMp(){
    	return mp;
    }
    
    public void setMp(float mp) {
    	if(mp >= this.maxMp){
    		this.mp = maxMp;
    	} else {
    		this.mp = mp;
    	}
    }
    /*public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public String getPath() {
    	return path;
    }
    
    public void setPath(String path) {
    	this.path = path;
    }
    
    public int getSize() {
    	return size;
    }
    
    public void setSize(int size) {
    	this.size = size;
    }
    
    public int getStatAtq() {
    	return statAtq;
    }
    
    public void setStatAtq(int statAtq) {
    	this.statAtq = statAtq;
    }
    
    public int getStatHp() {
    	return statHp;
    }
    
    public void setStatHp(int statHp) {
    	this.statHp = statHp;
    }
    
    public int getStatVel() {
    	return statVel;
    }
    
    public void setStatVel(int statVel) {
    	this.statVel = statVel;
    }
    
    public int getStatDef() {
    	return statDef;
    }
    
    public void setStatDef(int statDef) {
    	this.statDef = statDef;
    }

    public int getScore() {
    	return score;
    }
    
    public void setScore(int score) {
    	this.score = score;
    }
    
    public int getLvl() {
    	return lvl;
    }
    
    public void setLvl(int lvl) {
    	this.lvl = lvl;
    }
    
    public int getStatDef() {
    	return statDef;
    }
    
    public void setStatDef(int statDef) {
    	this.statDef = statDef;
    }
    
    public int getStatDef() {
    	return statDef;
    }
    
    public void setStatDef(int statDef) {
    	this.statDef = statDef;
    }*/
}
