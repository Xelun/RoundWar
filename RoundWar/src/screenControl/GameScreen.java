package screenControl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import roundwar.RoundWar;
import Attacks.Attack;
import Entities.Enemy;
import Entities.LivingEntity;
import Entities.MainCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class GameScreen extends AbstractScreen {
	private MainCharacter mainpj;
	
	private List<LivingEntity> entities;
	private Hud hud;
	private final Vector2 minLimit, maxLimit;
	public LinkedList<Attack> attacks;
	
	public static final float tileSize = 32f;

    public GameScreen(RoundWar game) {     
            super(game);
            setBackground(this);
            int h = Gdx.graphics.getHeight();
            int w = Gdx.graphics.getWidth();
            mainpj = new MainCharacter(LivingEntity.Type.PIRKO, "Pirko", this);
            attacks = new LinkedList<>();
            Attack.setScreen(this);
            
            minLimit = new Vector2(w*0.15f, h*0.85f);
    		maxLimit = new Vector2(w*0.75f - mainpj.getWidth(), h*0.15f + mainpj.getHeight());
        	
            hud = new Hud(this, true, mainpj);
            mainpj.setStage(stage);
            
            entities = new LinkedList<>();
            entities.add(mainpj);
            entities.add(new Enemy(LivingEntity.Type.ENEMY1, this));
            
            Iterator<LivingEntity> it = entities.iterator();
            //it.next();
            
            while (it.hasNext()) {
            	stage.addActor(it.next());
            }
            
            batch.setProjectionMatrix(stage.getCamera().combined);
    }
    
    @Override
    public void render(float delta) {
    	super.render(delta);
    	drawStage(delta);
    	hud.drawStage(delta);
    	stage.getSpriteBatch().begin();
    	for(Attack attack : attacks) {
    		attack.act(delta);
    		attack.draw(stage.getSpriteBatch());
    	}
    	stage.getSpriteBatch().end();
    }
    
    public Vector2 getMaxLimit(){
    	return stage.screenToStageCoordinates(maxLimit.cpy());
    }
    
    public Vector2 getMinLimit(){
    	return stage.screenToStageCoordinates(minLimit.cpy());
    }
    
    public MainCharacter getCharacter(){
    	return mainpj;
    }
    
    public boolean isFree(LivingEntity entity, float posX, float posY) {
    	boolean free = true;
    	
    	for (LivingEntity ent : entities) {
    		if(!entity.equals(ent) && ent.collides(posX, posY))
    			free = false;
    	}
    	if (!bg.isFree(posX, posY)) {
    		free = false;
    	}
    	return free;
    	//return bg.isFree(posX, posY);
    }
    
    @Override
	public void resize(int width, int height) {
    	super.resize(width, height);
    	hud.resize(width, height);
	}
    
    @Override
	public void dispose() {
        hud.dispose();
        for (LivingEntity entity : entities) {
        	entity.dispose();
        }
        for(Attack attack : attacks) {
    		attack.dispose();
    	}
	}
}
