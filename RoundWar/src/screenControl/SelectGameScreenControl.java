package screenControl;

import roundwar.RoundWar;

public class SelectGameScreenControl extends AbstractScreen {

    public SelectGameScreenControl(RoundWar game) {       
            super(game);
    }
    
    @Override
    public void show() {
        super.show();
		
		//Create background
		setBackground("background/startScreen.png");
    }
}