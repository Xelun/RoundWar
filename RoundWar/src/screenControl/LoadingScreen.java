/*******************************************************************************
 * Copyright (c) 2014
 *
 * @author Elisabet Romero Vaquero
 *******************************************************************************/
package screenControl;

import roundwar.LoadingIcon;
import roundwar.RoundWar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * @author Mats Svensson
 */
public class LoadingScreen extends AbstractScreen {
    private Image screenBg;
    private Actor icon;

    public LoadingScreen() {
        super();
    }

    /**
     * Muestra la pantalla de carga mientras se cargan los elementos.
     */
    @Override
    public void show() {
        // Tell the manager to load assets for the loading screen
        game.manager.load("skin/loading.pack", TextureAtlas.class);
        
        // Wait until they are finished loading
        game.manager.finishLoading();

        // Get our textureatlas from the manager
        TextureAtlas atlas = game.manager.get("skin/loading.pack", TextureAtlas.class);

        // Grab the regions from the atlas and create some images
        screenBg = new Image(atlas.findRegion("screen-bg"));
        screenBg.setSize(w, h);

        // Add the loading bar animation
        Animation anim = new Animation(0.1f, atlas.findRegions("loading-anim") );
        anim.setPlayMode(Animation.LOOP);
        icon = new LoadingIcon(anim);
        icon.setSize(256, 256);
        icon.setPosition(w/2-icon.getWidth()/2, h/2 - icon.getHeight()/2);

        // Add all the actors to the stage
        stage.addActor(screenBg);
        stage.addActor(icon);

        // Add everything to be loaded, for instance:
        loadResources();
    }

    /**
     * Dibuja la pantalla mientras comprueba si ya han terminado de cargarse los elementos.
     */
    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        // Load some, will return true if done loading
        if (game.manager.update())  {
        	game.setScreen(new MenuScreen());
        	RoundWar.startMusic();
        }

        // Show the loading screen
        stage.act();
        stage.draw();
    }

    /**
     * Recursos que se van a cargar mientras está la pantalla de carga.
     */
    private void loadResources() {
    	game.manager.load("sounds/callToAdventure.ogg", Music.class);
    }
    
    /**
     * Devuelve la música de fondo del juego.
     * @return
     */
    public static Music takeMusic() {
    	return game.manager.get("sounds/callToAdventure.ogg", Music.class);
    }
    
    /**
     * Libera memoria para eliminarla pantalla.
     */
    @Override
    public void dispose() {
        // Dispose the loading assets as we no longer need them
        game.manager.unload("skin/loading.pack");
    }
}