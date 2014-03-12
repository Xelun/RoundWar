package Entities;

import screenControl.GameScreen;
import screenControl.Hud;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class LivingEntity extends Entity{ 
	public enum Status {ILDE, WALK, DISAPEAR}
	protected GameScreen game;
	
	//Animaciones y sprites
	private static final int FRAME_COLS = 4;
    private static final int FRAME_ROWS = 1;
    protected boolean upleft, upright, downleft, downright;
    
    protected Animation walkAnimation, ildeAnimation, currentAnimation;
    protected TextureRegion[] walkFrames;
    protected TextureRegion ildeFrame;
    protected ShapeRenderer rectangle;
	
	//Atributos comunes a todos los tipos
    public int maxMp;
    protected int mp;
    public float recoverymp;
    protected Status status;
    protected int lvl;
    
    //Atributos únicos según el tipo
    public int statAtq, statHp, statVel, statDef;
    public int maxHealth;
    protected int health;
    
    //Tipos
    public enum Type {PIRKO, ENEMY1, ENEMY2}
    
    public LivingEntity(Type type, GameScreen game){
    	this(type, 0f, 0f, 0f, game);
    }
    
    public LivingEntity(Type type, float rotation, float posX, float posY, GameScreen game) {
    	this.game = game;
    	lvl = 0;
    	status = Status.ILDE;
    	switch (type){
			case PIRKO:
				initializeLivingEntity(60, 1f, "sprite/pirko.png", 10, 10, 2, 10, 100, rotation, posX, posY);
				break;
			default:
				initializeLivingEntity(64, 1f, "sprite/enemy.png", 10, 10, 2, 10, 100, rotation, posX, posY);
    	}
    	
    	//Animación
    	setAnimations();
    }
	
    public void setLevel(int lvl) {
    	this.lvl = lvl;
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
    
    private void initializeLivingEntity(float size, float scale, String path,
    		int statAtq, int statHp, int statVel, int statDef, int health,
    		float rotation, float posX, float posY) {
    	initializeEntity(size, scale, path, rotation, posX, posY);
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
    	rectangle = new ShapeRenderer();
    	rectangle.setColor(0f, 1f, 0f, 0f);
    }
    
    protected boolean moveEntity(float deltaX, float deltaY) {
		float xL = bounds.x + deltaX*statVel;
		float xR = bounds.x + deltaX*statVel + bounds.width;
		float yU = bounds.y + deltaY*statVel + bounds.height;
		float yD = bounds.y + deltaY*statVel;
		
		boolean free = false;
		upleft = game.isFree(this, xL, yU);
		downleft = game.isFree(this, xL, yD);
		upright = game.isFree(this, xR, yU);
		downright = game.isFree(this, xR, yD);
		
		if(deltaX > 0 && (upright || downright)){ // Se mueve a la derecha
			if (upright && downright) {
				setX(xL);
				free=true;
			} else if(!upright && game.isFree(this, xR, bounds.y + bounds.height)){
				setX(xL);
				free = true;
			} else if(!downright && game.isFree(this, xR, bounds.y)){
				setX(xL);
				free = true;
			}
		} else if(deltaX < 0 && (upleft || downleft)) { // Se mueve a la izquierda
			if (upleft && downleft) {
				setX(xL);
				free=true;
			} else if(!upleft && game.isFree(this, xL, bounds.y + bounds.height)){
				setX(xL);
				free = true;
			} else if(!downleft && game.isFree(this, xL, bounds.y)){
				setX(xL);
				free = true;
			}
		}

		if(deltaY >= 0) { // Se mueve hacia arriba
			if(upleft && upright) {
				setY(yD);
				free = true;
			} else if(!upleft && game.isFree(this, bounds.x, yU)){
				setY(yD);
				free = true;
			} else if(!upright && game.isFree(this, bounds.x + bounds.width, yU)){
				setY(yD);
				free = true;
			}
		} else { //Se mueve hacia abajo
			if(downright && downleft) {
				setY(yD);
				free = true;
			} else if(!downright && game.isFree(this, bounds.x + bounds.width, yD)){
				setY(yD);
				free = true;
			} else if(!downleft && game.isFree(this, bounds.x, yD)){
				setY(yD);
				free = true;
			}
		}
		
		//System.out.println("Movido " + getRectangle().x + " x " + getRectangle().y);
		
		return free;
	}
    
    public void receiveDamage(float quantity) {
    	System.out.println(this + " ha recibido " + (int)(quantity-(statDef*0.2f)));
    	health -= (int)(quantity-(statDef*0.2f));
    	if(health <= 0) {
    		System.out.println(this + " muerto");
    		// Hacer dispose y quitar del stage y de la lista de enemigos
    	} else
    	System.out.println("Vida de " + this + " = " + health);
    }
    
    public boolean collides(float posX, float posY) {
    	return bounds.contains(posX, posY);
    }
    
    @Override
    public void dispose(){
    	super.dispose();
    }

    @Override
	public void draw(SpriteBatch batch, float parentAlpha){
        batch.draw(currentFrame, bounds.x, bounds.y, bounds.getWidth()/2, bounds.getHeight()/2, 
        		bounds.getWidth(), bounds.getHeight(), 1, 1, getRotation());
    }
	
    @Override
    public void act(float delta){
    	stateTime += delta;
    	currentFrame = currentAnimation.getKeyFrame(stateTime, true);
    }
    
	public void updateHealth(float update){
		health += update;
		if(health < 0) health = 0;
		else if (health > maxHealth) health = maxHealth;
		Hud.actHealthBar(health);
	}
	
	public void updateMana(float update){
		mp += update;
		if(mp < 0) mp = 0;
		else if (mp > maxMp) mp = maxMp;
		Hud.actManaBar(mp);
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
