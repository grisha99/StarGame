package ru.stargame.ships;

import com.badlogic.gdx.graphics.Texture;

import ru.stargame.base.BaseShip;
import ru.stargame.math.Rect;

public class PlayerShip extends BaseShip {
//    public PlayerShip(String texturePath, float startX, float startY, float speed) {
//        super(texturePath, startX, startY, speed);
//    }
    public PlayerShip(Texture texture, float startX, float startY, float speed) {
        super(texture, startX, startY, speed);
    }
    
//    @Override
//    public void resize(Rect worldBounds) {
//        setHeightProportion(worldBounds.getHeight());
//        pos.set(worldBounds.pos);
//    }
}
