package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class LivingEntity extends Entity{ 
	public enum Status {ILDE, WALK, DISAPEAR}
	
	//Animaciones y sprites
	private static final int FRAME_COLS = 4;
    private static final int FRAME_ROWS = 1;
    
    protected Animation walkAnimation, ildeAnimation, currentAnimation;
    protected TextureRegion[] walkFrames;
    protected TextureRegion ildeFrame;
	
	//Atributos comunes a todos los tipos
    public int maxMp;
    protected int mp;
    public float recoverymp;
    protected Status status;
    
    //Atributos únicos según el tipo
    public int statAtq, statHp, statVel, statDef;
    public int maxHealth;
    protected int health;
    
    //Tipos
    public enum Type {PIRKO, ENEMY1, ENEMY2}
    
    public LivingEntity(Type type, String name){
    	this(type, name, 0f, 0f, 0f);
    }
    
    public LivingEntity(Type type, String name, float rotation, float posX, float posY) {
    	status = Status.ILDE;
    	switch (type){
			case PIRKO:
				inicialiceLivingEntity(name, 64, "sprite/pirko.png", 10, 10, 10, 10, 100, rotation, posX, posY);
			default:
				inicialiceLivingEntity(name, 64, "sprite/pirko.png", 10, 10, 10, 10, 100, rotation, posX, posY);
    	}
    	
    	//Animación
    	setAnimations();
        //setStatus(Status.ILDE);
    }
	
    public void setStatus(Status status){
    	if (this.status != status){
	    	this.status = status;
	    	switch(this.status){
	    		case WALK:
	    			currentAnimation = walkAnimation;
	    			break;
	    		default:
	    			currentAnimation = ildeAnimation;
	    			break;
	    	}
    	}
    }
    
    private void setAnimations(){
    	TextureRegion[][] tmp = TextureRegion.split(entityTexture, entityTexture.getWidth() / 
    			FRAME_COLS, entityTexture.getHeight() / FRAME_ROWS);
    	walkFrames = new TextureRegion[FRAME_COLS];
    	ildeFrame = tmp[0][1];
    	
        for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[j] = tmp[0][j];
        }
    	
    	walkAnimation = new Animation(0.2f, walkFrames);
    	ildeAnimation = new Animation(2f, ildeFrame);
    	currentAnimation = ildeAnimation;
    }
    
    private void inicialiceLivingEntity(String name, int radius, String path,
    		int statAtq, int statHp, int statVel, int statDef, int health,
    		float rotation, float posX, float posY) {
    	inicialiceEntity(name, radius, "sprite/pirko.png", rotation, posX, posY);
    	this.status = Status.ILDE;
    	this.statAtq = statAtq;
    	this.statDef = statDef;
    	this.statHp = statHp;
    	this.statVel = statVel;
    	this.maxHealth = health;
    	this.health = health;
    	maxMp = 100;
    	mp = 100;
    	recoverymp = 0.07f;
    }
    
    @Override
    public void dispose(){
    	super.dispose();
    }

	public void draw(SpriteBatch batch){
		stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = currentAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, entityCircle.x, entityCircle.y, radius, radius, 
        		radius*2, radius*2, 1, 1, rotation);
    }
	
	public boolean isCollision(LivingEntity entity){
		return this.entityCircle.overlaps(entity.entityCircle);
	}
    
	public void updateHealth(float update){
		health += update;
		if(health < 0) health = 0;
		else if (health > maxHealth) health = maxHealth;
	}
	
	public void updateMana(float update){
		mp += update;
		if(mp < 0) mp = 0;
		else if (mp > maxMp) mp = maxMp;
	}
	
    // Métodos get y set
    public float getHealth(){
    	return health;
    }
    
    public void setHealth(int health) {
    	if(health >= this.maxHealth){
    		this.health = maxHealth;
    	} else {
    		this.health = health;
    	}
    }
    
    public float getMp(){
    	return mp;
    }
    
    public void setMp(int mp) {
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
