package screenControl;

import roundwar.RoundWar;

public class ScoreScreenControl extends AbstractScreen {

    /**
     * Constructor
     * @param game Main class of the game
     */
    public ScoreScreenControl(RoundWar game) {       
            super(game);
    }
    
    @Override
    public void show() {
        super.show();
		
		//Create background
		setBackground("background/startScreen.png");
    }
}
