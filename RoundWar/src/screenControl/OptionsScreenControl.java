package screenControl;

import roundwar.RoundWar;

public class OptionsScreenControl extends AbstractScreen {

    public OptionsScreenControl(RoundWar game) {       
            super(game);
    }
    
    @Override 
    public void show() {
        super.show();
		
		//Create background
		setBackground("background/startScreen.png");
    }
}
