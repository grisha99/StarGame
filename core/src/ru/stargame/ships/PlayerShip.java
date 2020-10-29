package ru.stargame.ships;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.stargame.base.BaseShip;

public class PlayerShip extends BaseShip {

    
    public PlayerShip(TextureAtlas atlas, float startX, float startY, float speed){
        super(atlas.findRegion("main_ship"),startX, startY, speed);
    
    }
    
}
