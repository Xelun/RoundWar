package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

public abstract class LivingEntity extends Entity{ 
	//Animaciones y sprites
	private static final int FRAME_COLS = 4;
    private static final int FRAME_ROWS = 1;
    
    protected Animation walkAnimation;
    protected TextureRegion[] walkFrames;
    protected SpriteBatch batch;
	
	//Atributos comunes a todos los tipos
    public float maxMp;
    protected float mp;
    public float recoverymp;
    
    //Atributos únicos según el tipo
    public int statAtq, statHp, statVel, statDef;
    public float maxHealth;
    protected float health;
    
    //Tipos
    public enum Type {PIRKO, ENEMY1, ENEMY2}
    
    public LivingEntity(Type type, String name) {
    	super(name);
    	
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
    	entityCircle = new Circle();
    	entityCircle.setRadius(size/2);
    	entityTexture = new Texture(Gdx.files.internal(path));
    	
    	//Animación
    	TextureRegion[][] tmp = TextureRegion.split(entityTexture, entityTexture.getWidth() / 
    			FRAME_COLS, entityTexture.getHeight() / FRAME_ROWS);
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
                for (int j = 0; j < FRAME_COLS; j++) {
                        walkFrames[index++] = tmp[i][j];
                }
        }
        walkAnimation = new Animation(0.1f, walkFrames);
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
    
    @Override
    public void dispose(){
    	//super.dispose();
    	entityTexture.dispose();
    }
	
    public void draw(SpriteBatch batch){
    	stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, entityCircle.x, entityCircle.y);
    }
    
    public void move(float x, float y) {
    	entityCircle.x = x - entityCircle.radius;
    	entityCircle.y = y - entityCircle.radius;
    }
	
	public boolean isCollision(LivingEntity entity){
		return this.entityCircle.overlaps(entity.entityCircle);
	}
    
	public void actHealth(float act){
		health += act;
		if(health < 0) health = 0;
		else if (health > maxHealth) health = maxHealth;
	}
	
	public void actMana(float act){
		mp += act;
		if(mp < 0) mp = 0;
		else if (mp > maxMp) mp = maxMp;
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
