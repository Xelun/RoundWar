package screenControl;

import Entities.Minimal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;

public class TouchControl extends Actor {
    private Touchpad touchpad;
    private TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Stage stage;
    Minimal mainpj;
 
    public TouchControl(Minimal mainpj, SpriteBatch batch) {
    	this.mainpj = mainpj;
    	touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture("skin/touchBackground.png"));
        touchpadSkin.add("touchKnob", new Texture("skin/touchKnob.png"));
        
        touchpadStyle = new TouchpadStyle();
        touchpadStyle.background = touchpadSkin.getDrawable("touchBackground");
        touchpadStyle.knob = touchpadSkin.getDrawable("touchKnob");
        
        //Create new TouchPad with the created style
        touchpad = new Touchpad(10, touchpadStyle);
        touchpad.setBounds(0, 0, 128, 128);
        
        //Create a Stage and add TouchPad
        stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, batch);
        stage.addActor(touchpad);
        Gdx.input.setInputProcessor(stage);
    }
    
    public void dispose() {
    	touchpadSkin.dispose();
    }

    public void draw(SpriteBatch batch) {        
    	if(touchpad.isTouched())
    		mainpj.move(touchpad.getKnobPercentX(), touchpad.getKnobPercentY());
    }
    
    public void stageDraw(){
    	 stage.act(Gdx.graphics.getDeltaTime());        
         stage.draw();
    }
}