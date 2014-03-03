package screenControl;

import java.util.ArrayList;

import roundwar.RoundWar;
import Entities.Enemy;
import Entities.LivingEntity;
import Entities.MainCharacter;

public class GameScreenControl extends AbstractScreen {
	MainCharacter mainpj;
	
	ArrayList<Enemy> enemies;
	HudControl hud;

    public GameScreenControl(RoundWar game) {       
            super(game);
            setBackground(this);
            
            mainpj = new MainCharacter(LivingEntity.Type.PIRKO, "Pirko");
            
            hud = new HudControl(this, true, mainpj, stage.getSpriteBatch());
            mainpj.setStage(stage);
            
            enemies = new ArrayList<Enemy>();
            enemies.add(new Enemy(LivingEntity.Type.ENEMY1, "Cosa"));
            
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
    	
        /*if(mainpj.isCollision(enemy)){
        	mainpj.move(w/2, h/2);
        	mainpj.actHealth(-1);
        	mainpj.actMana(-2);
    		hud.actHealthBar(mainpj.getHealth());
    		hud.actManaBar(mainpj.getMp());
        }*/
    }
    
    public MainCharacter getCharacter(){
    	return mainpj;
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
