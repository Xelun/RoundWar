package screenControl;

import java.util.ArrayList;

import roundwar.RoundWar;
import Entities.Enemy;
import Entities.LivingEntity;
import Entities.MainCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class GameScreen extends AbstractScreen {
	private MainCharacter mainpj;
	
	private ArrayList<Enemy> enemies;
	private Hud hud;
	
	public static final float tileSize = 32f;

    public GameScreen(RoundWar game) {     
            super(game);
            setBackground(this);
            
            mainpj = new MainCharacter(LivingEntity.Type.PIRKO, "Pirko", this);
            
            hud = new Hud(this, true, mainpj, stage.getSpriteBatch());
            mainpj.setStage(stage);
            
            //enemies = new ArrayList<Enemy>();
            //enemies.add(new Enemy(LivingEntity.Type.ENEMY1, "Cosa", this));
            
            /*for (int i = 0; i < enemies.size(); i++) {
            	stage.addActor(enemies.get(i));
            }*/
            
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
    
    public boolean isFree(float posX, float posY) {
    	return bg.isFree(posX, posY);
    }
    
    public Background.Collision isCollision(float posX, float posY, LivingEntity entity) {
    	return bg.isCollision(entity, stage.screenToStageCoordinates(new Vector2(posX, Gdx.graphics.getHeight()-posY)));
    }
    
    @Override
	public void resize(int width, int height) {
    	super.resize(width, height);
    	hud.resize(width, height);
	}
    
    @Override
	public void dispose() {
        mainpj.dispose();
        hud.dispose();
        /*for (int i = 0; i < enemies.size(); i++) {
        	enemies.get(i).dispose();
        }*/
	}
}
