package screenControl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import roundwar.RoundWar;
import Entities.Enemy;
import Entities.LivingEntity;
import Entities.MainCharacter;

public class GameScreen extends AbstractScreen {
	private MainCharacter mainpj;
	
	private List<LivingEntity> entities;
	private Hud hud;
	
	public static final float tileSize = 32f;

    public GameScreen(RoundWar game) {     
            super(game);
            setBackground(this);
            
            mainpj = new MainCharacter(LivingEntity.Type.PIRKO, "Pirko", this);
            
            hud = new Hud(this, true, mainpj);
            mainpj.setStage(stage);
            
            entities = new LinkedList<>();
            entities.add(mainpj);
            entities.add(new Enemy(LivingEntity.Type.ENEMY1, this));
            
            Iterator<LivingEntity> it = entities.iterator();
            it.next();
            
            while (it.hasNext()) {
            	stage.addActor(it.next());
            	//System.out.println(stage);
            }
            
            batch.setProjectionMatrix(stage.getCamera().combined);
    }
    
    @Override
    public void render(float delta) {
    	super.render(delta);
        
    	drawStage(delta);
    	hud.drawStage(delta);
    }
    
    public MainCharacter getCharacter(){
    	return mainpj;
    }
    
    public boolean isFree(LivingEntity entity, float posX, float posY) {
    	boolean collision = true;
    	for (LivingEntity ent : entities) {
    		if(entity.isCollision(ent))
    			collision = false;
    	}
    	if (!bg.isFree(posX, posY)) {
    		collision = false;
    	}
    	return collision;
    }
    
    /*public Background.Collision isCollision(float posX, float posY, LivingEntity entity) {
    	return bg.isCollision(entity, stage.screenToStageCoordinates(new Vector2(posX, Gdx.graphics.getHeight()-posY)));
    }*/
    
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
	}
}
