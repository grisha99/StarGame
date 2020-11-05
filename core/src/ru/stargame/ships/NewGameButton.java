package ru.stargame.ships;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.stargame.base.BaseButton;
import ru.stargame.screen.GameScreen;

public class NewGameButton extends BaseButton {
    
    private static final float HEIGHT = 0.04f;
    private static final float POSITION = -0.2f;
    
    private GameScreen gameScreen;
    
    public NewGameButton(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        setHeightProportion(HEIGHT);
        setBottom(POSITION);
        this.gameScreen = gameScreen;
    }
    
    @Override
    public void action() {
        gameScreen.startNewGame();
    }
}
