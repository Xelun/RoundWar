package screenControl;

import Entities.LivingEntity.Status;
import Entities.MainCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class Controller extends Touchpad{

    private static TouchpadStyle touchpadStyle;
    MainCharacter mainpj;
 
    /**
     * Constructor.
     * @param mainpj
     */
    public Controller(MainCharacter mainpj){
    	super(10, Controller.getTouchPadStyle());
    	this.mainpj = mainpj;
    	
        setBounds(0, 0, Gdx.graphics.getWidth()*0.2f, Gdx.graphics.getWidth()*0.2f);
        
    }
    
    /**
     * Devuelve el estilo del touchpad.
     */
    private static TouchpadStyle getTouchPadStyle(){
        
        touchpadStyle = new TouchpadStyle();
        touchpadStyle.background = AbstractScreen.getSkin().getDrawable("bg-touchpad");
        touchpadStyle.knob = AbstractScreen.getSkin().getDrawable("touch-knob");
        
        return touchpadStyle;
    }
    
    /**
     * Actualiza al controlador.
     */
    @Override
    public void act (float delta) {
    	super.act(delta);
    	if(isTouched()){ // Si se está usando, mover al personaje principal.
    		mainpj.moveEntity(getKnobPercentX(), getKnobPercentY(), true);
    	}  else { // Poner el personaje en estado de espera.
    		mainpj.setStatus(Status.ILDE);
    	}
    		
    }
}