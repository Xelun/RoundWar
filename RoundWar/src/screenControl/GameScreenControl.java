package screenControl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

import roundwar.RoundWar;
import Entities.LivingEntity;
import Entities.MainCharacter;

public class GameScreenControl extends AbstractScreen {
	MainCharacter mainpj;
	//Enemy enemy;
	HudControl hud;
	TiledMap map;
	OrthogonalTiledMapRenderer renderer; 

    public GameScreenControl(RoundWar game) {       
            super(game);
            
            setTiledMap();
            //setBackground("background/gameScreen.png");
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
        renderer.render();
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
        map.dispose();
        renderer.dispose();
	}
    
    protected void setTiledMap(){
    	map = new TmxMapLoader().load("background/mapa1.tmx");

    	renderer = new OrthogonalTiledMapRenderer(map, 1 / 32);
    	//renderer.getSpriteBatch().disableBlending(); 
        renderer.setView((OrthographicCamera)stage.getCamera());
	    // Add background
		//stage.addActor();
	}
}
