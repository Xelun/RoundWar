package screenControl;

import Entities.Minimal;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;

public class TouchControl {
    private Touchpad touchpad;
    private TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    Minimal mainpj;
 
    public TouchControl(Minimal mainpj, Stage stage) {
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
        
        //Add TouchPad to stage
        stage.addActor(touchpad);
    }
    
    public void dispose() {
    	touchpadSkin.dispose();
    }

    public void act() {        
    	if(touchpad.isTouched())
    		mainpj.move(touchpad.getKnobPercentX(), touchpad.getKnobPercentY());
    }
    
    public void setPosition(float posX, float posY){
    	touchpad.setPosition(posX, posY);
    }
}