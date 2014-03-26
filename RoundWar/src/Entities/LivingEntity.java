package Entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class LivingEntity extends Entity{ 
	public enum Status {ILDE, WALK, DISAPEAR, DAMAGE, ATTACKING}
	
	//Animaciones y sprites
	private static final int FRAME_COLS = 4;
    private static final int FRAME_ROWS = 1;
    protected boolean upleft, upright, downleft, downright;
    protected float delay, maxDelay; // Tiempo en que no puede recibir daño
    
    protected Animation walkAnimation, ildeAnimation, currentAnimation, damageAnimation;
    protected TextureRegion[] walkFrames;
    protected TextureRegion ildeFrame, damageFrame;
    protected ShapeRenderer rectangle;
	
	//Atributos comunes a todos los tipos
    public int maxMp;
    protected float mp;
    public float recoveryMp;
    protected Status status;
    protected int lvl;
    
    //Atributos únicos según el tipo
    public float statAtq, statHp, statVel, statDef;
    protected float health;
    
    //Tipos
    public enum Type {PIRKO, ENEMY1, ENEMY2}
    
    // Constructores
    public LivingEntity(Type type, int lvl){
    	this(type, 0f, 0f, 0f, lvl);
    }
    
    public LivingEntity(Type type, float rotation, float posX, float posY, int lvl) {
    	this.lvl = lvl;
    	status = Status.ILDE;
    	delay = 0;
    	maxDelay = 1;
    	switch (type){
			case PIRKO:
				initializeLivingEntity(60, 1f, "sprite/pirko.png", 1, 1, 30, 1, rotation, posX, posY);
				break;
			case ENEMY1:
				initializeLivingEntity(62, 1f, "sprite/enemy.png", 0.2f, 0, 30, 0.2f, rotation, posX, posY);
				((Enemy)this).setStats(0.4f, 0.1f, 5f, 0.05f, 5);
				break;
			default:
    	}
    	
    	//Animación
    	setAnimations();
    }
    
    private void initializeLivingEntity(float size, float scale, String path,
    		float statAtq, float statDef, float statHp, float statVel,
    		float rotation, float posX, float posY) {
    	initializeEntity(size, scale, path, rotation, posX, posY);
    	this.status = Status.ILDE;
    	this.statAtq = statAtq;
    	this.statDef = statDef;
    	this.statHp = statHp;
    	this.statVel = statVel;
    	this.health = statHp;
    	maxMp = 100;
    	mp = 100;
    	recoveryMp = 0.05f;
    	rectangle = new ShapeRenderer();
    	rectangle.setColor(0f, 1f, 0f, 0f);
    }

    // Movimiento y colisión
    public void moveFree(float deltaX, float deltaY) {
    	bounds.x += deltaX;
		bounds.y += deltaY;
    }
    
    public boolean moveEntity(float deltaX, float deltaY, boolean rotate) {
    	deltaX = deltaX*statVel;
		deltaY = deltaY*statVel;
		if(deltaX == 0 && deltaY == 0) setStatus(Status.ILDE);
		else if (status != Status.WALK) setStatus(Status.WALK);
		ReturnIntEntity returned;
		if(this instanceof Enemy) returned = game.getScene().isFree(this, deltaX, deltaY, ((Enemy)this).countDown);
		else returned = game.getScene().isFree(this, deltaX, deltaY);
		int result = returned.getInt();
		LivingEntity entity = returned.getEntity();
    	boolean free = false;
    	
    	if(result == 0 ) { // Se puede mover en ambos ejes
    		bounds.x += deltaX;
    		bounds.y += deltaY;
    		free = true;
    	} else if(result == 1) { // Se puede mover en el eje x
    		bounds.x += deltaX;
    		free = true;
    	} else if(result == 2) { // Se puede mover en el eje y
    		bounds.y += deltaY;
    		free = true;
    	}
    	
    	if(entity != null && entity instanceof MainCharacter && !(this instanceof MainCharacter)) // Un enemigo golpea al personaje
			entity.receiveDamage(this, 1, 40*deltaX, 40*deltaY);
    	return free;
    }
    
    // Daño y ataques
    public void receiveDamage(LivingEntity entity, float quantity) { // Daño sin retroceso
    	if(delay == 0) {
    		delay = maxDelay;
    		addHealth(quantity-quantity*(1/(statDef*0.2f)));
    		System.out.println(this + " ha recibido " + (int)(quantity-quantity*(1/(statDef*0.2f))) + " y su vida ahora es de: " + health);
	    	if(health <= 0) { // Muerto
	    		System.out.println(this + " muerto");
	    		if(this instanceof Enemy ) {
	    			//((MainCharacter)entity).updateExperience(dead(entity));
	    			dead(entity);
	    			System.out.println("Has matado a un enemigo!");
	    		}
	    	}
    	}
    }
    
    public void receiveDamage(LivingEntity entity, float quantity, float deltaX, float deltaY) { // Daño con retroceso
    	if(delay == 0) {
	    	receiveDamage(entity, quantity);
	    	if(health > 0) {
	    		moveEntity(deltaX, deltaY, false);
	    	}
    	}
    }
    
    public boolean collides(float posX, float posY) {
    	return bounds.contains(posX, posY);
    }
    
	public boolean canAttack(float value) {
		if(mp + value < 0) return false;
		return true;
	}

    // Métodos get, set y add
	public void addStatAtq(int quantity) {
		statAtq += quantity;
	}
	
	public void addStatDef(int quantity) {
		statDef += quantity;
	}
	
	public void addStatHp(int quantity) {
		statHp  += quantity*2;
	}
	
	public void addStatVel(int quantity) {
		statVel += quantity*0.1f;
	}
	
    public float getHealth(){
    	return health;
    }
    
    public void setHealth(int health) {
    	this.health = (health >= statHp) ? statHp : health;
    }

    public void addHealth(float update){
		health += update;
		if(health < 0) health = 0;
		else if (health > statHp) health = statHp;
	}
    
    public float getMp() {
    	return mp;
    }
  
    public void setMp(int mp) {
		this.mp = (mp >= maxMp) ? maxMp : mp;
	}
	
	public void addMp(float update){
		mp += update;
		if(mp < 0) mp = 0;
		else if (mp > maxMp) mp = maxMp;
	}
	
	public void setStatus(Status status){
    	if (this.status != status){
	    	this.status = status;
	    	switch(this.status){
	    		case WALK:
	    			currentAnimation = walkAnimation;
	    			break;
	    		case DAMAGE:
	    			currentAnimation = damageAnimation;
	    		default:
	    			currentAnimation = ildeAnimation;
	    			break;
	    	}
    	}
    }
	
	public int getLevel() {
		return lvl;
	}
	
	public void setLevel(int lvl) {
    	this.lvl = lvl;
    }
	
	public void addLevel(int quantity) {
    	this.lvl += quantity;
    }
	
	private void setAnimations(){
    	TextureRegion[][] tmp = TextureRegion.split(entityTexture, entityTexture.getWidth() / 
    			FRAME_COLS, entityTexture.getHeight() / FRAME_ROWS);
    	walkFrames = new TextureRegion[FRAME_COLS];
    	ildeFrame = tmp[0][1];
    	damageFrame = tmp [0][1];
    	
        for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[j] = tmp[0][j];
        }
    	
    	walkAnimation = new Animation(0.4f, walkFrames);
    	ildeAnimation = new Animation(2f, ildeFrame);
    	damageAnimation = new Animation(1f, damageFrame);
    	currentAnimation = ildeAnimation;
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
	
	//Métodos act, dead y dispose
    @Override
    public void act(float delta){
    	if(delay > 0) { 
    		setStatus(Status.DAMAGE);
    		delay -= delta;
    	}
    	if(delay < 0) {
    		setStatus(Status.ILDE);
    		delay = 0;
    	}
    	if(mp < maxMp){
    		addMp(1*recoveryMp);
    	}
    	stateTime += delta;
    	currentFrame = currentAnimation.getKeyFrame(stateTime, true);
    }
    
    @Override
	public void draw(SpriteBatch batch, float parentAlpha){
        batch.draw(currentFrame, bounds.x, bounds.y, bounds.getWidth()/2, bounds.getHeight()/2, 
        		bounds.getWidth(), bounds.getHeight(), 1, 1, getRotation());
    }
    
    public void dead(LivingEntity killer) {
    	dispose();
    }
    
    @Override
    public void dispose(){
    	super.dispose();
    }
}
