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
            
            setBackground("background/gameScreen.png");
            mainpj = new MainCharacter(LivingEntity.Type.PIRKO, "Pirko");
            //enemy = new Enemy(LivingEntity.Type.ENEMY1, "Cosa");
            
            hud = new HudControl(this, true, mainpj, stage);
    }
    
    @Override
    public void render(float delta) {
    	super.render(delta);
    	batch.begin();
    	drawBg();
        mainpj.draw(batch);
        //enemy.draw(batch);
        hud.draw(batch);
        batch.end();
    	
    	draw(delta);
        
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
    public void show() {
        super.show();
    }
    
    @Override
	public void resize(int width, int height) {
    	super.resize(width, height);
	}
    
    @Override
	public void dispose() {
        //super.dispose();
        mainpj.dispose();
        //enemy.dispose();
        hud.dispose();
	}
}
