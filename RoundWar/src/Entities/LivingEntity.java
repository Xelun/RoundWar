package Entities;

import screenControl.GameScreen;

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
		float xL = getX() + deltaX*statVel;
		float xR = getX() + deltaX*statVel + getWidth();
		float yU = getY() + deltaY*statVel + getWidth();
		float yD = getY() + deltaY*statVel;
		
		boolean collision = false;
		upleft = game.isFree(this, xL, yU);
		downleft = game.isFree(this, xL, yD);
		upright = game.isFree(this, xR, yU);
		downright = game.isFree(this, xR, yD);
		
		if(deltaX > 0 && (upright || downright)){ // Se mueve a la derecha
			if (upright && downright) {
				setX(xL);
				collision = true;
			} else if(!upright && game.isFree(this, xR, getTop())){
				setX(xL);
				collision = true;
			} else if(!downright && game.isFree(this, xR, getY())){
				setX(xL);
				collision = true;
			}
		} else if(deltaX < 0 && (upleft || downleft)) { // Se mueve a la izquierda
			if (upleft && downleft) {
				setX(xL);
				collision = true;
			} else if(!upleft && game.isFree(this, xL, this.getTop())){
				setX(xL);
				collision = true;
			} else if(!downleft && game.isFree(this, xL, getY())){
				setX(xL);
				collision = true;
			}
		}

		if(deltaY >= 0) { // Se mueve hacia arriba
			if(upleft && upright) {
				setY(yD);
				collision = true;
			} else if(!upleft && game.isFree(this, getX(), yU)){
				setY(yD);
				collision = true;
			} else if(!upright && game.isFree(this, getRight(), yU)){
				setY(yD);
				collision = true;
			}
		} else { //Se mueve hacia abajo
			if(downright && downleft) {
				setY(yD);
				collision = true;
			} else if(!downright && game.isFree(this, getRight(), yD)){
				setY(yD);
				collision = true;
			} else if(!downleft && game.isFree(this, getX(), yD)){
				setY(yD);
				collision = true;
			}
		}
		return collision;
	}
    
    public boolean isCollision(LivingEntity entity) {
    	if(this.equals(entity))
    		return false;
    	//System.out.println((int)entity.getRectangle().x + " x " + (int)entity.getRectangle().y);
    	//System.out.println((int)getRectangle().x + " x " + (int)getRectangle().y + " -> Mi bicho");
    	/*if(bounds.overlaps(entity.getRectangle())){
    		System.out.println(" (" + entity.getCenterX() + " x " + entity.getCenterY() + ") choca con ("
    				+ this.getCenterX() + " x " + this.getCenterY() + ")");
    		return true;
    	}
    	System.out.println("No choca");
    	return false;*/
    	return bounds.overlaps(entity.getRectangle());
    }
    
    @Override
    public void dispose(){
    	super.dispose();
    }

    @Override
	public void draw(SpriteBatch batch, float parentAlpha){
        batch.draw(currentFrame, getX(), getY(), getWidth()/2, getHeight()/2, 
        		getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
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
