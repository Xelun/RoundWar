package screenControl;

import roundwar.RoundWar;

public class ScoreScreen extends AbstractScreen {

    /**
     * Constructor
     * @param game Main class of the game
     */
    public ScoreScreen(RoundWar game) {       
            super(game);
            setBackground("background/startScreen.png");
    }
}
