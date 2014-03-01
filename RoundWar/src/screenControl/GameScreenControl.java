package screenControl;

import roundwar.RoundWar;
import Entities.LivingEntity;
import Entities.MainCharacter;

public class GameScreenControl extends AbstractScreen {
	MainCharacter mainpj;
	
	//Enemy enemy;
	HudControl hud;

    public GameScreenControl(RoundWar game) {       
            super(game);
            
            setBackground(this);
            
            mainpj = new MainCharacter(LivingEntity.Type.PIRKO, "Pirko");
            //enemy = new Enemy(LivingEntity.Type.ENEMY1, "Cosa");
            
            hud = new HudControl(this, true, mainpj, stage.getSpriteBatch());
            mainpj.setStage(stage);
            
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
        //enemy.dispose();
        hud.dispose();
	}
}
