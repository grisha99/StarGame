package ru.stargame.ships;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.stargame.base.BaseButton;
import ru.stargame.math.Rect;
import ru.stargame.screen.GameScreen;

public class PlayButton extends BaseButton {
    private static final float MARGIN = 0.025f;
    
    private final Game game;
    
    public PlayButton(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.game = game;
    }
    
    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.25f);
        setLeft(worldBounds.getLeft() + MARGIN);
        setBottom(worldBounds.getBottom() + MARGIN);
    }
    
    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }
}
