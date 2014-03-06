package screenControl;

import java.util.ArrayList;

import roundwar.RoundWar;
import Entities.Enemy;
import Entities.LivingEntity;
import Entities.MainCharacter;

public class GameScreen extends AbstractScreen {
	MainCharacter mainpj;
	
	ArrayList<Enemy> enemies;
	Hud hud;

    public GameScreen(RoundWar game) {     
            super(game);
            setBackground(this);
            
            mainpj = new MainCharacter(LivingEntity.Type.PIRKO, "Pirko", this);
            
            hud = new Hud(this, true, mainpj, stage.getSpriteBatch());
            mainpj.setStage(stage);
            
            enemies = new ArrayList<Enemy>();
            enemies.add(new Enemy(LivingEntity.Type.ENEMY1, "Cosa", this));
            
            for (int i = 0; i < enemies.size(); i++) {
            	stage.addActor(enemies.get(i));
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
    
    public boolean isCollision(float posX, float posY) {
    	return bg.isCollision(posX, posY);
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
        for (int i = 0; i < enemies.size(); i++) {
        	enemies.get(i).dispose();
        }
	}
}
