package ru.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.stargame.base.Sprite;

public class GameOver extends Sprite {
    
    private static final float HEIGHT = 0.07f;
    private static final float POSITION = 0.2f;
    
    
    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
        setHeightProportion(HEIGHT);
        setBottom(POSITION);
    }
}
