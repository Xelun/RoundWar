package screenControl;

import roundwar.RoundWar;

import Entities.Enemy;
import Entities.Entity;
import Entities.MainCharacter;

import com.badlogic.gdx.Gdx;

public class GameScreenControl extends AbstractScreen {
	MainCharacter mainpj;
	Enemy enemy;
	HudControl hud;
    
    private int h, w;

    public GameScreenControl(RoundWar game) {       
            super(game);
            w = Gdx.graphics.getWidth();
            h = Gdx.graphics.getHeight();
            
            setBackground("background/gameScreen.png");
            mainpj = new MainCharacter(Entity.Type.PIRKO, "Pirko");
            enemy = new Enemy(Entity.Type.ENEMY1, "Cosa");
            
            hud = new HudControl(this, true, mainpj);
    }
    
    @Override
    public void render(float delta) {
    	super.render(delta);
    	
    	mainpj.setPosition(h, w);

        enemy.setPosition(h, w);
        
        if(mainpj.isCollision(enemy)){
        	mainpj.move(w/2, h/2);
        	mainpj.actHealth(-1);
    		hud.actHealthBar(mainpj.getHealth());
        }
        batch.begin();
        
        mainpj.draw(batch);
        enemy.draw(batch);
        hud.draw(batch);
        
        batch.end();
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
        h = height;
        w = width;
	}
    
    @Override
	public void dispose() {
        //super.dispose();
        mainpj.dispose();
        enemy.dispose();
        hud.dispose();
	}
}
