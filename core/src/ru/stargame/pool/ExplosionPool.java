package ru.stargame.pool;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.stargame.base.SpritesPool;
import ru.stargame.sprite.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {
    
    private TextureAtlas atlas;
    private Sound explosionSound;
    
    public ExplosionPool(TextureAtlas atlas, Sound explosionSound) {
        this.atlas = atlas;
        this.explosionSound = explosionSound;
    }
    
    @Override
    protected Explosion newObject() {
        return new Explosion(atlas.findRegion("explosion"), 9, 9, 74, explosionSound);
    }
}
