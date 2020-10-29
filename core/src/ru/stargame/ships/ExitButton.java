package ru.stargame.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.stargame.base.BaseButton;
import ru.stargame.math.Rect;

public class ExitButton extends BaseButton {
    private static final float MARGIN = 0.025f;
    
    public ExitButton(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
    }
    
    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
        setRight(worldBounds.getRight() - MARGIN);
        setBottom(worldBounds.getBottom() + MARGIN);
    }
    
    @Override
    public void action() {
        Gdx.app.exit();
    }
}
